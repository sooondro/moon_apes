package hr.ferit.sandroblavicki.sandroapp.splash

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import hr.ferit.sandroblavicki.sandroapp.databinding.SplashFragmentBinding


class SplashFragment () : Fragment () {

    private lateinit var binding: SplashFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SplashFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.v("splashStuff","onViewCreated")

        val currentUser = FirebaseAuth.getInstance().currentUser
        val navDirections : NavDirections = if(currentUser == null){
            SplashFragmentDirections.navigateToLoginFragment()
        }else {
            SplashFragmentDirections.navigateToHomeFragment()
        }
        Log.v("splashStuff","currentUser $currentUser")
        findNavController().navigate(navDirections)
    }

}