package com.piratecats.torrent_search;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

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
        final CompletableFuture<?> search = engine.search("How i met", results -> {
            System.out.println("results = " + results);
        });

        search.get();
    }
}