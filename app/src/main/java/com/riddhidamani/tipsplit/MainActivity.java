package com.riddhidamani.tipsplit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //private static final String TAG = "MainActivity";

    private EditText billTotalValue;
    private EditText numOfPplTxtValue;

    private TextView totAmtPerPersonTxtValue;
    private TextView tipAmtTxtValue;
    private TextView totWithTipTxtValue;
    private TextView overageTxtValue;

    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        billTotalValue = findViewById(R.id.billTotal);
        radioGroup = findViewById(R.id.tipPercentRG);
        tipAmtTxtValue = findViewById(R.id.tipAmtTxt);
        totWithTipTxtValue = findViewById(R.id.totWithTipTxt);
        numOfPplTxtValue = findViewById(R.id.numOfPplTxt);
        totAmtPerPersonTxtValue = findViewById(R.id.totAmtPerPersonTxt);
        overageTxtValue = findViewById(R.id.overageTxt);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        //outState.putString("billTotalValue", billTotalValue.getText().toString());
        //outState.putString("numOfPplTxtValue", numOfPplTxtValue.getText().toString());
        outState.putString("tipAmtTxtValue", tipAmtTxtValue.getText().toString());
        outState.putString("totWithTipTxtValue", totWithTipTxtValue.getText().toString());
        outState.putString("totAmtPerPersonTxtValue", totAmtPerPersonTxtValue.getText().toString());
        outState.putString("overageTxtValue", overageTxtValue.getText().toString());

        // Call super last
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        // Call super first
        super.onRestoreInstanceState(savedInstanceState);

        //billTotalValue.setText(savedInstanceState.getString("billTotalValue"));
        //numOfPplTxtValue.setText(savedInstanceState.getString("numOfPplTxtValue"));
        tipAmtTxtValue.setText(savedInstanceState.getString("tipAmtTxtValue"));
        totWithTipTxtValue.setText(savedInstanceState.getString("totWithTipTxtValue"));
        totAmtPerPersonTxtValue.setText(savedInstanceState.getString("totAmtPerPersonTxtValue"));
        overageTxtValue.setText(savedInstanceState.getString("overageTxtValue"));
    }

    // Method to calculate the Tip Amount and Total Amount with Tip
    public void calculateTipTotal(View v) {
        double tipAmount = 0.0;
        double totalAmount;
        String billTot = billTotalValue.getText().toString();
        DecimalFormat f = new DecimalFormat("##.00");
        NumberFormat currFormat = NumberFormat.getCurrencyInstance(Locale.US);

        if(billTot.matches("") || billTot.matches("0")){
            radioGroup.clearCheck();
            return;
        }

        // Calculating Tip Percentage/ Tip amount
        if(v.getId() == R.id.rb1) {
            tipAmount = Double.parseDouble(f.format((Double.parseDouble(billTot)) * 0.12));
        }
        else if(v.getId() == R.id.rb2) {
            tipAmount = Double.parseDouble(f.format((Double.parseDouble(billTot)) * 0.15));
        }
        else if(v.getId() == R.id.rb3) {
            tipAmount = Double.parseDouble(f.format((Double.parseDouble(billTot)) * 0.18));
        }
        else if(v.getId() == R.id.rb4) {
            tipAmount = Double.parseDouble(f.format((Double.parseDouble(billTot)) * 0.20));
        }

        // Calculating Total amount with Tip
        totalAmount = (Double.parseDouble(billTot)) + tipAmount;

        // Setting the screen values
        tipAmtTxtValue.setText(currFormat.format(tipAmount));
        totWithTipTxtValue.setText(currFormat.format(totalAmount));

    }

    // Method to calculate the Total Amount Per Person and Overage Value
    public void goBtn(View v) {
        // Format values as currency
        NumberFormat currFormat = NumberFormat.getCurrencyInstance();
        String billTotal = billTotalValue.getText().toString();
        String numOfPpl = numOfPplTxtValue.getText().toString();
        String totAmtWithTip = totWithTipTxtValue.getText().toString();

        if (billTotal.isEmpty() || billTotal.equals("0")) {
            return;
        }

        if(numOfPpl.isEmpty() || numOfPpl.equals("0")) {
            return;
        }

        // Extracting total amount with tip from screen without the 1st character i.e. $.
        totAmtWithTip = totAmtWithTip.substring(1);

        // Multiplying by 100 to remove decimals before dividing
        double totalAmount = Double.parseDouble(totAmtWithTip) * 100;

        // Taking in number of people as int value
        int numOfPeople = Integer.parseInt(numOfPpl);

        // Total per person calculation
        double tAppCValue = Math.ceil(totalAmount/numOfPeople);
        double tAppVal = (totalAmount/numOfPeople);
        double overage = ((tAppCValue - tAppVal) * numOfPeople);

        // Filling UI Data field on screen - Total Amount Per Person Text Field
        double finalVal = tAppCValue/100;
        totAmtPerPersonTxtValue.setText(currFormat.format(finalVal));

        // Filling UI Data field on screen - Overage Text Field
        double finalOvgVal = overage/100;
        overageTxtValue.setText(currFormat.format(finalOvgVal));

    }

    // Method that clears all the fields
    public void clearAllFields(View v) {
        billTotalValue.setText("");
        tipAmtTxtValue.setText("");
        totWithTipTxtValue.setText("");
        numOfPplTxtValue.setText("");
        totAmtPerPersonTxtValue.setText("");
        overageTxtValue.setText("");
        radioGroup.clearCheck();
    }
}