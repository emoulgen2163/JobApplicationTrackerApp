package com.emoulgen.jobtrackerapp.domain.model

enum class JobStatus(val title: String = "") {
    ALL("ALL"),
    APPLIED("Applied"),
    INTERVIEW("Interview"),
    REJECTED("Rejected"),
    OFFER("Offer")
}
