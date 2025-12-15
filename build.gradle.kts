plugins {
    id("com.android.application") version "8.1.4" apply false
    id("com.android.library") version "8.1.4" apply false

    id("org.jetbrains.kotlin.android") version "1.9.25" apply false
    id("org.jetbrains.kotlin.multiplatform") version "1.9.25" apply false
    id("org.jetbrains.kotlin.jvm") version "1.9.25" apply false
    id("org.jetbrains.kotlin.plugin.spring") version "1.9.25" apply false
    id("org.jetbrains.kotlin.plugin.jpa") version "1.9.25" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.25" apply false
    id("com.google.devtools.ksp") version "1.9.25-1.0.20" apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
    id("org.jetbrains.compose") version "1.7.1" apply false
}

// Desabilitar verificação do Yarn lock file
rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin> {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension>().lockFileDirectory =
        rootProject.projectDir.resolve("kotlin-js-store")
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension>().lockFileName = "yarn.lock"
}

tasks.whenTaskAdded {
    if (name == "kotlinStoreYarnLock") {
        enabled = false
    }
}

// Removido o registro da task 'clean' para evitar conflito com o Gradle
