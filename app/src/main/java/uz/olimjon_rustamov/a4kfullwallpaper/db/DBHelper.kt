package uz.olimjon_rustamov.a4kfullwallpaper.db

import android.content.Context
import androidx.room.Room

object DBHelper {
    private var INSTANCE: AppDataBase? = null

    fun getInstance(context: Context): AppDataBase {
        if (INSTANCE == null) {
            synchronized(AppDataBase::class) {
                INSTANCE = buildRoomDB(context)
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            AppDataBase::class.java,
            "olimjon_rustamov"
        ).allowMainThreadQueries().build()
}