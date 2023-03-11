package com.example.countdownapp.ui.common

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import java.time.LocalDate

fun createCalendarDialog(
    context: Context,
    initialDate: LocalDate = LocalDate.now(),
    onDateSelected: (year: Int, month: Int, day: Int) -> Unit
): DatePickerDialog {
    val mYear = initialDate.year
    val mMonth = initialDate.monthValue - 1
    val mDay = initialDate.dayOfMonth

    val mDatePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, day: Int ->
            onDateSelected(year, month, day)
        }, mYear, mMonth, mDay
    )
    return mDatePickerDialog
}