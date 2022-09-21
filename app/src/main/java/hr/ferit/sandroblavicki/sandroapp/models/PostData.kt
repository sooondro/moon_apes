package hr.ferit.sandroblavicki.sandroapp.models

data class PostData(
    val postId: String,
    val userId: String,
    val email: String,
    val username: String,
    val imageUrl: String,
    val description: String
) {

    companion object {
        fun fromFirebaseObject(firebaseObjectId: String, firebaseObject: Map<String, Any>): PostData = PostData(
            firebaseObjectId,
            firebaseObject["userId"].toString(),
            firebaseObject["email"].toString(),
            firebaseObject["username"].toString(),
            firebaseObject["imageUrl"].toString(),
            firebaseObject["description"].toString(),
        )


    }
}