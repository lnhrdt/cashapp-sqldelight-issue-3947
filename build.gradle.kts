import app.cash.sqldelight.gradle.SqlDelightExtension

plugins {
    kotlin("jvm") version "1.8.10"
    application
    id("app.cash.sqldelight") version "2.0.0-alpha05"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("app.cash.sqldelight:postgresql-dialect:2.0.0-alpha05")
    implementation("app.cash.sqldelight:jdbc-driver:2.0.0-alpha05")
}

configure<SqlDelightExtension> {
    val migrationsDir = File(buildDir, "resources/main/db/migration/fancy")
    databases {
        create("FancyDatabase") {
            dialect("app.cash.sqldelight:postgresql-dialect:2.0.0-alpha05")
            packageName.set("com.example.fancy.data")
            sourceFolders.set(listOf("sql", "migrations"))
            deriveSchemaFromMigrations.set(true)
            migrationOutputDirectory.set(migrationsDir)
            migrationOutputFileFormat.set(".sql")
        }
    }
}

configure<SourceSetContainer> {
    named("main") {
        java.srcDir("src/main/sql")
        java.srcDir("src/main/migrations")
    }
}
