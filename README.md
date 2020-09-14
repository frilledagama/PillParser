# PicPill

Android native application to read a pill label and save the information on a mobile device for translation, TTS and push notifications.
Submitted to MedHacks2020 Aging in Palce track. 2nd place winner. [Devpost page.](https://devpost.com/software/picpill)

## Installation

Clone this repository and import into Android Studio

```
git clone git@github.com:frilledagama/PillParser.git
```

## Pre-requisites

Android SDK v29

Android Build Tools v29.0.3

## Screenshots

![Image View](./img/readme_image.png?raw=true)

## External Dependancies
- Firebase for analytics, firestore and user authentication, ML(Vision)

- ML Kit Text Recognition for Android

## Issues
Cloud OCR functionality no longer working due to the following error:

```
firebase-ml-vision 24.1.0 isn't compatible with google-service plugin: 4.3.3
```

## Credits

Log-In Layout: [buildbro](https://gist.github.com/buildbro)

## TODO:

 Things that were planned but not completed for the hackathon:
 - TTS
 - Translation
 - Save Rx info

