package com.emoulgen.jobtrackerapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Job(
    @PrimaryKey(true)
    val id: Int = 0,
    val companyName: String = "",
    val position: String = "",
    val status: String = "", // Applied, Interview, Rejected, Offer
    val date: String = "",
    val notes: String = ""
)
