# Eloquent-Android
Repository for our Android App - Written in Kotlin
<br>


![Video](https://firebasestorage.googleapis.com/v0/b/startin-1efcf.appspot.com/o/eloquentGithubStorage%2Fapp-flow.gif?alt=media&token=bf1605ca-6db7-474c-8fd1-e5f5df8724f7)


## Setup Instructions
To run the Eloquent Android app on your Android smartphone, clone this repository and open it with [Android Studio](https://developer.android.com/studio).

When Android Studio is open, you can click on the green play button in the top right corner of the window.
You may have to make some additional setup on your phone if this is your first time running an Android app on your phone. You can get help [here](https://developer.android.com/training/basics/firstapp/running-app).


## Documentation

![Architecture](https://firebasestorage.googleapis.com/v0/b/startin-1efcf.appspot.com/o/eloquentGithubStorage%2FEloquentArchitecture.png?alt=media&token=7f82afb8-3974-4f1d-aa44-360188c3ccef)

The App makes use of the [MVVM](https://developer.android.com/jetpack/docs/guide) architecture pattern by delegating the business logic of the UI to a Viewmodel which requests data from a reposity. The repository gets the raw data from one or multiple datasources, in this application from a [Room](https://developer.android.com/topic/libraries/architecture/room?gclid=EAIaIQobChMI5KquzdiS6QIVkkTTCh1sDwroEAAYASAAEgIwyfD_BwE&gclsrc=aw.ds) database.


## Team

### Developer
- Martin Gressler
- Maurice Gerhardt

### UI/UX Design
- Jonathan Caspari
