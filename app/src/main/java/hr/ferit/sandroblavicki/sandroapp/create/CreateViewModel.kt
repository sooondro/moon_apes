package hr.ferit.sandroblavicki.sandroapp.create

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import hr.ferit.sandroblavicki.sandroapp.repositories.PostRepository

class CreateViewModel(
    private val repository: PostRepository,
) : ViewModel() {

    private val _navigationDelegate = MutableLiveData<NavDirections>()
    val navigationDelegate: LiveData<NavDirections> = _navigationDelegate

    private val _screenState = MutableLiveData<CreateScreenState>(
        CreateUserInputState(CreateUiModel("", ""))
    )
    val screenState: LiveData<CreateScreenState> = _screenState

    fun navigateTo(directions: NavDirections) {
        _navigationDelegate.value = directions
    }

    fun onSubmitClicked() {
        val createData = _screenState.value!!.createData
        _screenState.value = CreateLoadingState(createData)
        validateUserInputAndPost()
    }

    private fun validateUserInputAndPost() {
        val createData = _screenState.value!!.createData
        val errors = validateUserInput(createData.imageUrl, createData.description)

        if (errors.isNotEmpty()) {
            val errorText = errors.joinToString("\n")
            _screenState.value = CreateErrorState(createData, errorText)
            return
        }

        Log.v("createValidation", "PROSAO")
        repository.postPost(createData, ::onSuccessToPostPost, ::onFailedToPostPost)
    }

    private fun onSuccessToPostPost() {
        navigateTo(CreateFragmentDirections.navigateToHomeFragment())
    }

    private fun onFailedToPostPost(exception: Exception) {
        Log.e("postPost", "Failed to post post")
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

    fun onImageUrlChanged(imageUrl: String) {
        val createData = _screenState.value!!.createData.copyWith(imageUrl, null)
        _screenState.value = CreateUserInputState(createData)
        Log.v("IMAGEURL", _screenState.value!!.createData.imageUrl)
    }

    fun onDescriptionChanged(description: String) {
        val createData = _screenState.value!!.createData.copyWith(null, description)
        _screenState.value = CreateUserInputState(createData)
    }
}
