package com.example.android.calculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    //Add
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.page_swap) {
            Intent intent = new Intent(this, Main2Activity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    //Initialize global variables
    private Button[] buttons = new Button[10]; // Initialize array of numbers  (0 - 9)
    private TextView output; // where the result of the math expression will be
    private TextView input; // where user enters math expression
    Button equals; // to find the result of teh math expression
    ImageButton backspace; // to go back 1 character
    Calculation calculate = new Calculation(); // Create object of Calculation class to call functions
    ArrayList <Character> operations = new ArrayList<>(Arrays.asList('+', '-', '/', 'x'));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Assign variables to xml created id
        output = findViewById(R.id.output);
        input = findViewById(R.id.input);
        Button add = findViewById(R.id.add);
        Button sub = findViewById(R.id.sub);
        Button multiply = findViewById(R.id.multiply);
        Button divide = findViewById(R.id.divide);
        Button clear = findViewById(R.id.clear);
        backspace = findViewById(R.id.back);
        equals = findViewById(R.id.equal);
        Button Rbracket = findViewById(R.id.Rbracket);
        Button Lbracket = findViewById(R.id.Lbracket);
        Button decimal = findViewById(R.id.decimal);
        for (int i = 0; i < buttons.length; i++) {
            int res = getResources().getIdentifier("num"+i, "id", getPackageName());
            buttons[i] = findViewById(res);
        }
        //Set onClick Listeners so when button pressed, whatever onClickListener function is set to for that button it will preform
        add.setOnClickListener(onClickListener);
        sub.setOnClickListener(onClickListener);
        multiply.setOnClickListener(onClickListener);
        divide.setOnClickListener(onClickListener);
        clear.setOnClickListener(onClickListener);
        backspace.setOnClickListener(onClickListener);
        equals.setOnClickListener(onClickListener);
        Rbracket.setOnClickListener(onClickListener);
        Lbracket.setOnClickListener(onClickListener);
        decimal.setOnClickListener(onClickListener);
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setOnClickListener(onClickListener);
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String phrase = input.getText().toString();
            // If clear button is pressed then clear input math expression and result text to enter new
            if (v.getId() == R.id.clear) {
                input.setText("");
                output.setText("");
            }
            //Go back a character in the input expression unless the string is empty
            else if (v.getId() == backspace.getId()) {
                if (input.length() > 0)
                    input.setText(phrase.substring(0, phrase.length() - 1));
            }
            // Get result of input math expression but only display decimal values if number is not a whole number
            else if (v.getId() == equals.getId()) {
                //If just a number is entered, then do nothing
                if (!checkIfOperationInExpression(phrase) || checkIfCharIsOperation(phrase.charAt(phrase.length() - 1)))
                    return;
                //Calculate Expression
                try {
                    float result = calculate.calculateExpression(phrase);
                    String outputVal = Float.toString(result);
                    output.setText(outputVal);
                }
                catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Invalid Expression", Toast.LENGTH_LONG).show();
                }
            }
            //Add character to input expression
            else {
                Character charToAdd = ((Button) v).getText().toString().charAt(0);
                //If you want to add an operation but the last character is an operation then remove the last character to add the new operation
                if (phrase.length() > 0) {
                    if (checkIfCharIsOperation(phrase.charAt(phrase.length() - 1)) && checkIfCharIsOperation(charToAdd))
                        phrase = phrase.substring(0, phrase.length() - 1);
                }
                //If input phrase empty then only allow a number to be entered
                else {
                    if (checkIfCharIsOperation(charToAdd))
                        return;
                }
                input.setText(String.format("%s%s", phrase, charToAdd));
            }
        }
    };
    //Loops through the phrase and tries to find if any operation exists in the string
    public boolean checkIfOperationInExpression(String phrase) {
        for (int i = 0; i < phrase.length(); i++) {
            for (Character op : operations) {
                if (phrase.charAt(i) == op)
                    return true;
            }
        }
        return false;
    }
    //checks if the character is an operation or not
    public boolean checkIfCharIsOperation(Character ch) {
        for (Character op : operations) {
            if (ch == op)
                return true;
        }
        return false;
    }

}
