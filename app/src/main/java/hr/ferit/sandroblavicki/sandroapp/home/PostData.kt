package hr.ferit.sandroblavicki.sandroapp.home

data class PostData(
    val userId: String,
    val postId: String,
    val username: String,
    val imageUrl: String,
    val description: String
)