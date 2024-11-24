package com.example.helloworld.pages

import android.R.attr.title
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.helloworld.AuthViewModel
import com.example.helloworld.FeedReaderDbHelper
import com.example.helloworld.Product
import com.example.helloworld.R

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    authViewModel: AuthViewModel, // Ensure this is the correct type
    dbHelper: FeedReaderDbHelper
) {
    val scrollState = rememberScrollState()
    val username = authViewModel.getUsername() // Get the username
    val productList = remember {
        mutableStateListOf(*dbHelper.readData())
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Home Page", fontSize = 32.sp)
        Text(text = "Welcome, $username!", fontSize = 24.sp) // Display the username
        TextButton(onClick = {
            val id = dbHelper.insertData("Jordan Pro Strong", "Description", R.drawable.jordan_pro_strong, 199.99f)
            productList.add(Product(id,"Jordan Pro Strong", "Jordan Pro Strong",199.99f,"Description",R.drawable.jordan_pro_strong))
        }) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Insert Product",
            )
        }
        TextButton(onClick = {
            authViewModel.logout()
            navController.navigate("login")
        }) {
            Text(text = "Logout", fontSize = 16.sp)
        }
        LazyColumn (modifier = Modifier.fillMaxSize()) {
            items(productList) { product ->
                ProductCard(product = product, navController = navController, dbHelper = dbHelper,productList = productList)
            }
        }
    }
}

@Composable
fun ProductCard(product: Product, navController: NavHostController, dbHelper: FeedReaderDbHelper,productList: List<Product>
                ) {
    Card(
        modifier = Modifier.fillMaxSize().padding(
            horizontal = 16.dp,
            vertical = 8.dp,

        ),
        onClick = {
            // Handle product click
            // For example, you can navigate to the product details page
            navController.navigate("singleProduct/${product.name}/${product.price}/${product.image}/${product.description}/${product.id}")

        }

    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(text = product.name)
                Text(text = product.price.toString()+"$")
                IconButton(
                    onClick = {
                        dbHelper.deleteData(product.id!!)
                        //productList.filter { it.id != product.id }
                        //print(productList.toString())
                        navController.navigate("home")
                    }

                ){
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                }
            }
            Image(painter = painterResource(id = product.image), contentDescription = null, contentScale = ContentScale.Fit, modifier = Modifier.fillMaxSize())
        }
    }

}
