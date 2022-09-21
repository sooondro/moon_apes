package hr.ferit.sandroblavicki.sandroapp.register

sealed class RegisterScreenState (
    val registerData: RegisterUiModel
)

class RegisterErrorState(registerUiModel: RegisterUiModel, val errorText: String) : RegisterScreenState(registerUiModel)
class RegisterLoadingState(registerUiModel: RegisterUiModel) : RegisterScreenState(registerUiModel)
class RegisterUserInputState(registerUiModel: RegisterUiModel) : RegisterScreenState(registerUiModel)