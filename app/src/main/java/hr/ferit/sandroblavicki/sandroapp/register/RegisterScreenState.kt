package hr.ferit.sandroblavicki.sandroapp.register

sealed class RegisterScreenState (
    val registerData: RegisterUiModel
)

class RegisterError(registerUiModel: RegisterUiModel, val errors: List<String>) : RegisterScreenState(registerUiModel)
class RegisterLoading(registerUiModel: RegisterUiModel) : RegisterScreenState(registerUiModel)
class RegisterValidation(registerUiModel: RegisterUiModel) : RegisterScreenState(registerUiModel)