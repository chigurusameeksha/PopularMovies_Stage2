# PopularMovies-stage2
Popular movies display a gridview of the most popular and top rated movies.
Add moviedb.org api key to gradle.properties to try it out.

## This app will:

Upon launch, present the user with an grid arrangement of movie posters.
Allow the user to change sort order via a setting: The sort order can be by most popular, or by top rated
Allow the user to tap on a movie poster and transition to a details screen with additional information such as:
original title
movie poster image - background poster
A plot synopsis (called overview in the api)
user rating (called vote_average in the api)
release date
original language
## What Will I Learn After Stage 2?
You will fetch data from the Internet with theMovieDB API. You will use adapters and custom list layouts to populate list views. You will incorporate libraries to simplify the amount of code you need to write

API Key
The movie information uses The Movie Database (TMDb) API. To make your app work, you have to enter your own API key into build.gradle file.

API_KEY="Api Key"

## Libraries Used
* Volley
* Picasso
* Glide
* Room Persistence Library

## What is Required to Use This App?
Add your themoviedb api key to gradle.properties

## App Media

![ezgif com-video-to-gif](https://user-images.githubusercontent.com/42926055/81569387-d3840200-93bc-11ea-8336-313cb6f94e64.gif)


## Review

![screencapture-review-udacity-2020-05-13-08_19_48](https://user-images.githubusercontent.com/42926055/81767401-f36b1100-94f5-11ea-827d-0d5a89992023.png)

## License
MIT License

Copyright (c) 2020 chiguru sameeksha

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
