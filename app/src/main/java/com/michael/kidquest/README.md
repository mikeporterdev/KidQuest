# KidQuest

KidQuest is a gamified mobile task-management application designed for parents to manage their children's chores.

Its features include:

- Assigning and managing tasks for children.
- Giving game-based rewards upon completion of tasks.
- Offering real-life rewards for accumulated in-game currency.

## Setup

### Compilation from Source

As mentioned in the report, the application has been tested on older versions of the android ecosystem going back to JellyBean (API 16), however an up to date version of Marshmallow (API 23) is recommended for this application.

Android Studio 2.0 is recommended when compiling the application, other IDEs or older versions have not been tested and may have unknown effects.

### Dependencies

Dependencies are handled automatically by Gradle when building the project.
The app uses the following libraries:
* [GSON] - JSON to Java Object Mapper.
* [Android Support Library][androidsupport] - Google's library for common UI elements.
* [Async HTTP Client][asynchttp] - A library for easily making asynchronous HTTP requests from Android.
* [FloatingActionButton][fab] - A library for implementing floating action menus.
* [PrettyTime][prettytime] - Handles text formatting of timestamps and dates.

### Server

KidQuest is a server-connected application. 
The application connects to a REST API which handles the various workflows of the application and securely stores relevant data.

By default, the server is hosted at http://kitari.ddns.net:5000/.
The server is designed to be stable, but during testing issues were found with firewalls and port blocking when running the application from the University systems. 
Therefore it is recommended that examiners set up their own instance of the server.
The only changes required in the KidQuest application code is that the SERVER_URL variable must be edited to the newly hosted instances. This variable can be located in KidQuest\app\src\main\java\com\michael\kidquest\Constants.java

Exact instructions for the server setup can be found in the server's README file.

NB: The hosted instance will be maintained until June 10th. No changes will be made to server code, but uptime will be monitored.




[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)


   [gson]: <https://github.com/google/gson>
   [androidsupport]: <http://developer.android.com/tools/support-library/index.html>
   [asynchttp]: <http://loopj.com/android-async-http/>
   [fab]: <https://github.com/Clans/FloatingActionButton>
   [prettytime]: <http://www.ocpsoft.org/prettytime/>
