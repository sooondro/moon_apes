package hr.ferit.sandroblavicki.sandroapp.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import hr.ferit.sandroblavicki.sandroapp.databinding.HomeFragmentBinding
import hr.ferit.sandroblavicki.sandroapp.login.LoginErrorState
import hr.ferit.sandroblavicki.sandroapp.login.LoginLoadingState
import hr.ferit.sandroblavicki.sandroapp.login.LoginUserInputState
import hr.ferit.sandroblavicki.sandroapp.repositories.PostRepositoryImpl


class HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: HomePageRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(LayoutInflater.from(context), container, false)
        viewModel = HomeViewModel(PostRepositoryImpl())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HomePageRecyclerViewAdapter(requireContext(), listOf(), viewModel)

        viewModel.apply {
            fetchPosts()

            navigationDelegate.observe(viewLifecycleOwner) { navDirections ->
                findNavController().navigate(navDirections)
            }

            posts.observe(viewLifecycleOwner) { posts ->
                adapter.setPosts(posts)
            }

            screenState.observe(viewLifecycleOwner) { screenState ->
                when (screenState) {
                    is HomeErrorState -> displayErrorUi()
                    is HomeLoadingState -> displayLoadingUi()
                    is HomeUserInputState -> displayUserInputUi()
                }
            }
        }

        binding.apply {
            homeRecyclerView.adapter = adapter
            homeRecyclerView.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun displayUserInputUi() {
        binding.apply {
            progressbarHome.visibility = View.GONE
            textviewHomeError.visibility = View.GONE
        }
    }

    private fun displayLoadingUi() {
        binding.apply {
            progressbarHome.visibility = View.VISIBLE
        }
    }

    private fun displayErrorUi() {
        binding.apply {
            progressbarHome.visibility = View.GONE
            textviewHomeError.visibility = View.VISIBLE
        }
    }


}