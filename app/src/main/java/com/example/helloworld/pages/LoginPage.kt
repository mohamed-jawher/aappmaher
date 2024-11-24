package com.example.helloworld.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.helloworld.AuthViewModel
import com.example.helloworld.FeedReaderDbHelper
import com.example.helloworld.R
import java.util.regex.Pattern

@Composable
fun LoginPage(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    authViewModel: ViewModel, // Assuming you have an AuthViewModel with login logic
    dbHelper: FeedReaderDbHelper
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    // Email validation pattern
    val emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")


    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text(
            text = "Login",
            fontSize = 32.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = Color(126, 87, 194),
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif
        )

        // Login Image
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.mobile_login_bro),
            contentDescription = "Login Image",
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Email Input
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = when {
                    email.isEmpty() -> "Email is required"
                    !emailPattern.matcher(email).matches() -> "Invalid email address"
                    else -> ""
                }
                errorMessage = "" // Clear general error message on input
            },
            label = { Text(text = "Email") },
            isError = emailError.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(0.8f)
        )
        // Email error message
        if (emailError.isNotEmpty()) {
            Text(
                text = emailError,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.Start).padding(start = 40.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Password Input
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = when {
                    password.isEmpty() -> "Password is required"
                    //password.length < 8 -> "Password must be at least 8 characters long"
                    else -> ""
                }
                errorMessage = ""
            },
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation(),
            isError = passwordError.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(0.8f)
        )
        // Password error message
        if (passwordError.isNotEmpty()) {
            Text(
                text = passwordError,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.Start).padding(start = 40.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // General Error Message Display
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        // Forgot Password
        TextButton(
            onClick = { navController.navigate("forgetPassword") }
        ) {
            Text(text = "Forgot Password?")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Login Button
        Button(
            onClick = {
                if (email.isEmpty()) {
                    emailError = "Email is required"
                }
                if (password.isEmpty()) {
                    passwordError = "Password is required"
                }
                if (emailError.isEmpty() && passwordError.isEmpty()) {
                    val loginSuccessful = (authViewModel as? AuthViewModel)?.login(email, password) ?: false
                    if (!loginSuccessful) {
                        errorMessage = "Invalid credentials, please try again."
                    } else {
                        navController.navigate("home")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Register Button
        TextButton(
            onClick = { navController.navigate("register") }
        ) {
            Text(text = "I don't have an account!")
        }
    }
}
