import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

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
            }
        }
    }

    sourceSets {
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(libs.compress)
            implementation(libs.xz)
        }
    }
}
