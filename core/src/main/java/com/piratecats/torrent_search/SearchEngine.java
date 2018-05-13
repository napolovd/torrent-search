package com.piratecats.torrent_search;

import com.google.common.collect.ImmutableList;
import com.piratecats.torrent_search.adapters.rarbg.RarbgAdapter;
import com.piratecats.torrent_search.adapters.SearchAdapter;
import com.piratecats.torrent_search.adapters.tpb.TpbAdapter;
import com.piratecats.torrent_search.model.ResultCallback;
import com.piratecats.torrent_search.model.SearchResult;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SearchEngine {
    private final Collection<SearchAdapter> adapters;
    private final ExecutorService requestExecutor;

    public SearchEngine() {
        adapters = ImmutableList.of(
                new RarbgAdapter()
                , new TpbAdapter()
        );

        requestExecutor = Executors.newCachedThreadPool();
    }

    public CompletableFuture<?> search(String searchString, ResultCallback callback) {
        return CompletableFuture.allOf(
                adapters.stream().map(searchAdapter -> CompletableFuture.runAsync(() -> {
                    try {
                        final Collection<SearchResult> searchResults = searchAdapter.search(searchString);
                        callback.apply(searchResults);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }, requestExecutor)).toArray(size -> new CompletableFuture[size])
        );
    }

    public void shutdown() {
        requestExecutor.shutdown();
        requestExecutor.shutdownNow();
    }
}
