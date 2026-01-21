package com.emoulgen.jobtrackerapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.emoulgen.jobtrackerapp.domain.model.Job
import dagger.hilt.android.qualifiers.ApplicationContext

@Database(entities = [Job::class], version = 1, exportSchema = false)
abstract class JobDatabase(): RoomDatabase() {

    abstract fun jobDao(): JobDao

    companion object{
        @Volatile
        private var INSTANCE: JobDatabase? = null

        fun getInstance(@ApplicationContext context: Context): JobDatabase{
            synchronized(this){
                val instance = INSTANCE ?: Room.databaseBuilder(context.applicationContext, JobDatabase::class.java, "job_db").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}