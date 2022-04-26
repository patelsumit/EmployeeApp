package com.app.employeeapp.repository

import com.app.employeeapp.commonutil.Resource
import com.app.employeeapp.data.ApiService
import com.app.employeeapp.model.Detail
import com.app.employeeapp.model.Employee
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EmployeeRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    EmployeeRepository {
    override suspend fun getEmployeeList(): Flow<Resource<List<Employee>>> = flow {
        try {
            emit(Resource.loading(null))
            emit(Resource.success(apiService.getAllEmployee()))
        } catch (e: Exception) {
            emit(Resource.error("Check Network Connection!", null))
        }
    }

    override suspend fun getDetail(): Flow<Resource<List<Detail>>> = flow {
        try {
            emit(Resource.loading(null))
            emit(Resource.success(apiService.getDetail()))
        } catch (e: Exception) {
            emit(Resource.error("Check Network Connection!", null))
        }
    }

}