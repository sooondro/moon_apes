package hr.ferit.sandroblavicki.sandroapp.account

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import hr.ferit.sandroblavicki.sandroapp.models.PostData
import hr.ferit.sandroblavicki.sandroapp.models.UserData
import hr.ferit.sandroblavicki.sandroapp.repositories.PostRepository
import java.lang.Exception

class AccountViewModel(
    private val repository: PostRepository
) : ViewModel() {

    private val _navigationDelegate = MutableLiveData<NavDirections>()
    val navigationDelegate: LiveData<NavDirections> = _navigationDelegate

    private val _posts = MutableLiveData<List<PostData>>()
    val posts: LiveData<List<PostData>> = _posts

    private val _screenState =
        MutableLiveData<AccountScreenState>(AccountLoadingState())
    val screenState: LiveData<AccountScreenState> = _screenState


    fun navigateTo(directions: NavDirections) {
        _navigationDelegate.value = directions
    }

    fun fetchUserPosts(userData: UserData) {
        repository.getPostsByUserId(
            userData.userId,
            ::onSuccessToFetchPosts,
            ::onFailedToFetchPosts
        )
    }

    private fun onSuccessToFetchPosts(posts: List<PostData>) {
        Log.v("postsFetch", "Fetched posts $posts")
        _posts.value = posts
        _screenState.value = AccountUserInputState()
    }

    private fun onFailedToFetchPosts(exception: Exception) {
        Log.e("postsFetch", "Failed to fetch posts")
    }

    fun getCurrentUser(userJsonString: String?): UserData {
        val userData = getUserFromArgs(userJsonString)

        if (userData != null) {
            return userData
        }

        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        return UserData(currentUser!!.uid, currentUser.email!!.substringBefore("@"))
    }

    private fun getUserFromArgs(userJsonString: String?): UserData? {
        var user: UserData? = null
        Log.v("jsonStuff", userJsonString ?: "null")
        if (userJsonString != null) {
            user = Gson().fromJson(userJsonString, UserData::class.java)
        }
        return user
    }

    fun onPostClicked(postData: PostData) {
        val postJsonString: String = Gson().toJson(postData)

        navigateTo(
            AccountFragmentDirections.navigateToPostFragment(
                postJsonString
            )
        )
    }
}