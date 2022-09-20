package hr.ferit.sandroblavicki.sandroapp.repositories

import hr.ferit.sandroblavicki.sandroapp.home.PostComment
import hr.ferit.sandroblavicki.sandroapp.home.PostData

abstract  class PostRepository {
   abstract fun getPosts() : List<PostData>
   abstract fun updatePost(postId: String) : Unit
   abstract fun getCommentsForPost(postId: String) : MutableList<PostComment>
}