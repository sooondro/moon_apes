package hr.ferit.sandroblavicki.sandroapp.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import hr.ferit.sandroblavicki.sandroapp.register.RegisterScreenState

class RegisterViewModel: ViewModel() {
    private val _navigationDelegate = MutableLiveData<NavDirections>()
    val navigationDelegate : LiveData<NavDirections> = _navigationDelegate

    private val _screenState = MutableLiveData<RegisterScreenState>()
    val screenState : LiveData<RegisterScreenState> = _screenState

    fun navigateTo(directions: NavDirections) {
        _navigationDelegate.value = directions
    }

    fun setScreenState(screenState: RegisterScreenState) {
        _screenState.value = screenState
    }
}