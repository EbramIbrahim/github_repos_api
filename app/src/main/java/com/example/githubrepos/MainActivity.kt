package com.example.githubrepos

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.githubrepos.common.data.repository.GoogleAuthImpl
import com.example.githubrepos.common.presentation.navigation.Screen
import com.example.githubrepos.common.presentation.navigation.SetUpNavigation
import com.example.githubrepos.common.utils.Constant
import com.example.githubrepos.common.utils.Resource
import com.example.githubrepos.features.register.login.presentation.viewmodel.LoginViewModel
import com.example.githubrepos.features.register.signup.presentation.viewmodel.SignupActions
import com.example.githubrepos.features.register.signup.presentation.viewmodel.SignupViewModel
import com.example.githubrepos.ui.theme.GitHubReposTheme
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val signupViewModel by viewModels<SignupViewModel>()
    private val loginViewModel by viewModels<LoginViewModel>()
    private val callbackManager = CallbackManager.Factory.create()
    private val loginManager = LoginManager.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GitHubReposTheme {
                val context = LocalContext.current
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    SetUpNavigation(
                        modifier = Modifier.padding(innerPadding),
                        loginViewModel = loginViewModel,
                        signupViewModel = signupViewModel,
                        onGoogleAuthClick = {
                            signInWithGoogle(context, navController)
                        },
                        onFacebookAuthClick = {
                            loginManager.logInWithReadPermissions(
                                this,
                                Constant.facebook_permissions
                            )
                            signupViewModel.onAction(
                                SignupActions.LoginWithFacebook(
                                    callbackManager,
                                    loginManager
                                )
                            )
                        },
                        context = context,
                        navController = navController
                    )
                }
            }
        }
    }

    private fun signInWithGoogle(context: Context, navController: NavController) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                val extraAuthOptions = GoogleAuthImpl(context)
                when (val result = extraAuthOptions.signInWithGoogle()) {
                    is Resource.Failure -> {
                        Toast.makeText(
                            this@MainActivity,
                            result.exception.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    Resource.Loading -> Unit
                    is Resource.Success -> {
                        navController.navigate(Screen.ReposListScreen) {
                            popUpTo(Screen.SignupScreen) { inclusive = true }
                        }
                    }
                }
            }
        }
    }
}
