# NAGI Launcher — Android starter

This is the first Android UI milestone for NAGI Launcher.

## Requirements
- Android Studio
- Android SDK Platform 36
- JDK 17 (Android Studio's bundled JBR is recommended)
- Android device with USB debugging enabled, or an Android emulator

## Open locally
1. Clone or download this repository.
2. Open the project folder in Android Studio.
3. Allow Gradle sync and install any missing SDK components.
4. Run the app on an emulator or connected Android phone.

## Build APK locally
Use Android Studio's **Build > Build Bundle(s) / APK(s) > Build APK(s)**.

## Automatic APK build
Every push to `main` starts the Android Build workflow. Open the repository's **Actions** tab, select the latest successful run, and download the `NAGI-LAUNCHER-debug-apk` artifact.

## Current scope
The app contains a Compose dashboard and navigation selection. The Play button deliberately shows an explanatory dialog: Minecraft Java runtime, renderer, account authentication and game launching are NOT implemented yet.

Do not collect or store Microsoft passwords. Future authentication must use Microsoft's supported OAuth flow and secure token storage.
