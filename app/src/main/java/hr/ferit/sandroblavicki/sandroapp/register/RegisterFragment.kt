package hr.ferit.sandroblavicki.sandroapp.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import hr.ferit.sandroblavicki.sandroapp.databinding.RegisterFragmentBinding

class RegisterFragment : Fragment() {
    private lateinit var viewModel: RegisterViewModel
    private lateinit var binding: RegisterFragmentBinding
    // private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = RegisterViewModel()
        binding = RegisterFragmentBinding.inflate(LayoutInflater.from(context), container, false)
        //auth = Firebase.auth
        viewModel.setScreenState(RegisterLoading(RegisterUiModel("", "", "")))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navigationDelegate.observe(viewLifecycleOwner) { navDirections ->
            findNavController().navigate(navDirections)
        }

        viewModel.screenState.observe(viewLifecycleOwner) { screenState ->
            when (screenState) {
                is RegisterError -> displayErrorUi(screenState)
                is RegisterLoading -> displayLoadingUi(screenState)
                is RegisterValidation -> displayValidationUi(screenState)
            }
        }

        binding.apply {
            buttonRegisterLogin.setOnClickListener {
                viewModel.navigateTo(RegisterFragmentDirections.navigateToLoginFragment())
            }

            buttonRegisterSubmit.setOnClickListener {
                val email: String = edittextRegisterEmail.text.toString().trim()
                val password: String = edittextRegisterPassword.text.toString().trim()
                val repeatedPassword: String =
                    edittextRegisterRepeatedPassword.text.toString().trim()

                // validateUserInput(email, password, repeatedPassword)
                viewModel.navigateTo(RegisterFragmentDirections.navigateToHomeFragment())


/*                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("firebase", "createUserWithEmail:success")
                            val user = auth.currentUser
                            viewModel.navigateTo(RegisterFragmentDirections.navigateToHomeFragment())
                            // updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("firebase", "createUserWithEmail:failure", task.exception)
                            Toast.makeText(context, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                            // updateUI(null)
                        }
                    }*/
            }
        }
    }

    private fun validateUserInput(email: String, password: String, repeatedPassword: String) {
        val emailErrors = validateEmail(email)
        val passwordErrors = validatePassword(password, repeatedPassword)

        if (emailErrors.isEmpty() && passwordErrors.isEmpty()) {
            viewModel.navigateTo(RegisterFragmentDirections.navigateToHomeFragment())
        }

        viewModel.setScreenState(
            RegisterError(
                viewModel.screenState.value?.registerData?.copyWith(
                    email,
                    password,
                    repeatedPassword
                )!!, emailErrors + passwordErrors
            )
        )
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

    private fun displayLoadingUi(registerLoading: RegisterLoading) {

    }

    private fun displayErrorUi(registerError: RegisterError) {
        binding.apply {
            edittextRegisterEmail.setText(registerError.registerData.email)
            edittextRegisterPassword.setText(registerError.registerData.password)
            edittextRegisterRepeatedPassword.setText(registerError.registerData.repeatedPassword)
        }

        val errorsText = registerError.errors.joinToString("\n")

        Toast.makeText(requireContext(), errorsText, Toast.LENGTH_LONG).show()
    }

    private fun displayValidationUi(registerValidation: RegisterValidation) {

    }
}