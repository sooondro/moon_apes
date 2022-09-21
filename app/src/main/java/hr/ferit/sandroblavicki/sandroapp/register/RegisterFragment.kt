package hr.ferit.sandroblavicki.sandroapp.register

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import hr.ferit.sandroblavicki.sandroapp.databinding.RegisterFragmentBinding

class RegisterFragment : Fragment() {
    private lateinit var viewModel: RegisterViewModel
    private lateinit var binding: RegisterFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegisterFragmentBinding.inflate(LayoutInflater.from(context), container, false)
        viewModel = RegisterViewModel()
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
                    is RegisterErrorState -> displayErrorUi(screenState)
                    is RegisterLoadingState -> displayLoadingUi(screenState)
                    is RegisterUserInputState -> displayUserInputUi(screenState)
                }
            }
        }

        binding.apply {
            buttonRegisterLogin.setOnClickListener {
                viewModel.navigateTo(RegisterFragmentDirections.navigateToLoginFragment())
            }

            buttonRegisterSubmit.setOnClickListener {
                viewModel.onSubmitClicked();
            }

            edittextRegisterEmail.addTextChangedListener { editable ->
                val email = editable.toString();
                viewModel.onEmailChanged(email)
            }

            edittextRegisterPassword.addTextChangedListener { editable ->
                val password = editable.toString();
                viewModel.onPasswordChanged(password)
            }

            edittextRegisterRepeatedPassword.addTextChangedListener { editable ->
                val password = editable.toString();
                viewModel.onRepeatedPasswordChanged(password)
            }
        }
    }

    private fun displayLoadingUi(screenState: RegisterLoadingState) {
        disableRegisterForm();
        binding.progressbarRegister.visibility = View.VISIBLE
    }

    private fun displayErrorUi(screenState: RegisterErrorState) {
        enableRegisterForm()

        binding.apply {
            edittextRegisterEmail.setText(screenState.registerData.email)
            edittextRegisterPassword.setText("")
            edittextRegisterRepeatedPassword.setText("")
        }

        Toast.makeText(requireContext(), screenState.errorText, Toast.LENGTH_LONG).show()
    }

    private fun displayUserInputUi(screenState: RegisterUserInputState) {

    }

    private fun enableRegisterForm() {
        binding.apply {
            edittextRegisterEmail.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            edittextRegisterPassword.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            edittextRegisterRepeatedPassword.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            buttonRegisterLogin.isEnabled = true
            buttonRegisterSubmit.isEnabled = true
            progressbarRegister.visibility = View.GONE
        }
    }

    private fun disableRegisterForm() {
        binding.apply {
            edittextRegisterEmail.inputType = InputType.TYPE_NULL
            edittextRegisterPassword.inputType = InputType.TYPE_NULL
            edittextRegisterRepeatedPassword.inputType = InputType.TYPE_NULL
            buttonRegisterSubmit.isEnabled = false
            buttonRegisterLogin.isEnabled = false
        }
    }
}