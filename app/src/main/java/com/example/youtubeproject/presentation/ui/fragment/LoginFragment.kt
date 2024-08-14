package com.example.youtubeproject.presentation.ui.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.youtubeproject.R
import com.example.youtubeproject.databinding.FragmentLoginBinding
import com.example.youtubeproject.presentation.uistate.LoginUiState
import com.example.youtubeproject.presentation.ui.MainActivity
import com.example.youtubeproject.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewmodel:LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
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
                    is LoginUiState.Init -> null
                    is LoginUiState.Failure -> Toast.makeText(requireContext(),"아이디나 비밀번호를 다시 입력해주세요.",Toast.LENGTH_SHORT).show()
                    is LoginUiState.FailureCache -> Toast.makeText(requireContext(),"로그인 정보 저장에 실패하였습니다. 다시 시도해주세요.",Toast.LENGTH_SHORT).show()
                    is LoginUiState.Success -> null
                    is LoginUiState.Loading -> null
                    is LoginUiState.AutoLoginFailure ->null
                }
            }
        }

        loginBtnSignup.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main, SignUpFragment.newInstance()).addToBackStack(null).commit()
        }
        loginBtnLogin.setOnClickListener {
            val id = loginEtId.text.toString()
            val password = loginEtPassword.text.toString()
            val isCache = loginCbAutologin.isChecked
            viewmodel.loginCheck(id,password,isCache)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}