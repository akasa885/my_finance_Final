package android.latihan.my_finance.view

import android.latihan.my_finance.R
import android.latihan.my_finance.model.Category
import android.latihan.my_finance.model.TransaksiData
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class addEditTransaksi : AppCompatActivity() {
    private lateinit var mCatdatabase: DatabaseReference
    private lateinit var mTransdatabase: DatabaseReference
    private var firebaseAuth: FirebaseAuth
    private var currentUser: FirebaseUser?
    private var calendar : Calendar
    private var dateForm : SimpleDateFormat? = null
    private var date : String = ""
    var arrayList: ArrayList<String> = ArrayList()

    companion object{
        private const val TAG = "Transaksi"
    }

    init{
        calendar = Calendar.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        currentUser = firebaseAuth.currentUser
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item_transaksi)
        val dateText = findViewById<TextView>(R.id.dateTextPrintt)
        val saveButton : Button = findViewById(R.id.buttonAddTransacSave)
        val titleInsert = findViewById<TextInputEditText>(R.id.inside_title_input)
        val amountInsert = findViewById<TextInputEditText>(R.id.inside_amount_input)
        dateForm = SimpleDateFormat("dd/MMM/yyyy")
        date = dateForm!!.format(calendar.getTime())
        dateText.text = date


        // spinner type
        val spinnerType: Spinner = findViewById(R.id.spinnerAddType)
        ArrayAdapter.createFromResource(
            this,
            R.array.Type,
            android.R.layout.simple_spinner_dropdown_item
        ).also {adapter->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerType.adapter = adapter
        }

        // spinner category
        val spinnerCategory: Spinner = findViewById(R.id.spinnerAddCateg)
        var selectSpinType : String = ""
        var selectSpinCat : String = ""
        if(currentUser != null){
            mCatdatabase = FirebaseDatabase.getInstance().getReference(currentUser!!.uid).child("Category")
            //spinner type on change listener
            spinnerType.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, p3: Long) {
                    selectSpinType = parent?.getItemAtPosition(pos).toString()
                    if(pos == 0){
                        arrayList.clear()
                        val IncomeQuery: Query = mCatdatabase.orderByChild("ctype").equalTo("Income")
                        IncomeQuery.addListenerForSingleValueEvent(object : ValueEventListener{
                            override fun onDataChange(p0: DataSnapshot) {
                                for(cast in p0.children){
                                    val post = cast.getValue(Category::class.java)
                                    arrayList.add(post!!.Ctitle.toString())
                                }
                                val arrayAdapter: ArrayAdapter<String> =
                                    ArrayAdapter<String>(
                                        this@addEditTransaksi,
                                        android.R.layout.simple_spinner_dropdown_item,
                                        arrayList
                                    )
                                spinnerCategory.adapter = arrayAdapter
                            }

                            override fun onCancelled(p0: DatabaseError) {
                                // Getting Post failed, log a message
                                Log.w(TAG, "loadPost:onCancelled", p0.toException())
                                // [START_EXCLUDE]
                                Toast.makeText(
                                    baseContext, "Failed to category.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                // [END_EXCLUDE]
                            }
                        })

                    }else{
                        arrayList.clear()
                        val IncomeQuery: Query = mCatdatabase.orderByChild("ctype").equalTo("Outcome")
                        IncomeQuery.addListenerForSingleValueEvent(object : ValueEventListener{
                            override fun onDataChange(p0: DataSnapshot) {
                                for(cast in p0.children){
                                    val post = cast.getValue(Category::class.java)
                                    arrayList.add(post!!.Ctitle.toString())
                                }
                                val arrayAdapter: ArrayAdapter<String> =
                                    ArrayAdapter<String>(
                                        this@addEditTransaksi,
                                        android.R.layout.simple_spinner_dropdown_item,
                                        arrayList
                                    )
                                spinnerCategory.adapter = arrayAdapter
                            }

                            override fun onCancelled(p0: DatabaseError) {
                                // Getting Post failed, log a message
                                Log.w(TAG, "loadPost:onCancelled", p0.toException())
                                // [START_EXCLUDE]
                                Toast.makeText(
                                    baseContext, "Failed to category.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                // [END_EXCLUDE]
                            }
                        })
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    //do nothing
                }
            }
        }
        spinnerCategory.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectSpinCat = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //do nothing
            }
        }
        var amountNumber : String = amountInsert.text.toString()
        saveButton.setOnClickListener {
            newTransaksi(selectSpinType, selectSpinCat, titleInsert.text.toString(), amountNumber.toInt())
        }
    }

    private fun newTransaksi (type: String?, category: String?, title: String?, amount: Number?){
        mTransdatabase = FirebaseDatabase.getInstance().getReference(currentUser!!.uid).child("Transaksi")
        val key = mTransdatabase.push().key

        if (key == null){
            Log.w(TAG, "Couldn't get push key for category")
            return
        }
        val post = TransaksiData(type, category,amount , title)
        mCatdatabase.child(key).setValue(post)
    }

}