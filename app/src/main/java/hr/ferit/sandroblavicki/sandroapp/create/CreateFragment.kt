package hr.ferit.sandroblavicki.sandroapp.create

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
import hr.ferit.sandroblavicki.sandroapp.databinding.CreateFragmentBinding
import hr.ferit.sandroblavicki.sandroapp.repositories.PostRepositoryImpl

class CreateFragment : Fragment() {

    private lateinit var binding: CreateFragmentBinding
    private lateinit var viewModel: CreateViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CreateFragmentBinding.inflate(LayoutInflater.from(context), container, false)
        viewModel = CreateViewModel(PostRepositoryImpl())
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
                    is CreateLoadingState -> displayLoadingUi(screenState)
                    is CreateUserInputState -> displayUserInputUi(screenState)
                    is CreateErrorState -> displayErrorUi(screenState)
                }
            }
        }


        binding.apply {
            buttonCreateSubmit.setOnClickListener {

                buttonCreateSubmit.setOnClickListener {
                    Toast.makeText(requireContext(), "CLICKED", Toast.LENGTH_SHORT)
                    Log.v("submitButton", "oncreate")
                    viewModel.onSubmitClicked()
                }

                edittextCreateDescription.addTextChangedListener { editable ->
                    viewModel.onDescriptionChanged(editable.toString())
                }

                edittextCreateImageUrl.addTextChangedListener { editable ->
                    viewModel.onImageUrlChanged(editable.toString())
                }
            }
        }
    }

    private fun displayErrorUi(screenState: CreateErrorState) {
        enableCreateForm()
        Toast.makeText(requireContext(), screenState.errorText, Toast.LENGTH_LONG).show()
        binding.apply {
            edittextCreateDescription.setText(screenState.createData.description)
            edittextCreateImageUrl.setText(screenState.createData.imageUrl)
            progressbarCreate.visibility = View.GONE
        }
    }

    private fun displayUserInputUi(screenState: CreateUserInputState) {

    }

    private fun displayLoadingUi(screenState: CreateLoadingState) {
        disableCreateForm()

        binding.apply {
            progressbarCreate.visibility = View.VISIBLE
        }
    }

    private fun disableCreateForm() {
        binding.apply {
            edittextCreateDescription.inputType = InputType.TYPE_NULL
            edittextCreateImageUrl.inputType = InputType.TYPE_NULL
            buttonCreateSubmit.isEnabled = false
        }
    }

    private fun enableCreateForm() {
        binding.apply {
            edittextCreateDescription.inputType = InputType.TYPE_TEXT_VARIATION_NORMAL
            edittextCreateImageUrl.inputType = InputType.TYPE_TEXT_VARIATION_URI
            buttonCreateSubmit.isEnabled = true
        }
    }
}