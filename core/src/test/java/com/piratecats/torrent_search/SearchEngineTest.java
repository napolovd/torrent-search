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

import com.piratecats.torrent_search.model.SearchResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class SearchEngineTest {

    private SearchEngine engine;

    @Before
    public void before() {
        engine = new SearchEngine();
    }

    @After
    public void after() {
        engine.shutdown();
        engine = null;
    }

    @Test
    public void search() throws ExecutionException, InterruptedException {
        final Future<Collection<SearchResult>> search = engine.search("How i met", results -> System.out.println("results = " + results));

        search.get();
    }
}