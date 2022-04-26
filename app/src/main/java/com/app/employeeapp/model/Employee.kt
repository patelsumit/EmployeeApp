package com.app.employeeapp.model

import com.app.employeeapp.data.response.list.Data
import java.io.Serializable

data class Employee(
    val avatar: String? = null,
    val createdAt: String? = null,
    val data: Data? = null,
    val email: String? = null,
    val favouriteColor: String? = null,
    val firstName: String? = null,
    val fromName: String? = null,
    val id: String? = null,
    val jobtitle: String? = null,
    val lastName: String? = null,
    val to: String? = null
) : Serializable
