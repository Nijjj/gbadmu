# Y2KBoy Android

Y2KBoy is a premium Android frontend for the mGBA core with a Y2K-inspired handheld console UI.

This module contains:
- A Compose-based library, game screen, settings surface, and in-game overlay.
- A JNI bridge placeholder for mGBA integration.
- A native CMake entrypoint for Android builds.
- GitHub Actions workflows for CI and release APK generation.

## Layout

- `app/src/main/java/app/y2kboy/`: Kotlin app code.
- `app/src/main/cpp/`: JNI bridge and native build entrypoint.
- `.github/workflows/`: CI and release workflows.

## Build model

APK builds are intended to run in GitHub Actions only. The repository does not rely on local APK builds.

## Current state

This is a production-shaped scaffold:
- Library, game, and settings screens exist.
- Native integration is stubbed behind `EmulatorBridge`.
- Save states, cheats, rewind, and advanced renderer work are modeled in the app state and menu structure, but not fully wired yet.

