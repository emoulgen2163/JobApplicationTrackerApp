package com.emoulgen.jobtrackerapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.emoulgen.jobtrackerapp.domain.model.Job

@Dao
interface JobDao {

    @Insert
    suspend fun addJob(job: Job)

    @Delete
    suspend fun deleteJob(job: Job)

    @Update
    suspend fun updateJob(job: Job)

    @Query("SELECT * FROM Job")
    fun getAllJobs(): LiveData<List<Job>>


}