package hr.ferit.sandroblavicki.sandroapp.login

class LoginUiModel(
    val username: String,
    val password: String,
    //val errorMessage: String?,
) {

    fun copyWith(username: String?, password: String?): LoginUiModel =
        LoginUiModel(username = username ?: this.username, password = password ?: this.password)
}