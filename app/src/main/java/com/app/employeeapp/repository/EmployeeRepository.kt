package com.app.employeeapp.repository

import com.app.employeeapp.commonutil.Resource
import com.app.employeeapp.model.Detail
import com.app.employeeapp.model.Employee
import kotlinx.coroutines.flow.Flow

interface EmployeeRepository {
    suspend fun getEmployeeList(): Flow<Resource<List<Employee>>>
    suspend fun getDetail(): Flow<Resource<List<Detail>>>
}