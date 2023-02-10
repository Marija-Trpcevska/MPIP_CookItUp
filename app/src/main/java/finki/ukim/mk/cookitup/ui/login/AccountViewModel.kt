package finki.ukim.mk.cookitup.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import finki.ukim.mk.cookitup.domain.login.Account

class AccountViewModel: ViewModel() {

    private val _account = MutableLiveData(Account("","",""))
    fun getAccount(): LiveData<Account> = _account

    fun setAccount(account: Account){
        _account.value = account
    }
}