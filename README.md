# NAGI Launcher — Android

NAGI Launcher is an Android-first Minecraft: Java Edition launcher project.

## Requirements
- Android Studio
- Android SDK Platform 36
- JDK 17
- Android device or emulator

## Build
Open the project in Android Studio and build the debug APK, or use the GitHub Actions workflow. Each push to `main` starts an Android build. Download the `NAGI-LAUNCHER-debug-apk` artifact from the latest successful run.

## Version 0.2.0
- Keeps the black/red Jetpack Compose launcher UI.
- Adds a runtime setup screen.
- Detects the device ABI, total RAM and free app storage.
- Prepares private app folders for Minecraft data and runtime files.
- Adds navigation to the runtime, versions and mods sections.

## Important current limitations
This milestone is runtime preparation only. It does NOT yet contain an Android Java runtime, LWJGL/native graphics renderer, Microsoft OAuth, Minecraft version downloader, or the Minecraft game files. The Play action therefore opens Runtime Setup rather than pretending the game can launch.

## Runtime integration direction
A compatible Android launcher engine is required; a desktop Java runtime cannot simply be copied into an APK. PojavLauncher has been succeeded by Amethyst. Before integrating any engine code or binaries, review its current source, build instructions and LGPL obligations, then preserve required notices and source-offer requirements.

Never ask users for Microsoft passwords. Future account login must use Microsoft's supported OAuth flow and secure token handling.
