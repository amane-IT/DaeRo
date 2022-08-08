package com.ssafy.daero.data.dto.article

data class Expense(
    var expenses_name: String,
    var expenses: String
) {
    override fun toString(): String {
        return """"expense_name" : "$expenses_name", "expenses" : "${expenses}원""""
    }
}