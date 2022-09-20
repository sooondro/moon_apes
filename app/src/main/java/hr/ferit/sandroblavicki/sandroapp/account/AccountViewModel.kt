package hr.ferit.sandroblavicki.sandroapp.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import hr.ferit.sandroblavicki.sandroapp.home.PostData
import hr.ferit.sandroblavicki.sandroapp.login.LoginScreenState
import hr.ferit.sandroblavicki.sandroapp.repositories.PostRepository

class AccountViewModel (
    private val repository: PostRepository
        ) : ViewModel() {

    private val _navigationDelegate = MutableLiveData<NavDirections>()
    val navigationDelegate : LiveData<NavDirections> = _navigationDelegate

    private val _posts = MutableLiveData<List<PostData>>()
    val posts : LiveData<List<PostData>> = _posts

    fun navigateTo(directions: NavDirections) {
        _navigationDelegate.value = directions
    }

    fun fetchPosts()  {
        _posts.value = repository.getPosts()
    }

}