package com.emoulgen.jobtrackerapp.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emoulgen.jobtrackerapp.domain.model.Job
import com.emoulgen.jobtrackerapp.domain.repository.JobRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobViewModel @Inject constructor(private val repository: JobRepository): ViewModel() {

    val getAllJobs = repository.getAllJobs()

    var job by mutableStateOf<Job?>(null)
        private set

    fun addJob(job: Job){
        viewModelScope.launch {
            repository.addJob(job)
        }
    }

    fun deleteJob(job: Job){
        viewModelScope.launch {
            repository.deleteJob(job)
        }
    }

    fun updateJob(job: Job){
        viewModelScope.launch {
            repository.updateJob(job)
        }
    }

    fun setJobValues(jobItem: Job){
        job = jobItem
    }

    fun clearJob() {
        job = null
    }
}