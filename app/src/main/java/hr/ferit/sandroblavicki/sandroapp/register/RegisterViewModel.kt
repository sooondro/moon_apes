package hr.ferit.sandroblavicki.sandroapp.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.google.firebase.auth.FirebaseAuth
import hr.ferit.sandroblavicki.sandroapp.login.LoginLoadingState
import hr.ferit.sandroblavicki.sandroapp.login.LoginUserInputState
import hr.ferit.sandroblavicki.sandroapp.register.RegisterScreenState

class RegisterViewModel: ViewModel() {
    private val _navigationDelegate = MutableLiveData<NavDirections>()
    val navigationDelegate : LiveData<NavDirections> = _navigationDelegate

    private val _screenState = MutableLiveData<RegisterScreenState>()
    val screenState : LiveData<RegisterScreenState> = _screenState

    fun navigateTo(directions: NavDirections) {
        _navigationDelegate.value = directions
    }

    fun onEmailChanged(newEmail: String) {
        val registerData = _screenState.value!!.registerData.copyWith(newEmail, null, null);
        _screenState.value = RegisterUserInputState(registerData)
    }

    fun onPasswordChanged(newPassword: String) {
        val registerData = _screenState.value!!.registerData.copyWith(null, newPassword, null);
        _screenState.value = RegisterUserInputState(registerData)
    }

    fun onRepeatedPasswordChanged(newPassword: String) {
        val registerData = _screenState.value!!.registerData.copyWith(null, null, newPassword);
        _screenState.value = RegisterUserInputState(registerData)
    }

    fun onSubmitClicked() {
        val loginUiModel = _screenState.value!!.registerData
        _screenState.value = RegisterLoadingState(loginUiModel)
        validateUserInputAndRegister()
    }

    private fun validateUserInputAndRegister() {
        val registerData = _screenState.value!!.registerData
        val emailErrors = validateEmail(registerData.email)
        val passwordErrors = validatePassword(registerData.password, registerData.repeatedPassword)

        if (emailErrors.isNotEmpty() || passwordErrors.isNotEmpty()) {
            val errorText = (emailErrors + passwordErrors).joinToString("\n")
            _screenState.value = RegisterErrorState(registerData, errorText)
            return
        }

        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(registerData.email, registerData.password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    _navigationDelegate.value = RegisterFragmentDirections.navigateToHomeFragment()
                } else {
                    _screenState.value = RegisterErrorState(registerData, task.exception.toString())
                }
            }
    }

    private fun validateEmail(email: String): MutableList<String> {
        var errors: MutableList<String> = mutableListOf()
        if (email.isEmpty()) {
            errors.add("Email can't be empty")
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errors.add("Invalid email adress")
        }
        return errors
    }

    private fun validatePassword(password: String, repeatedPassword: String): MutableList<String> {
        var errors: MutableList<String> = mutableListOf()
        if (password.isEmpty()) {
            errors.add("Password can't be empty")
        }
        if (repeatedPassword.isEmpty()) {
            errors.add("Repeated password can't be empty")
        }
        if (password.length < 6) {
            errors.add("Min password length is 6")
        }
        if (password != repeatedPassword) {
            errors.add("Password have to match")
        }
        return errors
    }
}