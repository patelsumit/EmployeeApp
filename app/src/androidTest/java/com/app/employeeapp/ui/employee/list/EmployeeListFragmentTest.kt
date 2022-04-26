package com.app.employeeapp.ui.employee.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.paging.ExperimentalPagingApi
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.app.employeeapp.AndroidMainCoroutinesRule
import com.app.employeeapp.R
import com.app.employeeapp.launchFragmentInHiltContainer
import com.app.employeeapp.model.Employee
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@RunWith(AndroidJUnit4ClassRunner::class)
class EmployeeListFragmentTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var coroutinesRule = AndroidMainCoroutinesRule()

    private val data = listOf(
        Employee(
            "https://randomuser.me/api/portraits/women/21.jpg",
            "2022-01-24T17:02:23.729Z",
            null,
            "Crystel.Nicolas61@hotmail.com",
            "pink",
            "Maggie",
            null,
            "1",
            "Future Functionality Strategist",
            "Brekke",
            null
        ),
        Employee(
            "https://randomuser.me/api/portraits/women/21.jpg",
            "2022-01-24T17:02:23.729Z",
            null,
            "Crystel.Nicolas61@hotmail.com",
            "pink",
            "Maggie",
            null,
            "2",
            "Future Functionality Strategist",
            "Brekke",
            null
        )
    )

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun isEmployeeListVisible() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<EmployeeListFragment> {
            Navigation.setViewNavController(requireView(), navController)

            runTest {
                recyclerAdapter.submitList(data)
            }
        }

        onView(withId(R.id.employee_recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    fun clickEmployeeList_navigationToDetail() {
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<EmployeeListFragment> {
            Navigation.setViewNavController(requireView(), navController)

            runTest {
                recyclerAdapter.submitList(data)
            }
        }

        onView(withId(R.id.employee_recycler_view))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<EmployeeRecyclerAdapter.EmployeeViewHolder>(
                    0,
                    click()
                )
            )

        verify(navController).navigate(
            EmployeeListFragmentDirections.actionEmployeeListFragmentToDetailFragment(data[0])
        )
    }
}