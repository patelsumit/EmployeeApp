package com.app.employeeapp.ui.employee.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.employeeapp.commonutil.Resource
import com.app.employeeapp.model.Employee
import com.app.employeeapp.repository.EmployeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class EmployeeListViewModel @Inject constructor(private val repository: EmployeeRepository) :
    ViewModel() {
    private val _employees: MutableLiveData<Resource<List<Employee>>> = MutableLiveData()
    val employees: LiveData<Resource<List<Employee>>> = _employees

    init {
        getAllEmployees()
    }

    fun getAllEmployees() {
        viewModelScope.launch {
            repository.getEmployeeList().collect {
                _employees.value = it
            }
        }
    }
}