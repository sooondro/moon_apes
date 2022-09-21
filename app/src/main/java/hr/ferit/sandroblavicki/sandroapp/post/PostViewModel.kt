package hr.ferit.sandroblavicki.sandroapp.post

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.google.gson.Gson
import hr.ferit.sandroblavicki.sandroapp.home.HomeFragmentDirections
import hr.ferit.sandroblavicki.sandroapp.models.PostComment
import hr.ferit.sandroblavicki.sandroapp.models.PostData
import hr.ferit.sandroblavicki.sandroapp.repositories.PostRepository

class PostViewModel(
    private val repository: PostRepository,
) {

    private val _navigationDelegate = MutableLiveData<NavDirections>()
    val navigationDelegate: LiveData<NavDirections> = _navigationDelegate

    private val _screenState =
        MutableLiveData<PostScreenState>(
            PostLoadingState(
                PostUiModel(
                    "",
                    PostData("", "", "", "", "", "")
                )
            )
        )
    val screenState: LiveData<PostScreenState> = _screenState

    private val _comments = MutableLiveData<List<PostComment>>()
    val comments: LiveData<List<PostComment>> = _comments

    fun navigateTo(directions: NavDirections) {
        _navigationDelegate.value = directions
    }

    fun fetchCommentsForPost(postId: String) {
        Log.v("POSTIDFETSCG", postId)
        repository.getCommentsForPost(postId, ::onSuccessToFetchComments, ::onFailedToFetchComments)
    }

    private fun onSuccessToFetchComments(comments: List<PostComment>) {
        _comments.value = comments
        val postData = _screenState.value!!.postData.copyWith("", null)
        _screenState.value = PostUserInputState(postData)
    }

    private fun onFailedToFetchComments(exception: Exception) {
        Log.e("commentsFetch", "Failed to fetch comments")
    }

    fun onCommentChanged(comment: String) {
        val postData = _screenState.value!!.postData.copyWith(comment, null)
        _screenState.value = PostUserInputState(postData)
    }

    fun onSubmitClicked() {
        val postUiModel = _screenState.value!!.postData
        _screenState.value = PostLoadingState(postUiModel)

        val errorText = validateUserInput()

        if (errorText.isNotEmpty()) {
            _screenState.value = PostErrorState(postUiModel, errorText)
        } else {
            postComment()
        }
    }

    private fun postComment() {
        val postData = _screenState.value!!.postData.postData
        Log.v("POSTDATA", postData.toString())
        val postComment = PostComment(
            postData.postId,
            postData.userId,
            postData.username,
            _screenState.value!!.postData.editableComment
        )
        repository.postPostComment(postComment, ::onSuccessToPostComment, ::onFailedToPostComment)
        fetchCommentsForPost(postData.postId)
    }

    private fun onSuccessToPostComment() {
        val postData = _screenState.value!!.postData.copyWith(null, null)
        _screenState.value = PostUserInputState(postData)

        Log.v("postComment", "POST COMMENT")
    }

    private fun onFailedToPostComment(exception: Exception) {
        Log.e("postsFetch", "Failed to fetch posts")
    }

    private fun validateUserInput(): String {
        val comment = _screenState.value!!.postData.editableComment.trim()
        if (comment.isEmpty()) {
            return "You must write a comment!"
        }
        return ""
    }

    fun getPostFromArgs(postDataJsonString: String?): PostData? {
        Log.v("jsonStuff", postDataJsonString ?: "null")
        if (postDataJsonString != null) {
            val post = Gson().fromJson(postDataJsonString, PostData::class.java)
            _screenState.value!!.postData.postData = post
            return post
        }
        _screenState.value = PostErrorState(screenState.value!!.postData, "Failed to laod post")
        return null
    }

    fun onUsernameClicked(userId: String) {
        navigateTo(PostFragmentDirections.navigateToAccountFragment(userId))
    }

    fun onCommentUsernameClicked(userId: String) {
        navigateTo(PostFragmentDirections.navigateToAccountFragment(userId))
    }
}