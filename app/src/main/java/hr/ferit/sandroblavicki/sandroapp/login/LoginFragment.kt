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
                when (screenState) {
                    is LoginErrorState -> displayErrorUi(screenState)
                    is LoginLoadingState -> displayLoadingUi(screenState)
                    is LoginUserInputState -> displayUserInputUi(screenState)
                }
            }
        }

        binding.apply {
            buttonLoginRegister.setOnClickListener {
                viewModel.navigateTo(LoginFragmentDirections.navigateToRegisterFragment())
            }

            buttonLoginSubmit.setOnClickListener {
                viewModel.onSubmitClicked();
            }

            edittextLoginEmail.addTextChangedListener { editable ->
                viewModel.onEmailChanged(editable.toString())
            }

            edittextLoginPassword.addTextChangedListener { editable ->
                viewModel.onPasswordChanged(editable.toString())
            }
        }
    }

    private fun displayLoadingUi(screenState: LoginLoadingState) {
        disableLoginForm();
        binding.progressbarLogin.visibility = View.VISIBLE
    }

    private fun displayErrorUi(screenState: LoginErrorState) {
        enableLoginForm()
        Toast.makeText(requireContext(), screenState.errorText, Toast.LENGTH_LONG).show()
        binding.edittextLoginPassword.setText("")
    }

    private fun displayUserInputUi(screenState: LoginUserInputState) {
    }

    private fun disableLoginForm(){
        binding.apply {
            edittextLoginEmail.inputType = InputType.TYPE_NULL
            edittextLoginPassword.inputType = InputType.TYPE_NULL
            buttonLoginRegister.isEnabled = false
            buttonLoginSubmit.isEnabled = false
        }
    }

    private fun enableLoginForm() {
        binding.apply {
            edittextLoginEmail.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            edittextLoginPassword.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            buttonLoginRegister.isEnabled = true
            buttonLoginSubmit.isEnabled = true
            progressbarLogin.visibility = View.GONE
        }
    }

}