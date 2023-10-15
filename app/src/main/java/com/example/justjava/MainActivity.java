package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.justjava.R;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */

    int quantity = 1;

    public void increment(View view) {
        if(quantity == 100) {
            Toast.makeText(this,"You cannot order more than 100",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity++;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this,"You cannot order less than 1",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity--;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox cream = (CheckBox)findViewById(R.id.cream_box);
        boolean isCream = cream.isChecked();

        CheckBox chocolate = (CheckBox)findViewById(R.id.chocolate_checkbox);
        boolean isChocolate = chocolate.isChecked();

        EditText name = (EditText)findViewById(R.id.name_text);
        String enteredName = name.getText().toString();

        int price = calculatePrice(isCream,isChocolate);

        String message = createOrderSummary(price, isCream, isChocolate,enteredName);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT,"Coffee Order");
        intent.putExtra(Intent.EXTRA_TEXT,message);

        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    public int calculatePrice(boolean isCream, boolean isChocolate) {
        int price = 5;
        if(isCream)
            price+=1;
        if (isChocolate)
            price+=2;
        return quantity*price;
    }

    /*
     *  Create Order Summary
     *  @param price of order
     *  @return text summary
     */

    private String createOrderSummary(int price, boolean cream, boolean chocolate,String name) {
        String message = getString(R.string.nameSummary,name);
        String quantity = getString(R.string.quantitySummary) + price / 5;
        String total = getString(R.string.totalSummary) + price;
        String ack = getString(R.string.thankSummary);

        String itemCream = getString(R.string.cream) +cream;
        String itemChocolate = getString(R.string.chocolateSummary)+chocolate;
        return message + itemCream + itemChocolate + quantity + total + ack;
    }

}