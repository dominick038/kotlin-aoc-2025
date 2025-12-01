plugins {
    kotlin("multiplatform") version "2.1.20"
}

repositories {
    mavenCentral()
}

kotlin {
    linuxX64 {
        binaries {
            (1..12).forEach { day ->
                val dayStr = day.toString().padStart(2, '0')
                executable("day$dayStr") {
                    entryPoint = "day$dayStr.main"
                    baseName = "day$dayStr"
                }
            }
        }
    }

    sourceSets {
        val linuxX64Main by getting {
            kotlin.srcDirs((1..12).map { "day_${it.toString().padStart(2, '0')}" } + "shared")
        }
    }
}

// Helper tasks: ./gradlew run01, ./gradlew run02, etc.
(1..12).forEach { day ->
    val dayStr = day.toString().padStart(2, '0')
    tasks.register<Exec>("run$dayStr") {
        group = "aoc"
        description = "Build and run day $dayStr"
        dependsOn("linkDay${dayStr}DebugExecutableLinuxX64")
        commandLine("./build/bin/linuxX64/day${dayStr}DebugExecutable/day$dayStr.kexe")
    }
}
