package com.app.employeeapp.ui.employee.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.app.employeeapp.MainCoroutinesRule
import com.app.employeeapp.commonutil.Resource
import com.app.employeeapp.getOrAwaitValueTest
import com.app.employeeapp.model.Detail
import com.app.employeeapp.repository.EmployeeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutinesRule = MainCoroutinesRule()

    private lateinit var viewModel: DetailViewModel

    @Mock
    lateinit var detail: Detail

    @Mock
    lateinit var repository: EmployeeRepository

    @Mock
    lateinit var detailResponseObserver: Observer<Resource<List<Detail>>>

    private val _details: MutableLiveData<Resource<List<Detail>>> = MutableLiveData()
    val details: LiveData<Resource<List<Detail>>> = _details

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = DetailViewModel(repository)
    }

    @Test
    fun `fetch result and return detail successfully`() {
        runTest {
            val flow = flow<Resource<List<Detail>>> {
                emit(Resource.loading(null))
            }
            flow.collect {
                _details.value = it
            }
            viewModel.details.observeForever(detailResponseObserver)
            given(repository.getDetail()).willReturn(flow)
            verify(repository).getDetail()
            viewModel.getDetail()
            assertNotNull(viewModel.details.value)
            assertEquals(details.value, viewModel.details.getOrAwaitValueTest())
        }
    }

    @Test
    fun `when fetching results fails then return error`() {

        runTest {
            val flow = flow {
                emit(Resource.error("Check Network Connection!", null))
            }
            viewModel.details.observeForever(detailResponseObserver)
            `when`(repository.getDetail()).thenAnswer {
                flow
            }
            Assert.assertNotNull(viewModel.details.value)
            assertEquals(
                Resource.error("Check Network Connection!", null),
                viewModel.details.getOrAwaitValueTest()
            )
        }
    }
}