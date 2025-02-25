package com.example.task1

import android.os.Bundle // lưu trữ dlieu tạm thời
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme //giúp đnh nghĩa màu sắc, type, theme
import androidx.navigation.compose.rememberNavController // quản lý điều hướng giữua các màn hình
import androidx.navigation.compose.NavHost //chứa ds các màn hình mà user có thể đìu hướng đến
import androidx.navigation.compose.composable //lket 1 mhình vs 1 đg dẫn cụ thể
import com.example.task1.ProductDetailScreen
import com.example.task1.ProductListScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController() // đtg iều hướng giữa các màn hình, giúp lưu state navcontroler khi dao diện thay đổi
                NavHost(navController, startDestination = "list") {// chỉ đnh mhinh đầu tiên là plist
                    composable("list") { ProductListScreen(navController) }// hiển thị plist và truền navcontroller vào
                    composable("detail/{productId}") { backStackEntry -> //đìu hướng đến detail theo id, sau đó hiển thị
                        val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull() ?: 0 // đổi id chuỗi thành số để truy cập
                        ProductDetailScreen(productId, navController) // hthị chi tiết sp với id đc lấy từ đg dẫn
                    }
                }
            }
        }
    }
}
