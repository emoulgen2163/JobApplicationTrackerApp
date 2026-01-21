package com.emoulgen.jobtrackerapp.ui.screens.main

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.emoulgen.jobtrackerapp.R
import com.emoulgen.jobtrackerapp.domain.model.JobStatus
import com.emoulgen.jobtrackerapp.ui.theme.JobTrackerAppTheme
import com.emoulgen.jobtrackerapp.ui.viewModel.JobListViewModel
import com.emoulgen.jobtrackerapp.ui.viewModel.JobViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    jobListViewModel: JobListViewModel = hiltViewModel(),
    jobViewModel: JobViewModel = hiltViewModel(),
    navController: NavController = rememberNavController()
) {
    val selectedStatus by jobListViewModel.selectedStatus.collectAsState()
    val jobs by jobViewModel.getAllJobs.observeAsState(emptyList())

    val filteredJobs = remember(jobs, selectedStatus) {
        if (selectedStatus == JobStatus.ALL) {
            jobs
        } else {
            jobs.filter { it.status.equals(selectedStatus.title, ignoreCase = true) }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Job Application Tracker",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(Color(0xff1667c8)),
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .size(50.dp)
                            .padding(5.dp),
                        painter = painterResource(R.drawable.baseline_table_view_24),
                        contentDescription = null,
                        tint = Color.White
                    )
                },
                actions = {
                    Button(
                        modifier = Modifier.padding(end = 5.dp),
                        colors = ButtonDefaults.buttonColors(
                            Color(0xff3b83d8)
                        ),
                        shape = RoundedCornerShape(5.dp),
                        onClick = { navController.navigate("AddOrEdit") }
                    ) {

                        Text("Add a job")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            JobStatusTabs(
                selectedStatus = selectedStatus,
                onStatusSelected = jobListViewModel::onStatusSelected
            )

            LazyColumn {
                items(filteredJobs, key = { it.id }) { job ->
                    JobDetailItem(job) {
                        jobViewModel.setJobValues(it)
                        navController.navigate("AddOrEdit")
                    }
                }
            }
        }
    }
}

@Composable
fun JobStatusTabs(
    selectedStatus: JobStatus,
    onStatusSelected: (JobStatus) -> Unit
) {
    val statuses = JobStatus.entries

    SecondaryTabRow(
        selectedTabIndex = statuses.indexOf(selectedStatus),
        containerColor = Color.White,
        contentColor = Color(0xff1667c8)
    ) {
        statuses.forEach { status ->
            Tab(
                selected = selectedStatus == status,
                onClick = { onStatusSelected(status) },
                unselectedContentColor = Color.Gray,
                text = {
                    Text(
                        text = status.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 14.sp,
                        fontWeight = if (selectedStatus == status)
                            FontWeight.SemiBold else FontWeight.Normal
                    )

                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    JobTrackerAppTheme {
        MainScreen()
    }
}
