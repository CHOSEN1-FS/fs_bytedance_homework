# fs_bytedance_homework

字节跳动 Android 开发课程作业仓库，包含 **Day1 ~ Day3** 三个阶段的练习项目，从基础 Activity 跳转，到 SQLite 登录，再到基于高德天气 API 的综合天气应用。

## 仓库结构

```
fs_bytedance_homework/
├── Day1/                    # Day1：Activity 与页面跳转
├── Day2/                    # Day2：登录界面 + SQLite 数据库
├── Day3/                    # Day3：天气 App 综合项目
├── Day3/local.properties.example  # API Key 配置模板（复制为 local.properties 后本地使用）
├── .gitignore
├── .gitattributes
└── README.md
```

---

## 项目概览

| 项目 | 技术要点 | 主要功能 |
|------|----------|----------|
| **Day1** | Activity、Intent、EdgeToEdge | 主页面按钮点击，跳转到第二个 Activity |
| **Day2** | SQLite、RecyclerView、登录 UI | 邮箱/密码登录，本地数据库存储与验证用户信息 |
| **Day3** | Retrofit、ViewModel、LiveData、Fragment、ViewPager2 | 实时天气 + 未来预报，支持北京/上海/广州/深圳切换 |

---

## Day1 — Activity 基础

**包名**：`com.example.day1`

- 使用 `Intent` 实现 `MainActivity` → `SecondActivity` 页面跳转
- 启用 EdgeToEdge 全屏布局适配

**核心文件**：
- `MainActivity.java` — 主界面，处理按钮点击与跳转
- `SecondActivity.java` — 第二个页面

---

## Day2 — 登录与 SQLite

**包名**：`com.example.day2`

- 登录界面：邮箱、密码输入，微信/Apple 登录按钮（UI 演示）
- 使用 `SQLiteOpenHelper` 管理本地数据库
- 支持用户信息的增删改查与登录校验
- 预埋测试用户数据，方便调试

**核心文件**：
- `MainActivity.java` — 登录逻辑与数据库操作
- `ActivityUserPage.java` — 登录成功后的用户页面
- `sqlite/MyHelper.java` — SQLite 数据库辅助类

---

## Day3 — 天气应用（综合项目）

**包名**：`com.example.day3`

### 功能特性

- **实时天气**：展示当前城市、温度、天气状况
- **天气预报**：RecyclerView 展示未来多天预报
- **城市切换**：北京、上海、广州、深圳
- **页面切换**：ViewPager2 + Fragment，底部「城市 / 预测」Tab 导航
- **网络请求**：Retrofit2 调用高德地图天气 API
- **架构模式**：ViewModel + LiveData 管理 UI 状态

### 项目结构

```
Day3/app/src/main/java/com/example/day3/
├── view/
│   └── MainActivity.java           # 主 Activity，ViewPager 容器
├── fragment/
│   ├── CurrentWeatherFragment.java # 实时天气页
│   └── ForecastFragment.java       # 天气预报页
├── viewmodel/
│   └── WeatherViewModel.java       # 天气数据 ViewModel
├── network/
│   ├── RetrofitClient.java         # Retrofit 单例
│   └── WeatherApi.java             # API 接口定义
├── model/
│   └── WeatherBean.java            # 天气数据模型
├── adapter/
│   ├── WeatherPagerAdapter.java    # ViewPager 适配器
│   └── ForecastAdapter.java        # 预报列表适配器
└── utils/
    └── DateUtils.java              # 日期工具类
```

### 技术栈

| 类别 | 依赖 |
|------|------|
| 网络 | Retrofit 2.9.0、Gson |
| 架构 | Lifecycle ViewModel、LiveData |
| UI | Material Design、RecyclerView、ViewPager2、CardView |
| 最低 SDK | API 24 (Android 7.0) |
| 目标 SDK | API 36 |

### 天气 API

- **接口**：高德地图开放平台 — 天气查询
- **Base URL**：`https://restapi.amap.com/`
- **文档**：[高德天气 API](https://lbs.amap.com/api/webservice/guide/api/weatherinfo)

---

## 环境要求

- **Android Studio**：推荐 Hedgehog (2023.1.1) 或更高版本
- **JDK**：11+
- **Gradle**：8.x（项目使用 Version Catalog）
- **Android SDK**：API 24 ~ 36

---

## 快速开始

### 方式一：直接使用源码（推荐）

1. 克隆仓库后，用 Android Studio 打开 `Day1`、`Day2` 或 `Day3` 目录
2. Day3 需先配置 `local.properties`（见上文）
3. 等待 Gradle Sync 完成
4. 连接模拟器或真机，点击 Run

### Day3 额外配置：高德 API Key

Day3 通过 `local.properties` 读取 API Key，**不会写入源码仓库**：

1. 前往 [高德开放平台](https://lbs.amap.com/) 注册并创建应用
2. 获取 **Web 服务** 类型的 API Key
3. 复制配置模板并填入真实 Key：
   ```bash
   cd Day3
   copy local.properties.example local.properties   # Windows
   # cp local.properties.example local.properties  # macOS/Linux
   ```
4. 编辑 `Day3/local.properties`，设置 `AMAP_API_KEY=你的Key`


---

## 构建与运行

```bash
# 进入 Day3 项目目录
cd Day3

# 编译 Debug APK
./gradlew assembleDebug

# 安装到已连接设备
./gradlew installDebug
```

Windows 下使用 `gradlew.bat` 代替 `./gradlew`。


## 学习路径

```
Day1（Activity 跳转）
    ↓
Day2（SQLite + 登录 UI）
    ↓
Day3（Retrofit + MVVM + Fragment 天气 App）
```

---

## 已知问题

- `Day3/app/build.gradle.kts` 中存在部分重复依赖声明，不影响运行，后续可整理

---

## 作者

**CHOSEN1-FS**

- GitHub：[@CHOSEN1-FS](https://github.com/CHOSEN1-FS)

---

## License

本项目为课程学习作业，仅供学习交流使用。
