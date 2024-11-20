package com.example.githubrepos.common.presentation.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.githubrepos.features.register.login.presentation.ui.LoginScreen
import com.example.githubrepos.features.register.login.presentation.viewmodel.LoginEvent
import com.example.githubrepos.features.register.login.presentation.viewmodel.LoginViewModel
import com.example.githubrepos.features.register.signup.presentation.ui.SignupScreen
import com.example.githubrepos.features.register.signup.presentation.viewmodel.SignupEvent
import com.example.githubrepos.features.register.signup.presentation.viewmodel.SignupViewModel
import com.example.githubrepos.features.repos_list.presentation.ui.ReposScreen


@Composable
fun SetUpNavigation(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel,
    signupViewModel: SignupViewModel,
    onGoogleAuthClick:() -> Unit,
    onFacebookAuthClick:() -> Unit,
    navController: NavHostController,
    context: Context
) {


    val loginState by loginViewModel.loginState.collectAsStateWithLifecycle()
    val signupState by signupViewModel.signupState.collectAsStateWithLifecycle()

    val signUpEvent by signupViewModel.singleEvent.collectAsState(initial = SignupEvent.Idle)
    val loginEvent by loginViewModel.singleEvent.collectAsState(initial = LoginEvent.Idle)

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.LoginScreen
    ) {

        composable<Screen.LoginScreen> {
            LoginScreen(
                onAction =  loginViewModel::onAction,
                loginState = loginState,
                onGoogleAuthClick = { onGoogleAuthClick() },
                onFacebookAuthClick = { onFacebookAuthClick() },
                navController = navController,
                loginEvent = loginEvent
            )
        }

        composable<Screen.SignupScreen> {
            SignupScreen(
                onAction = signupViewModel::onAction,
                signupState = signupState ,
                onGoogleAuthClick = { onGoogleAuthClick() },
                onFacebookAuthClick = { onFacebookAuthClick() },
                navController = navController,
                signupEvent = signUpEvent
            )
        }

        composable<Screen.ReposListScreen> {
            ReposScreen(context = context, navController = navController)
        }

    }

}





















