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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.PickBeforeGo.R;
import com.example.PickBeforeGo.components.CalendarPicker;
import com.example.PickBeforeGo.helper.PromotionHelper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

public class AdminFormActivity extends AppCompatActivity {
    private static final String TAG = "admin";
    private static final String IMAGE_URL = "image_url";
    private String image_url;

    static String[] todayDate = CalendarPicker.getTodayInit();
    public static String dayy = todayDate[0];
    public static String monthh = todayDate[1];
    public static String yearr = todayDate[2];

    String intentName;
    String intentPrice;
    String intentPromoValue;

    boolean intentPromo;
    boolean intentStock;
    boolean intentIsNew;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_form);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();


        ///// Receiving Intents /////
        Bundle resultIntent = getIntent().getExtras();

        if(resultIntent != null) {
            intentName = resultIntent.getString("name","null");
            intentPrice = resultIntent.getString("price","null");
            intentPrice = intentPrice.substring(1);
            intentPromoValue = resultIntent.getString("promoValue", "0%");
            intentPromo = resultIntent.getBoolean("promotion",false);
            intentStock = resultIntent.getBoolean("inStock",false);
            intentIsNew = resultIntent.getBoolean("isNewProduct", false);
            image_url = resultIntent.getString(IMAGE_URL);
        } else {
            intentName = "null";
            intentPrice = "null";
            intentPromoValue = "0%";
        }


        //// Testing Values Here: ////
//        System.out.println("adasd" + intentPromoValue);
//         intentPromoValue = "50%";



        /*----------------------------------------------------------------------------*/

        //////////// INIT VARIABLES ///////////////

        final String[] promotionChoice = {"0%"};
        final String[] priceChoice = {"0"};
        TextView PriceAfterPromotion = findViewById(R.id.PriceAfterPromotion);
        TextView itemName = findViewById(R.id.editItemName);
        ImageButton btnBack = findViewById(R.id.btnBack);
        Button btnSubmit = findViewById(R.id.btnSubmit);
        EditText editPriceText = findViewById(R.id.texteditPrice);
        Button dateButton;
        ImageView placeImage = findViewById(R.id.placeImage);

        ///'
        System.out.println(image_url);
//        URL url = null;
//        try {
//            url = new URL(image_url);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        Bitmap image = null;
//        try {
//            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        placeImage.setImageBitmap(image);
        Picasso.get().load(image_url).placeholder(R.drawable.placeholder_product_pic).into(placeImage);
        ////

        //////////// INIT COMPONENTS ///////////////

        // Init Item Image
            // TODO: to config user image.

        // Init Item Name
            // TODO: On load, edit item name here.
        String itemNameValue = intentName;
        itemName.setText(itemNameValue);


        //// Init Spinner -> Stock Availability ////
        Spinner spinnerStockAvailability = findViewById(R.id.StockAvailability);
        ArrayAdapter<CharSequence> adapterStockAvailability = ArrayAdapter.createFromResource(this, R.array.StockAvailability, android.R.layout.simple_spinner_item);
        adapterStockAvailability.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerStockAvailability.setAdapter(adapterStockAvailability);

            // Default Stock Availability Position
        String[] spinnerStockAvailOptions = getResources().getStringArray(R.array.StockAvailability);
        String inPromotion = spinnerStockAvailOptions[1];
        String outStock = spinnerStockAvailOptions[0];
        String inAvailability = spinnerStockAvailOptions[2];
        ArrayAdapter SelectorAdaptor = (ArrayAdapter) spinnerStockAvailability.getAdapter();

        int inPromotionPos = SelectorAdaptor.getPosition(inPromotion);
        int outStockPos = SelectorAdaptor.getPosition(outStock);
        int inAvailabilityPos = SelectorAdaptor.getPosition(inAvailability);
        if (intentPromo) {
            spinnerStockAvailability.setSelection(inPromotionPos);
        } else if (!intentStock) {
            spinnerStockAvailability.setSelection(outStockPos);
        } else if (intentStock) {
            spinnerStockAvailability.setSelection(inAvailabilityPos);
        } else {
            System.out.println("There exist an error in Selecting the Spinner for Stock Avail!");
        }


        //// Init Spinner -> Next Restock Timing ////
        Spinner spinnerRestockTime = findViewById(R.id.NextRestockTime);
        ArrayAdapter<CharSequence> adapterRestockTime = ArrayAdapter.createFromResource(this, R.array.NextRestockTime, android.R.layout.simple_spinner_item);
        adapterRestockTime.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerRestockTime.setAdapter(adapterRestockTime);

        //// Init Calendar Selector -> Next Restock Date ////
        dateButton = findViewById(R.id.dateButton);
        CalendarPicker.initDatePicker(this, dateButton);
        dateButton.setText(CalendarPicker.getTodaysDate());

        //// Init Spinner -> Promotion ////
        Spinner spinnerPromotion = findViewById(R.id.promotionn);
        ArrayAdapter<CharSequence> adapterPromotion = ArrayAdapter.createFromResource(this, R.array.promotion, android.R.layout.simple_spinner_item);
        adapterPromotion.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerPromotion.setAdapter(adapterPromotion);

            // Default Promotion Position
        String[] spinnerPromoOptions = getResources().getStringArray(R.array.promotion);
        ArrayAdapter SelectorPromoAdaptor = (ArrayAdapter) spinnerPromotion.getAdapter();
        for (int i=0; i < spinnerPromoOptions.length; i++ ) {
            if (intentPromoValue.equals(spinnerPromoOptions[i])   ) {
                int chosenPromo = SelectorPromoAdaptor.getPosition(spinnerPromoOptions[i]);
                spinnerPromotion.setSelection(chosenPromo);
                promotionChoice[0] = intentPromoValue;
                System.out.println("hehehe" + promotionChoice[0]);
                String test = spinnerPromotion.getSelectedItem().toString();
                System.out.println("asdasdsaasdsdsa" + test);
            }
        }


        //// Init Price ////
        final String[] newPrice = new String[1];
        if (intentPrice!="null") {
            editPriceText.setText(intentPrice);
            priceChoice[0] = intentPrice;

            String newPromotedValue = new PromotionHelper(priceChoice[0], promotionChoice[0]).promoChange();
            PriceAfterPromotion.setText(newPromotedValue);
            newPrice[0] = intentPrice;
        }
            // Setting Promotion //
        final String[] sbmtPromotionSpinner = new String[1];
        spinnerPromotion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                promotionChoice[0] = spinnerPromotion.getSelectedItem().toString();
                System.out.println("helloooo");

                if (!priceChoice[0].isEmpty()) {
                    String newPromotedValue = new PromotionHelper(priceChoice[0], promotionChoice[0]).promoChange();
                    PriceAfterPromotion.setText(newPromotedValue);

                    sbmtPromotionSpinner[0] = spinnerPromotion.getSelectedItem().toString();
                    newPrice[0] = newPromotedValue;
                } else {
                    PriceAfterPromotion.setText(priceChoice[0]);

                    sbmtPromotionSpinner[0] = "null";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });


        //// Init Edit Text -> Price Value ////
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
                    newPrice[0] = newPromotedValue;

                } else {
                    PriceAfterPromotion.setText("0.00");
                }
            }
        });


        ///////// Storing NEW VARIABLES ///////////

        //// New Stock Availability Position ////
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

        //// Next Restock time ////
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

        /*----------------------------------------------------------------*/


        //// Init Buttons ////
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // SUBMITTING NEW VARIABLES TODO: submit to database.
        btnSubmit.setOnClickListener((view -> {

            System.out.println("item name is: " + itemNameValue);
            System.out.println("New price is: " + newPrice[0]);
            System.out.println("Stock Status is: " + sbmtStockAvailability[0]);
            System.out.println("Promotion Value is: " + sbmtPromotionSpinner[0]);



            // Restock Calendar
            System.out.println("Restock Time is: " + sbmtRestockTime[0]);
            System.out.println("Restock day is: " + dayy);
            System.out.println("Restock month is: " + monthh);
            System.out.println("Restock year is: " + yearr);

            // Miscell
            System.out.println("Is a new Product?: " + intentIsNew);

        }));


    }

    public void openDatePicker(View view) {
        CalendarPicker.datePickerDialog.show();
    }
}