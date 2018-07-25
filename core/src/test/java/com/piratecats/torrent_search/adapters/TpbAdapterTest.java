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

import com.piratecats.torrent_search.adapters.tpb.TpbAdapter;
import com.piratecats.torrent_search.model.SearchResult;
import org.junit.Test;

import java.io.IOException;
import java.util.Collection;

import static org.junit.Assert.assertFalse;

public class TpbAdapterTest {

    @Test
    public void test1() throws IOException {
        final TpbAdapter tpbAdapter = new TpbAdapter();
        final Collection<SearchResult> searchResults = tpbAdapter.search("Waterworld 1995", (r) -> System.out.println("result = " + r));
        assertFalse(searchResults.isEmpty());
    }

}