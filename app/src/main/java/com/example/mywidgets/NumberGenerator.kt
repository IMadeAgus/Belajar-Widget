package com.example.mywidgets

import java.util.*

//Nulber generator merupakan object yang sifatnya statis, jadi tidak perlu diinisiaslisasi
internal object NumberGenerator {
    fun generate(max: Int): Int {
        val random = Random()
        return  random.nextInt(max)
    }
}
//merupakan kelas helper unutk membantu membuat angka acak berdasarkan parameter