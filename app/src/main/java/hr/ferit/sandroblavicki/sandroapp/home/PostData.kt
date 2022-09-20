package hr.ferit.sandroblavicki.sandroapp.home

data class PostData(
    val userId: String,
    val postId: String,
    val email: String,
    val username: String,
    val imageUrl: String,
    val description: String
) {

    companion object {
        fun fromFirebaseObject(firebaseObject: Map<String, Any>): PostData = PostData(
            firebaseObject["userId"].toString(),
            firebaseObject["postId"].toString(),
            firebaseObject["email"].toString(),
            firebaseObject["username"].toString(),
            firebaseObject["imageUrl"].toString(),
            firebaseObject["description"].toString(),
        )


    }
}