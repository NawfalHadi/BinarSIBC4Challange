package com.thatnawfal.binarsibc4challange.presentasion.ui.registerform

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.thatnawfal.binarsibc4challange.wrapper.Resource
import com.thatnawfal.binarsibc4challange.data.local.database.entity.AccountEntity
import com.thatnawfal.binarsibc4challange.databinding.FragmentRegisterBinding
import com.thatnawfal.binarsibc4challange.di.ServiceLocator
import com.thatnawfal.binarsibc4challange.utills.viewModelFactory

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    private val viewModel: RegisterViewModel by viewModelFactory {
        RegisterViewModel(ServiceLocator.provideLocalRepository(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRegister.setOnClickListener {
            submitRegisterForm()
        }
        observeAction()
    }

    private fun observeAction() {
        viewModel.newAccount.observe(requireActivity()){
            when(it) {
                is Resource.Error -> {
                    setFormEnabled(true)
                    Toast.makeText(requireContext(), "There's Error When Inputting Data", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
                is Resource.Loading -> {
                    setFormEnabled(false)
                }
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "Success Creating Your Account", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }

    private fun setFormEnabled(b: Boolean) {
        with(binding) {
            etRegUsername.isEnabled = b
            etRegEmail.isEnabled = b
            etRegPass.isEnabled = b
            etRegComfPass.isEnabled = b
        }
    }

    private fun formValidate() : Boolean{
        val username = binding.etRegUsername.text.toString()
        val email = binding.etRegEmail.text.toString()
        val password = binding.etRegPass.text.toString()
        val comfPass = binding.etRegComfPass.text.toString()
        var validateForm = true

        if (username.isEmpty()){
            validateForm = false
            binding.tilEtRegUsername.isErrorEnabled = true
            binding.tilEtRegUsername.error = "Username Can't Be Empty"
        } else { binding.tilEtRegUsername.isErrorEnabled = false }

        if (email.isEmpty()){
            validateForm = false
            binding.tilEtRegEmail.isErrorEnabled = true
            binding.tilEtRegEmail.error = "Email Can't Be Empty"
        } else {
            validateForm = false
            viewModel.checkEmailExcist(email)
            viewModel.isEmailExcist.observe(requireActivity()){
                if (it) {
                    binding.tilEtRegEmail.isErrorEnabled = true
                    binding.tilEtRegEmail.error = "Email Has Been Registered"
                } else {
                    binding.tilEtRegEmail.isErrorEnabled = false
                    validateForm = true
                }
            }
        }

        if (password.isEmpty()){
            validateForm = false
            binding.tilEtRegPass.isErrorEnabled = true
            binding.tilEtRegPass.error = "Password Can't Be Empty"
        } else { binding.tilEtRegPass.isErrorEnabled = false }

        if (comfPass.isEmpty()){
            validateForm = false
            binding.tilEtRegComfPass.isErrorEnabled = true
            binding.tilEtRegComfPass.error = "Confirmation Password Can't Be Empty"
        } else {
            if (comfPass != password) {
                validateForm = false
                binding.tilEtRegComfPass.isErrorEnabled = true
                binding.tilEtRegComfPass.error = "Password Didn't Match"
            } else { binding.tilEtRegComfPass.isErrorEnabled = false }
        }

        return validateForm
    }

    private fun getEntityDataFromForm(): AccountEntity {
        return AccountEntity(
            username = binding.etRegUsername.text.toString(),
            email = binding.etRegEmail.text.toString(),
            password = binding.etRegPass.text.toString()
        )
    }

    private fun submitRegisterForm(){
        if (formValidate()) {
            viewModel.registerNewAccount(getEntityDataFromForm())
        }
    }

}