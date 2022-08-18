package com.ssafy.daero.utils.report

data class Report(
    val report_seq: Int,
    val description: String
)

val reports = listOf(
    Report(1, "스팸 또는 광고"),
    Report(2, "나체 이미지 또는 성적 행위"),
    Report(3, "혐오 발언 또는 암시"),
    Report(4, "폭력 또는 위험한 조직"),
    Report(5, "따돌림이나 괴롭힘"),
    Report(6, "부적절한 언어 사용"),
    Report(7, "불법 또는 규제 상품 판매"),
    Report(8, "지적 재산권 침해"),
    Report(9, "자살 또는 자해"),
    Report(10, "섭식 장애"),
    Report(11, "사기 또는 거짓"),
    Report(12, "거짓 정보"),
)