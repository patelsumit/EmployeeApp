package com.app.employeeapp.ui.employee.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.app.employeeapp.R
import com.app.employeeapp.commonutil.Status
import com.app.employeeapp.commonutil.circularProgressBar
import com.app.employeeapp.commonutil.getDateTime
import com.app.employeeapp.databinding.FragmentDetailBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initToolBar()
        setData()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            findNavController().popBackStack()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setData() {
        Glide.with(binding.root)
            .load(args.employee.avatar)
            .placeholder(circularProgressBar(requireContext()))
            .error(R.drawable.ic_baseline_error_24)
            .transition(DrawableTransitionOptions.withCrossFade())
            .fitCenter()
            .into(binding.imageView)
        binding.txtName.text = args.employee.firstName
        binding.txtTitle.text = args.employee.jobtitle
        binding.txtEmail.text = args.employee.email
        binding.txtDate.text = args.employee.createdAt?.let { getDateTime(it) }
        binding.txtColor.text = args.employee.favouriteColor
        viewModel.details.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    result.data?.forEach {
                        if (it.id == args.employee.id) {
                            println(it.isOccupied)
                            binding.txtRoom.text = "Room Occupied"
                        } else {
                            binding.txtRoom.text = "Room Not Occupied"
                        }
                    }
                }
                Status.ERROR -> {
                }
            }
        }
    }

    private fun initToolBar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.detailsToolbar)
        (activity as AppCompatActivity).supportActionBar?.apply {
            title = null
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().popBackStack()
        }
    }
}