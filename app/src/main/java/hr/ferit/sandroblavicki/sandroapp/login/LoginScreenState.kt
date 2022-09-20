package hr.ferit.sandroblavicki.sandroapp.login

sealed class LoginScreenState(
    val loginData: LoginUiModel
)


class LoginError(loginUiModel: LoginUiModel, val errors: List<String>) : LoginScreenState(loginUiModel)
class LoginLoading(loginUiModel: LoginUiModel) : LoginScreenState(loginUiModel)
class LoginUserInputState(loginUiModel: LoginUiModel) : LoginScreenState(loginUiModel) {

}