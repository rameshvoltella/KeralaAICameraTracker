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
    <img src="https://github.com/rameshvoltella/KeralaAICameraTracker/blob/beta/appfiles/eight.jpeg?raw=true" alt="MAP VIEW" style="width: 40%; height: 40%;">

</div>
<div style="text-align: center;">
        <h3>NOTIFICATION ALERT</h3>
        <img src="https://github.com/rameshvoltella/KeralaAICameraTracker/blob/beta/appfiles/five.jpeg?raw=true" alt="NOTIFICATION 1" style="width: 40%; height: 40%;">
    <img src="https://github.com/rameshvoltella/KeralaAICameraTracker/blob/beta/appfiles/sev.jpeg?raw=true" alt="NOTIFICATION 2" style="width: 40%; height: 40%;">

</div>


</div>

## Features

- **Camera Tracking**: The app tracks the locations of AI cameras placed on roads in Kerala.
- **Proximity Alert**: When the user approaches an AI camera, the app sends a notification to alert
  the user.
- **Camera Information**: Users can view information about the AI cameras

## How It Works

So, picture this: you're cruising down the road, minding your own business, when suddenly... beep beep! You're entering the camera zone! But fear not, because our trusty app is here to save the day.

Here's the lowdown on how it all works:

1. **Location, Location, Location**: First things first, we've got to know where you are. Our app grabs your current location and starts sorting through the nearest 50 cameras faster than you can say "cheese"!

2. **Frequent Check-ins**: Every 15 minutes, our app gives a little nudge to your location, making sure we're always on top of things. And we're not just talking about any old nudge, but a smooth, efficient check-in that won't bog down your system.

3. **Camera Radar**: Once we've got our eyes on the road, we start narrowing down the next 50 cameras in your vicinity. It's like having your very own camera radar, but way cooler.

4. **Geo-Fencing Alert**: Now, here's where the magic happens. When your vehicle saunters into the 500-meter radius of a camera, our app sends out a friendly alert, letting you know you've entered the camera zone. It's like having a personal navigator whispering in your ear, but with a touch of whimsy.

5. **OSM to the Rescue**: Say goodbye to those money-eating map apps, because we've got OSMDroid on our side. With its nifty features, we can pinpoint camera locations, navigate with ease, and calculate distances like a boss.

6. **Speedometer Surprise**: Oh, and did we mention the speed alerts? Yep, our app keeps an eye on your speed too, making sure you stay within the limits and avoid any pesky fines.

So there you have it, folks! With our AI camera tracker app, safety meets efficiency, sprinkled with a generous dose of fun. Happy cruising!

## Technologies Used

- **Kotlin**: The app is built using Kotlin, a modern programming language for Android development known for its conciseness and safety features. ([Kotlin GitHub Repository](https://github.com/JetBrains/kotlin))
- **Jetpack Compose**: Jetpack Compose is used for building the user interface (UI) of the app. It offers a modern and declarative way to construct UIs, making it easier to develop and maintain complex UIs. ([Jetpack Compose GitHub Repository](https://github.com/androidx/androidx/tree/androidx-main/compose))
- **WorkManager**: WorkManager is used for background processing tasks such as updating camera locations or sending notifications. It provides a flexible way to schedule and execute deferrable, asynchronous tasks. ([WorkManager Documentation](https://developer.android.com/topic/libraries/architecture/workmanager))
- **Geofencing**: Geofencing technology is utilized to detect when the user enters or exits predefined geographical boundaries (such as near AI camera locations). This allows the app to trigger proximity alerts accurately. ([Android Location and Geofencing Documentation](https://developer.android.com/training/location/geofencing))
- **Coroutines**: Coroutines are used for managing asynchronous operations, such as fetching camera information from a remote server or handling database operations. They simplify asynchronous programming by providing structured concurrency. ([Kotlin Coroutines GitHub Repository](https://github.com/Kotlin/kotlinx.coroutines))
- **MVVM Architecture**: The app follows the Model-View-ViewModel (MVVM) architectural pattern, separating the presentation layer from the business logic and data layer. This promotes better code organization, testability, and maintainability. ([Android Architecture Components Documentation](https://developer.android.com/topic/libraries/architecture))
- **Navigation Component**: Navigation Component is used for implementing navigation between different screens or destinations within the app. It provides a consistent and predictable way to navigate while handling fragment transactions and back stack management. ([Navigation Component Documentation](https://developer.android.com/guide/navigation))
- **osmdroid**: osmdroid is used as the map library for displaying maps and visualizing AI camera locations. ([osmdroid GitHub Repository](https://github.com/osmdroid/osmdroid))

## MY GITHUB TROPHY

[![trophy](https://github-profile-trophy.vercel.app/?username=rameshvoltella)](https://github.com/ryo-ma/github-profile-trophy)


## Installation

The Kerala AI Camera Tracker app is available for Android

### Android

1. Download the APK file from
   here [DOWNLOAD](https://github.com/rameshvoltella/KeralaAICameraTracker/tags)
2. Enable installation from unknown sources in your device settings.
3. Install the APK file on your Android device.

## Contributing

I am welcome contributions from the community to improve the Kerala AI Camera Tracker app.

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/improvement`).
3. Make your changes and commit them (`git commit -am 'Add feature/improvement'`).
4. Push to the branch (`git push origin feature/improvement`).
5. Create a new Pull Request.

