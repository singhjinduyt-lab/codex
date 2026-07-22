# JARVIS AI Launcher

A Kotlin, Jetpack Compose, Material 3 Android launcher prototype using MVVM, Hilt, Room, DataStore, Coroutines, and Flow.

## Step 2: Gradle files

The project uses Gradle Kotlin DSL with current stable Android tooling as of July 22, 2026:

- Android Gradle Plugin 9.3.0
- Kotlin 2.2.20
- KSP 2.2.20-2.0.3
- Compose BOM 2026.06.00
- Hilt 2.57.1
- Compile/target SDK 36 and min SDK 29 for Android 10+

## Build

Use JDK 17 and run:

```bash
gradle :app:assembleDebug
```

The app is packaged as `com.jarvis.launcher` and registers a HOME intent so Android can offer it as a launcher.

## Step 3: Navigation

Navigation is centralized in `JarvisDestination` and `JarvisNavigationActions` so feature screens use typed destinations instead of duplicating route strings or `NavOptions`. `JarvisNavHost` owns the graph and currently wires Home, Search, App Drawer, AI Chat, and Settings.
