package hr.ferit.sandroblavicki.sandroapp.models

data class NewPostData(
    val userId: String,
    val email: String,
    val username: String,
    val imageUrl: String,
    val description: String
)