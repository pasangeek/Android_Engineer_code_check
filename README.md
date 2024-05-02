# Yumemi Inc . Android Engineer Code Check Assignment

## Overview

This Kotlin-based Android application allows users to search GitHub repositories by name, view detailed information about the repositories, and manage their favorites. The app provides functionality to automatically delete user search keywords when the maximum limit of 50 is reached, following a last-in-first-out (LIFO) approach. Additionally, the app dynamically switches between English and Japanese languages based on the device's language settings.

## In Night Mode
https://github.com/pasangeek/Android_Engineer_code_check/assets/12561393/a9f91491-22f6-4b12-bb6a-6e9f3c3ad97f

## Key Features

1. **Search Functionality:**
  - Users can enter keywords to search for GitHub repositories.
   - The app utilizes the GitHub API (`search/repositories`) to fetch relevant repositories based on the search query.
     
2. **Repository Details:**
   - Detailed information about each repository, including the repository name, owner icon, project language, star count, watcher count, fork count, and issue count, is displayed to users.
     
3. **Favorites Management:**
   - Users can mark repositories as favorites, allowing them to easily access and manage their preferred repositories.
   - Favorite repositories are stored locally on the device for quick retrieval.

4. **Search History:**
   - The app maintains a search history where users can view their past search keywords.
   - Users have the option to delete all search history, which is done automatically in a last-in-first-out (LIFO) manner when the maximum limit of 50 keywords is reached.

5. **Automatic Keyword Deletion:**
   - When the maximum limit of 50 search keywords is reached, the application automatically deletes older keywords to make room for new searches.
   - This ensures efficient management of search history and optimal performance.

6. **Language Localization:**
   - The app supports both English and Japanese languages.
   - The language is automatically switched based on the device's language settings, providing a localized experience for users.

## Usage

1. **Search Repositories:**
   - Enter keywords in the search bar to find GitHub repositories matching the search query.
   - Scroll through the list of search results to find relevant repositories.

2. **View Repository Details:**
   - Tap on a repository from the search results to view detailed information, including project statistics and owner details.

3. **Manage Favorites:**
   - Mark repositories as favorites by toggling the heart icon next to each repository.
   - Access favorite repositories quickly from the favorites section for future reference.

4. **View and Manage Search History:**
   - Access the search history from the settings page to view past search keywords.
   - Delete all search history with a single tap, which is automatically managed to maintain the maximum limit of 50 keywords.


## Environment

- IDE: Android Studio Iguana | 2023.2.1
- Java: VERSION_1_8
- Android Gradle Plugin: 8.2.2
- minSdk: 24
- targetSdk: 34

# Installation

1. Clone the repository:git clone https://github.com/pasangeek/Android_Engineer_code_check.git
2. Open the project in Android Studio.
3. Build and run the app on an emulator or physical device.

# Project Structure

The project is organized into the following packages:

- **data**: Contains classes responsible for data management, including data models, repositories, and data sources.
  https://github.com/pasangeek/Android_Engineer_code_check/tree/master/app/src/main/kotlin/jp/co/yumemi/android/code_check/data

- **UI**: Contains classes related to the user interface (UI), including fragments, adapters, and view models.
  https://github.com/pasangeek/Android_Engineer_code_check/tree/master/app/src/main/kotlin/jp/co/yumemi/android/code_check/ui

- **common**: Contains common classes and that are used throughout the project.
  https://github.com/pasangeek/Android_Engineer_code_check/tree/master/app/src/main/kotlin/jp/co/yumemi/android/code_check/common

- **di**: Contains dependency injection (DI) modules and components used for managing dependencies across the app.
  https://github.com/pasangeek/Android_Engineer_code_check/tree/master/app/src/main/kotlin/jp/co/yumemi/android/code_check/di

- **test**: Contains unit tests for various components of the app, organized into respective packages.
  https://github.com/pasangeek/Android_Engineer_code_check/tree/master/app/src/test/kotlin/jp/co/yumemi/android/code_check/ui

- **res**: Contains Android resources, including layout files, drawable resources, string values, and other resource files.
   https://github.com/pasangeek/Android_Engineer_code_check/tree/master/app/src/main/res
  
- **navigation**: Contains navigation-related files, including navigation graphs and destination fragments.
  https://github.com/pasangeek/Android_Engineer_code_check/tree/master/app/src/main/res/navigation

- **gradle**: Contains Gradle build scripts and configuration files for project-level and module-level settings.
  https://github.com/pasangeek/Android_Engineer_code_check/tree/master/gradle

- **app**: Contains the main application module, including the app's entry point (Application class), manifest file, and other app-specific resources.
  https://github.com/pasangeek/Android_Engineer_code_check/tree/master/app
![lou![search_after_view](https://github.com/pasangeek/Android_Engineer_code_check/assets/12561393/a9a0298a-937f-45ad-82b0-09a144e4e922)
nch_iconSpla![localizat![favourites](https://github.com/pasangeek/Android_Engineer_code_check/assets/12561393/ac375828-5ecf-4141-8fc7-2389ed3e3969)
ion](https://github.com/pasangeek/Android_Engineer_code_check/assets/12561393/b8f32ddf-9369-4278-91d7-b107fd0bd0b7)
sh](h![repoView](https://github.com/pasangeek/Android_Engineer_code_check/assets/12561393/5e496c01-c215-4d6f-8c84-27473349ae7c)
ttps://github.com/pasangeek/Android_Engineer_code_check/assets/12561393/87f21f0f-3cb4-4be0-980a-028e3b217858)
![serach_vie![setting](https://github.com/pasangeek/Android_Engineer_code_check/assets/12561393/f8727f04-6f80-4953-a96b-126d59ff8327)
w](http![text_error](https://github.com/pasangeek/Android_Engineer_code_check/assets/12561393/ff7999a8-06f4-48d5-b487-6f3af7d63911)
s://github.com/pasangeek/Android_Engineer_code_check/assets/12561393/e0e1568f-69ab-43fe-b1f4-ef052d67534e)

![no_internet_connection](https://github.com/pasangeek/Android_Engineer_code_check/assets/12561393/4cde4631-b317-445d-8513-04a6b76e717e)

  
