package hr.ferit.sandroblavicki.sandroapp.post

import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.Gson
import hr.ferit.sandroblavicki.sandroapp.databinding.PostFragmentBinding
import hr.ferit.sandroblavicki.sandroapp.home.PostData
import hr.ferit.sandroblavicki.sandroapp.repositories.PostRepositoryImpl


class PostFragment : Fragment() {

    private lateinit var binding: PostFragmentBinding
    private lateinit var viewModel: PostViewModel
    private lateinit var postCommentsRecyclerAdapter: PostCommentsRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = PostViewModel(PostRepositoryImpl())
        binding = PostFragmentBinding.inflate(LayoutInflater.from(context),container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val post: PostData? = getPostFromArgs();
        Log.v("jsonStuff", post?.toString() ?: "null")

        if(post == null){
            showErrorView()
            return
        }

        postCommentsRecyclerAdapter = PostCommentsRecyclerAdapter(requireContext(), listOf())
        binding.recyclerviewPostComments.apply {
            adapter = postCommentsRecyclerAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.apply {
            comments.observe(viewLifecycleOwner) { comments ->
                postCommentsRecyclerAdapter.setComments(comments)
            }
            fetchCommentsForPost(post.postId)

        }

        viewModel.navigationDelegate.observe(viewLifecycleOwner) { navDirections ->
            findNavController().navigate(navDirections)
        }

        binding.apply {
            textviewPostUsername.text = post.username ?: "username is null"
            textviewPostDescription.text = post.description
            Glide.with(requireContext()).load(post.imageUrl).into(textviewPostImage)

            textviewPostUsername.setOnClickListener {
                viewModel.navigateTo(PostFragmentDirections.navigateToAccountFragment(post.userId))
            }

            buttonPostAddComment.setOnClickListener {
                buttonPostAddComment.visibility = View.GONE
                linearlayoutPostComment.visibility = View.VISIBLE
            }

            buttonPostSubmitComment.setOnClickListener {
                val comment = edittextPostCreateComment.text.toString().trim()
                if (comment.isEmpty()) {
                    Toast.makeText(requireContext(), "You must write a comment first!", Toast.LENGTH_LONG).show()
                }
            }

            buttonPostCancelComment.setOnClickListener {
                buttonPostAddComment.visibility = View.VISIBLE
                linearlayoutPostComment.visibility = View.GONE
            }
        }
    }
/*
    private fun validateUserInput(comment: String) {
        if (comment.isEmpty())

    }*/

    private fun getPostFromArgs(): PostData? {
        var post: PostData? = null
        val postJsonString: String? = arguments?.getString("post")
        Log.v("jsonStuff", postJsonString ?: "null")
        if(postJsonString != null){
            post = Gson().fromJson(postJsonString,PostData::class.java)
        }
        return post
    }

    private fun showErrorView() {
        binding.apply {
            textviewPostUsername.visibility = View.GONE
            textviewPostImage.visibility = View.GONE
            recyclerviewPostComments.visibility = View.GONE
            textviewPostDescription.visibility = View.GONE
            textviewPostError.visibility = View.VISIBLE
        }
    }


}