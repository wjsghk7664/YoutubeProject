package com.example.youtubeproject.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.youtubeproject.R
import com.example.youtubeproject.databinding.FragmentSignUpBinding
import com.example.youtubeproject.presentation.SignUpUiState
import com.example.youtubeproject.presentation.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _binding : FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    val viewmodel:SignUpViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    fun initView() = with(binding){

        viewLifecycleOwner.lifecycleScope.launch {
            viewmodel.uiState.collectLatest {
                when(it){
                    is SignUpUiState.Init -> null
                    is SignUpUiState.Failure -> {
                        signupTvNotify.visibility=View.VISIBLE
                        signupTvNotify.text = it.reason
                    }
                    is SignUpUiState.FailureRegister -> {
                        signupTvNotify.visibility=View.VISIBLE
                        signupTvNotify.text = it.reason
                        Toast.makeText(requireActivity(), "오류로 인해 등록에 실패하였습니다. 다시시도 해주세요.",Toast.LENGTH_SHORT).show()
                    }
                    is SignUpUiState.Success -> {
                        Toast.makeText(requireActivity(),"회원가입 성공",Toast.LENGTH_SHORT).show()
                        parentFragmentManager.beginTransaction().replace(R.id.main,LoginFragment.newInstance()).commit()
                    }
                    is SignUpUiState.Loading -> null
                }
            }
        }

        signupBtnSignup.setOnClickListener {
            val id=signupEtId.text.toString()
            val password = signupEtPassword.text.toString()
            val name = signupEtName.text.toString()
            val intro = signupEtIntro.text.toString()

            viewmodel.signUp(id, password, name, intro)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() = SignUpFragment()
    }
}