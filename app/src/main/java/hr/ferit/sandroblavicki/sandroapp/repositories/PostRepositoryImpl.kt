package hr.ferit.sandroblavicki.sandroapp.repositories

import hr.ferit.sandroblavicki.sandroapp.home.PostComment
import hr.ferit.sandroblavicki.sandroapp.home.PostData

class PostRepositoryImpl : PostRepository() {

    private val posts = listOf<PostData>(
        PostData(
            userId = "1",
            postId = "1",
            username = "Dacalino",
            imageUrl = "https://static.remove.bg/remove-bg-web/c4b29bf4b97131238fda6316e24c9b3606c18000/assets/start-1abfb4fe2980eabfbbaaa4365a0692539f7cd2725f324f904565a9a744f8e214.jpg",
            description = "Dasidsajdisaj",
            email = "test@test.com"
        ),
        PostData(
            userId = "2",
            postId = "1",
            username = "Dacalino 2",
            imageUrl = "https://static.remove.bg/remove-bg-web/c4b29bf4b97131238fda6316e24c9b3606c18000/assets/start-1abfb4fe2980eabfbbaaa4365a0692539f7cd2725f324f904565a9a744f8e214.jpg",
            description = "Dasidsajdisaj 2",
            email = "test@test.com"
        ),
        PostData(
            userId = "3",
            postId = "1",
            username = "Dacalino 3",
            imageUrl = "https://static.remove.bg/remove-bg-web/c4b29bf4b97131238fda6316e24c9b3606c18000/assets/start-1abfb4fe2980eabfbbaaa4365a0692539f7cd2725f324f904565a9a744f8e214.jpg",
            description = "Dasidsajdisaj 3",
            email = "test@test.com"
        ),
        PostData(
            userId = "4",
            postId = "1",

            username = "Dacalin4 o",
            imageUrl = "https://static.remove.bg/remove-bg-web/c4b29bf4b97131238fda6316e24c9b3606c18000/assets/start-1abfb4fe2980eabfbbaaa4365a0692539f7cd2725f324f904565a9a744f8e214.jpg",
            description = "Dasidsajdisaj 4",
            email = "test@test.com"
        ),
    )

    private val comments = mutableListOf<PostComment>(
        PostComment(
            postId = "1",
            userId = "1",
            username = "Dacalino",
            comment = "Nice boobs"
        ),
        PostComment(
            postId = "1",
            userId = "1",
            username = "drugi",
            comment = "Nice dasdasdasdasd"
        ),
        PostComment(
            postId = "1",
            userId = "1",
            username = "trec",
            comment = "Nice boobasdsadsas"
        ),
        PostComment(
            postId = "1",
            userId = "1",
            username = "Dacalino",
            comment = "Nice boobs"
        ),
        PostComment(
            postId = "1",
            userId = "1",
            username = "Dacalino",
            comment = "Nice boogggggggbs"
        ),

        )

    override fun getPosts(): List<PostData> {
        return posts
    }

    override fun updatePost(postId: String) {
        TODO("Not yet implemented")
    }

    override fun getCommentsForPost(postId: String) : MutableList<PostComment> = comments


}