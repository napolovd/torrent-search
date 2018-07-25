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

package com.piratecats.torrent_search.adapters.rarbg;

import com.piratecats.torrent_search.model.category.Category;

final class CategoryMapper {
    static Category getByName(String category) {
        switch (category) {
            case "XXX (18+)": return Category.PORN;
            case "Movies/XVID": return Category.VIDEO_MOVIES;
            case "Movies/XVID/720": return Category.VIDEO_MOVIES;
            case "Movies/x264": return Category.VIDEO_MOVIES;
            case "Movies/x264/1080": return Category.VIDEO_MOVIES_HD;
            case "Movies/x264/720": return Category.VIDEO_MOVIES;
            case "Movies/x264/3D": return Category.VIDEO_MOVIES_3D;
            case "Movies/x264/4k": return Category.VIDEO_MOVIES_4K;
            case "Movies/x265/4k": return Category.VIDEO_MOVIES_4K;
            case "Movs/x265/4k/HDR": return Category.VIDEO_MOVIES_4K;
            case "Movies/Full BD": return Category.VIDEO_MOVIES_BLUERAY;
            case "Movies/BD Remux": return Category.VIDEO_MOVIES_BLUERAY;
            case "TV Episodes": return Category.VIDEO_TV_SHOWS;
            case "TV HD Episodes": return Category.VIDEO_TV_SHOWS_HD;
            case "TV UHD Episodes": return Category.VIDEO_TV_SHOWS_UHD;
            case "Music/MP3": return Category.AUDIO_MUSIC;
            case "Music/FLAC": return Category.AUDIO_LOSSLESS;
            case "Games/PC ISO": return Category.GAMES_PC;
            case "Games/PC RIP": return Category.GAMES_PC;
            case "Games/PS3": return Category.GAMES_PSX;
            case "Games/XBOX-360": return Category.GAMES_XBOX360;
            case "Software/PC ISO": return Category.APPLICATIONS;

            default: return Category.ALL;
        }
    }

    private CategoryMapper() {
    }
}
