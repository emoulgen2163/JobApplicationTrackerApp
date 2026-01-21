package com.emoulgen.jobtrackerapp.ui.viewModel

import androidx.lifecycle.ViewModel
import com.emoulgen.jobtrackerapp.domain.model.JobStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class JobListViewModel : ViewModel() {

    private val _selectedStatus = MutableStateFlow(JobStatus.ALL)
    val selectedStatus = _selectedStatus.asStateFlow()

    fun onStatusSelected(status: JobStatus) {
        _selectedStatus.value = status
    }
}
