package hr.ferit.sandroblavicki.sandroapp.create

sealed class CreateScreenState (
    val createData: CreateUiModel
    )

class CreateError(createUiModel: CreateUiModel, val errors: List<String>) : CreateScreenState(createUiModel)
class CreateLoading(createUiModel: CreateUiModel): CreateScreenState(createUiModel)
class CreateValidation(createUiModel: CreateUiModel): CreateScreenState(createUiModel)