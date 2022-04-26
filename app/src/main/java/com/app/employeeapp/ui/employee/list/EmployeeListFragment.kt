package com.app.employeeapp.ui.employee.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.employeeapp.commonutil.Status
import com.app.employeeapp.databinding.FragmentEmployeeListBinding
import com.app.employeeapp.model.Employee
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@AndroidEntryPoint
class EmployeeListFragment : Fragment() {

    private val viewModel: EmployeeListViewModel by viewModels()
    private lateinit var binding: FragmentEmployeeListBinding
    lateinit var recyclerAdapter: EmployeeRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmployeeListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRecycleView()
        retry()
    }

    override fun onResume() {
        super.onResume()
        Log.i("baso", "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.i("baso", "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.i("baso", "onStop: ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("baso", "onDestroyView: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("baso", "onDestroy: ")
    }

    override fun onDetach() {
        super.onDetach()
        Log.i("baso", "onDetach: ")
    }

    private fun initializeRecycleView() {
        binding.employeeRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            recyclerAdapter = EmployeeRecyclerAdapter(requireContext()) { employee: Employee ->
                findNavController().navigate(
                    EmployeeListFragmentDirections
                        .actionEmployeeListFragmentToDetailFragment(employee = employee)
                )
            }
            //recyclerAdapter = EmployeeRecyclerAdapter(requireContext())
            adapter = recyclerAdapter
        }
        viewModel.employees.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.LOADING -> {
                    binding.progressBar.isVisible = true
                }
                Status.SUCCESS -> {
                    binding.progressBar.isVisible = false
                    binding.btnRetry.isVisible = false
                    binding.txtError.isVisible = false
                    binding.employeeRecyclerView.isVisible = true
                    println("in success :" + result.data)
                    result.data?.let { recyclerAdapter.submitList(it) }

                }
                Status.ERROR -> {
                    binding.progressBar.isVisible = false
                    binding.employeeRecyclerView.isVisible = false
                    binding.btnRetry.isVisible = true
                    binding.txtError.isVisible = true
                    binding.txtError.text = "Check Network Connection!"
                }
            }
        }
    }

    private fun retry() {
        binding.btnRetry.setOnClickListener { initializeRecycleView() }
    }
}