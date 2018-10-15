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

package com.piratecats.torrent_search.adapters.An1337x;

import com.google.common.collect.ImmutableList;
import com.piratecats.torrent_search.adapters.SearchAdapter;
import com.piratecats.torrent_search.model.ResultCallback;
import com.piratecats.torrent_search.model.SearchResult;
import com.piratecats.torrent_search.model.category.Category;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Collection;

import static com.google.common.primitives.Ints.tryParse;

public class An1337xAdapter implements SearchAdapter {
    private static final int KILO_1000 = 1000;
    private static final int KILO_1024 = 1024;
    private static final String ROOT = "https://1337x.to";

    @Override
    public Collection<SearchResult> search(String searchString, @Nullable ResultCallback callback) throws IOException {
        final int page = 1;
        final String url = ROOT + "/search/" + searchString + "/" + page + "/";

        Document doc = Jsoup.connect(url).get();

        final Elements rows = doc.select("div.table-list-wrap > table > tbody > tr");

        final ImmutableList.Builder<SearchResult> results = ImmutableList.builderWithExpectedSize(rows.size());

        for (Element element : rows) {
            //TODO final Elements categoriesElements = element.select("td.verth").select("a[href^=/browse/]");

            //TODO final Set<Category> categories = collectCategories(categoriesElements);

            final Elements titleReference = element.select(".coll-1 > a:nth-child(2)");
            final String trackerUrl = ROOT + titleReference.attr("href");
            final String name = titleReference.text();

            final String magnet = obtainMagnet(trackerUrl);

            final String seeders = element.select(".coll-2").text();
            final String leechers = element.select(".coll-3").text();

            final String sizeText = element.select(".coll-4").first().childNode(0).toString();

            long size = getSize(sizeText);

            final SearchResult searchResult = SearchResult.of(name, ImmutableList.<Category>of(), "1337x", trackerUrl, magnet, size, tryParse(seeders), tryParse(leechers));

            if (callback != null) {
                callback.apply(searchResult);
            }
            results.add(searchResult);
        }

        return results.build();
    }

    private String obtainMagnet(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();

        return doc.select(".btn-wrap-list > li:nth-child(1) > a").attr("href");
    }

    private long getSize(String sizeText) {


        if (sizeText == null || sizeText.length() < 3) {
            return 0;
        }

        final String units = sizeText.substring(Math.max(0, sizeText.length() - 2));
        final long scale;
        switch (units) {
            case "KB":
                scale = KILO_1000;
                break;
            case "MB":
                scale = KILO_1000 * KILO_1000;
                break;
            case "GB":
                scale = KILO_1000 * KILO_1000 * KILO_1000;
                break;
            case "Kb":
                scale = KILO_1024;
                break;
            case "Mb":
                scale = KILO_1024 * KILO_1024;
                break;
            case "Gb":
                scale = KILO_1024 * KILO_1024 * KILO_1024;
                break;
            default:
                scale = 1;
        }

        double size = Double.valueOf(sizeText.substring(0, sizeText.length() - 2).trim());

        return (long) (size * scale);
    }
}
