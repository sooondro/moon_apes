package hr.ferit.sandroblavicki.sandroapp.navbar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.google.firebase.auth.FirebaseAuth

class NavbarViewModel () : ViewModel() {

    private val _navigationDelegate = MutableLiveData<NavDirections>()
    val navigationDelegate : LiveData<NavDirections> = _navigationDelegate

    fun navigateTo(directions: NavDirections) {
        _navigationDelegate.value = directions
    }
}