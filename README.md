# Kerala AI Camera Tracker

The Kerala AI Camera Tracker is a mobile application designed to help users track AI cameras placed
on roads in Kerala. The app notifies users when they are near an AI camera and triggers an alert to
notify them about the presence of the camera.
App is still in development mode

<div style="display: grid; grid-template-columns: repeat(3, 1fr); gap: 20px;">
<div style="text-align: center;">
        <h3>TRACKER ON/OFF</h3>
        <img src="https://github.com/rameshvoltella/KeralaAICameraTracker/blob/beta/appfiles/one.jpeg?raw=true" alt="Tracker OFF" style="width: 40%; height: 40%;">
        <img src="https://github.com/rameshvoltella/KeralaAICameraTracker/blob/beta/appfiles/teo.jpeg?raw=true" alt="Tracker ON" style="width: 40%; height: 40%;">
    </div>
<div style="text-align: center;">
        <h3>LOCATION LIST / MAP VIEW</h3>
     <img src="https://github.com/rameshvoltella/KeralaAICameraTracker/blob/beta/appfiles/three.jpeg?raw=true" alt="Location List" style="width: 40%; height: 40%;">
    <img src="https://github.com/rameshvoltella/KeralaAICameraTracker/blob/beta/appfiles/four.jpeg?raw=true" alt="MAP VIEW" style="width: 40%; height: 40%;">

</div>
<div style="text-align: center;">
        <h3>NOTIFICATION ALERT</h3>
        <img src="https://github.com/rameshvoltella/KeralaAICameraTracker/blob/beta/appfiles/five.jpeg?raw=true" alt="NOTIFICATION 1" style="width: 40%; height: 40%;">
    <img src="https://github.com/rameshvoltella/KeralaAICameraTracker/blob/beta/appfiles/six.jpeg?raw=true" alt="NOTIFICATION 2" style="width: 40%; height: 40%;">

</div>


</div>

## Features

- **Camera Tracking**: The app tracks the locations of AI cameras placed on roads in Kerala.
- **Proximity Alert**: When the user approaches an AI camera, the app sends a notification to alert
  the user.
- **Camera Information**: Users can view information about the AI cameras

## Technologies Used

- **Kotlin**: The app is built using Kotlin, a modern programming language for Android development known for its conciseness and safety features.
- **Jetpack Compose**: Jetpack Compose is used for building the user interface (UI) of the app. It offers a modern and declarative way to construct UIs, making it easier to develop and maintain complex UIs.
- **WorkManager**: WorkManager is used for background processing tasks such as updating camera locations or sending notifications. It provides a flexible way to schedule and execute deferrable, asynchronous tasks.
- **Geofencing**: Geofencing technology is utilized to detect when the user enters or exits predefined geographical boundaries (such as near AI camera locations). This allows the app to trigger proximity alerts accurately.
- **Coroutines**: Coroutines are used for managing asynchronous operations, such as fetching camera information from a remote server or handling database operations. They simplify asynchronous programming by providing structured concurrency.
- **MVVM Architecture**: The app follows the Model-View-ViewModel (MVVM) architectural pattern, separating the presentation layer from the business logic and data layer. This promotes better code organization, testability, and maintainability.
- **Navigation Component**: Navigation Component is used for implementing navigation between different screens or destinations within the app. It provides a consistent and predictable way to navigate while handling fragment transactions and back stack management.


## Installation

The Kerala AI Camera Tracker app is available for Android

### Android

1. Download the APK file from
   here [DOWNLOAD](https://github.com/rameshvoltella/KeralaAICameraTracker/raw/beta/appfiles/apk/a1_cam_alpha_build%231.apk).
2. Enable installation from unknown sources in your device settings.
3. Install the APK file on your Android device.

## TROPHY

[![trophy](https://github-profile-trophy.vercel.app/?username=rameshvoltella)](https://github.com/ryo-ma/github-profile-trophy)

## Contributing

I am welcome contributions from the community to improve the Kerala AI Camera Tracker app.

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/improvement`).
3. Make your changes and commit them (`git commit -am 'Add feature/improvement'`).
4. Push to the branch (`git push origin feature/improvement`).
5. Create a new Pull Request.

