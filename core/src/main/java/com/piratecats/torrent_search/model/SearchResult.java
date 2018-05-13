package com.piratecats.torrent_search.model;

import com.piratecats.torrent_search.model.category.Category;
import lombok.Data;

import java.util.Collection;

@Data(staticConstructor="of")
public class SearchResult {
    private final String name;
    private final Collection<Category> categories;
    private final String trackerName;
    private final String trackerUrl;
    private final String magnetUrl;
    private final long size;
    private final int seeders;
    private final int leechers;
}
