package hr.ferit.sandroblavicki.sandroapp.navbar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import hr.ferit.sandroblavicki.sandroapp.databinding.NavbarFragmentBinding
import hr.ferit.sandroblavicki.sandroapp.post.PostFragmentDirections

class NavbarFragment: Fragment() {

    private lateinit var binding: NavbarFragmentBinding
    private lateinit var viewModel: NavbarViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NavbarFragmentBinding.inflate(LayoutInflater.from(context), container, false)
        viewModel = NavbarViewModel()
        Log.i("LOG", viewModel.navigationDelegate.toString())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navigationDelegate.observe(viewLifecycleOwner) { navDirections ->
            findNavController().navigate(navDirections)
        }

        binding.apply {
            buttonNavbarHome.setOnClickListener {
                viewModel.navigateTo(PostFragmentDirections.navigateToHomeFragment())
            }

            buttonNavbarAccount.setOnClickListener {
                viewModel.navigateTo(NavbarFragmentDirections.navigateToAccountFragment())
            }

            buttonNavbarCreate.setOnClickListener {
                viewModel.navigateTo((NavbarFragmentDirections.navigateToCreateFragment()))
            }
        }

    }
}