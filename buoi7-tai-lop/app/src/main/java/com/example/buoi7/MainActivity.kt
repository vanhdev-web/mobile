package com.example.buoi7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.buoi7.ui.ExpenseScreen
import com.example.buoi7.ui.theme.Buoi7Theme
import com.example.buoi7.viewmodel.ExpenseViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Buoi7Theme {
                val viewModel: ExpenseViewModel = viewModel()
                val expenses by viewModel.expenses.collectAsState()

                ExpenseScreen(
                    expenses = expenses,
                    onAddExpense = { title, amount, description ->
                        viewModel.addExpense(title, amount, description)
                    },
                    onUpdateExpense = { expense ->
                        viewModel.updateExpense(expense)
                    },
                    onDeleteExpense = { expense ->
                        viewModel.deleteExpense(expense)
                    }
                )
            }
        }
    }
}