package com.example.PickBeforeGo.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.PickBeforeGo.R;
import com.example.PickBeforeGo.components.CalendarPicker;
import com.example.PickBeforeGo.helper.PromotionHelper;

public class AdminFormActivity extends AppCompatActivity {
    private static final String TAG = "admin";

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

        //Init Item Name
        //TODO: On load, edit item name here.
        itemName.setText("[[placeholder]]");

        // Init Buttons
        btnBack.setOnClickListener((view -> {
            //TODO: functionaility of button here.
            System.out.println("back button called");
        }));
        btnDownload.setOnClickListener((view -> {
            //TODO: functionaility of button here.
            System.out.println("download button called");
        }));

        /// Calendar view ///
        dateButton = findViewById(R.id.dateButton);
//        dateButton.setEnabled(false);
        CalendarPicker.initDatePicker(this, dateButton);
        dateButton.setText(CalendarPicker.getTodaysDate());

        //

        /// Calendar view ///

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

        spinnerPromotion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                promotionChoice[0] = spinnerPromotion.getSelectedItem().toString();

                if (!priceChoice[0].isEmpty()) {
                    String newPromotedValue = new PromotionHelper(priceChoice[0], promotionChoice[0]).promoChange();
                    PriceAfterPromotion.setText(newPromotedValue);
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
                    String newPromotedValue = new PromotionHelper(priceChoice[0], promotionChoice[0]).promoChange();
                    PriceAfterPromotion.setText(newPromotedValue);
                } else {
                    PriceAfterPromotion.setText("0.00");
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    public void openDatePicker(View view) {
        CalendarPicker.datePickerDialog.show();
    }
}

