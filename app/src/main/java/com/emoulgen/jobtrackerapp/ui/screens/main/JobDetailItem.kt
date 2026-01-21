package com.emoulgen.jobtrackerapp.ui.screens.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emoulgen.jobtrackerapp.domain.model.Job
import com.emoulgen.jobtrackerapp.domain.model.JobStatus
import java.util.Locale.getDefault

@Composable
fun JobDetailItem(job: Job, onJobClick: (Job) -> Unit) {
    val color = when(job.status){
        JobStatus.APPLIED.title -> Color(0xff1269cb)
        JobStatus.INTERVIEW.title -> Color(0xffe4a037)
        JobStatus.OFFER.title -> Color(0xff429b7e)
        JobStatus.REJECTED.title -> Color(0xffd64d49)
        else -> {}
    }
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp).clickable{ onJobClick(job) },
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(8.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    Modifier.weight(1f)
                ) {
                    Text(
                        text = job.companyName,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(text = job.position)
                }

                Card(
                    modifier = Modifier.width(120.dp),
                    colors = CardDefaults.cardColors(color as Color),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        text = job.status.uppercase(getDefault()),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }

            HorizontalDivider(Modifier.padding(top = 12.dp, bottom = 12.dp).height(1.dp))

            Row {
                Text(text = job.status)
                Spacer(Modifier.width(8.dp))
                Text(text = "-")
                Spacer(Modifier.width(8.dp))
                Text(text = job.date)
            }

            Spacer(Modifier.height(18.dp))

            Text(text = job.notes)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JobDetailItemPreview() {
    JobDetailItem(
        Job(
            companyName = "Google",
            position = "Software Engineer",
            status = "Offer",
            date = "Aug 10, 2023",
            notes = "Followed up via email."
        )
    ){}
}