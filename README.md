# fetch-rewards-take-home-assignment
This is my submission for the Fetch Rewards Take Home Excersize for the Android Engineer Intern position for Summer 2023. The app implements the base functionality required in the description for the take home excersize:
 - Retriving data from https://fetch-hiring.s3.amazonaws.com/hiring.json
 - Display the list of items to the user based on:
   - All items to be grouped by "listId"
   - Results need to be sorted by "listId" first then "name"
   - Empty or null names to be filtered out
 
## Running the app

 - Open Android Studio and select `File->Open...` or from the Android Studio Launcher select `Open` and navigate to the root directory of your project.
 - Click 'OK' to open the the project in Android Studio.
 - A Gradle sync should start, but you can force a sync and build the 'app' module as needed.
 - Connect an Android device to your development environment or open an Android emulator
 - Select `Run -> Run 'app'` (or `Debug 'app'`) from the menu bar
 - Select the device you wish to run the app on and click 'OK'

## Possible Improvements
Here are the possible improvements that can be added to the app for better user experience and code quality:
 - Adding a refresh button so that the users can fetch the data again instead of having to restart the app
 - Adding a retry button so that the users can retry getting the data if the API calls fail
 - Adding a option for an alternative sort using the items' "id" instead of "name", since using "name" sorts the strings and can lead to unexpected sorting such as "Item 276" coming before "Item 28"
 - Testing using JUnit and Mockito for mocking API calls
 
 ![image](https://user-images.githubusercontent.com/40931260/216728248-38caa94d-a07b-4417-b10d-5990b16aa9c9.png)
