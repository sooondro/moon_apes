package hr.ferit.sandroblavicki.sandroapp.create

sealed class CreateScreenState(
    val createData: CreateUiModel
)

class CreateErrorState(createUiModel: CreateUiModel, val errorText: String) :
    CreateScreenState(createUiModel)

class CreateLoadingState(createUiModel: CreateUiModel) : CreateScreenState(createUiModel)
class CreateUserInputState(createUiModel: CreateUiModel) : CreateScreenState(createUiModel)