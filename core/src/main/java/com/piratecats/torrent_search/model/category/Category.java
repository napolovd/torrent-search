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

package com.piratecats.torrent_search.model.category;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class Category {
    public static final Category ALL = Category.of("All");

    public static final Category AUDIO = Category.of("Audio");

    public static final Category AUDIO_MUSIC = Category.of("Music", AUDIO);
    public static final Category AUDIO_BOOKS = Category.of("AudioBooks", AUDIO);
    public static final Category AUDIO_CLIPS = Category.of("SoundClips", AUDIO);
    public static final Category AUDIO_LOSSLESS = Category.of("Lossless", AUDIO);
    public static final Category AUDIO_OTHER = Category.of("Other", AUDIO);

    public static final Category VIDEO = Category.of("Video");

    public static final Category VIDEO_MOVIES = Category.of("Movies", VIDEO);
    public static final Category VIDEO_MOVIES_DVR = Category.of("Movies DVR", VIDEO);
    public static final Category VIDEO_MOVIES_BLUERAY = Category.of("Movies BLUERAY", VIDEO);
    public static final Category VIDEO_MOVIES_HD = Category.of("Movies HD", VIDEO);
    public static final Category VIDEO_MOVIES_4K = Category.of("Movies 4K", VIDEO);
    public static final Category VIDEO_MOVIES_3D = Category.of("Movies 3D", VIDEO);

    public static final Category VIDEO_TV_SHOWS = Category.of("TV Shows", VIDEO);
    public static final Category VIDEO_TV_SHOWS_HD = Category.of("TV Shows HD", VIDEO);
    public static final Category VIDEO_TV_SHOWS_UHD = Category.of("TV Shows UHD", VIDEO);

    public static final Category VIDEO_MUSIC = Category.of("Music", VIDEO);
    public static final Category VIDEO_MOVIE_CLIPS = Category.of("Movie Clips", VIDEO);

    public static final Category VIDEO_HANDHELD = Category.of("Handheld", VIDEO);
    public static final Category VIDEO_OTHER = Category.of("Other", VIDEO);

    public static final Category APPLICATIONS = Category.of("Applications");

    public static final Category APPLICATIONS_WINDOWS = Category.of("Windows", APPLICATIONS);
    public static final Category APPLICATIONS_MAC = Category.of("Mac", APPLICATIONS);
    public static final Category APPLICATIONS_UNIX = Category.of("UNIX", APPLICATIONS);
    public static final Category APPLICATIONS_HANDHELD = Category.of("Handheld", APPLICATIONS);
    public static final Category APPLICATIONS_IOS = Category.of("IOS", APPLICATIONS);
    public static final Category APPLICATIONS_ANDROID = Category.of("Android", APPLICATIONS);
    public static final Category APPLICATIONS_OTHER = Category.of("Other", APPLICATIONS);

    public static final Category GAMES = Category.of("Games");

    public static final Category GAMES_PC = Category.of("PC", GAMES);
    public static final Category GAMES_MAC = Category.of("MAC", GAMES);
    public static final Category GAMES_PSX = Category.of("PSx", GAMES);
    public static final Category GAMES_XBOX360 = Category.of("XBox360", GAMES);
    public static final Category GAMES_WII = Category.of("Wii", GAMES);
    public static final Category GAMES_HANDHELD = Category.of("Handheld", GAMES);
    public static final Category GAMES_IOS = Category.of("IOS", GAMES);
    public static final Category GAMES_ANDROID = Category.of("Android", GAMES);
    public static final Category GAMES_OTHER = Category.of("Other", GAMES);

    public static final Category PORN = Category.of("Porn");

    public static final Category PORN_MOVIES = Category.of("Movies", PORN);
    public static final Category PORN_MOVIES_HD = Category.of("Movies HD", PORN);
    public static final Category PORN_MOVIES_DVD = Category.of("Movies DVD", PORN);
    public static final Category PORN_MOVIE_CLIPS = Category.of("Movie Clips", PORN);
    public static final Category PORN_PICTURES = Category.of("Pictures", PORN);
    public static final Category PORN_GAMES = Category.of("Games", PORN);
    public static final Category PORN_OTHER = Category.of("Other", PORN);

    public static final Category OTHER = Category.of("Other");

    public static final Category OTHER_EBOOKS = Category.of("EBooks", OTHER);
    public static final Category OTHER_COMICS = Category.of("Comics", OTHER);
    public static final Category OTHER_PICTURES = Category.of("Pictures", OTHER);
    public static final Category OTHER_COVERS = Category.of("Covers", OTHER);
    public static final Category OTHER_PHYSIBLES = Category.of("Physibles", OTHER);
    public static final Category OTHER_OTHER = Category.of("Other", OTHER);

    @Nonnull
    private final String name;
    @Nullable
    private final Category parent;
    @Nullable
    private Set<Category> subCategories;

    private Category(String name, @Nullable Category parent) {
        this.name = name;
        this.parent = parent;
    }

    @Nonnull
    public String getName() {
        return (parent != null ? parent.getName() + '\\' : "") + name;
    }

    @Nonnull
    public Collection<Category> getSubCategories() {
        return subCategories != null ? subCategories : ImmutableList.of();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equal(name, category.name) &&
                Objects.equal(subCategories, category.subCategories);
    }

    @Override
    public String toString() {
        return "Category{" + getName() + "}";
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, subCategories);
    }

    private void addSubCategory(Category category) {
        if(subCategories == null) {
            subCategories = new HashSet<>();
        }
        subCategories.add(category);
    }

    private static Category of(@Nonnull String name) {
        return new Category(name, null);
    }

    private static Category of(@Nonnull String name, @Nonnull Category parent) {
        final Category category = new Category(name, parent);
        parent.addSubCategory(category);
        return category;
    }
}
