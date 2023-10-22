package com.example.uee.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TimePicker
import com.example.uee.R
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar


class HourlyServiceFragment : Fragment() {
    private var selectedHours: Int = 0 // Initialize it with a default value
    private val calendar = Calendar.getInstance()
    private val startDateEditText by lazy { view?.findViewById<EditText>(R.id.startDateEditText)  }
    private var selectedGender: String? = null
    private var StartTime: String? = null
    private var EndTime: String? = null
    private var selectedDate: String? = null
    private lateinit var termsAndConditionsCheckBox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_hourly_service, container, false)
        val PName = view.findViewById<TextInputEditText>(R.id.name)
        val ExpectedHours = view.findViewById<NumberPicker>(R.id.ExpectedH)
        val startDateButton = view.findViewById<Button>(R.id.StartDate)
        val startTimePicker = view.findViewById<TimePicker>(R.id.Starttime)
        val EndTimePicker = view.findViewById<TimePicker>(R.id.Endtime)
        val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)
        termsAndConditionsCheckBox = view.findViewById(R.id.termsAndConditionsCheckBox)
        val RequestBtn = view.findViewById<Button>(R.id.request)

        // Set the TimePicker to 24-hour format
        startTimePicker.setIs24HourView(true)
        EndTimePicker.setIs24HourView(true)




        val text = PName.text.toString()


        ExpectedHours.minValue = 1
        ExpectedHours.maxValue = 24
        ExpectedHours.value = 1

        ExpectedHours.setOnValueChangedListener { picker, oldVal, newVal ->
            selectedHours = newVal
        }

        startDateButton.setOnClickListener {
            showDatePickerDialog()
        }

        startTimePicker.setOnTimeChangedListener { timePicker, hour, minute ->

            StartTime = String.format("%02d:%02d", hour, minute)

        }
        EndTimePicker.setOnTimeChangedListener { timePicker, hour, minute ->

            EndTime = String.format("%02d:%02d", hour, minute)

        }

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            // Check which radio button was selected
            when (checkedId) {
                R.id.radioButton -> selectedGender = "Male"
                R.id.female -> selectedGender = "Female"
                R.id.other -> selectedGender = "Other"
            }
        }

        termsAndConditionsCheckBox.setOnCheckedChangeListener { _, isChecked ->
            // Enable or disable the submit button based on the checkbox status
            RequestBtn.isEnabled = isChecked
        }

        RequestBtn.setOnClickListener {
            // Check if the checkbox is checked
            if (termsAndConditionsCheckBox.isChecked) {
                // Show the terms and conditions dialog
                showTermsAndConditionsDialog()
            } else {
                // Handle the case where the checkbox is not checked
                // You can display an error message or take appropriate action
            }
        }





        return view
    }

    private fun showDatePickerDialog() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { _, year, month, day ->
            // Update the EditText with the selected date
            selectedDate = String.format("%d-%02d-%02d", year, month + 1, day)
            startDateEditText?.setText(selectedDate)
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun showTermsAndConditionsDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Terms and Conditions")
        builder.setMessage("These are the terms and conditions of using our service.")

        builder.setPositiveButton("Accept") { _, _ ->
            termsAndConditionsCheckBox.isChecked = true
        }

        builder.setNegativeButton("Decline") { _, _ ->
            termsAndConditionsCheckBox.isChecked = false
        }

        builder.show()
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HourlyServiceFragment().apply {

            }
    }
}