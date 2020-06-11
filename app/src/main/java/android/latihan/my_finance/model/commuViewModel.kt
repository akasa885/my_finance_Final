package android.latihan.my_finance.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class commuViewModel : ViewModel() {
    private val mCurrency = MutableLiveData<String>()

    val mCur : LiveData<String>
        get() = mCurrency

    fun setCur(currency: String){
        mCurrency.value = currency
    }
}