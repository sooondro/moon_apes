package hr.ferit.sandroblavicki.sandroapp.post

import hr.ferit.sandroblavicki.sandroapp.models.PostData

class PostUiModel (
    val editableComment: String,
    var postData: PostData
) {
    fun copyWith(comment: String?, postData: PostData?): PostUiModel =
        PostUiModel(editableComment = comment ?: this.editableComment, postData = postData ?: this.postData)


}
