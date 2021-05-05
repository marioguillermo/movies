package com.example.movies.domain.kotlin

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Movie(
    @PrimaryKey
    var tittle: String = "",
    var rate: Double = 0.0,
    var year: Int = 0,
    var director: String = "",
    @Ignore
    var stars: List<String> = emptyList(),
    var summary: String = "",
    var poster: String = "",
    var genere: String = ""
) : Parcelable