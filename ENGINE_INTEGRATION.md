# NAGI Launcher v0.3.0 — Android engine integration plan

## Current implementation status

NAGI currently has a Jetpack Compose launcher UI and Android device diagnostics. It can report the device ABI, total RAM and available app storage, and create private app data directories.

It does **not** yet contain a Minecraft Java runtime, LWJGL, a graphics backend, Minecraft asset/version downloading, Microsoft authentication, or a game process launcher. This document is an integration plan, not a claim that those components are installed.

## Upstream engine research

The current PojavLauncher successor is **Amethyst-Android**:
https://github.com/AngelAuraMC/Amethyst-Android

Its upstream README documents:
- Recursive repository cloning because native components are included as submodules.
- A prebuilt multi-architecture JRE artifact for the quick-build path.
- Custom LWJGL components and a GLFW stub build.
- The Android launcher Gradle task: `:app_pojavlauncher:assembleDebug`.
- A GNU LGPLv3 project license.

Upstream build instructions:
1. `git clone --recursive https://github.com/AngelAuraMC/Amethyst-Android.git`
2. `./gradlew :app_pojavlauncher:assembleDebug`

For a detailed runtime build, upstream documents obtaining its `jre8-pojav` CI artifact and placing its contents in `app_pojavlauncher/src/main/assets/components/jre/`, plus building `:jre_lwjgl3glfw:build`.

## Integration decision

Do not copy random JARs or native libraries into this app and do not assume a desktop JRE works on Android. The runtime, native libraries, renderer, input bridge, game surface lifecycle, and launcher process interface must be integrated as one compatible system.

Before importing or adapting upstream code:
- Pin a reviewed upstream commit/tag.
- Preserve LGPLv3 license notices and comply with source-code/relinking obligations for distributed builds.
- Review every submodule and dependency license; upstream lists dependencies with different licenses and some with unknown license status.
- Keep upstream engine code in a clearly isolated module or maintained fork so updates can be merged and audited.
- Do not bundle Minecraft game files or bypass Mojang/Microsoft account licensing.

## Planned milestones

### 0.3.x — Engine integration foundation
- Choose and pin the Amethyst upstream revision.
- Build its Android project independently in CI.
- Verify the runtime and native library artifacts for `arm64-v8a`.
- Define the NAGI-to-engine launch contract and lifecycle handoff.
- Keep NAGI UI and engine modules separated.

### 0.4.x — First controlled launch
- Integrate a compatible runtime and renderer.
- Add secure, official Microsoft authentication.
- Download version metadata and game files from official Minecraft endpoints.
- Launch only a legally licensed account's selected version.
- Test on physical ARM64 devices and capture logs for failures.

### Later
- Version profiles, Fabric/Forge support, controls, memory settings, and performance tuning.

## Build validation

The NAGI GitHub Actions workflow currently builds the NAGI Compose application. A successful NAGI APK build does not prove that the Amethyst engine has been integrated or that Minecraft can launch.
