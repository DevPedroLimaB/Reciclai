plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

kotlin {
    jvm {
        // CORRIGIDO: Configura Java 8 para compatibilidade com Android
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
        java {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
    }

    js(IR) {
        browser()
        binaries.executable()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                // Ktor com versões compatíveis com Kotlin 1.9.25
                implementation("io.ktor:ktor-client-core:2.3.7")
                implementation("io.ktor:ktor-client-content-negotiation:2.3.7")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.7")

                // Coroutines compatível
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

                // CORRIGIDO: Serialization compatível com Kotlin 1.9.25
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-okhttp:2.3.7")
            }
        }

        val jsMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-js:2.3.7")
            }
        }
    }
}

// Configuração adicional para garantir Java 8
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

// Não declare nenhuma task 'clean' aqui, pois já existe no build.gradle.kts do projeto raiz
