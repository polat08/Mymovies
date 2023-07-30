package com.myandroid.mymovies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private val signInLauncher = registerForActivityResult( //создали объект авторизации экрана
        FirebaseAuthUIActivityResultContract(),
    ) { res ->
        this.onSignInResult(res) // запуск экрана
    }

    private lateinit var database: DatabaseReference //создали объект для записи в БД

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intentToAnotherScreen = Intent(this, MoviesActivity::class.java)
        startActivity(intentToAnotherScreen)

//        Log.d("testlogs","register")
//
//        database = Firebase.database.reference //инициализация базы данных
//        val providers = arrayListOf(
//            AuthUI.IdpConfig.EmailBuilder().build()) //список регистрации кт. мы используем
//
//        // Create and launch sign-in intent
//        val signInIntent = AuthUI.getInstance()
//            .createSignInIntentBuilder()
//            .setAvailableProviders(providers)
//            .build() //создали интент для экрана firebase auth
//        signInLauncher.launch(signInIntent) //запустили экран firebase auth
//        Log.d("testlogs","in onCreate")
    }



    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse // результат с экрана Firebase auth
        if (result.resultCode == RESULT_OK) { // проверка если результат ОК ,то вызывается след. код
            Log.d("testLogs","sign success ${response?.email}")

            // Successfully signed in
            val authUser = FirebaseAuth.getInstance().currentUser //создаем объект текущего пользователя Firebase auth

            authUser?.let {// и если пользователь ОК, т.е существует, то сохраняем в БД
                val email = it.email.toString() //из authUser извлекаем email нашего пользователя
                val uid = it.uid // извлекаем uid нашего пользователя
                val firebaseUser = User(email, uid) // создаем новый объект пользователя User  с параметрами email и uid
                Log.d("testLogs", "RegistrationActivity firebaseUser $firebaseUser")
                database.child("users").child(uid).setValue(firebaseUser)// сохраняем нашего пользователя в Firebase Realtime

                val intentToAnotherScreen = Intent(this, MoviesActivity::class.java)
                startActivity(intentToAnotherScreen)

            }


        } else if (result.resultCode == RESULT_CANCELED){ // если результат не ОК должны обработать ошибку
            Log.d("test","else")
            Toast.makeText(this@MainActivity, "Something wrong with registration.", Toast.LENGTH_SHORT).show()
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        } else {
            // do not do anything
        }
    }
}