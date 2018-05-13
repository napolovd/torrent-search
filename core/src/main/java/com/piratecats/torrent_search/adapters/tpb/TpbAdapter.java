package com.piratecats.torrent_search.adapters.tpb;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.piratecats.torrent_search.adapters.SearchAdapter;
import com.piratecats.torrent_search.model.SearchResult;
import com.piratecats.torrent_search.model.category.Category;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class TpbAdapter implements SearchAdapter {
    private String root = "https://thepiratebay.org";

    @Override
    public Collection<SearchResult> search(String searchString) throws IOException {

        final String url = root + "/search/" + searchString + "/0/99/0";

        Document doc = Jsoup.connect(url).get();

        final Elements rows = doc.select("#searchResult tbody tr");

        final ImmutableList.Builder<SearchResult> results = ImmutableList.builderWithExpectedSize(rows.size());

        for (Element element : rows) {
            final Elements categoriesElements = element.select("td.verth").select("a[href^=/browse/]");

            final Set<Category> categories = collectCategories(categoriesElements);

            final Elements titleReference = element.select("div.detname a.detlink");
            final String trackerUrl = root + titleReference.attr("href");
            final String name = titleReference.text();

            final String magnet = element.select("a[href^=magnet:?xt]").attr("href");

            final String seeders = element.select("td").eq(2).text();
            final String leechers = element.select("td").eq(3).text();

            final String description = element.select("font.detDesc").text();

            long size = getSizeFromDescription(description);

            results.add(SearchResult.of(name, categories, "TPB", trackerUrl, magnet, size, tryParse(seeders), tryParse(leechers)));
        }

        return results.build();
    }

    private Set<Category> collectCategories(Elements categoriesElements) {
        Set<Category> categories = new HashSet<>();
        for (Element category : categoriesElements) {
            final String link = category.attr("href");
            final String categoryString = link.substring(link.lastIndexOf('/') + 1);

            final int categoryInt = tryParse(categoryString);

            categories.add(CategoryMapper.getByNum(categoryInt));
        }
        return categories;
    }

    private long getSizeFromDescription(String description) {
        long size = 0;
        for (String part : Splitter.on(", ").split(description)) {
            if (part.startsWith("Size")) {
                final Iterator<String> split = Splitter.on(' ').split(part).iterator();
                split.next();
                String sizeStr = split.next();
                String sizeUnit = split.next();

                double sizeDouble = Double.parseDouble(sizeStr);

                switch (sizeUnit) {
                    case "KiB":
                        size = (long) (sizeDouble * 1000);
                        break;
                    case "MiB":
                        size = (long) (sizeDouble * 1000000);
                        break;
                    case "GiB":
                        size = (long) (sizeDouble * 1000000000);
                        break;
                    case "TiB":
                        size = (long) (sizeDouble * 1000000000000L);
                        break;

                }

            }
        }
        return size;
    }

    public static int tryParse(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
