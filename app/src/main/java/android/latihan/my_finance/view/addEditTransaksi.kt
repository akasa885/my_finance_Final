package android.latihan.my_finance.view

import android.latihan.my_finance.R
import android.latihan.my_finance.model.Category
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class addEditTransaksi : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var mCatdatabase: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private var currentUser: FirebaseUser?
    var arrayList: ArrayList<String> = ArrayList()

    companion object{
        private const val TAG = "Transaksi"
    }

    init{
        firebaseAuth = FirebaseAuth.getInstance()
        currentUser = firebaseAuth.currentUser
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item_transaksi)

        // Dummy spinner date
        val spinnerDate: Spinner = findViewById(R.id.spinnerAddDate)
        ArrayAdapter.createFromResource(
            this,
            R.array.Date,
            android.R.layout.simple_spinner_dropdown_item
        ).also {adapter->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerDate.adapter = adapter
        }

        // Dummy spinner type
        val spinnerType: Spinner = findViewById(R.id.spinnerAddType)
        ArrayAdapter.createFromResource(
            this,
            R.array.Type,
            android.R.layout.simple_spinner_dropdown_item
        ).also {adapter->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerType.adapter = adapter
        }

        // Dummy spinner category
        val spinnerCategory: Spinner = findViewById(R.id.spinnerAddCateg)
        var selectSpin : String =""
        if(currentUser != null){
            mCatdatabase = FirebaseDatabase.getInstance().getReference(currentUser!!.uid).child("Category")
            spinnerType.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, p3: Long) {
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

                }
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
    }

}