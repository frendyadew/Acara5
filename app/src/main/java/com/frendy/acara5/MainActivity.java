package com.frendy.acara5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.frendy.acara5.databinding.ActivityMainBinding;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String[] statuses = {"Hadir Tepat Waktu", "Sakit", "Terlambat", "Izin"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // View binding setup
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        // Populate statuses in spinner
        ArrayAdapter<String> adapterStatus = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, statuses);
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerStatus.setAdapter(adapterStatus);

        // Listen for status selection
        binding.spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (statuses[position].equals("Sakit")) {
                    binding.editTextKeterangan.setVisibility(View.VISIBLE);
                } else {
                    binding.editTextKeterangan.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // DatePicker initialization
        binding.datePicker.init(
                binding.datePicker.getYear(),
                binding.datePicker.getMonth(),
                binding.datePicker.getDayOfMonth(),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Handle date change if needed
                    }
                }
        );

        // TimePicker initialization
        binding.timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                // Handle time change if needed
            }
        });

        // Handle submit button click
        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = binding.spinnerStatus.getSelectedItem().toString();
                String keterangan = binding.editTextKeterangan.getText().toString();

                Calendar calendar = Calendar.getInstance();
                int year = binding.datePicker.getYear();
                int month = binding.datePicker.getMonth();
                int day = binding.datePicker.getDayOfMonth();
                calendar.set(year, month, day);

                int hour = binding.timePicker.getHour();
                int minute = binding.timePicker.getMinute();
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);

                String date = String.format("%02d-%02d-%04d", day, month + 1, year);
                String time = String.format("%02d:%02d", hour, minute);

                String resultMessage = String.format("Presensi berhasil: %s %s jam %s", date, time);
                binding.textViewHasil.setText(resultMessage);
                binding.textViewHasil.setVisibility(View.VISIBLE);

                Toast.makeText(MainActivity.this, "Presensi berhasil", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
