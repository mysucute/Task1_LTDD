package com.example.task1

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.* // cung cấp row, column, spacer, padding
import androidx.compose.foundation.lazy.LazyColumn// tạo ds cuộn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape// bo tròn
import androidx.compose.foundation.shape.RoundedCornerShape// bo tròn
import androidx.compose.material.* // cung cấp card, text, button, scaffold, topappbar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable // xđinh hàm compose
import androidx.compose.runtime.remember // lưu trạng thái
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext // lấy context hiện tại trong compose
import androidx.navigation.NavController
import androidx.navigation.testing.TestNavHostController // navcontroller giả lập cho preview

@Composable
fun rememberFakeNavController(): NavController { // tạo navcontroller giả lập để preview
    val context = LocalContext.current // lấy context hiện tại
    return remember { TestNavHostController(context) }
}

@Preview
@Composable
fun PreviewProductDetailScreen() {
    val navController = rememberFakeNavController()
    ProductDetailScreen(productId = 1, navController = navController)
}

//hiển thị thông tin chi tiết của sp
@Composable
fun ProductDetailScreen(productId: Int, navController: NavController) { //lấy tt dựa trên productid
    val product = sampleProducts.firstOrNull { it.id == productId }// tìm sp theo id

    if (product == null) { // nếu ko tìm thấy trả về null
        Text("Không tìm thấy sản phẩm", fontSize = 20.sp, modifier = Modifier.padding(16.dp))
        return
    }

    //giao ịeện chính
    Scaffold(
        topBar = {
            TopAppBar( // thanh tiêu đề
                title = {
                    Text(
                        text = product.name, // hiển thị tên
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                backgroundColor = Color(0xFF8192B7),
                navigationIcon = { // hiển thị nút quay lại
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = { BottomMenu() } // thanh điều hướng dưới cùng
    ) { paddingValues -> // trống giữa topappbar và bottommenu
        Column(
            modifier = Modifier
                .padding(paddingValues) // chừa không gian giữa topappbar và bottommenu
                .fillMaxSize()
        ) {
            LazyColumn( // tạo danh sách cuộn để hiển thị sản phẩm và đánh giá
                modifier = Modifier.weight(1f) // chiếm toàn bộ không gian có thể cuộn
                    .padding(16.dp)
            ) {
                item {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Star, contentDescription = "Rating", tint = Color.Yellow)
                        Text(text = "${product.rating} (2 Reviews)", fontSize = 14.sp, color = Color.Gray)
                    }
                    Spacer(modifier = Modifier.height(10.dp)) // tạo khoảng cách cho phần tiếp theo

                    Image(
                        painter = painterResource(id = product.imageUrl), // lấy ảnh từ tài nguyên drawable
                        contentDescription = product.name, // mô tả ảnh bằng tên sản phẩm
                        modifier = Modifier
                            .fillMaxWidth() // chiếm toàn bộ chiều rộng
                            .height(200.dp)
                            .clip(RoundedCornerShape(16.dp)) // bo góc ảnh
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = product.description, fontSize = 16.sp)

                    Spacer(modifier = Modifier.height(16.dp))

                    Divider() // đường kẻ ngang

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Đánh giá", fontSize = 18.sp, fontWeight = FontWeight.Bold)

                    Spacer(modifier = Modifier.height(8.dp))
                }

                items(sampleReviews) { review -> // lặp qua danh sách sampleReviews để hiển thị
                    ReviewItem(
                        userName = review.userName,
                        date = review.date,
                        rating = if (review.userName == "Hà My") 5 else 4,
                        title = review.title,
                        comment = review.comment
                    )
                }
            }

            BottomBar(price = product.price) // thanh hiển thị giá ngay trên BottomMenu
        }
    }
}

data class Review(val userName: String, val date: String, val title: String, val comment: String)

val sampleReviews = listOf(
    Review("Hà My", "5/15/2022", "Quá đẹp!", "Omg, đẹp quá nên mua quá xinh xắn đáng yêu cute dễ thương"),
    Review("Hữu Khánh", "7/20/2022", "Tôi iu em", "Hàng đẹp nha sốp."),
    Review("Con Chó", "9/5/2023", "Tốt lắm!", "Giá thành rẻ mặc hơi ngứa ôi quá xá là ngoan xinh yêu của mẹ ơi."),
)


//hàm đánh giá
@Preview
@Composable
fun PreviewReviewItem() {
    ReviewItem(
        userName = "Hà My",
        date = "10/10/2023",
        rating = 5,
        title = "Sản phẩm tốt!",
        comment = "Tôi rất thích cái áo này, nên mua nha!"
    )
}
@Composable
fun ReviewItem(userName: String, date: String, rating: Int, title: String, comment: String) {// tham số truyền vào
    Card( // làm hộp chứa đánh giá
        shape = RoundedCornerShape(10.dp), // bo góc hộp
        elevation = 4.dp, // hiệu ứngdđổ bóng
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp) //kc giữa các đnáh giá
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) { //chia avt và tt ở cạnh nhau
                Image(
                    painter = painterResource(id = R.drawable.ic_person),// lấy ảnh từ tài nguuyên drawable
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape) // làm tròn ảnh
                )

                Spacer(modifier = Modifier.width(20.dp)) // kc giữua ảnh và tt

                Column {
                    Text(text = userName, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Text(text = date, fontSize = 12.sp, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(4.dp)) //kc giữa tt và star

            Row {
                repeat(rating) { // vẽ số sao tương ứng vs rating
                    Icon(
                        imageVector = Icons.Default.Star, // bỉu tg star
                        contentDescription = "Star",
                        tint = Color.Yellow,
                        modifier = Modifier.size(17.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = comment, fontSize = 15.sp)
        }
    }
}


@Preview
@Composable
fun PreviewBottomBar() {
    BottomBar(price = "$99.99")
}
@Composable
fun BottomBar(price: String) {
    Surface(elevation = 8.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween, // căn giá và nút cách xa nhau
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "$price", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Button(
                onClick = { /* Add to Cart */ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF394357))
            ) {
                Text("Thêm vào giỏ hàng", color = Color.White)
            }
        }
    }
}

@Preview
@Composable
fun PreviewBottomMenu() {
    Surface {
        BottomMenu()
    }
}

@Composable
fun BottomMenu() {
    BottomNavigation( // thanh điều hướng
        backgroundColor = Color(0xFF8192B7),
        modifier = Modifier.fillMaxWidth() // đảm bảo menu hiển thị đầy đủ chiều rộng
    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Star, contentDescription = "Home") },
            label = { Text("Trang chủ") },
            selected = false,
            onClick = {}
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Star, contentDescription = "Categories") },
            label = { Text("Danh mục") },
            selected = false,
            onClick = {}
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Star, contentDescription = "Profile") },
            label = { Text("Thông tin") },
            selected = false,
            onClick = {}
        )
    }
}