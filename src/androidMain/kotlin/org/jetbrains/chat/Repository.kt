package org.jetbrains.chat

import com.resurrection.composebase.util.startRequest
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.flow
import org.jetbrains.chat.model.EmployeeResponse

class Repository {

    val _url = "https://dummy.restapiexample.com/api/v1/employees"

     suspend fun request() = startRequest<String> { HttpClient().get(_url) }

         // flow { emit( HttpClient().get(_url).body<EmployeeResponse>() ) }



}