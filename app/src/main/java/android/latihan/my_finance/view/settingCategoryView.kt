package android.latihan.my_finance.view

import android.content.Context
import android.latihan.my_finance.R
import android.latihan.my_finance.controller.rv_CatSetting
import android.latihan.my_finance.model.Category
import android.latihan.my_finance.model.loginWithGoogle
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.activity_add_category.*


class settingCategoryView : AppCompatActivity()
{
    // [START declare_database_ref]
    private lateinit var mCatdatabase: DatabaseReference
    private lateinit var addButton: MaterialButton
    private lateinit var firebaseAuth: FirebaseAuth
    private var CatList = ArrayList<Category>()

    companion object{
        private const val TAG = "AddCategory"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)
        addButton = findViewById(R.id.add_category)
        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser
        var textCategory = findViewById<TextInputEditText>(R.id.categoryInText)

        //viewing type
        val spinnerType: Spinner = findViewById(R.id.CatPartspinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.Type,
            android.R.layout.simple_spinner_dropdown_item
        ).also {adapter->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerType.adapter = adapter
        }

        //spinner selectedListener
        var selectSpin : String = ""
        spinnerType.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, p3: Long) {
                selectSpin = parent?.getItemAtPosition(pos).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        //reference databse by user id
        var userId : String = ""
        if(currentUser != null){
            addButton.setOnClickListener {
                    addCategoryLogin(currentUser.uid, textCategory.text.toString(), selectSpin)
                    Toast.makeText(this, "Add Success!", Toast.LENGTH_LONG).show()
                    textCategory.setText("")
                    spinnerType.setSelection(0)
                    Log.d(TAG, "Add Category Success")
            }
            userId = currentUser.uid

            //Main Action of Activity
            //write reference
            mCatdatabase = FirebaseDatabase.getInstance().getReference(userId).child("Category")
            //initiate for recycler view
            val mManager = LinearLayoutManager(this)
            mManager.reverseLayout = true
            mManager.stackFromEnd = true
            cListPart.layoutManager = mManager
            cListPart.setHasFixedSize(true)
            val catListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // [START_EXCLUDE]
                    if(dataSnapshot.exists()){
                        CatList.clear()
                        // Get Post object and use the values to update the UI
                        for(cast in dataSnapshot.children){
                            val post = cast.getValue(Category::class.java)
                            if(post == null){
                                Log.d(TAG, "PostNULL")
                            }
                            CatList.add(post!!)
                            Log.d(TAG, "TOtal isi ${CatList.size}")
                        }
                        val adapter = rv_CatSetting(this@settingCategoryView, CatList)
                        cListPart?.setAdapter(adapter)
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
            mCatdatabase.addValueEventListener(catListener)
        }
    }

    override fun onStart() {
        super.onStart()
    }


    private fun addCategoryLogin(userid: String, title: String, type: String){
        val key = mCatdatabase.push().key

        if (key == null){
            Log.w(TAG, "Couldn't get push key for category")
            return
        }
        val post = Category(userid, type, title)
        mCatdatabase.child(key).setValue(post)
    }

    private fun addCategory(title: String){

    }
}