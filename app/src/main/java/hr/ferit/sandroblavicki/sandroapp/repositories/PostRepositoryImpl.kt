package hr.ferit.sandroblavicki.sandroapp.repositories

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hr.ferit.sandroblavicki.sandroapp.create.CreateUiModel
import hr.ferit.sandroblavicki.sandroapp.models.PostComment
import hr.ferit.sandroblavicki.sandroapp.models.PostData
import hr.ferit.sandroblavicki.sandroapp.models.NewPostData
import java.lang.Exception

class PostRepositoryImpl : PostRepository() {

    private var firestoreInstance: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun getPosts(
        onSuccessListener: (List<PostData>) -> Unit,
        onFailureListener: (Exception) -> Unit
    ) {
        firestoreInstance.collection("posts")
            .get()
            .addOnFailureListener(onFailureListener)
            .addOnSuccessListener { result ->
                val posts = mutableListOf<PostData>()

                for (document in result) {
                    Log.d("postsFetch", "${document.id} => ${document.data}")
                    val postData = PostData.fromFirebaseObject(document.id, document.data)
                    posts.add(postData)
                }
                onSuccessListener(posts)
            }
            .addOnFailureListener { exception ->
                Log.w("postsFetch", "Error getting documents.", exception)

            }
    }

    override fun getPostsByUserId(
        userId: String,
        onSuccessListener: (List<PostData>) -> Unit,
        onFailureListener: (Exception) -> Unit
    ) {
        firestoreInstance.collection("posts")
            .whereEqualTo("userId", userId)
            .get()
            .addOnFailureListener(onFailureListener)
            .addOnSuccessListener { result ->
                val posts = mutableListOf<PostData>()

                for (document in result) {
                    Log.d("postsFetch", "${document.id} => ${document.data}")
                    val postData = PostData.fromFirebaseObject(document.id, document.data)
                    posts.add(postData)
                }
                onSuccessListener(posts)
            }
            .addOnFailureListener { exception ->
                onFailureListener(exception)
                Log.w("postsFetch", "Error getting documents.", exception)

            }
    }

    override fun postPostComment(
        postComment: PostComment,
        onSuccessListener: () -> Unit,
        onFailureListener: (Exception) -> Unit,
    ) {
        Log.v("postCommentPostComment", postComment.toString())
        Log.v("POSTIDPOSTCOMMENT", postComment.postId)
        Log.v("POSTIDPOSTCOMMENT", postComment.userId)
        Log.v("POSTIDPOSTCOMMENT", postComment.comment)
        Log.v("POSTIDPOSTCOMMENT", postComment.username)

        firestoreInstance.collection("posts")
            .document(postComment.postId)
            .collection("comments")
            .add(postComment)
            .addOnFailureListener { exception ->
                onFailureListener(exception)
            }
            .addOnSuccessListener { _ ->
                onSuccessListener()
            }
    }

    override fun postPost(
        createData: CreateUiModel,
        onSuccessListener: () -> Unit,
        onFailureListener: (Exception) -> Unit
    ) {
        Log.v("POSTPOST", "DOSOJE")
        val currentUser = FirebaseAuth.getInstance().currentUser
        val currentUserUsername = currentUser!!.email!!.substringBefore("@")
        val post = NewPostData(
            currentUser.uid,
            currentUser.email!!,
            currentUserUsername,
            createData.imageUrl,
            createData.description
        )
        firestoreInstance.collection("posts")
            .add(post)
            .addOnFailureListener { exception ->
                onFailureListener(exception)
            }
            .addOnSuccessListener { _ ->
                onSuccessListener()
            }
    }

    override fun getCommentsForPost(
        postId: String,
        onSuccessListener: (List<PostComment>) -> Unit,
        onFailureListener: (Exception) -> Unit
    ) {
        firestoreInstance.collection("posts")
            .document(postId)
            .collection("comments")
            .get()
            .addOnFailureListener { exception ->
                onFailureListener(exception)
            }
            .addOnSuccessListener { result ->
                val comments = mutableListOf<PostComment>()

                for (document in result) {
                    Log.d("commentsFetch", "${document.id} => ${document.data}")
                    val postComment = PostComment.fromFirebaseObject(document.id, document.data)
                    comments.add(postComment)
                }
                onSuccessListener(comments)
            }
    }


}