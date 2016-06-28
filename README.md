## Setup

- Clone the project
- Build the project, preferrably in Android studio.
- This project uses the wiremock jar to be stored in build/libs folder. I have been working with the `wiremock-2.0.8-beta.jar`. The newest version (`wiremock-2.1.0-beta.jar` is failing on android because of [this](https://groups.google.com/forum/#!topic/wiremock-user/qDy0eZ2RjbI) issue)
- UPDATE - June 28 - wiremock-2.1.1-beta.jar is added in the repo.
- Run the test present in _AndroidHttpMockingExamples/app/src/androidTest/java/com/handstandsam/httpmocking/tests/wiremock/WireMockApplicationTestCase.java._
- *If you are testing HTTPS* : The BKS type keystore (android_wiremock_keystore) included should be uploaded in the `/sdcard/`  directory of the emulator. To do this, make sure you have emulator started. Then run
  open a terminal, and run
  ```
  adb push <filename> /sdcard/.
  ```
- You can also generate your own keystore using http://www.keystore-explorer.org/
- If testing HTTPS, uncomment line 49 and 81 in _AndroidHttpMockingExamples/app/src/androidTest/java/com/handstandsam/httpmocking/tests/wiremock/WireMockApplicationTestCase.java_

## AndroidHttpMockingExamples ##

**These are examples by @HandstandSam - http://handstandsam.com to allow you to mock out external HTTP services on and Android device itself.**

## Android "androidTest" Examples for: ##
* WireMock 2.0.8-beta (Android support working starting in 2.0.8-beta)
* okhttp MockWebServer

### Links ###
* WireMock Android Issue regarding Android Support - https://github.com/tomakehurst/wiremock/issues/53
* WireMock - http://wiremock.org/
* WireMock GitHub 2.0-beta branch - https://github.com/tomakehurst/wiremock/tree/2.0-beta
* okhttp MockWebServer on GitHub - https://github.com/square/okhttp/tree/master/mockwebserver

### Credits ###
The Sample Application and Espresso Testing Logic is from the following repositories:
* https://github.com/IgorGanapolsky/weatherview
* https://github.com/mutexkid/weatherview
* https://github.com/mike011/Wiremock_Android_Example

Thanks @yogurtearl for tips about how he got Wiremock 1.x to work on Android.

