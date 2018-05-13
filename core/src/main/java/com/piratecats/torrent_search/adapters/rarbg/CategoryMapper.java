package com.piratecats.torrent_search.adapters.rarbg;

import com.piratecats.torrent_search.model.category.Category;

class CategoryMapper {
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
}
