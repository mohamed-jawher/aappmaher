package com.example.helloworld.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController

@Composable
fun SingleProductPage(
    name: String,
    price: String,
    image: Int,
    description: String,
    id: Any,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    authViewModel: ViewModel,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = name)
            Text(text = "$$price")
            Text(text = description)
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .height(200.dp) // You can adjust the height as needed
            )
        }
    }
}
