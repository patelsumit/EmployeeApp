package com.app.employeeapp.commonutil

import androidx.recyclerview.widget.DiffUtil
import com.app.employeeapp.model.Employee

object EmployeeDiffCallBack : DiffUtil.ItemCallback<Employee>() {
    override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
        return oldItem == newItem
    }
}