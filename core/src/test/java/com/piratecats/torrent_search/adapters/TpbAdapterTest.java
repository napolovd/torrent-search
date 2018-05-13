package com.piratecats.torrent_search.adapters;

import com.piratecats.torrent_search.adapters.tpb.TpbAdapter;
import com.piratecats.torrent_search.model.SearchResult;
import org.junit.Test;

import java.io.IOException;
import java.util.Collection;

import static org.junit.Assert.*;

public class TpbAdapterTest {

    @Test
    public void test1() throws IOException {
        final TpbAdapter tpbAdapter = new TpbAdapter();
        final Collection<SearchResult> searchResults = tpbAdapter.search("Waterworld 1995");
        assertFalse(searchResults.isEmpty());
    }

}