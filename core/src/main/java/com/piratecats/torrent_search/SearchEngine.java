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
import com.piratecats.torrent_search.adapters.An1337x.An1337xAdapter;
import com.piratecats.torrent_search.adapters.SearchAdapter;
import com.piratecats.torrent_search.adapters.rarbg.RarbgAdapter;
import com.piratecats.torrent_search.adapters.tpb.TpbAdapter;
import com.piratecats.torrent_search.adapters.yts.YtsAdapter;
import com.piratecats.torrent_search.model.ResultCallback;
import com.piratecats.torrent_search.model.SearchResult;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class SearchEngine {
    private final Collection<SearchAdapter> adapters;
    private final ExecutorService requestExecutor;

    public SearchEngine() {
        adapters = ImmutableList.of(
                new RarbgAdapter()
                , new TpbAdapter()
                , new An1337xAdapter()
                , new YtsAdapter()
        );

        requestExecutor = Executors.newCachedThreadPool();
    }

    public Future<Collection<SearchResult>> search(final String searchString, @Nullable final ResultCallback callback) {
        return requestExecutor.submit(new Callable<Collection<SearchResult>>() {
            @Override
            public Collection<SearchResult> call() {
                List<SearchResult> searchResultList = new ArrayList<>();
                try {
                    List<Callable<Collection<SearchResult>>> callables = createCallables(searchString, callback);

                    List<Future<Collection<SearchResult>>> futures = executeCallables(callables);

                    for (Future<Collection<SearchResult>> future : futures) {
                        try {
                            Collection<SearchResult> searchResults = future.get();
                            searchResultList.addAll(searchResults);
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return searchResultList;
            }
        });
    }

    private List<Future<Collection<SearchResult>>> executeCallables(List<Callable<Collection<SearchResult>>> callables) {
        List<Future<Collection<SearchResult>>> result = new ArrayList<>(callables.size());
        for (Callable<Collection<SearchResult>> callable : callables) {
            result.add(requestExecutor.submit(callable));
        }
        return result;
    }

    private List<Callable<Collection<SearchResult>>> createCallables(final String searchString, final ResultCallback callback) {
        List<Callable<Collection<SearchResult>>> result = new ArrayList<>(adapters.size());
        for (final SearchAdapter adapter : adapters) {
            result.add(new Callable<Collection<SearchResult>>() {
                @Override
                public Collection<SearchResult> call() {
                    try {
                        return adapter.search(searchString, callback);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    return Collections.emptyList();
                }
            });
        }
        return result;
    }

    public void shutdown() {
        requestExecutor.shutdown();
        requestExecutor.shutdownNow();
    }
}
