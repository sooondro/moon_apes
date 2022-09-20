package hr.ferit.sandroblavicki.sandroapp.login

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.apply {
            navigationDelegate.observe(viewLifecycleOwner) { navDirections ->
                findNavController().navigate(navDirections)
            }

            screenState.observe(viewLifecycleOwner) { screenState ->
                Log.v("loginStuff","screen state changed $screenState")

                when (screenState) {
                    is LoginError -> displayErrorUi(screenState)
                    is LoginLoading -> displayLoadingUi(screenState)
                    is LoginUserInputState -> displayUserInputUi(screenState)
                }
            }
        }

        binding.apply {
            buttonLoginRegister.setOnClickListener {
                viewModel.navigateTo(LoginFragmentDirections.navigateToRegisterFragment())
            }
            edittextLoginEmail.addTextChangedListener { editable ->
                val email = editable.toString();
                viewModel.onEmailChnaged(email)
            }

            edittextLoginPassword.addTextChangedListener { editable ->
                val email = editable.toString();
                viewModel.onPasswordChanged(email)
            }

            buttonLoginSubmit.setOnClickListener {
                viewModel.onSubmitClicked();
            }
        }
    }

    private fun displayLoadingUi(loginLoading: LoginLoading) {
       // bindText(loginLoading)
        disableLoginForm();
        binding.progressbarLogin.visibility = View.VISIBLE
    }

    private fun displayErrorUi(loginError: LoginError) {
        //bindText(loginError)
        val errorsText = loginError.errors.joinToString("\n") // u viewmodel
        Toast.makeText(requireContext(), errorsText, Toast.LENGTH_LONG).show()
    }

    private fun displayUserInputUi(loginValidation: LoginUserInputState) {
     // bindText(loginValidation)
    }

    private fun bindText(loginScreenState: LoginScreenState) {
        Log.v("loginStuff","bindText $loginScreenState")
        binding.apply {
            edittextLoginEmail.setText(loginScreenState.loginData.username)
            edittextLoginPassword.setText(loginScreenState.loginData.password)
        }
    }

    private fun disableLoginForm(){
        binding.apply {
            edittextLoginEmail.inputType = InputType.TYPE_NULL
            edittextLoginPassword.inputType = InputType.TYPE_NULL
            buttonLoginRegister.isEnabled = false
            buttonLoginSubmit.isEnabled = false
        }
    }

}