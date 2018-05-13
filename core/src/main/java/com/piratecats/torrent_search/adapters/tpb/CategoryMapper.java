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

package com.piratecats.torrent_search.adapters.tpb;

import com.piratecats.torrent_search.model.category.Category;

class CategoryMapper {
    static Category getByNum(int categoryInt) {
        switch (categoryInt) {
            case 100: return Category.AUDIO;
            case 101: return Category.AUDIO_MUSIC;
            case 102: return Category.AUDIO_BOOKS;
            case 103: return Category.AUDIO_CLIPS;
            case 104: return Category.AUDIO_LOSSLESS;
            case 199: return Category.AUDIO_OTHER;

            case 200: return Category.VIDEO;
            case 201: return Category.VIDEO_MOVIES;
            case 202: return Category.VIDEO_MOVIES_DVR;
            case 203: return Category.VIDEO_MUSIC;
            case 204: return Category.VIDEO_MOVIE_CLIPS;
            case 205: return Category.VIDEO_TV_SHOWS;
            case 206: return Category.VIDEO_HANDHELD;
            case 207: return Category.VIDEO_MOVIES_HD;
            case 208: return Category.VIDEO_TV_SHOWS_HD;
            case 209: return Category.VIDEO_MOVIES_3D;
            case 299: return Category.VIDEO_OTHER;

            case 300: return Category.APPLICATIONS;
            case 301: return Category.APPLICATIONS_WINDOWS;
            case 302: return Category.APPLICATIONS_MAC;
            case 303: return Category.APPLICATIONS_UNIX;
            case 304: return Category.APPLICATIONS_HANDHELD;
            case 305: return Category.APPLICATIONS_IOS;
            case 306: return Category.APPLICATIONS_ANDROID;
            case 399: return Category.APPLICATIONS_OTHER;

            case 400: return Category.GAMES;
            case 401: return Category.GAMES_PC;
            case 402: return Category.GAMES_MAC;
            case 403: return Category.GAMES_PSX;
            case 404: return Category.GAMES_XBOX360;
            case 405: return Category.GAMES_WII;
            case 406: return Category.GAMES_HANDHELD;
            case 407: return Category.GAMES_IOS;
            case 408: return Category.GAMES_ANDROID;
            case 499: return Category.GAMES_OTHER;

            case 500: return Category.PORN;
            case 501: return Category.PORN_MOVIES;
            case 502: return Category.PORN_MOVIES_DVD;
            case 503: return Category.PORN_PICTURES;
            case 504: return Category.PORN_GAMES;
            case 505: return Category.PORN_MOVIES_HD;
            case 506: return Category.PORN_MOVIE_CLIPS;
            case 599: return Category.PORN_OTHER;

            case 600: return Category.OTHER;
            case 601: return Category.OTHER_EBOOKS;
            case 602: return Category.OTHER_COMICS;
            case 603: return Category.OTHER_PICTURES;
            case 604: return Category.OTHER_COVERS;
            case 605: return Category.OTHER_PHYSIBLES;
            case 699: return Category.OTHER_OTHER;

            default: return Category.ALL;
        }
    }
}
