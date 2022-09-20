package hr.ferit.sandroblavicki.sandroapp.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections

class LoginViewModel: ViewModel() {

    private val _navigationDelegate = MutableLiveData<NavDirections>()
    val navigationDelegate : LiveData<NavDirections> = _navigationDelegate

    private val _screenState = MutableLiveData<LoginScreenState>()
    val screenState : LiveData<LoginScreenState> = _screenState

    fun navigateTo(directions: NavDirections) {
        _navigationDelegate.value = directions
    }

    fun setScreenState(screenState: LoginScreenState) {
        _screenState.value = screenState
    }
}