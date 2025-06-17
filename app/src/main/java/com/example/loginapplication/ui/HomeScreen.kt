package com.example.loginapplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginapplication.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onLogout: () -> Unit,
    viewModel: AuthViewModel = AuthViewModel(LocalContext.current)
) {
    val isLoggedIn by viewModel.isLoggedIn
    
    // 如果未登录，自动返回登录页面
    LaunchedEffect(isLoggedIn) {
        if (!isLoggedIn) {
            onLogout()
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF667EEA),
                        Color(0xFF764BA2)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            // 顶部栏
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "欢迎使用",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                
                IconButton(
                    onClick = {
                        viewModel.logout()
                        onLogout()
                    },
                    colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White)
                ) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "退出登录",
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
            
            // 主要内容区域
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // 用户信息卡片
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // 用户头像
                        Card(
                            modifier = Modifier
                                .size(100.dp)
                                .padding(bottom = 24.dp),
                            shape = RoundedCornerShape(50.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF667EEA).copy(alpha = 0.1f))
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "用户头像",
                                    modifier = Modifier.size(50.dp),
                                    tint = Color(0xFF667EEA)
                                )
                            }
                        }
                        
                        Text(
                            text = "登录成功！",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF333333),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        
                        Text(
                            text = "欢迎来到WanAndroid",
                            fontSize = 16.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(bottom = 32.dp)
                        )
                        
                        // 功能按钮区域
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                onClick = { /* TODO: 添加功能 */ },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF667EEA)
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.weight(1f).padding(end = 8.dp)
                            ) {
                                Text(
                                    text = "我的收藏",
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            
                            Button(
                                onClick = { /* TODO: 添加功能 */ },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF764BA2)
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.weight(1f).padding(start = 8.dp)
                            ) {
                                Text(
                                    text = "个人设置",
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
                
                // 状态信息卡片
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Green.copy(alpha = 0.1f))
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "✅ 登录状态已保存",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Green.copy(alpha = 0.8f),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        
                        Text(
                            text = "您的登录信息已通过Cookie安全保存，下次打开应用时将自动登录",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                
                // 底部提示
                Spacer(modifier = Modifier.height(32.dp))
                
                Text(
                    text = "点击右上角图标可退出登录",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
} 