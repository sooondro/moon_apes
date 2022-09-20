package hr.ferit.sandroblavicki.sandroapp.login

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.google.firebase.auth.FirebaseAuth
import hr.ferit.sandroblavicki.sandroapp.home.HomeFragmentDirections

class LoginViewModel: ViewModel() {

    private val _navigationDelegate = MutableLiveData<NavDirections>()
    val navigationDelegate : LiveData<NavDirections> = _navigationDelegate

    private val _screenState = MutableLiveData<LoginScreenState>(LoginUserInputState(LoginUiModel("","")))
    val screenState : LiveData<LoginScreenState> = _screenState

    fun navigateTo(directions: NavDirections) {
        _navigationDelegate.value = directions
    }

    fun onEmailChnaged(newEmail: String) {
        val loginData = _screenState.value!!.loginData.copyWith(newEmail,null);
        _screenState.value = LoginUserInputState(loginData)
    }

    fun onPasswordChanged(newPassword: String) {
        val loginData = _screenState.value!!.loginData.copyWith(null,newPassword);
        _screenState.value = LoginUserInputState(loginData)
    }

    fun onSubmitClicked(){
        val loginUiModel = _screenState.value!!.loginData
        _screenState.value = LoginLoading(loginUiModel)
        validateUserInputAndLogin()
    }

    private fun validateUserInputAndLogin() {
        val loginData = _screenState.value!!.loginData
        val emailErrors = validateEmail(loginData.username)
        val passwordErrors = validatePassword(loginData.password)

        if( emailErrors.isNotEmpty() || passwordErrors.isNotEmpty()){
            _screenState.value = LoginError(loginData,emailErrors + passwordErrors)
            return
        }
                val auth =  FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(loginData.username,loginData.password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _navigationDelegate.value = LoginFragmentDirections.navigateToHomeFragment()
            } else {
                _screenState.value = LoginError(loginData, listOf("Invalid credentials"))
            }
        }
    }

    private fun validateEmail(email: String): MutableList<String> {
        val errors: MutableList<String> = mutableListOf()
        if (email.isEmpty()) {
            errors.add("Email can't be empty")
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errors.add("Invalid email adress")
        }
        return errors
    }

    private fun validatePassword(password: String): MutableList<String> {
        val errors: MutableList<String> = mutableListOf()

        if (password.isEmpty()) {
            errors.add("Password can't be empty")
        }

        if (password.length < 6) {
            errors.add("Min password length is 6")
        }

        return errors
    }


}