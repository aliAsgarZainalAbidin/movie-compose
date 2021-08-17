package com.example.movie_app_compose.ui.login

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import com.example.movie_app_compose.R
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.movie_app_compose.BuildConfig
import com.example.movie_app_compose.BuildConfig.TAG
import com.example.movie_app_compose.api.ApiFactory
import com.example.movie_app_compose.api.ApiInterface
import com.example.movie_app_compose.data.AppDatabase
import com.example.movie_app_compose.data.Repository
import com.example.movie_app_compose.model.RequestWrapper
import com.example.movie_app_compose.navigation.Navigation
import com.example.movie_app_compose.ui.components.ButtonComponent
import com.example.movie_app_compose.ui.components.OutlinedTextFieldComponent
import com.example.movie_app_compose.ui.theme.ColorPalette
import com.example.movie_app_compose.ui.theme.MovieAppComposeTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Login : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MovieAppComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = ColorPalette.background) {
                    LoginContent()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()


    }
}

@Composable
fun LoginContent(modifier: Modifier = Modifier, onClickButtonLogin: () -> Unit = {}) {
    val restApi by lazy { ApiFactory.create() }
    val appDatabase: AppDatabase = AppDatabase.getDatabase(context = LocalContext.current)

    val loginViewModel: LoginViewModel = viewModel()
    loginViewModel.repository = Repository(appDatabase = appDatabase, apiInterface = restApi)
    val context = LocalContext.current
    var token = loginViewModel.token.observeAsState("")

    ConstraintLayout(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        val (logo, etUsername, etPassword, btnLogin) = createRefs()
        var vUsername by remember { mutableStateOf("") }
        var vPassword by remember { mutableStateOf("") }

        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(logo) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    centerVerticallyTo(parent)
                }
                .width(150.dp)
                .height(150.dp)
        )

        ButtonComponent(
            title = "Login",
            modifier = modifier
                .constrainAs(btnLogin) {
                    top.linkTo(logo.bottom)
                    start.linkTo(logo.start)
                    end.linkTo(logo.end)
                    bottom.linkTo(parent.bottom)
                }
                .width(200.dp),
            onButtonClick = {
                createRequestToken(
                    context = context,
                    apiInterface = restApi,
                    onClickButtonLogin = { onClickButtonLogin() }
                )
            }
        )
    }
}

fun createRequestToken(
    context: Context,
    apiInterface: ApiInterface,
    onClickButtonLogin: () -> Unit
): String {
    val result = apiInterface.createRequestToken(BuildConfig.API)
    var token = ""
    result.enqueue(object : Callback<RequestWrapper> {
        override fun onResponse(
            call: Call<RequestWrapper>,
            response: Response<RequestWrapper>
        ) {
            if (response.isSuccessful) {
                val data = response.body()
                token = data?.requestToken.toString()
                Log.d(TAG, "onResponse: CreateRequestToken is Successful $token")

                var intent = Intent(Intent.ACTION_VIEW)
                val url =
                    "${BuildConfig.WEB_BASE_URL}$token?redirect_to=app://${BuildConfig.APPLICATION_ID}/approved"
                intent.setData(Uri.parse(url))
                context.startActivity(intent)
                Log.d(TAG, "onResponse: URL $url")
                createSessionId(
                    token,
                    apiInterface = apiInterface,
                    onClickButtonLogin = { onClickButtonLogin() })
            } else {
                Log.d(
                    TAG,
                    "onResponse: CreateRequestToken is Not Successful ${response.body()}"
                )
            }
        }

        override fun onFailure(call: Call<RequestWrapper>, t: Throwable) {
            Log.d(TAG, "onFailure: CreateRequestToken$t")
        }

    })
    return token
}

fun createSessionId(token: String, apiInterface: ApiInterface, onClickButtonLogin: () -> Unit) {
    var sessionId: String
    var isLogin: Boolean
    val result = apiInterface.createSessionId(BuildConfig.API, requestToken = token)
    result.enqueue(object : Callback<RequestWrapper> {
        override fun onResponse(
            call: Call<RequestWrapper>,
            response: Response<RequestWrapper>
        ) {
            if (response.isSuccessful) {
                val data = response.body()
                sessionId = data?.sessionId.toString()
                isLogin = data?.success ?: false

                if (isLogin) {
                    onClickButtonLogin()
                }
                Log.d(TAG, "onResponse: CreateSessionId is Successful ${response.body()}")
            } else {
                Log.d(TAG, "onResponse: CreateSessionId is Not Successful ${response.body()}")
            }
        }

        override fun onFailure(call: Call<RequestWrapper>, t: Throwable) {
            Log.d(TAG, "onFailure: CreateSessionId $t")
        }

    })
}

@Preview(showBackground = true)
@Composable
fun PreviewLogin() {
    MovieAppComposeTheme {
        Surface(color = ColorPalette.background) {
            LoginContent()
        }
    }
}