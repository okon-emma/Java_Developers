# Java_Developers
This is an android app project built with android studio. This project lists all java developer in lagos registered on github. And then opens up a profile screen for each developer. The project uses Github Public Search Api.
# Github Api URLs
The app made http requests to the following url to access user data: 

https://api.github.com/search/users?q=+language:%22java%22+location:%22lagos%22&page=1&per_page=100

To Search the Github API for users in a location(lagos), Use (make sure to change the location to your preffered location): <br>
https://api.github.com/search/users?q=+location:%22lagos%22&page=1&per_page=100 or <br>
https://api.github.com/search/users?q=+location:%22china%22&page=1&per_page=100 or<br>
https://api.github.com/search/users?q=+location:%22canada%22&page=1&per_page=100

To Search the Github API for users using a particular language, USe: <br>
https://api.github.com/search/users?q=+language:%22java%22&page=1&per_page=100 or <br>
https://api.github.com/search/users?q=+language:%22Go%22&page=1&per_page=100 or <br>
https://api.github.com/search/users?q=+language:%22Ruby%22&page=1&per_page=100

# Libraries Used
The Project made use of Picasso for Image Loading, RoundedImageView for the ImageView, Android Material Design v24 and HttpHandler Class to
parse data from the json array.

# App Activities
The app contains 2 major activities: <br>
The first activity dislplays in a recyclerview a list of java developers in lagos accessed from the Github Api <br>
And the Second activity directs to a profile page showing information about the user

# Other
The app made use of INTENTS and this enables you to share text or any data to other apps. <br> <br>
The app had 3 buttons in the profile page which enabled you to open a users github page, 
share a text content on whatsap and share a text content on other apps. <br> <br>
The app also made use of shared preferences in the Startup screen and to
enable the user select the number of users he/she wants shown per page. Available Options were 30(default), 50, 70, 80, 100

# Download Link
You can access this Direct Link to donwload the Apk file for the app <br>
http://anthemas.com/ANDELA_Project_Okon_Emmanuel.apk <br>
or from the the repository
