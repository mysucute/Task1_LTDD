package com.example.task1

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.task1.ui.theme.Background

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: String,
    val imageUrl: Int,
    val rating: Float
)

val sampleProducts = listOf(
    Product(1, "SỬ THỊ HÀ MY", "Chất liệu mềm mại, thoải mái, 100% Cotton, dễ giặt và thoáng mát, ", "$12.99", R.drawable.tshirt, 4.5f),
    Product(2, "SỬ THỊ HÀ MY", "100% Cotton, dễ giặt và thoáng mát, Sản phẩm cao cấp, thiết kế đẹp", "$7.95", R.drawable.red_shirt, 4.0f),
    Product(3, "SỬ THỊ HÀ MY", "Sản phẩm cao cấp, thiết kế đẹp, 100% Cotton, dễ giặt và thoáng mát", "$9.85", R.drawable.white_shirt, 3.8f),
    Product(4, "SỬ THỊ HÀ MY", "Chống nước, thích hợp đi mưa, 100% Cotton, dễ giặt và thoáng mát", "$29.99", R.drawable.jacket, 4.7f)
)

@Composable
fun ProductListScreen(navController: NavController) {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Hàng mới về") }, backgroundColor = Color(0xFF8192B7), contentColor = Color.Black) },
        bottomBar = { BottomNavigationBar(selectedTab, onTabSelected = { selectedTab = it }) }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.padding(16.dp).background(Background)
        ) {
            items(sampleProducts) { product ->
                ProductItem(product, onClick = {
                    navController.navigate("detail/${product.id}")
                })
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = product.imageUrl),
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = product.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = product.description, fontSize = 12.sp, color = Color.Gray)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, contentDescription = "Rating", tint = Color.Yellow)
                    Text(text = product.rating.toString(), fontSize = 14.sp, color = Color.Gray)
                }

                // Hộp chứa giá tiền & nút thêm
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    shape = RoundedCornerShape(30.dp),
                    elevation = 2.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = product.price,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                        IconButton(onClick = { /* TODO: Thêm vào giỏ hàng */ }) {
                            Icon(Icons.Default.Add, contentDescription = "Add to Cart", tint = Color.Black)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.primary
    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = selectedTab == 0,
            onClick = { onTabSelected(0) }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Cart") },
            label = { Text("Cart") },
            selected = selectedTab == 1,
            onClick = { onTabSelected(1) }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Account") },
            label = { Text("Account") },
            selected = selectedTab == 2,
            onClick = { onTabSelected(2) }
        )
    }
}
