/**
 * IMPORTANT: Make sure you are using the correct package name. 
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    int coffeePrice = 4;
    int chocolatePrice = 2;
    int whippedCreamPrice = 1;
    int total_price = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView checkBoxChocolate = (TextView) findViewById(R.id.chocolate);
        TextView checkBoxWhippedCream = (TextView) findViewById(R.id.whipped_cream);
        checkBoxChocolate.setText(getString(R.string.chocolate,2));
        checkBoxWhippedCream.setText(getString(R.string.whippedCream,1));

    }
    public void test(){
        //this is an empty test
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        boolean whippedCream = ((CheckBox) findViewById(R.id.whipped_cream)).isChecked();
        boolean chocolate = ((CheckBox) findViewById(R.id.chocolate)).isChecked();

        String outputText = getString(R.string.greetings) + "\n";
        String name = ((EditText) findViewById(R.id.name_input_field)).getText().toString();
        outputText += getString(R.string.mailText,name,quantity) + " ";

        if(whippedCream && chocolate) outputText += getString(R.string.wWCwCh);
        if(whippedCream && !chocolate) outputText += getString(R.string.wWCnoCh);
        if(chocolate && !whippedCream) outputText += getString(R.string.noWCwCh);
        if(!(whippedCream && chocolate)) outputText += getString(R.string.noWCnoCh);

        outputText += " " + getString(R.string.pricingText,total_price);
        outputText += "\n" + getString(R.string.thanks);
        composeEmail(outputText);
    }

    public void composeEmail(String text) {

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto: elias.soeren@gmail.com")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.mailSubject));
        intent.putExtra(Intent.EXTRA_TEXT,text);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }


    private int calculatePrice(boolean whippedCream, boolean chocolate, int quantity){
        int price = coffeePrice;
        if(whippedCream) price += whippedCreamPrice;
        if(chocolate) price += chocolatePrice;
        return price*quantity;
    }

    private void display(int quantity, int price) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        TextView totalPriceView = (TextView) findViewById(R.id.totalprice);
        quantityTextView.setText("" + quantity);
        totalPriceView.setText(price + " €");
    }

    /**
     * This method adjusts the quantity as wanted (decement/ increment by 1) and displays it in the quantity_text_view
     */
    public void adjustOrder(View view) {
        switch (view.getId()){
            case R.id.increment_quantity:
                if(quantity < 101) quantity++;
                break;
            case R.id.decrement_quantity:
                if(quantity > 0) quantity--;
                break;
            default:
                break;
        }
        total_price = calculatePrice(
                ((CheckBox) findViewById(R.id.whipped_cream)).isChecked(),
                ((CheckBox) findViewById(R.id.chocolate)).isChecked(),quantity
        );
        display(quantity,total_price);
    }
}