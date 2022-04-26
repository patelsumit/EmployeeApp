package com.app.employeeapp.ui.employee.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.employeeapp.commonutil.Resource
import com.app.employeeapp.model.Detail
import com.app.employeeapp.repository.EmployeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: EmployeeRepository) :
    ViewModel() {
    private val _details: MutableLiveData<Resource<List<Detail>>> = MutableLiveData()
    val details: LiveData<Resource<List<Detail>>> = _details

    init {
        getDetail()
    }

    fun getDetail() {
        viewModelScope.launch {
            repository.getDetail().collect {
                _details.value = it
            }
        }
    }
}