// Archivo de nivel proyecto (no el del módulo app)

plugins {
    id("com.android.application") version "8.4.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.24" apply false
}

tasks.register("clean", Delete::class) {
    delete(layout.buildDirectory)
}
