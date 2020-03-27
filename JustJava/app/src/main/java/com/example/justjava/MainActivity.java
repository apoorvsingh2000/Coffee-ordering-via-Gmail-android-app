/**
 * IMPORTANT: Make sure you are using the correct package name. 
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.justjava;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox= (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        EditText nameField = (EditText)findViewById(R.id.name_field);

        /**getting the name */
        String name = nameField.getText().toString();

        /**calculating price*/
        int price=calculatePrice(hasWhippedCream,hasChocolate);

        /**creating order summary*/
        String priceMessage=createOrderSummary(price,hasWhippedCream,hasChocolate,name);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject,name));
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    public void increment(View view){
        if (quantity<100) {
            quantity++;
            display(quantity);
        }
        else Toast.makeText(this,getString(R.string.toast_more),Toast.LENGTH_SHORT).show();
    }
    public void decrement(View view){
        if(quantity>1){
        quantity--;
        display(quantity);
    }
        else Toast.makeText(this,getString(R.string.toast_less),Toast.LENGTH_SHORT).show();
    }
    private int calculatePrice(boolean whippedCream,boolean chocolate){
        int base_price=5;
        if (whippedCream){
            base_price+=1;
        }
        if (chocolate){
            base_price+=2;
        }
        base_price=base_price*quantity;
        return(base_price);
    }
    private String createOrderSummary(int price,boolean addWhippedCream,boolean addChocolate,String name){
        String priceMessage=getString(R.string.order_summary_name,name);
        priceMessage+="\n"+getString(R.string.order_summary_whipped_cream,addWhippedCream);
        priceMessage+="\n"+getString(R.string.order_summary_chocolate,addChocolate);
        priceMessage+="\n"+getString(R.string.order_summary_quantity,quantity);
        priceMessage+="\n"+getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(price));
        priceMessage+="\n"+getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}