package hr.ferit.sandroblavicki.sandroapp.post

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import hr.ferit.sandroblavicki.sandroapp.databinding.PostFragmentBinding
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
        binding = PostFragmentBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val postArguments = arguments?.getString("post")
        val post = viewModel.getPostFromArgs(postArguments)
        Log.v("jsonStuffOG", post?.toString() ?: "null")

        if (post == null) {
            displayErrorMessageView()
            return
        }

        postCommentsRecyclerAdapter = PostCommentsRecyclerAdapter(requireContext(), listOf())

        binding.apply {
            recyclerviewPostComments.apply {
                adapter = postCommentsRecyclerAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }

            textviewPostUsername.apply {
                text = "@" + post.username

                setOnClickListener {
                    viewModel.onUsernameClicked(post.userId)
                }
            }

            edittextPostCreateComment.addTextChangedListener { editable ->
                viewModel.onCommentChanged(editable.toString())
            }

            textviewPostDescription.text = post.description

            Glide.with(requireContext()).load(post.imageUrl).into(textviewPostImage)


            buttonPostAddComment.setOnClickListener {
                showCommentInputView()
            }

            buttonPostSubmitComment.setOnClickListener {
                viewModel.onSubmitClicked()
            }

            buttonPostCancelComment.setOnClickListener {
                hideCommentInputView()
            }
        }

        viewModel.apply {
            fetchCommentsForPost(post.postId)

            navigationDelegate.observe(viewLifecycleOwner) { navDirections ->
                findNavController().navigate(navDirections)
            }

            screenState.observe(viewLifecycleOwner) { screenState ->
                when (screenState) {
                    is PostErrorState -> displayErrorUi()
                    is PostLoadingState -> displayLoadingUi()
                    is PostUserInputState -> displayUserInputUi()
                }
            }

            comments.observe(viewLifecycleOwner) { comments ->
                postCommentsRecyclerAdapter.setComments(comments)
            }
        }


    }

    private fun displayLoadingUi() {
        binding.apply {
            progressbarPost.visibility = View.VISIBLE
        }

        hideCommentInputView()
    }

    private fun displayUserInputUi() {
        binding.apply {
            progressbarPost.visibility = View.GONE
        }
    }

    private fun displayErrorUi() {
        binding.apply {
            progressbarPost.visibility = View.GONE
            textviewPostError.visibility = View.VISIBLE
        }
        Toast.makeText(requireContext(), "You must write a comment first!", Toast.LENGTH_LONG)
            .show()
    }

    private fun displayErrorMessageView() {
        binding.apply {
            textviewPostUsername.visibility = View.GONE
            textviewPostImage.visibility = View.GONE
            recyclerviewPostComments.visibility = View.GONE
            textviewPostDescription.visibility = View.GONE
            textviewPostError.visibility = View.VISIBLE
        }
    }

    private fun showCommentInputView() {
        binding.apply {
            buttonPostAddComment.visibility = View.GONE
            buttonPostSubmitComment.visibility = View.VISIBLE
            buttonPostCancelComment.visibility = View.VISIBLE
            edittextPostCreateComment.visibility = View.VISIBLE
        }
    }

    private fun hideCommentInputView() {
        binding.apply {
            buttonPostAddComment.visibility = View.VISIBLE
            buttonPostSubmitComment.visibility = View.GONE
            buttonPostCancelComment.visibility = View.GONE
            edittextPostCreateComment.visibility = View.GONE
        }
    }
}
