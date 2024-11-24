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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.helloworld.FeedReaderDbHelper
import com.example.helloworld.R
import java.util.regex.Pattern

@Composable
fun ForgetPasswordPage(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    authViewModel: ViewModel, // Assuming you have an AuthViewModel to handle logic
    dbHelper: FeedReaderDbHelper
) {
    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    // Email validation pattern
    val emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

    Column (modifier = Modifier.fillMaxSize()) {
        // Back Button
        TextButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(16.dp)
                //.align(Alignment.TopStart)
        ) {
            Text(text = "Back", color = Color(126, 87, 194))
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = "Forget Password",
                fontSize = 32.sp,
                color = Color(126, 87, 194),
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            // Image
            Image(
                painter = painterResource(id = R.drawable.forgot_password_bro),
                contentDescription = "Forget Password Image",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )

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
                },
                label = { Text(text = "Email") },
                isError = emailError.isNotEmpty(),
                modifier = Modifier.fillMaxWidth()
            )

            // Display error message if email is invalid
            if (emailError.isNotEmpty()) {
                Text(
                    text = emailError,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Start).padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Submit Button
            Button(
                onClick = {
                    if (emailError.isEmpty()) {
                        // Handle forget password logic using authViewModel
                        // For example: authViewModel.forgetPassword(email)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Submit")
            }
        }
    }
}
