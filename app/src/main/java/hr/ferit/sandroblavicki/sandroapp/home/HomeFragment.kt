package hr.ferit.sandroblavicki.sandroapp.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import hr.ferit.sandroblavicki.sandroapp.databinding.HomeFragmentBinding
import hr.ferit.sandroblavicki.sandroapp.repositories.PostRepositoryImpl


class HomeFragment : Fragment(){

    private lateinit var binding: HomeFragmentBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: HomePageRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(LayoutInflater.from(context),container,false)
        viewModel = HomeViewModel(PostRepositoryImpl())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HomePageRecyclerViewAdapter(requireContext(), listOf(),viewModel)
        viewModel.fetchPosts()

        viewModel.navigationDelegate.observe(viewLifecycleOwner) { navDirections ->
           findNavController().navigate(navDirections)
        }

        viewModel.posts.observe(viewLifecycleOwner) { posts ->
            adapter.setPosts(posts)
        }

        binding.apply {
            homeRecyclerView.adapter = adapter
            homeRecyclerView.layoutManager = LinearLayoutManager(context)
        }
    }




}