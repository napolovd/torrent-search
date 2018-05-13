package com.piratecats.torrent_search.adapters;

import com.piratecats.torrent_search.model.SearchResult;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.Future;

public interface SearchAdapter {
    Collection<SearchResult> search(String searchString) throws IOException, InterruptedException;
}
