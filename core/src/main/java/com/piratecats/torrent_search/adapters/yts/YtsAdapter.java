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

package com.piratecats.torrent_search.adapters.yts;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.piratecats.torrent_search.adapters.SearchAdapter;
import com.piratecats.torrent_search.model.ResultCallback;
import com.piratecats.torrent_search.model.SearchResult;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Collection;

public class YtsAdapter implements SearchAdapter {
    private static final String QUERY_TERM = "query_term";

    private HttpUrl url = HttpUrl.parse("https://yts.am/api/v2/list_movies.json");

    @Override
    public Collection<SearchResult> search(String searchString, @Nullable ResultCallback callback) throws IOException {
        OkHttpClient client = new OkHttpClient();
        final Gson gson = new Gson();

        final HttpUrl searchUrl = url.newBuilder()
                .addQueryParameter(QUERY_TERM, searchString)
                .build();
        Request searchRequest = new Request.Builder()
                .url(searchUrl)
                .build();
        try (Response response = client.newCall(searchRequest).execute()) {
            if (!response.isSuccessful()) {
                return ImmutableList.of();
            }
            final String string = response.body().string();
            SearchResponse searchResponse = gson.fromJson(string, SearchResponse.class);
            final Collection<SearchResult> searchResults = convert(searchResponse);

            if (callback != null) {
                searchResults.forEach(callback::apply);
            }

            return searchResults;
        }
    }

    private Collection<SearchResult> convert(SearchResponse searchResponse) {

        final ImmutableList.Builder<SearchResult> builder = ImmutableList.builder();

        if ("ok".equals(searchResponse.getStatus())) {
            if (searchResponse.getData().getMovies() != null) {
                for (SearchResponse.Movie movie : searchResponse.getData().getMovies()) {
                    if (movie.getTorrents() != null) {
                        for (SearchResponse.Torrent torrent : movie.getTorrents()) {
                            builder.add(SearchResult.of(
                                    movie.getTitle() + " " + torrent.getCategory()
                                    , ImmutableList.of(CategoryMapper.getByName(torrent.getCategory()))
                                    , "YTS"
                                    , movie.getUrl()
                                    , createMagnet(torrent.getHash())
                                    , torrent.getSize()
                                    , torrent.getSeeds()
                                    , torrent.getPeers()
                                    )
                            );
                        }
                    }
                }
            }
        }

        return builder.build();
    }

    private String createMagnet(String hash) {
        final String template = "magnet:?xt=urn:btih:%s&dn=Url+Encoded+Movie+Name";

        return String.format(template, hash) +
                "&tr=udp://open.demonii.com:1337/announce" +
                "&tr=udp://tracker.openbittorrent.com:80" +
                "&tr=udp://tracker.coppersurfer.tk:6969" +
                "&tr=udp://glotorrents.pw:6969/announce" +
                "&tr=udp://tracker.opentrackr.org:1337/announce" +
                "&tr=udp://torrent.gresille.org:80/announce" +
                "&tr=udp://p4p.arenabg.com:1337" +
                "&tr=udp://tracker.leechers-paradise.org:6969";
    }
}
