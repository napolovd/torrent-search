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

package com.piratecats.torrent_search.model;

import com.piratecats.torrent_search.model.category.Category;

import java.util.Collection;

public final class SearchResult {
    private final String name;
    private final Collection<Category> categories;
    private final String trackerName;
    private final String trackerUrl;
    private final String magnetUrl;
    private final long size;
    private final int seeders;
    private final int leechers;

    private SearchResult(String name, Collection<Category> categories, String trackerName, String trackerUrl, String magnetUrl, long size, int seeders, int leechers) {
        this.name = name;
        this.categories = categories;
        this.trackerName = trackerName;
        this.trackerUrl = trackerUrl;
        this.magnetUrl = magnetUrl;
        this.size = size;
        this.seeders = seeders;
        this.leechers = leechers;
    }

    public static SearchResult of(String name, Collection<Category> categories, String trackerName, String trackerUrl, String magnetUrl, long size, int seeders, int leechers) {
        return new SearchResult(name, categories, trackerName, trackerUrl, magnetUrl, size, seeders, leechers);
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "name='" + name + '\'' +
                ", categories=" + categories +
                ", trackerName='" + trackerName + '\'' +
                ", trackerUrl='" + trackerUrl + '\'' +
                ", magnetUrl='" + magnetUrl + '\'' +
                ", size=" + size +
                ", seeders=" + seeders +
                ", leechers=" + leechers +
                '}';
    }
}
