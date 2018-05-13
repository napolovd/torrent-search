package com.piratecats.torrent_search.model;

import com.piratecats.torrent_search.model.SearchResult;

import java.util.Collection;

public interface ResultCallback {
    void apply(Collection<SearchResult> results);
}
