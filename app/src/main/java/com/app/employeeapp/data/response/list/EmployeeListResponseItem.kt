package com.app.employeeapp.data.response.list

data class EmployeeListResponseItem(
    val avatar: String,
    val createdAt: String,
    val `data`: Data,
    val email: String,
    val favouriteColor: String,
    val firstName: String,
    val fromName: String,
    val id: String,
    val jobtitle: String,
    val lastName: String,
    val to: String
)