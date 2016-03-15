# NLassoSolstice

Hiring demo app for Solstice. The Main idea is to retrive Contacts data to list them and if clicked open details. 

### Documentation
The app was built using:
* Android Studio 1.5.1
* Min SDK: 17
* Target SDK: 22
* Build Tools Version: 23.0.1
* Dependencies:
  * okHttp3 -> for HTTP Requests
  * Ion -> to bind images to ImageView
  * Several Android Native

### Structure
This app contains a main activity that loads the Contact List. Also, in case this is a tablet or width is bigger that 900dp a second layout is loaded. The second layout is a Fragment that loads the details. In case this is not a tablet then it will load in a different activity which will load the Fragment within.

All the requests are performed asynchronously so the app doesn't depend on the connection. Despite that all the views are loaded after the data is downloaded and parsed.

### Ideas not implemented
* Favorites: since the favorite data was not in the data from the contact list it was not implemented a filter to show all the favorites. Otherwise it'd have been a bigger process and transaction to get all data in once.
* Caching: It was the idea to implement data caching so the data is downloaded once the first time a user opens the app. So the data traffic is way less than downloading each time an Activity loads. This is on hold since the first idea was to implement a DB and the second to insert it within SharedPreferences (the last is not a good idea since it's not scalable but an easier one to implement the demo). 
* The edit button was not implemented since there's no data storing so it would be useless.
* ProgressFragment. Each time the Activity loads it's data it should be a Loading icon or progress bar or somehing to notify the user the app is working in background.

### Known Issues:
* If the user use the app in a tablet and loads the partitioned view it should load the details of the first user.
* If the user switch from landscape to portrait in a tablet it might fail. Not tested in several tablets. 


For any doubt don't hesitate to contact me! 

