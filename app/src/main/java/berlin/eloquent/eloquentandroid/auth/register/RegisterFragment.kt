package berlin.eloquent.eloquentandroid.auth.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import berlin.eloquent.eloquentandroid.MainActivity
import berlin.eloquent.eloquentandroid.R
import berlin.eloquent.eloquentandroid.databinding.RegisterFragmentBinding

class RegisterFragment : Fragment() {

    private lateinit var binding: RegisterFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = RegisterFragmentBinding.inflate(inflater)

        binding.loginLink.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.registerButton.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

}
