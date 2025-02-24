package com.example.task1

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items




@Composable
fun ProductDetailScreen(productId: Int, navController: NavController) {
    val product = sampleProducts.first { it.id == productId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = product.name,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                backgroundColor = Color(0xFF8192B7), // Hồng nhạt
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = { BottomMenu() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                item {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Star, contentDescription = "Rating", tint = Color.Yellow)
                        Text(text = "${product.rating} (2 Reviews)", fontSize = 14.sp, color = Color.Gray)
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Image(
                        painter = painterResource(id = product.imageUrl),
                        contentDescription = product.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = product.description, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Reviews", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                items(sampleReviews) { review ->
                    ReviewItem(
                        userName = review.userName,
                        date = review.date,
                        rating = if (review.userName == "Test User") 5 else 4,
                        title = review.title,
                        comment = review.comment
                    )
                }
            }

            BottomBar(product.price)
        }
    }
}


// Danh sách mẫu cho bình luận
data class Review(val userName: String, val date: String, val title: String, val comment: String)

val sampleReviews = listOf(
    Review("Test User", "5/15/2022", "Loved it!", "Amazing product. Can’t go wrong with this one. 100% recommend."),
    Review("Ahmed Khan", "7/20/2022", "Excellent quality", "Amazing quality for the price."),
    Review("Emily Johnson", "9/5/2023", "Very nice!", "Nice quality, will buy again."),
)




@Composable
fun ReviewItem(userName: String, date: String, rating: Int, title: String, comment: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_person),
                    contentDescription = "User Icon",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = userName, fontWeight = FontWeight.Bold)
                    Text(text = date, fontSize = 12.sp, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // ⭐ Hiển thị đánh giá sao
            Row {
                repeat(rating) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Star",
                        tint = Color.Yellow,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = title, fontWeight = FontWeight.Bold)
            Text(text = comment)
        }
    }
}


@Composable
fun BottomBar(price: String) {
    Surface(elevation = 8.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "$price", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Button(
                onClick = { /* Add to Cart */ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF394357)) // Hồng nhạt
            ) {
                Text("ADD TO CART", color = Color.White) // Chữ trắng để dễ đọc
            }
        }
    }
}

@Composable
fun BottomMenu() {
    BottomNavigation(backgroundColor = Color(0xFF8192B7)) { // Hồng nhạt
        BottomNavigationItem(icon = { Icon(Icons.Default.Star, contentDescription = "Home") }, label = { Text("Home") }, selected = false, onClick = {})
        BottomNavigationItem(icon = { Icon(Icons.Default.Star, contentDescription = "Categories") }, label = { Text("Categories") }, selected = false, onClick = {})
        BottomNavigationItem(icon = { Icon(Icons.Default.Star, contentDescription = "Profile") }, label = { Text("Profile") }, selected = false, onClick = {})
    }
}
