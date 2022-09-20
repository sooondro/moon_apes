package hr.ferit.sandroblavicki.sandroapp.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import hr.ferit.sandroblavicki.sandroapp.databinding.LoginFragmentBinding

class LoginFragment() : Fragment() {

    private lateinit var binding: LoginFragmentBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(LayoutInflater.from(context), container, false)
        viewModel = LoginViewModel()
        viewModel.setScreenState(LoginLoading(LoginUiModel("", "")))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navigationDelegate.observe(viewLifecycleOwner) { navDirections ->
            findNavController().navigate(navDirections)
        }

        viewModel.screenState.observe(viewLifecycleOwner) { screenState ->
            when (screenState) {
                is LoginError -> displayErrorUi(screenState)
                is LoginLoading -> displayLoadingUi(screenState)
                is LoginValidation -> displayValidationUi(screenState)
            }
        }

        binding.apply {
            buttonLoginRegister.setOnClickListener {
                viewModel.navigateTo(LoginFragmentDirections.navigateToRegisterFragment())
            }

            buttonLoginSubmit.setOnClickListener {
                val email = edittextLoginEmail.text.toString().trim()
                val password = edittextLoginPassword.text.toString().trim()

                validateUserInput(email, password)
                //viewModel.navigateTo(RegisterFragmentDirections.navigateToHomeFragment())
            }
        }
    }

    private fun validateUserInput(email: String, password: String) {
        val emailErrors = validateEmail(email)
        val passwordErrors = validatePassword(password)

        if (emailErrors.isNotEmpty() || passwordErrors.isNotEmpty()) {
            viewModel.setScreenState(
                LoginError(
                    viewModel.screenState.value?.loginData?.copyWith(
                        email,
                        password
                    )!!, emailErrors + passwordErrors
                )
            )
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

    private fun displayLoadingUi(loginLoading: LoginLoading) {

    }

    private fun displayErrorUi(loginError: LoginError) {
        binding.apply {
            edittextLoginEmail.setText(loginError.loginData.username)
            edittextLoginPassword.setText(loginError.loginData.password)
        }

        val errorsText = loginError.errors.joinToString("\n")

        Toast.makeText(requireContext(), errorsText, Toast.LENGTH_LONG).show()
    }

    private fun displayValidationUi(loginValidation: LoginValidation) {

    }

}