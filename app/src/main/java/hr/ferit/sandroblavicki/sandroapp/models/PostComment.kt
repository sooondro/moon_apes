package hr.ferit.sandroblavicki.sandroapp.models

class PostComment(
    val postId: String,
    val userId: String,
    val username: String,
    val comment: String
) {

    companion object {
        fun fromFirebaseObject(firebaseObjectId: String, firebaseObject: Map<String, Any>): PostComment =
            PostComment(
                firebaseObject["postId"].toString(),
                firebaseObject["userId"].toString(),
                firebaseObject["username"].toString(),
                firebaseObject["comment"].toString()
            )
    }
}