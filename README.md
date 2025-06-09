# Translator KMP

Translator KMP is a Kotlin Multiplatform Mobile application that allows users to translate text between different languages.

## Technologies Used

This project leverages a variety of modern technologies to deliver a seamless cross-platform experience.

### Shared Module
The core logic of the application resides in the shared Kotlin Multiplatform module, utilizing:
- **Kotlin Coroutines**: For managing asynchronous operations.
- **Ktor**: For making network requests to translation APIs.
- **SQLDelight**: For local database storage of translation history or saved phrases.
- **Kotlin Serialization**: For handling JSON data.

### Android App
The Android application is built using:
- **Jetpack Compose**: For creating the user interface.
- **Coil**: For efficient image loading.
- **Hilt**: For dependency injection in the Android-specific parts of the app.
- **Android Architecture Components**: Likely uses ViewModel, LiveData/Flow for UI logic.

### iOS App
The iOS application is built using:
- **SwiftUI**: For creating the user interface.
- **Combine**: For reactive programming (commonly used with SwiftUI).

## Project Structure

The project is organized into three main modules:

- **`shared`**: This Kotlin Multiplatform module contains the core business logic, data handling, networking, and database interactions. It's designed to be platform-agnostic and is shared between the Android and iOS applications.
- **`androidApp`**: This module contains the Android-specific UI, platform integrations, and dependencies. It utilizes the `shared` module for its core functionality.
- **`iosApp`**: This module contains the iOS-specific UI (built with SwiftUI), platform integrations, and dependencies. It also utilizes the `shared` module for its core functionality.

## How to Build

To build and run this project, you'll need the following prerequisites:

- **Android Studio**: For the Android app and the shared module. (Specify version if known, e.g., Hedgehog or newer)
- **Xcode**: For the iOS app. (Specify version if known, e.g., 15 or newer)
- **Kotlin Multiplatform Mobile plugin**: Ensure it's installed in Android Studio.
- **Java Development Kit (JDK)**: Version 17 or higher is recommended.

### Android App

1.  Open the project in Android Studio.
2.  Let Gradle sync and download dependencies.
3.  Select the `androidApp` run configuration.
4.  Choose an emulator or connect a physical Android device.
5.  Click the "Run" button.

### iOS App

1.  Navigate to the `iosApp` directory in your terminal.
2.  You might need to install CocoaPods dependencies if it's the first time or if dependencies have changed:
    ```bash
    pod install
    ```
3.  Open the `iosApp.xcworkspace` file in Xcode.
4.  Select a simulator or connect a physical iOS device.
5.  Click the "Build and Run" button in Xcode.

**Note**: Ensure your environment (Android SDK, Kotlin version, CocoaPods, etc.) is set up correctly for KMM development.

## Features

This application aims to provide a simple and intuitive interface for translating text and voice.

- **Text Translation**: Translate text between a wide variety of supported languages.
- **Voice-to-Text**: Input text for translation using your device's microphone.
- **Language Selection**: Easily choose your source and target languages.
- **Swap Languages**: Quickly swap the selected source and target languages.
- **Translation History**: View and manage your past translations.
- **Cross-Platform**: Works on both Android and iOS devices, sharing common logic.
