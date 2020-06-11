package android.latihan.my_finance.view

import android.content.Intent
import android.latihan.my_finance.R
import android.latihan.my_finance.model.Currency
import android.latihan.my_finance.model.commuViewModel
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class transaksiFragment :  Fragment(){
    private var communicationViewModel: commuViewModel? = null
    private lateinit var mCurUserdata: DatabaseReference
    private var firebaseAuth: FirebaseAuth
    private var currentUser: FirebaseUser?
    private var calendar : Calendar
    private var dateForm : SimpleDateFormat? = null
    private var date : String = ""
    private var defaultCurrency : String = "IDR"

    init{
        calendar = Calendar.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        currentUser = firebaseAuth.currentUser
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        communicationViewModel = ViewModelProviders.of(requireActivity()).get(commuViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        var v : View = inflater.inflate(R.layout.fragment_transaksi, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addTransac = view.findViewById<FloatingActionButton>(R.id.buttonAddTransac)
        val curTag_One = view.findViewById<TextView>(R.id.tagCurrency1)
        val curTag_Two = view.findViewById<TextView>(R.id.tagCurrency2)
        val dateText = view.findViewById<TextView>(R.id.viewDate)
        dateForm = SimpleDateFormat("dd/MMM/yyyy")
        date = dateForm!!.format(calendar.getTime())
        dateText.text = date
        if(currentUser!= null){
            mCurUserdata = FirebaseDatabase.getInstance().getReference(currentUser!!.uid).child("Currency")
            mCurUserdata.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if(dataSnapshot.exists()){
                        for(cast in dataSnapshot.children){
                            val post = cast.getValue(Currency::class.java)
                            if(post!!.attend == 1){
                                curTag_One.text = post.curTag
                                curTag_Two.text = post.curTag
                            }
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                    // [START_EXCLUDE]
                    Toast.makeText(
                        activity, "Failed to load currency.",
                        Toast.LENGTH_SHORT
                    ).show()
                    // [END_EXCLUDE]
                }
            })
        }
        addTransac.setOnClickListener {
            startActivityForResult(
                Intent(activity, addEditTransaksi::class.java),
                ADD_TRANS_REQUEST
            )
        }
        communicationViewModel!!.mCur.observe(requireActivity(),
            Observer { s -> defaultCurrency  = s })
    }

    companion object {
        const val ADD_TRANS_REQUEST = 1
        private const val TAG = "Transaksi"
        fun newInstance(): transaksiFragment {
            return transaksiFragment()
        }
    }
}