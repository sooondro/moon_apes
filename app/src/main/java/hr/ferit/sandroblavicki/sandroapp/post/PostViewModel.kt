package hr.ferit.sandroblavicki.sandroapp.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import hr.ferit.sandroblavicki.sandroapp.home.PostComment
import hr.ferit.sandroblavicki.sandroapp.repositories.PostRepository

class PostViewModel (
    private val postRepository: PostRepository,
        ) {

    private val _navigationDelegate = MutableLiveData<NavDirections>()
    val navigationDelegate : LiveData<NavDirections> = _navigationDelegate

    fun navigateTo(directions: NavDirections) {
        _navigationDelegate.value = directions
    }

    private val _comments = MutableLiveData<MutableList<PostComment>>()
    val comments : LiveData<MutableList<PostComment>> = _comments

    fun fetchCommentsForPost(postId: String){
        val comments: MutableList<PostComment> = postRepository.getCommentsForPost(postId)
        _comments.value = comments
    }

    fun postCommentForPost(postId: String, comment: String, username: String, userId: String) {
        val comment = PostComment(postId, userId, username, comment)
        _comments.value?.add(comment)
    }


}