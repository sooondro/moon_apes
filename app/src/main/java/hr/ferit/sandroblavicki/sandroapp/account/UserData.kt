package hr.ferit.sandroblavicki.sandroapp.account

import hr.ferit.sandroblavicki.sandroapp.home.PostData

class UserData (
    val userId: String,
    val username: String,
    val posts: List<PostData>
    )