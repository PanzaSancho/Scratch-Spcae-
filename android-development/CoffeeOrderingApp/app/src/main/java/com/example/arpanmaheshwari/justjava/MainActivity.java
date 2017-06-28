package com.example.arpanmaheshwari.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 *
 */


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.NumberFormat;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 0;
    final double pricePerCup=5;
    final double pricePerWhippedCream=1;
    final double pricePerChocolate=2;
    final int minQ=1;
    final int maxQ=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //String message = "Total: " + "$"+ calculatePrice(quantity,5) + "\n" + "Thankyou!";

        if(quantity < minQ || quantity > maxQ){
            displayMessage("Quantity should be between(including both end-points) "+minQ+" and "+maxQ);
            return;
        }
        TextView textView = (TextView) findViewById(R.id.order_summary_text_view) ;
        textView.setText("ORDER SUMMARY");
        CheckBox checkBoxWhippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox_view);
        boolean isCheckedWhippedCream=checkBoxWhippedCream.isChecked();
        CheckBox checkBoxChocolate = (CheckBox) findViewById(R.id.chocolate_checkbox_view);
        boolean isCheckedChocolate=checkBoxChocolate.isChecked();
        EditText Nameview = (EditText) findViewById(R.id.name_edit_text_view) ;
        String name =  Nameview.getText().toString();
        /*Log.v("MainActivity",name);*/
        double price = calculatePrice(quantity,isCheckedWhippedCream,isCheckedChocolate);
        /*Log.v("MainActivity",""+isCheckedWhippedCream);
        Log.v("MainActivity",""+isCheckedChocolate);*/

        String OrderSummary = createOrderSummary(price,isCheckedWhippedCream,isCheckedChocolate,name);


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:mahe@gmail.com")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"mahe@gmail.com"});

        // Get a string resource from your app's Resources
        String order_line = getResources().getString(R.string.just_java_order_for);

        intent.putExtra(Intent.EXTRA_SUBJECT, order_line+ name);
        intent.putExtra(Intent.EXTRA_TEXT, OrderSummary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }



       // displayMessage(createOrderSummary(price,isCheckedWhippedCream,isCheckedChocolate,name));
    }

    public String createOrderSummary(double price, boolean isCheckedWhippedCream, boolean isCheckedChocolate, String name){
        return ("Name: "+name+"\nAdd whipped cream? "+isCheckedWhippedCream +
                "\nAdd Chocolate? "+isCheckedChocolate +"\nQuantity: " + quantity + "\nTotal: $" + price + "\nThank you!");

    }
    /* To increase the quantity by onem when the "plus"(+) button is clicked on*/
    public void increment(View view){
        if(quantity < maxQ) {
            TextView textView = (TextView) findViewById(R.id.order_summary_text_view);
            textView.setText(R.string.price);
            quantity = quantity + 1;
            display(quantity);


            textView = (TextView) findViewById(R.id.order_summary_text_view) ;
            textView.setText("ORDER SUMMARY");
            CheckBox checkBoxWhippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox_view);
            boolean isCheckedWhippedCream=checkBoxWhippedCream.isChecked();
            CheckBox checkBoxChocolate = (CheckBox) findViewById(R.id.chocolate_checkbox_view);
            boolean isCheckedChocolate=checkBoxChocolate.isChecked();
            EditText Nameview = (EditText) findViewById(R.id.name_edit_text_view) ;
            String name =  Nameview.getText().toString();
        /*Log.v("MainActivity",name);*/
            double price = calculatePrice(quantity,isCheckedWhippedCream,isCheckedChocolate);


            displayPrice(price);
        }
        else{
            Toast toast;
            toast = Toast.makeText(this, "Quantity should be between(including both end-points) "+minQ+" and "+maxQ, Toast.LENGTH_SHORT);
            if(toast != null) {
                toast.show();
            }

        }
    }
    /* To decrease the quantity by onem when the "minus"(-) button is clicked on */
    public void decrement(View view){
        if(quantity > minQ) {
            TextView textView = (TextView) findViewById(R.id.order_summary_text_view) ;
            textView.setText(R.string.price);
            quantity = quantity - 1;
            display(quantity);
            displayPrice(quantity * 5);
        }
        else{
            Toast toast;
            toast = Toast.makeText(this, "Quantity should be between(including both end-points) "+minQ+" and "+maxQ, Toast.LENGTH_SHORT);
            if(toast != null) {
                toast.show();
            }
        }

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(double number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(message);
    }

    /*
    *@param quantity : number of cups of coffee ordered
    *@param pricePerCup : cost of one cup of coffee
    *@return : total price of all cups ordered
     */
    private double calculatePrice(int quantity,boolean isCheckedWhippedCream,boolean isCheckedChocolate){

        double price = quantity * pricePerCup ;
        if(isCheckedWhippedCream){
            price += quantity * pricePerWhippedCream;
        }
        if(isCheckedChocolate) {
            price += quantity * pricePerChocolate;
        }
        return price;
    }
}
