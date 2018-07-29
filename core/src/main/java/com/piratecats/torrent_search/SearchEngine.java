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
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

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

    public Future<Collection<SearchResult>> search(String searchString, @Nullable ResultCallback callback) {
        return requestExecutor.submit(() ->
                adapters.parallelStream()
                        .map(adapter -> {
                            try {
                                return adapter.search(searchString, callback);
                            } catch (IOException | InterruptedException e) {
                                e.printStackTrace();
                            }
                            return ImmutableList.<SearchResult>of();
                        })
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList())
        );
    }

    public void shutdown() {
        requestExecutor.shutdown();
        requestExecutor.shutdownNow();
    }
}
