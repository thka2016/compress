import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    id("maven-publish")
}

group = "com.hand.compress"
version = "1.0.240319"

kotlin {
    jvm {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        mainRun {
            mainClass.set("com.hand.compress.MainKt")
        }
    }

    mingwX64 {
        binaries {
            executable {
                baseName = "compress"
                entryPoint("com.hand.compress.main")
            }
        }
        compilations.getByName("main") {
            cinterops {
                val bzip2 by creating
                val bzip3 by creating
                val xz by creating
                val lz4 by creating
            }
            kotlinOptions.freeCompilerArgs = listOf(
//                "-include-binary", "$buildDir/libui/${konanTarget.name}/libui.a"
                "-include-binary", "${projectDir}/src/cpp/lib/mingwX64/libbz2_static.a",
                "-include-binary", "${projectDir}/src/cpp/lib/mingwX64/libbzip3.a",
                "-include-binary", "${projectDir}/src/cpp/lib/mingwX64/liblzma.a",
                "-include-binary", "${projectDir}/src/cpp/lib/mingwX64/liblz4.a",
            )
        }
    }

    sourceSets {
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(libs.compress)
            implementation(libs.xz)
            implementation(libs.lz4.java)
        }
    }
}
