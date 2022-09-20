package hr.ferit.sandroblavicki.sandroapp.create

class CreateUiModel (
    val imageUrl: String,
    val description: String
    ) {

    fun copyWith(imageUrl: String?, description: String?): CreateUiModel =
        CreateUiModel(imageUrl = imageUrl ?: this.imageUrl, description = description ?: this.description)
}