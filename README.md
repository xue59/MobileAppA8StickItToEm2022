# MobileAppA8StickItToEm2022
A8 Stick It To 'Em  The assignment:  Use Firebase Realtime Database to create an app to send a sticker to a specific friend who also has the app.  Think of it as a one-emoji messaging system, except you will use your predefined "stickers" (images) that users tap on instead of letting the user type emojis with the keyboard.
The assignment:

Use Firebase Realtime Database to create an app to send a sticker to a specific friend who also has the app.  Think of it as a one-emoji messaging system, except you will use your predefined "stickers" (images) that users tap on instead of letting the user type emojis with the keyboard.

Do not authenticate users with a password -- if a user logs in with a particular username, accept that login without a password.  (The TAs shouldn't be spending time trying to recover passwords.)  Display how many of each sticker a user has sent because a future version of the app might have users purchase those stickers that they send, and the stickers might have different costs.  Show each user a history of stickers that they have received (which sticker was sent and who sent it and when it was sent).  Do not use SQLite or other local storage (SharedPreferences) except possibly to store the username of the currently logged-in user.  In other words, which stickers were received and sent should be stored in the cloud, not on the local device.

I will leave the design to you, but I will point out a few things to think about.  These are not the only design considerations you may run into:

1.  Not everyone has the same size screen, and not everyone holds their device the same orientation.  If the user rotates the device, reload the activity data so that the user stays smoothly on the same screen.  (In the past, you have saved the UI state.  Now, the state is being loaded from the cloud, so you just load again after a display configuration change.)

2.  Users should have usernames, but you do not authenticate them with a password.  If you decide to ask for a password during login anyways, the login should work regardless of what password is entered.  If you decide to implement authentication anyways, turn it off for your submission for the benefit of the TAs.  If the TAs are unable to log in to the test accounts, your grade will be affected.

3.  The app does not need to receive stickers and show notifications when it is not running.  Hint: this means you can build the app with Firebase Realtime Database and notifications without using Firebase Cloud Messaging.  Be sure to experiment with the receiving app running in the foreground and in the background.

4.  Receiving a new sticker is a big deal.  Notify the user in a way that recognizes this.  That is, go beyond just text in your notification.  If you aren't sure what can be in a notification other than text, you need to read more about Android notifications:

https://developer.android.com/guide/topics/ui/notifiers/notifications#Templates
5.  If you're sending pixels, you're approaching this the wrong way.  The app has a series of images that we call stickers.  Every instance of your app of the same version will have that same series of images in its resources.  Each image has a unique identifier.  Send the unique identifier that identifies an image, not the actual pixels of the image.  (Obviously, the users don't see the unique identifiers.  They see the images.)  Don't forget to properly handle the situation where an unknown sticker identifier is received because two users have different versions of the app.

Submit the apk and a text file with a GitHub link (CCS or GitHub.com), a list of every team member's GitHub ID and real name, and the user names for three test accounts that have already sent each other a few stickers.
