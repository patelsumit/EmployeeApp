package com.app.employeeapp.ui.employee.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.employeeapp.R
import com.app.employeeapp.commonutil.circularProgressBar
import com.app.employeeapp.commonutil.getDateTime
import com.app.employeeapp.databinding.EmplyeeListItemBinding
import com.app.employeeapp.model.Employee
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class EmployeeRecyclerAdapter @Inject constructor(
    @ApplicationContext val context: Context,
    private val clicked: (Employee) -> Unit
) : ListAdapter<Employee, EmployeeRecyclerAdapter.EmployeeViewHolder>(DiffCallback) {

    inner class EmployeeViewHolder(
        private val binding: EmplyeeListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Employee?) {
            binding.txtName.text = data?.firstName
            binding.txtTitle.text = data?.jobtitle
            binding.txtEmail.text = data?.email
            if (data != null) {
                binding.txtDate.text = data.createdAt?.let { getDateTime(it) }
            }
            Glide.with(binding.root)
                .load(data?.avatar)
                .placeholder(circularProgressBar(context))
                .error(R.drawable.ic_baseline_error_24)
                .transition(DrawableTransitionOptions.withCrossFade())
                .fitCenter()
                .into(binding.imageView)

            binding.root.setOnClickListener {
                data?.let { id ->
                    println(id)
                    clicked.invoke(id)
                }
            }

            /*binding.root.setOnClickListener {
                data?.let { employee ->
                    println("Employee : $employee")
                    val direction = EmployeeListFragmentDirections.actionEmployeeListFragmentToDetailsFragment(employee)
                    applicationContext.findNavController().navigate(direction)
                }
            }*/
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Employee>() {
        override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        return EmployeeViewHolder(
            EmplyeeListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val employee = getItem(position)
        holder.bind(employee)
    }
}