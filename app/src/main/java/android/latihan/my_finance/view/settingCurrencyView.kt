package android.latihan.my_finance.view

import android.app.Activity
import android.latihan.my_finance.R
import android.latihan.my_finance.controller.rv_CatCurrency
import android.latihan.my_finance.model.Currency
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_setting_currency_view.*


class settingCurrencyView : AppCompatActivity() {

    private lateinit var mCurdatabase: DatabaseReference
    private lateinit var mCurUserdata: DatabaseReference
    private var firebaseAuth: FirebaseAuth
    private var currentUser: FirebaseUser?
    private var CurList = ArrayList<Currency>()

    companion object{
        private const val TAG = "CurrencyPart"
    }

    init{
        firebaseAuth = FirebaseAuth.getInstance()
        currentUser = firebaseAuth.currentUser
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_currency_view)
        val mManager = LinearLayoutManager(this)
        mManager.reverseLayout = true
        mManager.stackFromEnd = true
        rvCurrency.layoutManager = mManager
        rvCurrency.setHasFixedSize(true)
        mCurdatabase = FirebaseDatabase.getInstance().getReference("Currency")
        //step by step
        if(currentUser!=null){
            mCurUserdata = FirebaseDatabase.getInstance().getReference(currentUser!!.uid).child("Currency")
            mCurUserdata.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    //[Start Exclude]
                    if(dataSnapshot.exists()){
                        CurList.clear()
                        // Get Post object and use the values to update the UI
                        for(cast in dataSnapshot.children){
                            val post = cast.getValue(Currency::class.java)
                            if(post == null){
                                Log.d(TAG, "PostNULL")
                            }
                            CurList.add(post!!)
                        }
                        val adapter = rv_CatCurrency(
                            this@settingCurrencyView,
                            CurList,
                            { curItem : Currency -> currencyClicked(curItem) }
                        )
                        rvCurrency?.setAdapter(adapter)
                    }else{
                        defaultCurrencyData()
                    }
                    // [END_EXCLUDE]
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                    // [START_EXCLUDE]
                    Toast.makeText(
                        baseContext, "Failed to load post.",
                        Toast.LENGTH_SHORT
                    ).show()
                    // [END_EXCLUDE]
                }
            })
        }else{
            //event for no user login
            defaultCurrencyData()
        }

    }

    fun defaultCurrencyData(){
        val curListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // [START_EXCLUDE]
                if(dataSnapshot.exists()){
                    CurList.clear()
                    // Get Post object and use the values to update the UI
                    for(cast in dataSnapshot.children){
                        val post = cast.getValue(Currency::class.java)
                        if(post == null){
                            Log.d(TAG, "PostNULL")
                        }
                        CurList.add(post!!)
                    }
                    val adapter = rv_CatCurrency(
                        this@settingCurrencyView,
                        CurList,
                        { curItem : Currency -> currencyClicked(curItem) }
                    )
                    rvCurrency?.setAdapter(adapter)
                }
                // [END_EXCLUDE]
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                // [START_EXCLUDE]
                Toast.makeText(
                    baseContext, "Failed to load post.",
                    Toast.LENGTH_SHORT
                ).show()
                // [END_EXCLUDE]
            }
        }
        mCurdatabase.addListenerForSingleValueEvent(curListener)
    }

    fun currencyClicked(curItem : Currency){
        if(currentUser != null){
            for(cast in CurList){
                if(cast.curId == curItem.curId){
                    copyToUser(cast.curId,cast.curTag,cast.curCountry,1)
                }else{
                    copyToUser(cast.curId,cast.curTag,cast.curCountry,0)
                }
            }
            setResult(Activity.RESULT_OK)
            finish()
        }else{
            Toast.makeText(
                baseContext, "Login to Select Currency",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun copyToUser(id: String?, tag: String? , country: String? , attend: Int?){
        var key : String? = null
        if(currentUser != null){
            mCurUserdata = FirebaseDatabase.getInstance().getReference(currentUser!!.uid).child("Currency")
            key = mCurUserdata.push().key
        }

        if(key == null){
            Log.w(TAG, "Couldn't get push key for currency")
            return
        }

        val applesQuery: Query = mCurUserdata.orderByChild("curId")
        applesQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (appleSnapshot in dataSnapshot.children) {
                    appleSnapshot.ref.removeValue()
                }
                val post = Currency(tag,country,attend,id)
                mCurUserdata.child(key).setValue(post)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException())
            }
        })

    }


}
