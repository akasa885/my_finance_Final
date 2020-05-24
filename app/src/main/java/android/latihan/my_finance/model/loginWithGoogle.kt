package android.latihan.my_finance.model

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.SignInButton
import com.google.firebase.auth.FirebaseAuth

class loginWithGoogle : AppCompatActivity() {
    private var signButton: SignInButton? = null
    private var mAuth: FirebaseAuth? = null
    private var mGoogleSignIn : GoogleSignInClient? = null
    companion object {
        const val EXTRA_JUDUL = "com.piusanggoro.notesapp.EXTRA_JUDUL"
        const val EXTRA_DESKRIPSI = "com.piusanggoro.notesapp.EXTRA_DESKRIPSI"
        const val EXTRA_PRIORITAS = "com.piusanggoro.notesapp.EXTRA_PRIORITAS"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = Intent().apply {
            putExtra(EXTRA_JUDUL, "Judulnya")
            putExtra(EXTRA_DESKRIPSI, "jasd")
            putExtra(EXTRA_PRIORITAS, "asd")
        }
        setResult(Activity.RESULT_OK, data)
        finish()
    }
}