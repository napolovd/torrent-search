package com.piratecats.torrent_search.adapters;

import com.piratecats.torrent_search.adapters.rarbg.RarbgAdapter;
import com.piratecats.torrent_search.model.SearchResult;
import org.junit.Test;

import java.io.IOException;
import java.util.Collection;

public class RarbgAdapterTest {
    @Test
    public void test() throws IOException, InterruptedException {
        final RarbgAdapter rarbgAdapter = new RarbgAdapter();
        final Collection<SearchResult> howIMetYourMother = rarbgAdapter.search("how i met your mother s05");
        for (SearchResult searchResult : howIMetYourMother) {
            System.out.println("searchResult = " + searchResult);
        }
        final Collection<SearchResult> howIMetYourMother2 = rarbgAdapter.search("how i met your mother s06");
        for (SearchResult searchResult : howIMetYourMother2) {
            System.out.println("searchResult = " + searchResult);
        }
    }
}
