# CloudKitchens App

CloudKitchens is an Android application designed to manage orders, provide real-time statistics, and more. This project demonstrates the use of various Android libraries and follows modern Android development practices.

## Table of Contents

- [Features](#features)
- [Architecture](#architecture)
- [Libraries Used](#libraries-used)
- [Prerequisites](#prerequisites)
- [Build Instructions](#build-instructions)
- [Running the App](#running-the-app)
- [Testing](#testing)
- [License](#license)

## Features

- Order management with real-time updates
- Statistics visualization using charts
- Modern Android architecture with MVVM Clean
- Dependency injection with Dagger 2
- Networking with Retrofit
- Local data storage with Room
- Coroutine-based concurrency

## Architecture

The app follows the MVVM Clean architecture pattern, which is divided into three main layers: Data, Domain, and Presentation.

### Data Layer

This layer is responsible for handling data operations. It includes local data storage (Room), remote data fetching (Retrofit), and data mapping.

- **Entities**: Database models used by Room.
- **DTOs**: Data Transfer Objects for network responses.
- **Repositories**: Provide a clean API for data access to the domain layer.
- **Data Sources**: Manage local and remote data sources.

### Domain Layer

This layer contains the business logic of the application. It is independent of any specific data sources or frameworks, making it highly reusable and testable.

- **Models**: Business models used throughout the application.
- **Use Cases**: Encapsulate specific business logic and data manipulation operations.

### Presentation Layer

This layer handles the UI and user interaction. It includes ViewModels, which interact with the use cases to fetch and manipulate data, and Views (Activities, Fragments), which display the data.

- **ViewModels**: Provide data to the UI and handle user interactions.
- **Views**: Activities and Fragments that display data and interact with the user.
- **Adapters**: Handle the presentation of data in RecyclerViews.

## Libraries Used

- **Room**: For local data storage.
- **Retrofit**: For network calls.
- **Kotlin Coroutines**: For concurrency.
- **Dagger 2**: For dependency injection.
- **AndroidX**: Core libraries for modern Android development.
- **Lottie**: For animations.
- **MPAndroidChart**: For charting and graphing.
- **Material Components**: For modern UI components.

## Prerequisites

- **Android Studio**: Version 4.0 or higher.
- **Java 17**: The project uses Java 17 compatibility.
- **Internet Connection**: Required for fetching data from the network.

## Build Instructions

1. **Extract the zip file**:
   - Locate the zip file containing the project.
   - Extract the contents to a directory of your choice.

2. **Open the project in Android Studio**:
   - Open Android Studio.
   - Select `File` -> `Open...`.
   - Navigate to the directory where you extracted the project and select it.

3. **Sync the project**:
   - Android Studio will prompt you to sync the project with Gradle files. Click on `Sync Now`.

4. **Set up the Emulator or Connect a Device**:
   - You can use an Android emulator or a real device to run the app.
   - Make sure the emulator is running or the device is connected via USB.

5. **Build and Run the Project**:
   - Click on the `Run` button in Android Studio or use the shortcut `Shift + F10`.
   - Select the target device or emulator.

## Running the App

1. **Launch the app** on your device or emulator.
2. **Navigate** through the different features using the bottom navigation bar.
3. **Observe** real-time updates in the order statistics and other features.

## Server Issues on Apple Silicon Chip

If you are using an Apple Silicon chip and find that the provided server does not work, you may encounter issues running the server. To resolve this, you can try the following:

- **Use Windows for running the server**:
   1. Run the server on a Windows machine.
   2. Use the `ipconfig` command in Windows to find the IPv4 address.
   3. Replace the server address in your `buildConfig` with this IPv4 address.

- **Use localhost** if feasible.

## Testing

1. **Unit Tests**:
   - To run unit tests, use the following command in the terminal:
     ```sh
     ./gradlew test
     ```

