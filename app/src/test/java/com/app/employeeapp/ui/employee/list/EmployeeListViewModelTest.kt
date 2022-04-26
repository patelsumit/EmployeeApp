package com.app.employeeapp.ui.employee.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.app.employeeapp.MainCoroutinesRule
import com.app.employeeapp.commonutil.Resource
import com.app.employeeapp.getOrAwaitValueTest
import com.app.employeeapp.model.Employee
import com.app.employeeapp.repository.EmployeeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class EmployeeListViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutinesRule = MainCoroutinesRule()

    private lateinit var viewModel: EmployeeListViewModel

    private val _res: MutableLiveData<Resource<List<Employee>>> = MutableLiveData()
    val res: LiveData<Resource<List<Employee>>> = _res

    @Mock
    lateinit var repository: EmployeeRepository

    @Mock
    lateinit var employeeListResponseObserver: Observer<Resource<List<Employee>>>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = EmployeeListViewModel(repository)
    }

    @Test
    fun `return employee list success`() {
        runTest {
            val flow = flow<Resource<List<Employee>>> {
                emit(Resource.loading(null))
            }
            flow.collect {
                _res.value = it
            }
            viewModel.employees.observeForever(employeeListResponseObserver)
            given(repository.getEmployeeList()).willReturn(flow)
            verify(repository).getEmployeeList()
            viewModel.getAllEmployees()
            assertNotNull(viewModel.employees.value)
            assertEquals(res.value, viewModel.employees.getOrAwaitValueTest())
        }
    }
}