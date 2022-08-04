package com.ssafy.daero.ui.signup

import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentTermsBinding

class TermsFragment : BaseFragment<FragmentTermsBinding>(R.layout.fragment_terms){
    override fun init() {
        initView()
        setOnClickListeners()
    }

    fun initView() {
        // 참고: https://www.law.go.kr/%ED%96%89%EC%A0%95%EA%B7%9C%EC%B9%99/%EB%94%94%EC%A7%80%ED%84%B8%EC%BD%98%ED%85%90%EC%B8%A0%20%EC%9D%B4%EC%9A%A9%20%ED%91%9C%EC%A4%80%EC%95%BD%EA%B4%80
        binding.apply {
            textTermDetail2.text = "이 약관에서 사용하는 용어의 정의는 다음과 같습니다.   \n" +
                    "1. \"회사\"라 함은 \"콘텐츠\" 산업과 관련된 경제활동을 영위하는 자로서 콘텐츠 및 제반서비스를 제공하는 자를 말합니다. \n" +
                    "\n" +
                    "2. \"이용자\"라 함은 \"회사\"의 사이트에 접속하여 이 약관에 따라 \"회사\"가 제공하는 \"콘텐츠\" 및 제반서비스를 이용하는 회원 및 비회원을 말합니다. \n" +
                    "\n" +
                    "3. \"회원\"이라 함은 \"회사\"와 이용계약을 체결하고 \"이용자\" 아이디(ID)를 부여받은 \"이용자\"로서 \"회사\"의 정보를 지속적으로 제공받으며 \"회사\"가 제공하는 서비스를 지속적으로 이용할 수 있는 자를 말합니다. \n" +
                    "\n" +
                    "4. \"비회원\"이라 함은 \"회원\"이 아니면서 \"회사\"가 제공하는 서비스를 이용하는 자를 말합니다. \n" +
                    "\n" +
                    "5. \"콘텐츠\"라 함은 정보통신망이용촉진 및 정보보호 등에 관한 법률 제2조 제1항 제1호의 규정에 의한 정보통신망에서 사용되는 부호·문자·음성·음향·이미지 또는 영상 등으로 표현된 자료 또는 정보로서, 그 보존 및 이용에 있어서 효용을 높일 수 있도록 전자적 형태로 제작 또는 처리된 것을 말합니다. \n" +
                    "\n" +
                    "6. \"아이디(ID)\"라 함은 \"회원\"의 식별과 서비스이용을 위하여 \"회원\"이 정하고 \"회사\"가 승인하는 문자 또는 숫자의 조합을 말합니다. \n" +
                    "\n" +
                    "7. \"비밀번호(PASSWORD)\"라 함은 \"회원\"이 부여받은 \"아이디\"와 일치되는 \"회원\"임을 확인하고 비밀보호를 위해 \"회원\" 자신이 정한 문자 또는 숫자의 조합을 말합니다. "

            textTermDetail1.text =
                "이 약관은 회사가 온라인으로 제공하는 디지털콘텐츠(이하 \"콘텐츠\"라고 한다) 및 제반서비스의 이용과 관련하여 회사와 이용자와의 권리, 의무 및 책임사항 등을 규정함을 목적으로 합니다. "
        }
    }

    private fun setOnClickListeners(){
        binding.imgTermBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}