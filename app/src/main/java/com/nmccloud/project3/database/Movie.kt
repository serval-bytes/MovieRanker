package com.nmccloud.project3.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nmccloud.project3.R
import java.util.*

@Entity(tableName = "movie_table")
data class Movie(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var title: String = "",
    var director: String = "",
    var yearReleased: Int = 0,
    var genre: Int = 0,
    var watched: Boolean = false,
    var notes: String = "",
    var rating: Int = 0,
    var uuid: UUID = UUID.randomUUID()){
    val photoFileName
        get() = "IMG_$uuid.jpg"

    val typeImage
        get() = when (genre) {
            0 -> R.drawable.icons8_drama_96
            1 -> R.drawable.icons8_action_96
            2 -> R.drawable.icons8_comedy_96
            3 -> R.drawable.icons8_musical_100
            4 -> R.drawable.icons8_horror_96 //horror is actually thriller
            5 -> R.drawable.icons8_ghost_100 //ghost is actually horror...
            6 -> R.drawable.icons8_detective_96
            7 -> R.drawable.icons8_documentary_100
            else -> R.drawable.icons8_grey_96
        }

    val ratingText
        get() = when(rating){
            0 -> "None/5"
            1 -> ".5/5"
            2 -> "1/5"
            3 -> "1.5/5"
            4 -> "2/5"
            5 -> "2.5/5"
            6 -> "3/5"
            7 -> "3.5/5"
            8 -> "4/5"
            9 -> "4.5/5"
            else -> "5/5"
        }

    val yearReleasedText
        get() = "($yearReleased)"
}
