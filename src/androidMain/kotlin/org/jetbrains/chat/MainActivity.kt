package org.jetbrains.chat

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.resurrection.composebase.state.MutableStatelessResource
import com.resurrection.composebase.state.Status
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this)[ViewModel::class.java] }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {


            viewModel.loadList()

            viewModel.list.observeStateless(
                success = {
                    Text(it.toString())
                }
            )
        }
    }


}

@Composable
fun <T> MutableStatelessResource<T>.observeStateless(
    success: @Composable (T?) -> Unit,
    loading: @Composable (() -> Unit)? = null,
    error: @Composable ((Throwable?) -> Unit)? = null
) {
    val resource = this.resource.value
    val status by remember { this.resource.value.status }

    when (status) {
        Status.SUCCESS -> success.invoke(resource.data)
        Status.LOADING -> loading?.invoke()
        Status.ERROR -> error?.invoke(resource.throwable)
    }
}


