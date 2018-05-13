package com.piratecats.torrent_search.adapters.rarbg;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

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

    @Data
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
    }
}
