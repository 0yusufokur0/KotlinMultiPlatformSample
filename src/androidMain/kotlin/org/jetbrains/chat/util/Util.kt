package com.resurrection.composebase.util

import android.util.Log
import com.resurrection.composebase.state.Resource
import io.ktor.client.call.*
import io.ktor.client.statement.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.jetbrains.chat.model.EmployeeResponse
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

fun Any.isValid(): Boolean {
    var isValid = true
    val fields = this.javaClass.declaredFields

    fields.forEachIndexed { i, field ->

        fields[i].isAccessible = true

        val value = fields[i].get(this)

        Log.w("Msg", "Value of Field " + fields[i].name + " is " + value)

        if (value == 0 || value == 0.0 || value == "" || value == null) {
            isValid = false
        }
    }
    return isValid
}

fun <T : Any> T.getPrivatePropertyOfJava(variableName: String): Any? {

    return javaClass.getDeclaredField(variableName).let { field ->
        field.isAccessible = true
        return@let field.get(this)
    }
}

fun <T : Any> T.setAndReturnPrivateProperty(variableName: String, data: Any): Any? {
    return javaClass.getDeclaredField(variableName).let { field ->
        field.isAccessible = true
        field.set(this, data)
        return@let field.get(this)
    }
}

inline fun <reified T> T.callPrivateFunc(name: String, vararg args: Any?): Any? =
    T::class
        .declaredMemberFunctions
        .firstOrNull { it.name == name }
        ?.apply { isAccessible = true }
        ?.call(this, *args)

inline fun <reified T : Any, R> T.getPrivatePropertyOfKotlin(name: String): R? =
    T::class
        .memberProperties
        .firstOrNull { it.name == name }
        ?.apply { isAccessible = true }
        ?.get(this) as R?


/*@JvmName("getData1")
inline fun <reified T> getData(crossinline request: suspend () -> HttpResponse ) : Flow<Resource<T>> =
    flow { emit(getResourceByNetworkRequest{ request() } ) }

suspend inline fun <reified T> getResourceByNetworkRequest(request: suspend () -> HttpResponse): Resource<T> {
    try {
        val response = request()
        return Resource.Success(response.body<T>())
    } catch (e: Exception) {
        e.printStackTrace()
        return Resource.Error(e)
    }
    return Resource.Loading()
}*/

@JvmName("getData2")
inline fun <reified T> startRequest(crossinline request: suspend () -> HttpResponse ) : Flow<Resource<T>> = flow {
    try {
        emit(Resource.Loading())
        val response = request()
         emit(Resource.Success(response.body<T>()))
    } catch (e: Exception) {
        e.printStackTrace()
         emit(Resource.Error(e))
    }
}.flowOn(Dispatchers.IO)












