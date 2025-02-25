package com.example.task1

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable //xử lí skien click vào sp
import androidx.compose.foundation.layout.* // cung cấp row, column, box, spacer, padding...
import androidx.compose.foundation.lazy.LazyColumn // danh sách cuộn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.* // cung cấp card, text, button, scaffold, topappbar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier // chỉnh sửa layout
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource // load ảnh từ drawable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp // kcach
import androidx.compose.ui.unit.sp // kthuoc chữ
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
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
    Product(1, "SỬ THỊ HÀ MY", "Chất liệu mềm mại, thoải mái, 100% Cotton, dễ giặt và thoáng mát", "$12.99", R.drawable.tshirt, 4.5f),
    Product(2, "SỬ THỊ HÀ MY", "100% Cotton, dễ giặt và thoáng mát, Sản phẩm cao cấp, thiết kế đẹp", "$7.95", R.drawable.red_shirt, 4.0f),
    Product(3, "SỬ THỊ HÀ MY", "Sản phẩm cao cấp, thiết kế đẹp, 100% Cotton, dễ giặt và thoáng mát", "$9.85", R.drawable.white_shirt, 3.8f),
    Product(4, "SỬ THỊ HÀ MY", "Chống nước, thích hợp đi mưa, 100% Cotton, dễ giặt và thoáng mát", "$29.99", R.drawable.jacket, 4.7f)
)

@Composable
fun ProductListScreen(navController: NavController) {
    var selectedTab by remember { mutableStateOf(0) } // biến trạng thái

    Scaffold( // giúp qly tổng thể màn hình
        topBar = { TopAppBar(title = { Text("Hàng mới về") }, backgroundColor = Color(0xFF8192B7), contentColor = Color.Black) }, //thanh tiêu đề
        bottomBar = { BottomNavigationBar(selectedTab, onTabSelected = { selectedTab = it }) } // thanh điều hướng
    ) { paddingValues -> // khoảng trống giữa topappbar và bottombar
        LazyColumn( // ds cuộn hthi ds sp
            contentPadding = paddingValues, // đảm bảo danh sch không bị che bởi 2 cái kia
            modifier = Modifier.padding(16.dp).background(Background) // thêm kc quanh ds và đặt bg
        ) {
            items(sampleProducts) { product -> // lặp các ds sampleP, hiển thị qua pItem
                ProductItem(product, onClick = { //onclik khi nhấn vô sp
                    navController.navigate("detail/${product.id}") // chuyển sang màn hình detail
                })
            }
        }
    }
}

// productitem hiển thị thng tin spam
@Composable
fun ProductItem(product: Product, onClick: () -> Unit) { // onclick điều hướng đến màn hình chi tiết sp
    Card( // tạo 1 ô chứa sp
        modifier = Modifier
            .fillMaxWidth() // chiếm toàn bộ chìu r man hình
            .padding(5.dp) // thêm kc giữa các sp
            .clickable { onClick() }, //khi click vào sp sẽ thực thi onclick
        shape = RoundedCornerShape(16.dp), // bo góc ô
        elevation = 4.dp
    ) {
        Row( // hiển thị ảnh và tt theo hàng ngang
            modifier = Modifier.padding(10.dp), //tạo kc bên trong card
            verticalAlignment = Alignment.CenterVertically // căn giữa theo trục dọc
        ) {
            Image(
                painter = painterResource(id = product.imageUrl), // hiển thị ảnh từ product.imageUrl
                contentDescription = null,
                modifier = Modifier.size(150.dp) // kthuoc cố định ảnh
            )
            Spacer(modifier = Modifier.width(10.dp)) // kcach giua ảnh và tt

            Column { // sxep tt theo cột dọc
                Text(text = product.name, fontWeight = FontWeight.Bold, fontSize = 16.sp) //tên sp đc in đậm
                Text(text = product.description, fontSize = 12.sp, color = Color.Gray) //mô tả sp

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, contentDescription = "Rating", tint = Color.Yellow) //hiển thị icon star vàng
                    Text(text = product.rating.toString(), fontSize = 14.sp, color = Color.Gray) // hiển thị số điểm đánh giá
                }

                //tạo hộp chứa giá và +
                Card(
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp), //
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
                        IconButton(onClick = { /* code thêm vào giỏ */ }) {
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

@Preview(showBackground = true)
@Composable
fun PreviewProductItem() {
    ProductItem(
        product = Product(1, "SỬ THỊ HÀ MY", "Chất liệu mềm mại, thoải mái, 100% Cotton, dễ giặt và thoáng mát", "$12.99", R.drawable.tshirt, 4.5f),
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewProductListScreen() {
    ProductListScreen(navController = rememberNavController())
}

@Preview(showBackground = true)
@Composable
fun PreviewBottomNavigationBar() {
    BottomNavigationBar(selectedTab = 0, onTabSelected = {})
}
