## Hello-News
A simple android application to demonstrate the working of <b>Kotlin Coroutines</b>.
The application fetches news from a remote API, stores it in the local databases and displays the news articles to the user.

### Architecture
The app makes use of the MVVM pattern. 
- The <b>activites</b> represent the view layer.
- The architecture component's <b>viewmodels</b> serve as the business logic layer.
- The api models and the database entities serve as the model layer.

Additionally, the app makes use of a <b>repository</b> pattern to abstract the data access mechanisms from the view
and viewmodels, in line with the <b>CLEAN</b> architecture principles.

### Third Party Libraries
- #### Retorfit
Hello News makes use of Retrofit to access the news api. The current version of Retrofit does not support Kotlin coroutines.
To be able to leverage the power and simplicity of coroutines, I have added a simple extension function in the <b>Extensions.kt</b>
file which allows to use the <b>async-await</b> mechanism on exisiting Retrofit calls.
- #### Room
Room is being used as the ORM library which simplifies CRUD operations on the database. The current version of Room provides 
out of the box support for Kotlin Coroutines. The DAO methods can be marked as suspend functions which allow them to be used 
inside coroutine blocks.
