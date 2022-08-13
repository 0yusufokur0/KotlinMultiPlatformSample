package org.jetbrains.chat.model

data class EmployeeResponse(
	val data: List<DataItem?>? = null,
	val message: String? = null,
	val status: String? = null
)

data class DataItem(
	val profileImage: String? = null,
	val employeeName: String? = null,
	val employeeSalary: Int? = null,
	val id: Int? = null,
	val employeeAge: Int? = null
)

