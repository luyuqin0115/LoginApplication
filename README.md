# LoginApplication

一个基于Android Jetpack Compose的登录注册应用，使用RxJava + Retrofit网络框架，支持Cookie自动管理。

## 功能特性

- ✅ 现代化UI设计，使用Jetpack Compose
- ✅ 登录和注册功能
- ✅ RxJava + Retrofit网络请求框架
- ✅ Cookie自动保存和管理
- ✅ 登录状态持久化
- ✅ 网络请求错误处理
- ✅ 表单验证
- ✅ 响应式UI状态管理

## 技术栈

- **UI框架**: Jetpack Compose
- **网络请求**: Retrofit + RxJava3
- **状态管理**: ViewModel + Compose State
- **导航**: Navigation Compose
- **JSON解析**: Gson
- **HTTP客户端**: OkHttp3
- **Cookie管理**: 自定义CookieJar

## 项目结构

```
app/src/main/java/com/example/loginapplication/
├── data/
│   ├── api/                 # API接口定义
│   └── model/              # 数据模型
├── network/                # 网络配置和管理
├── repository/             # 数据仓库层
├── ui/                     # UI界面
├── viewmodel/              # ViewModel层
└── MainActivity.kt         # 主Activity
```

## API接口

### 登录接口
- **URL**: `https://www.wanandroid.com/user/login`
- **方法**: POST
- **参数**: username, password

### 注册接口
- **URL**: `https://www.wanandroid.com/user/register`
- **方法**: POST
- **参数**: username, password, repassword

## 使用方法

1. 克隆项目到本地
2. 使用Android Studio打开项目
3. 等待Gradle同步完成
4. 运行应用到设备或模拟器

## 核心功能说明

### 1. 网络框架
- 使用Retrofit构建RESTful API客户端
- RxJava3处理异步操作和线程切换
- OkHttp拦截器用于日志记录
- 自定义CookieJar实现Cookie持久化

### 2. 登录状态管理
- 登录成功后自动保存Cookie到SharedPreferences
- 应用启动时自动检查登录状态
- 支持一键退出登录并清除Cookie

### 3. 表单验证
- 实时输入验证
- 密码强度检查
- 确认密码一致性验证
- 友好的错误提示

### 4. UI设计
- Material Design 3设计规范
- 渐变色背景
- 圆角卡片设计
- 响应式布局
- 加载状态指示器

## 界面截图

应用包含三个主要界面：
1. **登录界面** - 用户输入账号密码进行登录
2. **注册界面** - 新用户注册账号
3. **主页界面** - 登录成功后的欢迎页面

## 开发环境

- Android Studio Hedgehog | 2023.1.1
- Kotlin 1.9.0
- Compose BOM 2023.08.00
- Min SDK: 24
- Target SDK: 35

## 依赖库

```kotlin
// 网络请求
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
implementation("com.squareup.retrofit2:adapter-rxjava3:2.9.0")
implementation("com.squareup.okhttp3:okhttp:4.12.0")
implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

// RxJava
implementation("io.reactivex.rxjava3:rxjava:3.1.8")
implementation("io.reactivex.rxjava3:rxandroid:3.0.2")

// Compose
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
implementation("androidx.navigation:navigation-compose:2.7.6")
```

## 注意事项

1. 确保设备或模拟器有网络连接
2. 首次运行需要网络权限
3. Cookie会自动保存，重启应用后保持登录状态
4. 支持横竖屏切换

## License

MIT License # LoginApplication
