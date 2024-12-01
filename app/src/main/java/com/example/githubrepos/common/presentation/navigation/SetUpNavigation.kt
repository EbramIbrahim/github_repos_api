package com.example.githubrepos.common.presentation.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.githubrepos.features.register.login.presentation.ui.LoginScreen
import com.example.githubrepos.features.register.login.presentation.viewmodel.LoginViewModel2
import com.example.githubrepos.features.register.signup.presentation.ui.SignupScreen
import com.example.githubrepos.features.register.signup.presentation.viewmodel.RegisterViewModel
import com.example.githubrepos.features.repos_list.presentation.ui.ReposScreen
import com.facebook.CallbackManager
import com.facebook.login.LoginManager


@Composable
fun SetUpNavigation(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel2,
    signupViewModel: RegisterViewModel,
    onGoogleAuthClick: () -> Unit,
    onFacebookAuthClick: () -> Unit,
    navController: NavHostController,
    callbackManager: CallbackManager,
    loginManager: LoginManager,
    context: Context
) {


    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.LoginScreen
    ) {

        composable<Screen.LoginScreen> {
            LoginScreen(
                onGoogleAuthClick = { onGoogleAuthClick() },
                onFacebookAuthClick = { onFacebookAuthClick() },
                navController = navController,
                context = context,
                viewModel = loginViewModel,
                loginManager = loginManager,
            )
        }

        composable<Screen.SignupScreen> {
            SignupScreen(
                onGoogleAuthClick = { onGoogleAuthClick() },
                navController = navController,
                viewModel = signupViewModel,
                callbackManager = callbackManager,
                loginManager = loginManager,
                context = context
            )
        }

        composable<Screen.ReposListScreen> {
            ReposScreen(context = context, navController = navController)
        }

    }

}





















