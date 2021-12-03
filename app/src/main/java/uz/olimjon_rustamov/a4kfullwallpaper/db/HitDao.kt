package uz.olimjon_rustamov.a4kfullwallpaper.db

import androidx.room.*
import uz.olimjon_rustamov.a4kfullwallpaper.retrofit.model.Hit

@Dao
interface HitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHit(hit: Hit)

    @Delete
    fun deleteHit(hit: Hit)

    @Query("select * from Hit")
    fun getALlQuery(): List<Hit>

    @Query("select * from Hit where id=:id")
    fun getById(id: Int): List<Hit>
}