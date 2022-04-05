package com.example.PickBeforeGo.activities;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
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
import android.widget.Toast;

import com.example.PickBeforeGo.components.ProductAttributes;
import com.squareup.picasso.Picasso;


import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.PickBeforeGo.R;
import com.example.PickBeforeGo.components.CalendarPicker;
import com.example.PickBeforeGo.helper.PromotionHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminFormActivity extends AppCompatActivity {
    private static final String TAG = "admin";
    private static final String IMAGE_URL = "image_url";
    private String image_url;


    public static String dayy;
    public static String monthh;
    public static String yearr;

    String intentName;
    private String product_id;
    String intentPrice;
    String intentPromoValue;

    final String[] promotionChoice = {"0%"};
    final String[] priceChoice = {"0"};
    final String[] sbmtStockAvailability = new String[1];

    boolean intentPromo;
    boolean intentStock;
    boolean intentIsNew;

    private static final int GalleryPick = 1;
    private ImageView placeImage;


    private String CategoryName, Description, Price, Pname, saveCurrentDate, saveCurrentTime, Weight="", Next_restock="";
    private String productRandomKey, downloadImageUrl;
    private StorageReference ProductImagesRef;
    private DatabaseReference ProductsRef;
    private ProgressDialog loadingBar;
    private Boolean Favourite=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ///// Receiving Intents /////
        Bundle resultIntent = getIntent().getExtras();

        if(resultIntent != null) {
            intentName = resultIntent.getString("name","null");
            product_id = resultIntent.getString(ProductAttributes.PRODUCT_ID);
            intentPrice = resultIntent.getString("price","null");
            intentPrice = intentPrice.substring(1);
            intentPromoValue = resultIntent.getString("promoValue", "0%");
            intentPromo = resultIntent.getBoolean("promotion",false);
            intentStock = resultIntent.getBoolean("inStock",false);
            intentIsNew = resultIntent.getBoolean("isNewProduct", true);
            image_url = resultIntent.getString(IMAGE_URL);
        } else {
            intentName = "null";
            intentPrice = "null";
            intentPromoValue = "0%";
            intentIsNew = true;
            image_url = "null";
        }

        String[] todayDate = CalendarPicker.getTodayInit();
        dayy = todayDate[0];
        monthh = todayDate[1];
        yearr = todayDate[2];

        if (intentIsNew) {
            dayy = "null";
            monthh = "null";
            yearr = "null";
            setContentView(R.layout.activity_admin_form_add);
        } else {
            setContentView(R.layout.activity_admin_form_edit);
        }

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        loadingBar = new ProgressDialog(this);



        /*----------------------------------------------------------------------------*/

        //////////// INIT VARIABLES ///////////////


        TextView PriceAfterPromotion = findViewById(R.id.PriceAfterPromotion);
        TextView itemName = findViewById(R.id.editItemName);
        ImageButton btnBack = findViewById(R.id.btnBack);
        Button btnSubmit = findViewById(R.id.btnSubmit);
        EditText editPriceText = findViewById(R.id.texteditPrice);
        Button dateButton;
        placeImage = findViewById(R.id.placeImage);



        //////////// INIT COMPONENTS ///////////////

        // Get Item Name
        String itemNameValue = intentName;
        itemName.setText(itemNameValue);

        // Get Item Image
        Picasso.get().load(image_url).placeholder(R.drawable.placeholder_product_pic).into(placeImage);

        // TODO: Opening Gallery
        placeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
//                System.out.println("clicked image");
                OpenGallery();
            }
        });


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

            // New Stock Availability Position //

        spinnerStockAvailability.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                sbmtStockAvailability[0] = spinnerStockAvailability.getSelectedItem().toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
                sbmtStockAvailability[0] = "null";
            }
        });


        final String[] sbmtRestockTime = new String[1];
        if (!intentIsNew) {
            //// Init Spinner -> Next Restock Timing ////
            Spinner spinnerRestockTime = findViewById(R.id.NextRestockTime);
            ArrayAdapter<CharSequence> adapterRestockTime = ArrayAdapter.createFromResource(this, R.array.NextRestockTime, android.R.layout.simple_spinner_item);
            adapterRestockTime.setDropDownViewResource(android.R.layout.simple_spinner_item);
            spinnerRestockTime.setAdapter(adapterRestockTime);

            //// Init Calendar Selector -> Next Restock Date ////
            dateButton = findViewById(R.id.dateButton);
            CalendarPicker.initDatePicker(this, dateButton);
            dateButton.setText(CalendarPicker.getTodaysDate());

            //// Next Restock time ////

            spinnerRestockTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Object item = parent.getItemAtPosition(position);
                    sbmtRestockTime[0] = spinnerRestockTime.getSelectedItem().toString();
                }
                public void onNothingSelected(AdapterView<?> parent) {
                    sbmtRestockTime[0] = "null";
                }
            });
        }

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
            
            //////////
            if (placeImage == null || image_url=="null")
            {
                Toast toast = Toast.makeText(AdminFormActivity.this, "Product image is mandatory...", Toast.LENGTH_SHORT);
                toast.show();
                System.out.println("invoke error here");
            }
//            else if (TextUtils.isEmpty(Description))
//            {
//                Toast.makeText(this, "Please write product description...", Toast.LENGTH_SHORT).show();
//            }
            else if (TextUtils.isEmpty(newPrice[0]))
            {
                Toast toast = Toast.makeText(AdminFormActivity.this, "Please write product Price...", Toast.LENGTH_LONG);
                toast.show();
            }
            else if (TextUtils.isEmpty(itemNameValue))
            {
                Toast.makeText(AdminFormActivity.this, "Please write product name...", Toast.LENGTH_LONG).show();
            }
            else
            {
                if (intentIsNew) {
                    StoreNewProductInformation();
                } else {
                    System.out.println("editing data base was calllededdededed");
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Product_List").child(product_id);
                    reference.child("productName").setValue(itemNameValue);
                    System.out.println(reference.child("productName"));
                    reference.child("price").setValue(newPrice[0]);
                    if (sbmtStockAvailability[0] == "No Stock"){
                        reference.child("inStock").setValue(false);
                    }
                    if (sbmtStockAvailability[0] == "Promotion"){
                        reference.child("isPromo").setValue(true);
                    }
                    if (sbmtStockAvailability[0] == "Available"){
                        reference.child("inStock").setValue(true);
                    }
                }
            }



            // DEBUGGING
            System.out.println("-----DEBUGGING-----");

            System.out.println("item name is: " + itemNameValue);
            System.out.println("New price is: " + newPrice[0]);
            System.out.println("Stock Status is: " + sbmtStockAvailability[0]);
            System.out.println("Promotion Value is: " + sbmtPromotionSpinner[0]);

            System.out.println("Restock Time is: " + sbmtRestockTime[0]);
            System.out.println("Restock day is: " + dayy);
            System.out.println("Restock month is: " + monthh);
            System.out.println("Restock year is: " + yearr);

            System.out.println("Is a new Product?: " + intentIsNew);

        }));




    }

    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            image_url = data.getData().toString();
            placeImage.setImageURI(Uri.parse(image_url));
        }
    }



    private void StoreNewProductInformation()
    {
        loadingBar.setTitle("Add New Product");
        loadingBar.setMessage("Dear Admin, please wait while we are adding the new product.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;


        final StorageReference filePath = ProductImagesRef.child(Uri.parse(image_url).getLastPathSegment() + productRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(Uri.parse(image_url));


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(AdminFormActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(AdminFormActivity.this, "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if (task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(AdminFormActivity.this, "got the Product image Url Successfully...", Toast.LENGTH_SHORT).show();

                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });
    }


    private void SaveProductInfoToDatabase()
    {
        //
        System.out.println("this was called databadsseeeee");


        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("category", CategoryName);
        productMap.put("date", saveCurrentDate);
        productMap.put("description", Description);
        productMap.put("imageURL", downloadImageUrl);
        productMap.put("productID", productRandomKey);
        productMap.put("productName", Pname);
        productMap.put("price", Price);
        productMap.put("time", saveCurrentTime);
        productMap.put("weight", Weight );
        productMap.put("nextRestockTime", Next_restock);
        productMap.put("discountPercent", promotionChoice[0].substring(0, promotionChoice[0].length() - 1));
        productMap.put("isPromo",promotionChoice[0]!="0%"? false : true);
        productMap.put("isFavourite",Favourite);
        productMap.put("inStock", sbmtStockAvailability[0] == "No Stock"? false:true);


        ProductsRef.child(productRandomKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful())
                {
                    loadingBar.dismiss();
                    Toast.makeText(AdminFormActivity.this, "Product is added successfully..", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    loadingBar.dismiss();
                    String message = task.getException().toString();
                    Toast.makeText(AdminFormActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void openDatePicker(View view) {
        CalendarPicker.datePickerDialog.show();
    }
}