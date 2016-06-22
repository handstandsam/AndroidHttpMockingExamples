## Setup

- Clone the project
- Build the project
- Run the test present in AndroidHttpMockingExamples/app/src/androidTest/java/com/handstandsam/httpmocking/tests/wiremock/WireMockApplicationTestCase.java.
- The BKS type keystore (android_wiremock_keystore) included should be uploaded in the /sdcard/ directory of the emulator. To do this, make sure you have emulator started. Then run
  open a terminal, and run `adb push <filename> /sdcard/. `
- I have not been able to create a valid keystore myself. This is something I am using from Marius . See [here](https://groups.google.com/forum/#!topic/wiremock-user/oDExHctRPCc)
- Uncomment line number 80 in AndroidHttpMockingExamples/app/src/androidTest/java/com/handstandsam/httpmocking/tests/wiremock/WireMockApplicationTestCase.java to make sure that
 http protocol also works for same url.

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
