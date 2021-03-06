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

package com.piratecats.torrent_search.adapters;

import com.piratecats.torrent_search.adapters.yts.YtsAdapter;
import com.piratecats.torrent_search.model.SearchResult;
import org.junit.Test;

import java.io.IOException;
import java.util.Collection;

import static org.junit.Assert.assertFalse;

public class YtsAdapterTest {
    @Test
    public void test() throws IOException {
        final YtsAdapter ytsAdapter = new YtsAdapter();
        final Collection<SearchResult> searchResults = ytsAdapter.search("blade runner", (r) -> System.out.println("result = " + r));
        for (SearchResult searchResult : searchResults) {
            System.out.println("searchResult = " + searchResult);
        }
        assertFalse(searchResults.isEmpty());
    }
}
