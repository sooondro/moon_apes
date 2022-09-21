package hr.ferit.sandroblavicki.sandroapp.login

class LoginUiModel(
    val email: String,
    val password: String,
) {

    fun copyWith(email: String?, password: String?): LoginUiModel =
        LoginUiModel(email = email ?: this.email, password = password ?: this.password)
}