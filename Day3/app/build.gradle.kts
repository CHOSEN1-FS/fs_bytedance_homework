import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

android {
    namespace = "com.example.day3"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.day3"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String",
            "AMAP_API_KEY",
            "\"${localProperties.getProperty("AMAP_API_KEY", "")}\""
        )
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.google.code.gson:gson:2.10.1")


    //add by austin in 23025.12.11
    // ========== 需要添加的依赖 ==========

    // 1. Retrofit2 网络库
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // 2. Retrofit2 的 Gson 转换器
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // 3. ViewModel (生命周期管理)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

    // 4. LiveData (数据观察)
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")

    // 5. CardView (卡片布局)
    implementation("androidx.cardview:cardview:1.0.0")

    // 6. RecyclerView (列表显示)
    implementation("androidx.recyclerview:recyclerview:1.3.1")

    // ViewPager2
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    // Material Design（如果还没有）
    implementation("com.google.android.material:material:1.9.0")



    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    // Retrofit2 网络请求库
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // Gson 库
    implementation("com.google.code.gson:gson:2.10.1")
    // Lifecycle 组件
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
}