package org.jetbrains.chat

import androidx.lifecycle.viewModelScope
import com.resurrection.composebase.state.MutableStatelessResource
import com.resurrection.composebase.state.Resource
import com.resurrection.composebase.state.mutableStatelessResourceOf
import com.resurrection.composebase.util.core.BaseViewModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jetbrains.chat.model.EmployeeResponse

class ViewModel:BaseViewModel(){

    val repository = Repository()

    var list = mutableStatelessResourceOf<String>()



    fun loadList()  = fetchStatelessResource(
        stateless = list,
        request = { Repository().request() }
    )

    fun <T> fetchStatelessResource(
        condition: Boolean = true,
        stateless: MutableStatelessResource<T>,
        request: suspend () -> Flow<Resource<T>>,
        success: (Resource<T>) -> Unit = { stateless.postSuccess(it.data) },
        loading: () -> Unit = { stateless.postLoading() },
        error: (Throwable) -> Unit = { stateless.postError(it) }
    ) = viewModelScope.launch {
        request()
            .onStart { loading() }
            .catch {
                error(it)
            }
            .collect { success(it)}

    }

}