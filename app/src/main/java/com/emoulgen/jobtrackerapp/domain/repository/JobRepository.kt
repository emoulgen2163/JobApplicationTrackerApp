package com.emoulgen.jobtrackerapp.domain.repository

import androidx.lifecycle.LiveData
import com.emoulgen.jobtrackerapp.domain.model.Job

interface JobRepository {

    suspend fun addJob(job: Job)
    suspend fun deleteJob(job: Job)
    suspend fun updateJob(job: Job)
    fun getAllJobs(): LiveData<List<Job>>
}