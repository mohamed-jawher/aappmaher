package com.example.helloworld.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
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
fun RegisterPage(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    authViewModel: ViewModel, // Assuming AuthViewModel has registration logic
    dbHelper: FeedReaderDbHelper
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var usernameError by remember { mutableStateOf("") }
    var phoneError by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    // Email validation pattern
    val emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    // Phone validation pattern (dummy for now)
    val phonePattern = Pattern.compile("^\\d{8}$") // Example for a 10-digit phone number

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text(
            text = "Register",
            fontSize = 32.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = Color(126, 87, 194),
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif
        )

        // Registration Image
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.welcome_cuate),
            contentDescription = "Register Image",
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Username Input
        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                usernameError = if (username.isEmpty()) "Username is required" else ""
                errorMessage = ""
            },
            label = { Text(text = "Username") },
            isError = usernameError.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(0.8f)
        )
        if (usernameError.isNotEmpty()) {
            Text(
                text = usernameError,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.Start).padding(start = 40.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Phone Number Input
        OutlinedTextField(
            value = phone,
            onValueChange = {
                phone = it
                phoneError = when {
                    phone.isEmpty() -> "Phone number is required"
                    !phonePattern.matcher(phone).matches() -> "Phone number must be 8 digits"
                    else -> ""
                }
                errorMessage = ""
            },
            label = { Text(text = "Phone Number") },
            isError = phoneError.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(0.8f),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        if (phoneError.isNotEmpty()) {
            Text(
                text = phoneError,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.Start).padding(start = 40.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

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
                errorMessage = ""
            },
            label = { Text(text = "Email") },
            isError = emailError.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(0.8f)
        )
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
                    password.length < 8 -> "Password must be at least 8 characters long"
                    else -> ""
                }
                errorMessage = ""
            },
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation(),
            isError = passwordError.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(0.8f)
        )
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

        // Register Button
        Button(
            onClick = {
                if (username.isEmpty()) usernameError = "Username is required"
                if (email.isEmpty()) emailError = "Email is required"
                if (password.isEmpty()) passwordError = "Password is required"
                if (phone.isEmpty()) phoneError = "Phone number is required"
                if (usernameError.isEmpty() && emailError.isEmpty() && passwordError.isEmpty() && phoneError.isEmpty()) {
                    // Call register method in AuthViewModel
                    val registerSuccessful = (authViewModel as? AuthViewModel)?.register(username, email, password, phone) ?: false
                    // Handle registration result
                    if (registerSuccessful == false) {
                        errorMessage = "Registration failed, please try again."
                    } else {
                        // Navigate to home page on successful registration
                        navController.navigate("home")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text(text = "Register")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Navigate to Login Button
        TextButton(
            onClick = { navController.navigate("login") }
        ) {
            Text(text = "Already have an account!")
        }
    }
}
