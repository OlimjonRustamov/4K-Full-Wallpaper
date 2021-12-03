package uz.olimjon_rustamov.a4kfullwallpaper.db

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.olimjon_rustamov.a4kfullwallpaper.retrofit.model.Hit

@Database(entities = [Hit::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun hitDao():HitDao
}