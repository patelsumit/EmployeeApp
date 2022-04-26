package com.app.employeeapp.data

import com.app.employeeapp.model.Detail
import com.app.employeeapp.model.Employee
import retrofit2.http.GET

interface ApiService {
    @GET("people")
    suspend fun getAllEmployee(): List<Employee>

    @GET("rooms")
    suspend fun getDetail(): List<Detail>
}