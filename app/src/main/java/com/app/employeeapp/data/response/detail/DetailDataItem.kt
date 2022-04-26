package com.app.employeeapp.data.response.detail

data class DetailDataItem(
    val createdAt: String,
    val id: String,
    val isOccupied: Boolean,
    val maxOccupancy: Int
)