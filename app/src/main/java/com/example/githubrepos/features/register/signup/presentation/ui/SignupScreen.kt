package com.example.githubrepos.features.register.signup.presentation.ui

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
import androidx.compose.material.icons.rounded.AccountCircle
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
import com.example.githubrepos.features.register.signup.presentation.viewmodel.SignupActions
import com.example.githubrepos.features.register.signup.presentation.viewmodel.SignupEvent
import com.example.githubrepos.features.register.signup.presentation.viewmodel.SignupState

@Composable
fun SignupScreen(
    modifier: Modifier = Modifier,
    onAction: (SignupActions) -> Unit,
    signupState: SignupState,
    signupEvent: SignupEvent,
    onGoogleAuthClick: () -> Unit,
    onFacebookAuthClick: () -> Unit,
    navController: NavController,
) {

    val emailState = rememberTextFieldState()
    val passwordState = rememberTextFieldState()
    val nameState = rememberTextFieldState()

    LaunchedEffect(key1 = signupEvent) {
        when (signupEvent) {
            is SignupEvent.SignupSuccessfully -> {
                navController.navigate(Screen.ReposListScreen) {
                    popUpTo(Screen.SignupScreen) { inclusive = true }
                }
            }

            SignupEvent.Idle -> Unit
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        if (signupState.isLoading) {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }


        Column {
            Image(
                painter = painterResource(id = R.drawable.register),
                contentDescription = "Register Screen",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.25f)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "SignUp",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }

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

        Text(
            text = "Or, login with....",
            fontSize = 15.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .alpha(0.5f)
        )

        CustomTextField(
            textFieldState = emailState,
            hint = "Email",
            keyboardType = KeyboardType.Email,
            leadingIcon = Icons.Rounded.Email,
            trailingIcon = Icons.Default.Check,
            modifier = Modifier.fillMaxWidth(),
        )

        CustomTextField(
            textFieldState = nameState,
            hint = "Name",
            keyboardType = KeyboardType.Text,
            leadingIcon = Icons.Rounded.AccountCircle,
            modifier = Modifier.fillMaxWidth(),
        )

        CustomTextField(
            textFieldState = passwordState,
            hint = "Password",
            keyboardType = KeyboardType.Password,
            leadingIcon = Icons.Rounded.Lock,
            isPassword = true,
            modifier = Modifier.fillMaxWidth(),
        )


        Button(
            onClick = {
                onAction(
                    SignupActions.Signup(
                        emailState.text.toString(),
                        passwordState.text.toString(),
                        nameState.text.toString()
                    )
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue
            )
        ) {
            Text(
                text = "Sign up",
                fontSize = 17.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }



        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {

            Text(
                text = "have an account?",
                fontSize = 16.sp,
            )
            Text(
                text = "Login",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { navController.navigate(Screen.LoginScreen) }
            )

        }

        Spacer(modifier = Modifier.height(1.dp))

    }

}










