package hr.ferit.sandroblavicki.sandroapp.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import hr.ferit.sandroblavicki.sandroapp.databinding.AccountFragmentBinding
import hr.ferit.sandroblavicki.sandroapp.repositories.PostRepositoryImpl

class AccountFragment : Fragment() {

    private lateinit var binding: AccountFragmentBinding
    private lateinit var viewModel: AccountViewModel
    private lateinit var adapter: AccountRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AccountFragmentBinding.inflate(LayoutInflater.from(context), container, false)
        viewModel = AccountViewModel(PostRepositoryImpl())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userJsonString: String? = arguments?.getString("user")
        val user = viewModel.getCurrentUser(userJsonString)

        adapter = AccountRecyclerViewAdapter(requireContext(), listOf(), viewModel)

        viewModel.apply {
            fetchUserPosts(user)

            screenState.observe(viewLifecycleOwner) { screenState ->
                when (screenState) {
                    is AccountErrorState -> displayErrorUi(screenState)
                    is AccountLoadingState -> displayLoadingUi(screenState)
                    is AccountUserInputState -> displayUserInputUi(screenState)
                }
            }

            navigationDelegate.observe(viewLifecycleOwner) { navDirections ->
                findNavController().navigate(navDirections)
            }

            posts.observe(viewLifecycleOwner) { posts ->
                adapter.setPosts(posts)
            }

        }

        binding.apply {
            recyclerviewAccount.adapter = adapter
            textviewAccountUsername.text = user.username
            recyclerviewAccount.layoutManager = GridLayoutManager(context, 3)
        }
    }

    private fun displayErrorUi(screenState: AccountErrorState) {
        binding.apply {
            textviewAccountError.visibility = View.VISIBLE
            recyclerviewAccount.visibility = View.GONE
            progressbarAccount.visibility = View.GONE
        }
    }

    private fun displayLoadingUi(screenState: AccountLoadingState) {
        binding.apply {
            progressbarAccount.visibility = View.VISIBLE
            recyclerviewAccount.visibility = View.GONE
            textviewAccountError.visibility = View.GONE
        }
    }

    private fun displayUserInputUi(screenState: AccountUserInputState) {
        binding.apply {
            recyclerviewAccount.visibility = View.VISIBLE
            progressbarAccount.visibility = View.GONE
            textviewAccountError.visibility = View.GONE
        }
    }
}