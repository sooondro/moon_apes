package hr.ferit.sandroblavicki.sandroapp.post


sealed class PostScreenState(
    val postData: PostUiModel
)

class PostErrorState(postUiModel: PostUiModel, val errorText: String) : PostScreenState(postUiModel)
class PostLoadingState(postUiModel: PostUiModel) : PostScreenState(postUiModel)
class PostUserInputState(postUiModel: PostUiModel) : PostScreenState(postUiModel)
