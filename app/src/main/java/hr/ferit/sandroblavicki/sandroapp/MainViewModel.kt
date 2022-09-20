package hr.ferit.sandroblavicki.sandroapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections

class MainViewModel: ViewModel() {

    private val _navigationDelegate = MutableLiveData<NavDirections>()
    val navigationDelegate : LiveData<NavDirections> = _navigationDelegate

}