package hr.ferit.sandroblavicki.sandroapp.login

sealed class LoginScreenState(
    val loginData: LoginUiModel
)


class LoginErrorState(loginUiModel: LoginUiModel, val errorText: String) :
    LoginScreenState(loginUiModel)

class LoginLoadingState(loginUiModel: LoginUiModel) : LoginScreenState(loginUiModel)
class LoginUserInputState(loginUiModel: LoginUiModel) : LoginScreenState(loginUiModel)

