package com.example.PickBeforeGo.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.PickBeforeGo.R;
import com.example.PickBeforeGo.components.CalendarPicker;
import com.example.PickBeforeGo.helperclass.Promotion_helper;

public class AdminFormActivity extends AppCompatActivity {
    private static final String TAG = "admin";

    public static String dayy;
    public static String monthh;
    public static String yearr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_form);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        final String[] promotionChoice = {"5%"};
        final String[] priceChoice = {"0"};
        TextView PriceAfterPromotion = findViewById(R.id.PriceAfterPromotion);
        TextView itemName = findViewById(R.id.itemName);
        ImageButton btnBack = findViewById(R.id.btnBack);
        ImageButton btnDownload = findViewById(R.id.btnDownload);
        Button dateButton;
        Button btnSubmit = findViewById(R.id.btnSubmit);

        // TODO: to config user image.
//        ImageView imageUser = findViewById(R.id.imageUser);
        //Init Item Name
        //TODO: On load, edit item name here.
        String itemNameValue = "[[placeholder]]";
        itemName.setText(itemNameValue);

        // Init Buttons
        btnBack.setOnClickListener((view -> {
            //TODO: functionaility of button here.
            System.out.println("back button called");
        }));
        btnDownload.setOnClickListener((view -> {
            //TODO: functionaility of button here.
            System.out.println("download button called");
        }));



        /// TEST VALUES ///
        dateButton = findViewById(R.id.dateButton);
//        dateButton.setEnabled(false);
        CalendarPicker.initDatePicker(this, dateButton);
        dateButton.setText(CalendarPicker.getTodaysDate());



        //





        /// TEST VALUES ///

        // Init Spinner -> Stock Availability
        Spinner spinnerStockAvailability = findViewById(R.id.StockAvailability);
        ArrayAdapter<CharSequence> adapterStockAvailability = ArrayAdapter.createFromResource(this, R.array.StockAvailability, android.R.layout.simple_spinner_item);
        adapterStockAvailability.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerStockAvailability.setAdapter(adapterStockAvailability);

        // Init Spinner -> Next Restock Timing
        Spinner spinnerRestockTime = findViewById(R.id.NextRestockTime);
        ArrayAdapter<CharSequence> adapterRestockTime = ArrayAdapter.createFromResource(this, R.array.NextRestockTime, android.R.layout.simple_spinner_item);
        adapterRestockTime.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerRestockTime.setAdapter(adapterRestockTime);

        // Init Spinner -> Promotion
        Spinner spinnerPromotion = findViewById(R.id.promotion);
        ArrayAdapter<CharSequence> adapterPromotion = ArrayAdapter.createFromResource(this, R.array.promotion, android.R.layout.simple_spinner_item);
        adapterPromotion.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerPromotion.setAdapter(adapterPromotion);

        final String[] newPrice = new String[1];

        spinnerPromotion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                promotionChoice[0] = spinnerPromotion.getSelectedItem().toString();

                if (!priceChoice[0].isEmpty()) {
                    String newPromotedValue = new Promotion_helper(priceChoice[0], promotionChoice[0]).promoChange();
                    PriceAfterPromotion.setText(newPromotedValue);
                    newPrice[0] = newPromotedValue;
                } else {
                    PriceAfterPromotion.setText(priceChoice[0]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        // Init Edit Text -> Price Value

        EditText editPrice = findViewById(R.id.texteditPrice);
        editPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {  return; }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { return; }

            @Override
            public void afterTextChanged(Editable editable) {
                priceChoice[0] = editable.toString();

                if (!priceChoice[0].isEmpty()) {
                    String newPromotedValue = new Promotion_helper(priceChoice[0], promotionChoice[0]).promoChange();
                    PriceAfterPromotion.setText(newPromotedValue);
                    newPrice[0] = newPromotedValue;

                } else {
                    PriceAfterPromotion.setText("0.00");
                }
            }
        });




        // Submitted Variables
        // stock
        final String[] sbmtStockAvailability = new String[1];
        spinnerStockAvailability.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                sbmtStockAvailability[0] = spinnerStockAvailability.getSelectedItem().toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
                sbmtStockAvailability[0] = "null";
            }
        });
        //Next Restock time
        final String[] sbmtRestockTime = new String[1];
        spinnerRestockTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                sbmtRestockTime[0] = spinnerRestockTime.getSelectedItem().toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
                sbmtRestockTime[0] = "null";
            }
        });


        //Promotion spinner
        final String[] sbmtPromotionSpinner = new String[1];
        spinnerPromotion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                sbmtPromotionSpinner[0] = spinnerPromotion.getSelectedItem().toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
                sbmtPromotionSpinner[0] = "null";
            }
        });

        // SUBMITTTTT
        btnSubmit.setOnClickListener((view -> {

            System.out.println("item name is :" + itemNameValue);
            System.out.println("New price is :" + newPrice[0]);
            System.out.println("Stock Status is :" + sbmtStockAvailability[0]);
            System.out.println("Promotion Value is :" + sbmtPromotionSpinner[0]);



            // Restock Calendar
            System.out.println("Restock Time is :" + sbmtRestockTime[0]);
            System.out.println("Restock day is :" + dayy);
            System.out.println("Restock month is :" + monthh);
            System.out.println("Restock year is :" + yearr);
        }));

    }


    public void openDatePicker(View view) {
        CalendarPicker.datePickerDialog.show();
    }
}