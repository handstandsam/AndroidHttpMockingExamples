## Setup

-Clone the project
-build the project 
- run the test present in AndroidHttpMockingExamples/app/src/androidTest/java/com/handstandsam/httpmocking/tests/wiremock/WireMockActivityInstrumentationTestCase2.java
- You will need certificate in ./certs to run the https
- also you will need to mount the /certs/test.bks to /sdcard/. to mount the file to emulator . Run `adb push test.bks /sdcard/.`
- Run the test again and see `adb logcat` to see the SSLHandshake error

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
