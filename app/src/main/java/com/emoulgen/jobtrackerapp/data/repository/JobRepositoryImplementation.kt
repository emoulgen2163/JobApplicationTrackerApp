package com.emoulgen.jobtrackerapp.data.repository

import androidx.lifecycle.LiveData
import com.emoulgen.jobtrackerapp.data.local.JobDao
import com.emoulgen.jobtrackerapp.domain.model.Job
import com.emoulgen.jobtrackerapp.domain.repository.JobRepository
import javax.inject.Inject

class JobRepositoryImplementation @Inject constructor(val dao: JobDao): JobRepository {
    override suspend fun addJob(job: Job) = dao.addJob(job)

    override suspend fun deleteJob(job: Job) = dao.deleteJob(job)

    override suspend fun updateJob(job: Job) = dao.updateJob(job)

    override fun getAllJobs() = dao.getAllJobs()
}