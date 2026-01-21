package com.emoulgen.jobtrackerapp.ui.screens.jobDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.emoulgen.jobtrackerapp.R
import com.emoulgen.jobtrackerapp.domain.model.Job
import com.emoulgen.jobtrackerapp.domain.model.JobStatus
import com.emoulgen.jobtrackerapp.ui.theme.JobTrackerAppTheme
import com.emoulgen.jobtrackerapp.ui.viewModel.JobViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobEntryScreen(
    viewModel: JobViewModel = hiltViewModel(),
    navController: NavController = rememberNavController()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val job = viewModel.job

    var companyName by rememberSaveable { mutableStateOf("") }
    var jobPosition by rememberSaveable { mutableStateOf("") }
    var applicationDate by rememberSaveable { mutableStateOf("") }
    var status by rememberSaveable { mutableStateOf("") }
    var expandedStatus by rememberSaveable { mutableStateOf(false) }
    var notes by rememberSaveable { mutableStateOf("") }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.clearJob()
        }
    }

    LaunchedEffect(job) {
        job?.let {
            companyName = it.companyName
            jobPosition = it.position
            applicationDate = it.date
            status = it.status
            notes = it.notes
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = if (job == null) "Add Job" else "Edit Job",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(Color(0xff1667c8)),
                navigationIcon = {
                    TextButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Text(
                            text = "Cancel",
                            fontSize = 17.sp,
                            color = Color.White
                        )
                    }
                },
                actions = {
                    TextButton(
                        modifier = Modifier.padding(end = 5.dp),
                        onClick = {
                            if (companyName.isNotEmpty() && jobPosition.isNotEmpty() && applicationDate.isNotEmpty() && status.isNotEmpty()) {
                                val updatedJob = Job(
                                    id = job?.id ?: 0,
                                    companyName = companyName,
                                    position = jobPosition,
                                    status = status,
                                    date = applicationDate,
                                    notes = notes
                                )
                                if (job == null) {
                                    viewModel.addJob(updatedJob)
                                } else {
                                    viewModel.updateJob(updatedJob)
                                }

                                scope.launch {
                                    snackbarHostState.showSnackbar("Saved successfully!")
                                }

                                navController.popBackStack()
                            }
                        }
                    ) {
                        Text(
                            text = "Save",
                            fontSize = 17.sp,
                            color = Color.White
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        var showModal by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
        ) {

            Text("Company Name")
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color.White),
                value = companyName,
                onValueChange = { companyName = it }
            )
            Spacer(Modifier.height(16.dp))

            Text("Job Position")
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color.White),
                value = jobPosition,
                onValueChange = { jobPosition = it }
            )
            Spacer(Modifier.height(16.dp))

            Text("Application Date")
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color.White),
                value = applicationDate,
                onValueChange = { },
                suffix = {
                    IconButton(
                        modifier = Modifier.size(24.dp),
                        onClick = { showModal = true }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_table_view_24),
                            contentDescription = null
                        )
                    }
                },
                readOnly = true
            )

            if (showModal) {
                DatePickerScreen(
                    onDateSelected = { dateMillis ->
                        dateMillis?.let {
                            applicationDate = convertMillisToDate(it)
                        }
                    },
                    onDismiss = { showModal = false }
                )
            }
            Spacer(Modifier.height(16.dp))

            Text("Status")
            Spacer(Modifier.height(8.dp))
            ExposedDropdownMenuBox(
                expanded = expandedStatus,
                onExpandedChange = { expandedStatus = it }
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(Color.White),
                    value = status,
                    onValueChange = { },
                    trailingIcon = {
                        IconButton(
                            modifier = Modifier.size(24.dp),
                            onClick = { expandedStatus = true }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_arrow_drop_down_24),
                                contentDescription = null
                            )
                        }
                    },
                    readOnly = true
                )

                ExposedDropdownMenu(
                    expanded = expandedStatus,
                    onDismissRequest = { expandedStatus = false }
                ) {
                    JobStatus.entries.filter { it != JobStatus.ALL }.forEach { jobStatus ->
                        DropdownMenuItem(
                            text = { Text(jobStatus.title) },
                            onClick = {
                                status = jobStatus.title
                                expandedStatus = false
                            }
                        )
                    }
                }
            }
            Spacer(Modifier.height(16.dp))

            Text("Notes")
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color.White),
                value = notes,
                onValueChange = { notes = it },
            )
            Spacer(Modifier.height(16.dp))

            if (job != null) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        viewModel.deleteJob(job)
                        navController.popBackStack()
                    },
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(Color.Red)
                ) {
                    Text("Delete Job")
                }
            }
        }
    }
}

@Composable
fun DatePickerScreen(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateSelected(datePickerState.selectedDateMillis)
                    onDismiss()
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(datePickerState)
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

@Preview(showBackground = true)
@Composable
fun AddOrEditJobScreenPreview() {
    JobTrackerAppTheme {
        JobEntryScreen()
    }
}
