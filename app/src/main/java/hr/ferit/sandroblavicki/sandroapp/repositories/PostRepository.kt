package hr.ferit.sandroblavicki.sandroapp.repositories

import hr.ferit.sandroblavicki.sandroapp.create.CreateUiModel
import hr.ferit.sandroblavicki.sandroapp.models.PostComment
import hr.ferit.sandroblavicki.sandroapp.models.PostData
import java.lang.Exception

abstract class PostRepository {
    abstract fun getPosts(
        onSuccessListener: (List<PostData>) -> Unit,
        onFailureListener: (Exception) -> Unit
    )

    abstract fun getPostsByUserId(
        userId: String,
        onSuccessListener: (List<PostData>) -> Unit,
        onFailureListener: (Exception) -> Unit
    )

    abstract fun getCommentsForPost(
        postId: String,
        onSuccessListener: (List<PostComment>) -> Unit,
        onFailureListener: (Exception) -> Unit,
    )

    abstract fun postPostComment(
        postComment: PostComment,
        onSuccessListener: () -> Unit,
        onFailureListener: (Exception) -> Unit,
    )

    abstract fun postPost(
        createData: CreateUiModel,
        onSuccessListener: () -> Unit,
        onFailureListener: (Exception) -> Unit
    )
}