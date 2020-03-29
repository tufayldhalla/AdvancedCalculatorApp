package com.example.android.calculator;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;


public class Main2Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.page_swap) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    TextView dec_output;
    TextView bin_output;
    TextView hex_output;
    EditText input;
    String input_string_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Spinner spinner = findViewById(R.id.spinner);
        String[] convertOptions = {"DEC", "BIN", "HEX"};
        ArrayAdapter <String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, convertOptions);
        adapter.setDropDownViewResource(R.layout.spinner_design);
        spinner.setOnItemSelectedListener(Main2Activity.this);
        spinner.setAdapter(adapter);

        dec_output = findViewById(R.id.dec_output);
        bin_output = findViewById(R.id.bin_output);
        hex_output = findViewById(R.id.hex_output);
        input = findViewById(R.id.input);

    }

    public void setText(View v) {
        String newText = input.getText().toString();
        if (!(newText.equals(""))) {
            try {
                if (input_string_type.equals("DEC")) {
                    dec_output.setText(newText);
                    bin_output.setText(Integer.toBinaryString(Integer.parseInt(newText)));
                    hex_output.setText(Integer.toHexString(Integer.parseInt(newText)).toUpperCase());
                } else if (input_string_type.equals("BIN")) {
                    dec_output.setText(String.valueOf(Integer.parseInt(newText, 2)));
                    bin_output.setText(newText);
                    int decimal = Integer.parseInt(newText, 2);
                    hex_output.setText(Integer.toString(decimal, 16));
                } else if (input_string_type.equals("HEX")) {
                    dec_output.setText(String.valueOf(Integer.parseInt(newText, 16)));
                    bin_output.setText(Integer.toBinaryString(Integer.parseInt(newText, 16)));
                    hex_output.setText(newText);
                }
            }
            catch (Exception e) {
                Toast.makeText(Main2Activity.this,
                        "Invalid Entry", Toast.LENGTH_LONG).show();
            }
        }
        UIUtil.hideKeyboard(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView) view).setTextColor(Color.BLACK);
        ((TextView) view).setTextSize(20);
        switch (position) {
            case 0:
                input_string_type = "DEC";
                break;
            case 1:
                input_string_type = "BIN";
                break;
            case 2:
                input_string_type = "HEX";
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

}
