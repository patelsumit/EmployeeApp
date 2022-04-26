package com.app.employeeapp.ui.employee.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.MediumTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.app.employeeapp.AndroidMainCoroutinesRule
import com.app.employeeapp.R
import com.app.employeeapp.launchFragmentInHiltContainer
import com.app.employeeapp.model.Detail
import com.app.employeeapp.model.Employee
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
class DetailFragmentTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesRule = AndroidMainCoroutinesRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private val data = listOf(
        Detail(
            "2022-01-24T20:52:50.765Z",
            "1",
            false,
            53539
        ),
        Detail(
            "2022-01-25T15:36:51.812Z",
            "7",
            true,
            27837
        )
    )

    @Before
    fun setup() {
        hiltRule.inject()
    }


    @Test
    fun pressBackButton_popBackStack() {
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<DetailFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        Espresso.pressBack()

        Mockito.verify(navController).popBackStack()
    }
}