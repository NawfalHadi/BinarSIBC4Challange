package com.thatnawfal.binarsibc4challange.presentasion.ui.loginform

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.thatnawfal.binarsibc4challange.R
import com.thatnawfal.binarsibc4challange.databinding.FragmentLoginBinding
import com.thatnawfal.binarsibc4challange.presentasion.ui.AuthActivity
import com.thatnawfal.binarsibc4challange.presentasion.ui.MainActivity

class LoginFragment : Fragment() {

    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLoginLogin.setOnClickListener {
            requireActivity().run {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }

        }

        binding.btnLoginRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

}