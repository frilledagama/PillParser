# Pill Parser | MedHacks2020

Android native application to read a pill label and save the information on a mobile device for translation, TTS and push notifications.

## Installation

Clone this repository and import into Android Studio

```
git clone git@github.com:frilledagama/PillParser.git
```

## Pre-requisites

Android SDK v29

Android Build Tools v29.0.3

## Screenshots

![LogIn View](https://github.com/frilledagama/PillParser/blob/master/login_image.jpg?raw=true)

## External Dependancies
- Firebase for analytics, firestore and user authentication, ML(Vision)

- ML Kit Text Recognition for Android

## Issues

OCR functionality no longer works due to the following error:

```
firebase-ml-vision 24.1.0 isn't compatible with google-service plugin: 4.3.3
```
 ### TODO:

 Things that were planned but not completed for the hackathon:
 - TTS
 - OCR 
 - User Registration
 - Translation
 - Display Firebase data as CardViews
 - Save Rx info

