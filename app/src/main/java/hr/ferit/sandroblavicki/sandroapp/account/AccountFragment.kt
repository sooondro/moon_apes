package hr.ferit.sandroblavicki.sandroapp.account

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import hr.ferit.sandroblavicki.sandroapp.databinding.AccountFragmentBinding
import hr.ferit.sandroblavicki.sandroapp.home.PostData
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

        val user: UserData? = getUserFromArgs();
        Log.v("jsonStuff", user?.toString() ?: "null")

/*        if(post == null){
            showErrorView()
            return
        }*/

        adapter = AccountRecyclerViewAdapter(requireContext(), listOf(), viewModel)
        viewModel.fetchPosts()

        viewModel.navigationDelegate.observe(viewLifecycleOwner) { navDirections ->
            findNavController().navigate(navDirections)
        }

        viewModel.posts.observe(viewLifecycleOwner) { posts ->
            adapter.setPosts(posts)
        }

        binding.apply {
            recyclerviewAccount.adapter = adapter
            textviewAccountUsername.text = "USERNAME"
            recyclerviewAccount.layoutManager = GridLayoutManager(context, 3)
        }
    }

    private fun getUserFromArgs(): UserData? {
        var user: UserData? = null
        val postJsonString: String? = arguments?.getString("post")
        Log.v("jsonStuff", postJsonString ?: "null")
        if(postJsonString != null){
            user = Gson().fromJson(postJsonString,UserData::class.java)
        }
        return user
    }

    private fun showErrorView() {
        binding.apply {
            recyclerviewAccount.visibility = View.GONE
            textviewAccountUsername.visibility = View.GONE
            textviewAccountError.visibility = View.VISIBLE
        }
    }
}