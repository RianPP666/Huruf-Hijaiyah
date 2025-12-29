package com.example.hurufhijaiyah

data class Surah(
    val nama: String,
    val arti: String,
    val ayat: String, // Isi surat dalam text Arab
    val terjemahan: String // Terjemahan per ayat (digabung)
)

object SurahData {
    val listSurah = listOf(
        Surah(
            "Al-Fatihah",
            "Pembukaan",
            "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ (١)\n" +
            "الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ (٢)\n" +
            "الرَّحْمَٰنِ الرَّحِيمِ (٣)\n" +
            "مَالِكِ يَوْمِ الدِّينِ (٤)\n" +
            "إِيَّاكَ نَعْبُدُ وَإِيَّاكَ نَسْتَعِينُ (٥)\n" +
            "اهْدِنَا الصِّرَاطَ الْمُسْتَقِيمَ (٦)\n" +
            "صِرَاطَ الَّذِينَ أَنْعَمْتَ عَلَيْهِمْ غَيْرِ الْمَغْضُوبِ عَلَيْهِمْ وَلَا الضَّالِّينَ (٧)",
            "1. Dengan nama Allah Yang Maha Pengasih, Maha Penyayang.\n" +
            "2. Segala puji bagi Allah, Tuhan seluruh alam,\n" +
            "3. Yang Maha Pengasih, Maha Penyayang,\n" +
            "4. Pemilik Hari Pembalasan.\n" +
            "5. Hanya kepada Engkaulah kami menyembah dan hanya kepada Engkaulah kami mohon pertolongan.\n" +
            "6. Tunjukilah kami jalan yang lurus,\n" +
            "7. (yaitu) jalan orang-orang yang telah Engkau beri nikmat kepadanya; bukan (jalan) mereka yang dimurkai, dan bukan (pula jalan) mereka yang sesat."
        ),
        Surah(
            "An-Nas",
            "Manusia",
            "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ\n" +
            "قُلْ أَعُوذُ بِرَبِّ النَّاسِ (١)\n" +
            "مَلِكِ النَّاسِ (٢)\n" +
            "إِلَٰهِ النَّاسِ (٣)\n" +
            "مِنْ شَرِّ الْوَسْوَاسِ الْخَنَّاسِ (٤)\n" +
            "الَّذِي يُوَسْوِسُ فِي صُدُورِ النَّاسِ (٥)\n" +
            "مِنَ الْجِنَّةِ وَالنَّاسِ (٦)",
            "1. Katakanlah: \"Aku berlindung kepada Tuhan (yang memelihara dan menguasai) manusia.\n" +
            "2. Raja manusia.\n" +
            "3. Sembahan manusia.\n" +
            "4. Dari kejahatan (bisikan) syaitan yang biasa bersembunyi,\n" +
            "5. yang membisikkan (kejahatan) ke dalam dada manusia,\n" +
            "6. dari (golongan) jin dan manusia.\""
        ),
        Surah(
            "Al-Falaq",
            "Waktu Subuh",
            "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ\n" +
            "قُلْ أَعُوذُ بِرَبِّ الْفَلَقِ (١)\n" +
            "مِنْ شَرِّ مَا خَلَقَ (٢)\n" +
            "وَمِنْ شَرِّ غَاسِقٍ إِذَا وَقَبَ (٣)\n" +
            "وَمِنْ شَرِّ النَّفَّاثَاتِ فِي الْعُقَدِ (٤)\n" +
            "وَمِنْ شَرِّ حَاسِدٍ إِذَا حَسَدَ (٥)",
            "1. Katakanlah: \"Aku berlindung kepada Tuhan Yang Menguasai subuh,\n" +
            "2. dari kejahatan makhluk-Nya,\n" +
            "3. dan dari kejahatan malam apabila telah gelap gulita,\n" +
            "4. dan dari kejahatan wanita-wanita tukang sihir yang menghembus pada buhul-buhul,\n" +
            "5. dan dari kejahatan pendengki bila ia dengki.\""
        ),
        Surah(
            "Al-Ikhlas",
            "Memurnikan Keesaan Allah",
            "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ\n" +
            "قُلْ هُوَ اللَّهُ أَحَدٌ (١)\n" +
            "اللَّهُ الصَّمَدُ (٢)\n" +
            "لَمْ يَلِدْ وَلَمْ يُولَدْ (٣)\n" +
            "وَلَمْ يَكُنْ لَهُ كُفُوًا أَحَدٌ (٤)",
            "1. Katakanlah: \"Dialah Allah, Yang Maha Esa.\n" +
            "2. Allah adalah Tuhan yang bergantung kepada-Nya segala sesuatu.\n" +
            "3. Dia tiada beranak dan tidak pula diperanakkan,\n" +
            "4. dan tidak ada seorangpun yang setara dengan Dia.\""
        ),
        Surah(
            "Al-Lahab",
            "Gejolak Api",
            "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ\n" +
            "تَبَّتْ يَدَا أَبِي لَهَبٍ وَتَبَّ (١)\n" +
            "مَا أَغْنَىٰ عَنْهُ مَالُهُ وَمَا كَسَبَ (٢)\n" +
            "سَيَصْلَىٰ نَارًا ذَاتَ لَهَبٍ (٣)\n" +
            "وَامْرَأَتُهُ حَمَّالَةَ الْحَطَبِ (٤)\n" +
            "فِي جِيدِهَا حَبْلٌ مِنْ مَسَدٍ (٥)",
            "1. Binasalah kedua tangan Abu Lahab dan sesungguhnya dia akan binasa.\n" +
            "2. Tidaklah berfaedah kepadanya harta bendanya dan apa yang ia usahakan.\n" +
            "3. Kelak dia akan masuk ke dalam api yang bergejolak.\n" +
            "4. Dan (begitu pula) istrinya, pembawa kayu bakar.\n" +
            "5. Yang di lehernya ada tali dari sabut."
        ),
        Surah(
            "An-Nasr",
            "Pertolongan",
            "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ\n" +
            "إِذَا جَاءَ نَصْرُ اللَّهِ وَالْفَتْحُ (١)\n" +
            "وَرَأَيْتَ النَّاسَ يَدْخُلُونَ فِي دِينِ اللَّهِ أَفْوَاجًا (٢)\n" +
            "فَسَبِّحْ بِحَمْدِ رَبِّكَ وَاسْتَغْفِرْهُ ۚ إِنَّهُ كَانَ تَوَّابًا (٣)",
            "1. Apabila telah datang pertolongan Allah dan kemenangan,\n" +
            "2. dan kamu lihat manusia masuk agama Allah dengan berbondong-bondong,\n" +
            "3. maka bertasbihlah dengan memuji Tuhanmu dan mohonlah ampun kepada-Nya. Sesungguhnya Dia adalah Maha Penerima taubat."
        ),
        Surah(
            "Al-Kafirun",
            "Orang-Orang Kafir",
            "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ\n" +
            "قُلْ يَا أَيُّهَا الْكَافِرُونَ (١)\n" +
            "لَا أَعْبُدُ مَا تَعْبُدُونَ (٢)\n" +
            "وَلَا أَنْتُمْ عَابِدُونَ مَا أَعْبُدُ (٣)\n" +
            "وَلَا أَنَا عَابِدٌ مَا عَبَدْتُمْ (٤)\n" +
            "وَلَا أَنْتُمْ عَابِدُونَ مَا أَعْبُدُ (٥)\n" +
            "لَكُمْ دِينُكُمْ وَلِيَ دِينِ (٦)",
            "1. Katakanlah: \"Hai orang-orang kafir,\n" +
            "2. Aku tidak akan menyembah apa yang kamu sembah.\n" +
            "3. Dan kamu bukan penyembah Tuhan yang aku sembah.\n" +
            "4. Dan aku tidak pernah menjadi penyembah apa yang kamu sembah,\n" +
            "5. dan kamu tidak pernah (pula) menjadi penyembah Tuhan yang aku sembah.\n" +
            "6. Untukmu agamamu, dan untukkulah, agamaku.\""
        ),
        Surah(
            "Al-Kautsar",
            "Nikmat Yang Berlimpah",
            "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ\n" +
            "إِنَّا أَعْطَيْنَاكَ الْكَوْثَرَ (١)\n" +
            "فَصَلِّ لِرَبِّكَ وَانْحَرْ (٢)\n" +
            "إِنَّ شَانِئَكَ هُوَ الْأَبْتَرُ (٣)",
            "1. Sesungguhnya Kami telah memberikan kepadamu nikmat yang banyak.\n" +
            "2. Maka dirikanlah shalat karena Tuhanmu; dan berkorbanlah.\n" +
            "3. Sesungguhnya orang-orang yang membenci kamu dialah yang terputus."
        ),
        Surah(
            "Al-Ma'un",
            "Barang-Barang Yang Berguna",
            "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ\n" +
            "أَرَأَيْتَ الَّذِي يُكَذِّبُ بِالدِّينِ (١)\n" +
            "فَذَٰلِكَ الَّذِي يَدُعُّ الْيَتِيمَ (٢)\n" +
            "وَلَا يَحُضُّ عَلَىٰ طَعَامِ الْمِسْكِينِ (٣)\n" +
            "فَوَيْلٌ لِلْمُصَلِّينَ (٤)\n" +
            "الَّذِينَ هُمْ عَنْ صَلَاتِهِمْ سَاهُونَ (٥)\n" +
            "الَّذِينَ هُمْ يُرَاءُونَ (٦)\n" +
            "وَيَمْنَعُونَ الْمَاعُونَ (٧)",
            "1. Tahukah kamu (orang) yang mendustakan agama?\n" +
            "2. Itulah orang yang menghardik anak yatim,\n" +
            "3. dan tidak menganjurkan memberi makan orang miskin.\n" +
            "4. Maka kecelakaanlah bagi orang-orang yang shalat,\n" +
            "5. (yaitu) orang-orang yang lalai dari shalatnya,\n" +
            "6. orang-orang yang berbuat riya,\n" +
            "7. dan enggan (menolong dengan) barang berguna."
        ),
        Surah(
            "Quraisy",
            "Suku Quraisy",
            "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ\n" +
            "لِإِيلَافِ قُرَيْشٍ (١)\n" +
            "إِيلَافِهِمْ رِحْلَةَ الشِّتَاءِ وَالصَّيْفِ (٢)\n" +
            "فَلْيَعْبُدُوا رَبَّ هَٰذَا الْبَيْتِ (٣)\n" +
            "الَّذِي أَطْعَمَهُمْ مِنْ جُوعٍ وَآَمَنَهُمْ مِنْ خَوْفٍ (٤)",
            "1. Karena kebiasaan orang-orang Quraisy,\n" +
            "2. (yaitu) kebiasaan mereka bepergian pada musim dingin dan musim panas.\n" +
            "3. Maka hendaklah mereka menyembah Tuhan Pemilik rumah ini (Ka'bah).\n" +
            "4. Yang telah memberi makanan kepada mereka untuk menghilangkan lapar dan mengamankan mereka dari ketakutan."
        )
    )
}
