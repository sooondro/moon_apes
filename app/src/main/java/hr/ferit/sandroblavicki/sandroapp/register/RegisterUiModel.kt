package hr.ferit.sandroblavicki.sandroapp.register

class RegisterUiModel (
    val email: String,
    val password: String,
    val repeatedPassword: String,

) {

    fun copyWith(email: String?, password: String?, repeatedPassword: String?): RegisterUiModel =
        RegisterUiModel(email = email ?: this.email, password = password ?: this.password, repeatedPassword = repeatedPassword ?: this.repeatedPassword)

}