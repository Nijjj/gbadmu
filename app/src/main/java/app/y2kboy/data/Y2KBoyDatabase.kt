package app.y2kboy.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Query
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.coroutines.flow.Flow

@Database(
    entities = [GameRom::class, SaveStateEntry::class, CheatEntry::class],
    version = 1,
    exportSchema = true,
)
@TypeConverters(RoomConverters::class)
abstract class Y2KBoyDatabase : RoomDatabase() {
    abstract fun libraryDao(): LibraryDao
}

class RoomConverters {
    @TypeConverter
    fun fromCheatType(value: CheatType): String = value.name

    @TypeConverter
    fun toCheatType(value: String): CheatType = CheatType.valueOf(value)
}

@Dao
interface LibraryDao {
    @Query("SELECT * FROM roms ORDER BY favorite DESC, lastPlayedEpochMillis DESC, title ASC")
    fun observeLibrary(): Flow<List<GameRom>>

    @Query("SELECT * FROM save_states WHERE romId = :romId ORDER BY slot ASC")
    fun observeSaveStates(romId: String): Flow<List<SaveStateEntry>>

    @Query("SELECT * FROM cheats WHERE romId = :romId ORDER BY name ASC")
    fun observeCheats(romId: String): Flow<List<CheatEntry>>
}
