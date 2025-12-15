plugins {
    kotlin("multiplatform")
}

kotlin {
    js(IR) {
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
            webpackTask {
                mainOutputFileName = "webApp.js"
            }
            runTask {
                mainOutputFileName = "webApp.js"
            }
        }
        binaries.executable()
        useCommonJs()
    }

    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(project(":shared"))

                // Kotlinx
                implementation("org.jetbrains.kotlinx:kotlinx-html:0.9.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

                // Serialization para JSON
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
            }
        }
    }
}

// Desabilitar verificação do Yarn lock file
tasks.whenTaskAdded {
    if (name == "kotlinStoreYarnLock") {
        enabled = false
    }
}
