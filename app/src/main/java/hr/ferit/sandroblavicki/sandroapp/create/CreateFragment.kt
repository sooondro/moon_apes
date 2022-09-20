package hr.ferit.sandroblavicki.sandroapp.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import hr.ferit.sandroblavicki.sandroapp.databinding.CreateFragmentBinding

class CreateFragment : Fragment() {

    private lateinit var binding: CreateFragmentBinding
    private lateinit var viewModel: CreateViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CreateFragmentBinding.inflate(LayoutInflater.from(context), container, false)
        viewModel = CreateViewModel()
        viewModel.setScreenState(CreateLoading(CreateUiModel("", "")))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navigationDelegate.observe(viewLifecycleOwner) { navDirections ->
            findNavController().navigate(navDirections)
        }

        viewModel.screenState.observe(viewLifecycleOwner) { screenState ->
            when (screenState) {
                is CreateLoading -> displayLoadingUi(screenState)
                is CreateValidation -> displayValidationUi(screenState)
                is CreateError -> displayErrorUi(screenState)
            }
        }

        binding.apply {
            buttonCreateSubmit.setOnClickListener {
                val imageUrl = edittextCreateImageUrl.text.toString().trim()
                val description = edittextCreateDescription.text.toString().trim()

                val errors = validateUserInput(imageUrl, description)

                if (errors.isNotEmpty()) {
                    viewModel.setScreenState(
                        CreateError(
                            viewModel.screenState.value?.createData?.copyWith(
                                imageUrl,
                                description
                            )!!, errors
                        )
                    )
                }

                //submitPost()
            }
        }
    }

    private fun displayErrorUi(screenState: CreateError) {
        binding.apply {
            edittextCreateImageUrl.setText(screenState.createData.imageUrl)
            edittextCreateDescription.setText(screenState.createData.description)
        }

        val errorsText = screenState.errors.joinToString("\n")

        Toast.makeText(requireContext(), errorsText, Toast.LENGTH_LONG).show()
    }

    private fun displayValidationUi(screenState: CreateValidation) {

    }

    private fun displayLoadingUi(screenState: CreateLoading) {

    }

    private fun validateUserInput(imageUrl: String, description: String): MutableList<String> {
        var errors: MutableList<String> = mutableListOf()

        if (imageUrl.isEmpty()) {
            errors.add("Image URL has to be filled")
        }

        if (description.isEmpty()) {
            errors.add("Description has to be filled")
        }

        return errors
    }
}