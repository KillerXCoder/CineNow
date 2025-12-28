package cz.utb.fai.cinenow.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Entity(tableName = "movies")
@TypeConverters(Converters::class)
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val genres: List<String>,
    val lastUpdated: Long = System.currentTimeMillis()
)

class Converters {
    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(",").map { it.trim() }
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.joinToString(", ")
    }
}
