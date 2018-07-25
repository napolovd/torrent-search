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

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nullable;
import java.util.List;

public class SearchResponse {
    @Nullable
    @SerializedName("torrent_results")
    private final List<TorrentResult> torrentResults;

    public SearchResponse(List<TorrentResult> torrentResults) {
        this.torrentResults = torrentResults;
    }

    @Nullable
    List<TorrentResult> getTorrentResults() {
        return torrentResults;
    }

    static class TorrentResult {
        @SerializedName("title")
        private final String title;
        @SerializedName("category")
        private final String category;
        @SerializedName("download")
        private final String magnet;
        @SerializedName("seeders")
        private final int seeders;
        @SerializedName("leechers")
        private final int leechers;
        @SerializedName("size")
        private final long size;
        @SerializedName("info_page")
        private final String page;

        public TorrentResult(String title, String category, String magnet, int seeders, int leechers, long size, String page) {
            this.title = title;
            this.category = category;
            this.magnet = magnet;
            this.seeders = seeders;
            this.leechers = leechers;
            this.size = size;
            this.page = page;
        }

        String getTitle() {
            return title;
        }

        String getCategory() {
            return category;
        }

        String getMagnet() {
            return magnet;
        }

        int getSeeders() {
            return seeders;
        }

        int getLeechers() {
            return leechers;
        }

        long getSize() {
            return size;
        }

        String getPage() {
            return page;
        }
    }
}
