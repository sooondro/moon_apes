package hr.ferit.sandroblavicki.sandroapp.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import hr.ferit.sandroblavicki.sandroapp.login.LoginScreenState
import hr.ferit.sandroblavicki.sandroapp.login.LoginUiModel
import hr.ferit.sandroblavicki.sandroapp.login.LoginUserInputState
import hr.ferit.sandroblavicki.sandroapp.models.PostData
import hr.ferit.sandroblavicki.sandroapp.repositories.PostRepository
import java.lang.Exception

class HomeViewModel(
    private val repository: PostRepository
) : ViewModel(

) {

    private val _navigationDelegate = MutableLiveData<NavDirections>()
    val navigationDelegate : LiveData<NavDirections> = _navigationDelegate

    private val _posts = MutableLiveData<List<PostData>>()
    val posts : LiveData<List<PostData>> = _posts

    private val _screenState =
        MutableLiveData<HomeScreenState>(HomeLoadingState())
    val screenState: LiveData<HomeScreenState> = _screenState

    fun navigateTo(directions: NavDirections) {
       _navigationDelegate.value = directions
   }

    fun fetchPosts()  {
       repository.getPosts(::onPostsFetched, ::onFailedToFetchPosts)
    }

    private fun onPostsFetched(posts: List<PostData>) {
        Log.v("postsFetch","Fetched posts $posts")
        _posts.value = posts
        _screenState.value = HomeUserInputState()
    }

    private fun onFailedToFetchPosts(exception: Exception) {
        Log.e("postsFetch","Failed to fetch posts")
    }//screenstate





}