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
