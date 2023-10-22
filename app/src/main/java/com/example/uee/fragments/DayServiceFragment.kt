package com.example.uee.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.RadioGroup
import android.widget.TimePicker
import com.example.uee.R
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar


class DayServiceFragment : Fragment() {
    private var selectedDays: Int = 0 // Initialize it with a default value
    private val calendar = Calendar.getInstance()
    private val startDateEditText by lazy { view?.findViewById<EditText>(R.id.startDateEditText)  }
    private val endDateEditText by lazy { view?.findViewById<EditText>(R.id.endDateEditText)  }
    private var selectedGender: String? = null
    private var StartTime: String? = null
    private var selectedDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_day_service, container, false)

        val PName = view.findViewById<TextInputEditText>(R.id.name)
        val ExpectedDays = view.findViewById<NumberPicker>(R.id.ExpectedH)
        val startDateButton = view.findViewById<Button>(R.id.StartDate)
        val startTimePicker = view.findViewById<TimePicker>(R.id.Starttime)
        val EndTDateButton = view.findViewById<Button>(R.id.EndDate)
        val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)

        startTimePicker.setIs24HourView(true)

        val text = PName.text.toString()

        ExpectedDays.setOnValueChangedListener { picker, oldVal, newVal ->
            selectedDays = newVal
        }

        startDateButton.setOnClickListener {
            startDateEditText?.setText(showDatePickerDialog())
        }

        EndTDateButton.setOnClickListener {
            endDateEditText?.setText(showDatePickerDialog())
        }

        startTimePicker.setOnTimeChangedListener { timePicker, hour, minute ->

            StartTime = String.format("%02d:%02d", hour, minute)

        }

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            // Check which radio button was selected
            when (checkedId) {
                R.id.radioButton -> selectedGender = "Male"
                R.id.female -> selectedGender = "Female"
                R.id.other -> selectedGender = "Other"
            }
        }


        return view
    }

    private fun showDatePickerDialog(): String? {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { _, year, month, day ->
            // Update the EditText with the selected date
            selectedDate = String.format("%d-%02d-%02d", year, month + 1, day)

        }, year, month, day)

        datePickerDialog.show()
        return selectedDate

    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DayServiceFragment().apply {

            }
    }
}