package android.latihan.my_finance.model

import android.app.Activity
import android.content.Intent
import android.latihan.my_finance.R
import android.latihan.my_finance.databinding.BaseActivityBinding
import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class loginWithGoogle : AppCompatActivity() {

    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var binding: BaseActivityBinding
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions



    companion object{
        const val TAG = "loginProcess"
        const val EXTRA_OUT = "com.piusanggoro.logapp.EXTRA_OUT"
        private const val RC_SIGN_IN = 9001
        var accountStatus : Int? = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
        configureGoogleSignIn()
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = firebaseAuth.currentUser
        afterChecking(currentUser)
        if(intent.hasExtra(EXTRA_OUT)){
            signOut(intent.getStringExtra(EXTRA_OUT))
        }else{
            if(accountStatus == 0){
                signIn()
            }
        }
    }

    private fun configureGoogleSignIn() {
        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions)
    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    fun signOut(request: String?) {
        // Firebase sign out
        firebaseAuth.signOut()

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this) {
            afterChecking(null)
            if(accountStatus == 0){
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }

    private fun revokeAccess() {
        // Firebase sign out
        firebaseAuth.signOut()

        // Google revoke access
        mGoogleSignInClient.revokeAccess().addOnCompleteListener(this) {
            afterChecking(null)
        }
    }

    private fun afterChecking(user: FirebaseUser?){
        if (user != null) {
            val intent = Intent()
            intent.putExtra("datauser", getString(R.string.google_email_user, user!!.email))
            setResult(Activity.RESULT_OK, intent)
            finish()
        }else{
            Log.d(TAG, "Have Success Sign Out")
            accountStatus = 0
        }

    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(TAG, "signInWithCredential:success")
                val user = firebaseAuth.currentUser
                afterChecking(user)
//                setResult(Activity.RESULT_OK)
//                finish()
            } else {
                Log.w(TAG, "signInWithCredential:failure", it.exception)
                // [START_EXCLUDE]
//                val view = binding.drawerLayout
                // [END_EXCLUDE]
//                Snackbar.make(view, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
                afterChecking(null)
                if(accountStatus == 0){
                    setResult(Activity.RESULT_CANCELED)
                    finish()
                }

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // ...
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
        }
    }


}