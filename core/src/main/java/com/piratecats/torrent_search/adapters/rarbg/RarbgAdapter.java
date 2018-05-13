/*
 * Copyright 2018 Dmitrii Napolov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.piratecats.torrent_search.adapters.rarbg;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.piratecats.torrent_search.adapters.SearchAdapter;
import com.piratecats.torrent_search.model.SearchResult;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class RarbgAdapter implements SearchAdapter {
    private static final String APP_ID_VALUE = "cattorrent_dev";
    private static final String MODE = "mode";
    private static final String APP_ID = "app_id";
    private static final String SEARCH_STRING = "search_string";
    private static final String TOKEN = "token";
    private static final String FORMAT = "format";
    private static final String JSON_EXTENDED = "json_extended";
    private static final String SEARCH = "search";
    private static final String GET_TOKEN = "get_token";

    private Token token = new Token();
    private HttpUrl url = HttpUrl.parse("https://torrentapi.org/pubapi_v2.php");

    @Override
    public Collection<SearchResult> search(String searchString) throws IOException, InterruptedException {
        OkHttpClient client = new OkHttpClient();
        final Gson gson = new Gson();
        String token = getToken(gson, client);

        if(token != null) {
            final HttpUrl searchUrl = url.newBuilder()
                    .addQueryParameter(MODE, SEARCH)
                    .addQueryParameter(APP_ID, APP_ID_VALUE)
                    .addQueryParameter(SEARCH_STRING, searchString)
                    .addQueryParameter(TOKEN, token)
                    .addQueryParameter(FORMAT, JSON_EXTENDED)
                    .build();
            Request searchRequest = new Request.Builder()
                    .url(searchUrl)
                    .build();
            try (Response response = client.newCall(searchRequest).execute()) {
                if(!response.isSuccessful()) {
                    return ImmutableList.of();
                }
                final String string = response.body().string();
                SearchResponse searchResponse = gson.fromJson(string, SearchResponse.class);
                return convert(searchResponse);
            } finally {
                System.out.println("Sleeping for 1010ms");
                Thread.sleep(1010);
            }
        }
        return null;
    }

    private String getToken(Gson gson, OkHttpClient client) throws IOException, InterruptedException {
        final String token = this.token.getToken();
        if(token != null) {
            return token;
        }
        final HttpUrl httpUrl = url.newBuilder()
                .addQueryParameter(APP_ID, APP_ID_VALUE)
                .addQueryParameter(GET_TOKEN, GET_TOKEN)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .build();

        TokenResponse tokenResponse;

        try (Response response = client.newCall(request).execute()) {
            if(!response.isSuccessful()) {
                throw new IOException(String.format("Unable to get token. Response HTTP code %d", response.code()));
            }
            tokenResponse = gson.fromJson(response.body().string(), TokenResponse.class);
        }

        this.token.updateToken(tokenResponse.getToken());

        System.out.println("Sleeping for 1010ms");
        Thread.sleep(1010); // RARBG has limit to one request per second
        return tokenResponse.getToken();
    }

    private Collection<SearchResult> convert(SearchResponse searchResponse) {
        final List<SearchResponse.TorrentResult> torrentResults = searchResponse.getTorrentResults();
        if (torrentResults == null) {
            return ImmutableList.of();
        }
        return torrentResults
                .stream()
                .map(torrentResult -> SearchResult.of(
                        torrentResult.getTitle()
                        , ImmutableList.of(CategoryMapper.getByName(torrentResult.getCategory()))
                        , "RARBG"
                        , torrentResult.getPage()
                        , torrentResult.getMagnet()
                        , torrentResult.getSize()
                        , torrentResult.getSeeders()
                        , torrentResult.getLeechers()
                        )
                )
                .collect(Collectors.toList());
    }

    private static class Token {
        private final long TIMEOUT = TimeUnit.MINUTES.toMillis(15);
        private String token;
        private long milis;

        synchronized void updateToken(String token) {
            this.token = token;
            this.milis = System.currentTimeMillis();
        }

        synchronized String getToken() {
            if(milis > 0 && milis + TIMEOUT > System.currentTimeMillis()) {
                return token;
            } else {
                return null;
            }
        }
    }
}
