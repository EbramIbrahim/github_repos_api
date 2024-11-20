package com.example.githubrepos.features.register.login.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.githubrepos.R
import com.example.githubrepos.common.presentation.navigation.Screen
import com.example.githubrepos.common.presentation.ui.AuthBorderBox
import com.example.githubrepos.common.presentation.ui.CustomTextField
import com.example.githubrepos.features.register.login.presentation.viewmodel.LoginActions
import com.example.githubrepos.features.register.login.presentation.viewmodel.LoginEvent
import com.example.githubrepos.features.register.login.presentation.viewmodel.LoginState

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onAction: (LoginActions) -> Unit,
    loginState: LoginState,
    loginEvent: LoginEvent,
    onGoogleAuthClick: () -> Unit,
    onFacebookAuthClick: () -> Unit,
    navController: NavController,
) {


    val emailState = rememberTextFieldState()
    val passwordState = rememberTextFieldState()

    LaunchedEffect(key1 = loginEvent) {
        when (loginEvent) {
            is LoginEvent.LoginSuccessfully -> {
                navController.navigate(Screen.ReposListScreen) {
                    popUpTo(Screen.LoginScreen) { inclusive = true }
                }
            }

            LoginEvent.Idle -> Unit
        }
    }

    if (loginState.isLoggedIn) {
        navController.navigate(Screen.ReposListScreen) {
            popUpTo(Screen.LoginScreen) { inclusive = true }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {

        if (loginState.isLoading) {
            Box(contentAlignment = Alignment.Center, modifier = modifier) {
                CircularProgressIndicator()
            }
        }


        Column {
            Image(
                painter = painterResource(id = R.drawable.login),
                contentDescription = "Login Screen",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.25f)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Login",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }

        CustomTextField(
            textFieldState = emailState,
            hint = "Email",
            keyboardType = KeyboardType.Email,
            leadingIcon = Icons.Rounded.Email,
            trailingIcon = Icons.Default.Check,
            modifier = Modifier.fillMaxWidth(),
        )

        CustomTextField(
            textFieldState = passwordState,
            hint = "Password",
            keyboardType = KeyboardType.Password,
            leadingIcon = Icons.Rounded.Lock,
            trailingText = "Forget?",
            isPassword = true,
            modifier = Modifier.fillMaxWidth(),
        )

        Button(
            onClick = {
                onAction(
                    LoginActions.Login(
                        emailState.text.toString(),
                        passwordState.text.toString()
                    )
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue
            )
        ) {
            Text(
                text = "Login",
                fontSize = 17.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Text(
            text = "Or, login with....",
            fontSize = 15.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .alpha(0.5f)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            AuthBorderBox(
                onBoxClick = { onGoogleAuthClick() },
                image = R.drawable.google
            )

            AuthBorderBox(
                onBoxClick = { onFacebookAuthClick() },
                image = R.drawable.facebook
            )

        }

        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {

            Text(
                text = "Don't have an account?",
                fontSize = 16.sp,
            )
            Text(
                text = "Register",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { navController.navigate(Screen.SignupScreen) }
            )

        }

        Spacer(modifier = Modifier.height(1.dp))

    }

}










