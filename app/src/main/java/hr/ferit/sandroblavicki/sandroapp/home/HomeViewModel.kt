package hr.ferit.sandroblavicki.sandroapp.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import hr.ferit.sandroblavicki.sandroapp.repositories.PostRepository
import java.lang.Exception
import java.util.concurrent.Future

class HomeViewModel(
    private val homeRepo: PostRepository
) : ViewModel(

) {

    private val _navigationDelegate = MutableLiveData<NavDirections>()
    val navigationDelegate : LiveData<NavDirections> = _navigationDelegate

    private val _posts = MutableLiveData<List<PostData>>()
    val posts : LiveData<List<PostData>> = _posts

    fun navigateTo(directions: NavDirections) {
       _navigationDelegate.value = directions
   }

    fun fetchPosts()  {
       homeRepo.getPosts(::onPostsFetched, ::onFiledToFecthPosts)
    }

    private fun onPostsFetched(posts: List<PostData>) {
        Log.v("postsFetch","Fetched posts $posts")

        _posts.value = posts
    }

    private fun onFiledToFecthPosts(exception: Exception) {
        Log.e("postsFetch","Failed to fetch posts")
    }//screenstate





}