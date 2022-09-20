package hr.ferit.sandroblavicki.sandroapp.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections

class CreateViewModel : ViewModel() {

    private val _navigationDelegate = MutableLiveData<NavDirections>()
    val navigationDelegate : LiveData<NavDirections> = _navigationDelegate

    fun navigateTo(directions: NavDirections) {
        _navigationDelegate.value = directions
    }

    private val _screenState = MutableLiveData<CreateScreenState>()
    val screenState : LiveData<CreateScreenState> = _screenState

    fun setScreenState(screenState: CreateScreenState) {
        _screenState.value = screenState
    }
}