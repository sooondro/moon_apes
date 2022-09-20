package hr.ferit.sandroblavicki.sandroapp.repositories

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import hr.ferit.sandroblavicki.sandroapp.home.PostComment
import hr.ferit.sandroblavicki.sandroapp.home.PostData

abstract  class PostRepository {
   abstract fun getPosts(onSuccessListener: (List<PostData>) -> Unit, onFailureListener: OnFailureListener)
   abstract fun updatePost(postId: String) : Unit
   abstract fun getCommentsForPost(postId: String) : MutableList<PostComment>
}