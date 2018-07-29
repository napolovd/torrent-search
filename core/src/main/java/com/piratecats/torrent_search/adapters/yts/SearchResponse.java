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

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {
    @SerializedName("status")
    private final String status;

    @SerializedName("data")
    private final Data data;

    public SearchResponse(String status, Data data) {
        this.status = status;
        this.data = data;
    }

    String getStatus() {
        return status;
    }

    Data getData() {
        return data;
    }

    static class Data {
        @SerializedName("movies")
        private final List<Movie> movies;

        private Data(List<Movie> movies) {
            this.movies = movies;
        }

        List<Movie> getMovies() {
            return movies;
        }
    }

    static class Movie {

        @SerializedName("title")
        private final String title;
        @SerializedName("url")
        private final String url;
        @SerializedName("torrents")
        private final List<Torrent> torrents;

        private Movie(String title, String url, List<Torrent> torrents) {
            this.title = title;
            this.url = url;
            this.torrents = torrents;
        }

        String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }

        List<Torrent> getTorrents() {
            return torrents;
        }
    }

    static class Torrent {
        @SerializedName("url")
        private final String trackerUrl;
        @SerializedName("hash")
        private final String hash;
        @SerializedName("quality")
        private final String category;
        @SerializedName("seeds")
        private final int seeds;
        @SerializedName("peers")
        private final int peers;
        @SerializedName("size_bytes")
        private final long size;

        public Torrent(String trackerUrl, String hash, String category, int seeds, int peers, long size) {
            this.trackerUrl = trackerUrl;
            this.hash = hash;
            this.category = category;
            this.seeds = seeds;
            this.peers = peers;
            this.size = size;
        }

        String getTrackerUrl() {
            return trackerUrl;
        }

        String getHash() {
            return hash;
        }

        String getCategory() {
            return category;
        }

        int getSeeds() {
            return seeds;
        }

        int getPeers() {
            return peers;
        }

        long getSize() {
            return size;
        }
    }
}
