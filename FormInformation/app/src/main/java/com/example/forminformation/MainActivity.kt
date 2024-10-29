package com.example.forminformation

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var edtStudentId: EditText
    private lateinit var edtFullName: EditText
    private lateinit var radioGroupGender: RadioGroup
    private lateinit var edtEmail: EditText
    private lateinit var edtPhone: EditText
    private lateinit var txtBirthday: TextView
    private lateinit var btnPickDate: Button
    private lateinit var spinnerProvince: Spinner
    private lateinit var spinnerDistrict: Spinner
    private lateinit var spinnerWard: Spinner
    private lateinit var checkSports: CheckBox
    private lateinit var checkMovies: CheckBox
    private lateinit var checkMusic: CheckBox
    private lateinit var checkTerms: CheckBox
    private lateinit var btnSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        setupSpinners()
        setupDatePicker()
        setupSubmitButton()
    }

    private fun initializeViews() {
        edtStudentId = findViewById(R.id.edtStudentId)
        edtFullName = findViewById(R.id.edtFullName)
        radioGroupGender = findViewById(R.id.radioGroupGender)
        edtEmail = findViewById(R.id.edtEmail)
        edtPhone = findViewById(R.id.edtPhone)
        txtBirthday = findViewById(R.id.txtBirthday)
        btnPickDate = findViewById(R.id.btnPickDate)
        spinnerProvince = findViewById(R.id.spinnerProvince)
        spinnerDistrict = findViewById(R.id.spinnerDistrict)
        spinnerWard = findViewById(R.id.spinnerWard)
        checkSports = findViewById(R.id.checkSports)
        checkMovies = findViewById(R.id.checkMovies)
        checkMusic = findViewById(R.id.checkMusic)
        checkTerms = findViewById(R.id.checkTerms)
        btnSubmit = findViewById(R.id.btnSubmit)
    }

    private fun setupSpinners() {
        val provinces = arrayOf("Hà Nội", "TP.HCM", "Đà Nẵng")
        val districts = arrayOf("Quận 1", "Quận 2", "Quận 3", "Long Biên", "Hai Bà Trưng")
        val wards = arrayOf("Phường 1", "Phường 2", "Phường 3", "Ngọc Thụy", "Lý Thường Kiệt")

        spinnerProvince.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, provinces)
        spinnerDistrict.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, districts)
        spinnerWard.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, wards)
    }

    private fun setupDatePicker() {
        btnPickDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                txtBirthday.text = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            }, year, month, day).show()
        }
    }

    private fun setupSubmitButton() {
        btnSubmit.setOnClickListener {
            if (validateForm()) {
                // Process form submission
                Toast.makeText(this, "Form submitted successfully!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true
        val errorMessage = StringBuilder()

        if (edtStudentId.text.isNullOrEmpty()) {
            errorMessage.append("- Vui lòng nhập MSSV\n")
            isValid = false
        }

        if (edtFullName.text.isNullOrEmpty()) {
            errorMessage.append("- Vui lòng nhập họ tên\n")
            isValid = false
        }

        if (radioGroupGender.checkedRadioButtonId == -1) {
            errorMessage.append("- Vui lòng chọn giới tính\n")
            isValid = false
        }

        if (edtEmail.text.isNullOrEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(edtEmail.text.toString()).matches()) {
            errorMessage.append("- Vui lòng nhập email hợp lệ\n")
            isValid = false
        }

        // Validate Phone
        if (edtPhone.text.isNullOrEmpty() || edtPhone.text.toString().length < 10) {
            errorMessage.append("- Vui lòng nhập số điện thoại hợp lệ\n")
            isValid = false
        }

        // Validate Birthday
        if (txtBirthday.text.toString() == "Ngày sinh") {
            errorMessage.append("- Vui lòng chọn ngày sinh\n")
            isValid = false
        }

        // Validate Terms
        if (!checkTerms.isChecked) {
            errorMessage.append("- Vui lòng đồng ý với điều khoản\n")
            isValid = false
        }

        if (!isValid) {
            AlertDialog.Builder(this)
                .setTitle("Lỗi")
                .setMessage(errorMessage.toString())
                .setPositiveButton("OK", null)
                .show()
        }

        return isValid
    }
}