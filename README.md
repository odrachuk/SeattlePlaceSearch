# SeattlePlaceSearch
A sample project that uses Foursquare API to get list of places near of Seattle.

# Installation
To install the project clone this repository in your machine and then import into Android Studio or in your preferable IDE.
In case of Android Studio, right after installation you would need to wait some time till AS dowloads dependencies and Gradle finished setup of the dependencies for the project.

Once the Gradle tells you that first compilation is good, you are ready to complile application apk-file.
To complile/install project you would need to create a Virtual device via menu in AS or you would need to connect your personal device to computer. After connection or starting te virtual device you are good to tap on run-button from toolbar menu of AS.
After compilation and archiving the final apk-file would be installed at you device.

The project supports build variants: release and debug. To select what type of build you want go to the left menu-bar of AS.

The compilation is much easier to do from command line. 
Navigate to the project's root directory and run such commands in command line to build debug build:

./gradlew clean assembleDebug
adb install -r app/build/outputs/apk/debug/app-debug.apk

To compile and run the release build use similar command line instructions:
./gradlew clean assembleRelease
adb install -r app/build/outputs/apk/release/app-release.apk

# Screenshots

The Application has two Activities: Search and Details.
On the Search activity a user has input field for search and two data representation views: list and map.
1) Once beckend returns search items, they are displayed in the list. When the list has items user can see a floating action button in the right bottom corner of the screen. The button is for switching between map and list representations of search results.
2) After user first time tap at the floating button, the Map view appears with marker inside. Every marker represents an search item from the List. Tap on a Marker leads to showing a marker's popup with name of the item. User can click at name inside the popup to open the Details page.
3) Click at an item in the List or click on the text inside of a Marker on the Map opens the Details page.
4) The Details page is another Activity in the Application. On this screen user also has Map and some information below the Map.

# Technologies
I tried to use the latest tendentions and most modern approaches when write logic for the Application. The Application uses 
- Room database to keep save/unsave action for the items
- RxJava 2 at many places for reactive processing of Streams data
- Kotlin mostly for coding logic
- Dagger 2 as a DI
- Gson for parsing Json-responses from backend
- Glide for loading and caching images
- AndroidX compoments (LiveData, ViewModel, Room, Databinding)

# Todo
- There would be more job on test coverage for the Application
- There would be some improvements and bug fixes soon
