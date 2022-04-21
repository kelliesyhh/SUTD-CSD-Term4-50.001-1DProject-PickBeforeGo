package com.example.PickBeforeGo.activities;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.PickBeforeGo.R;
import com.example.PickBeforeGo.components.CalendarPicker;
import com.example.PickBeforeGo.components.ProductAttributes;
import com.example.PickBeforeGo.helper.PromotionHelper;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class AdminFormActivity extends AppCompatActivity {
    private static final String DATABASE_TAG = "admin_database";
    private static final String DEBUG = "debug_admin";
    private Uri imageUri;

    public static String restockDay;
    public static String restockMonth;
    public static String restockYear;

    String productNameFromIntent;
    String productIdFromIntent;
    String productPriceFromIntent;
    String productDescriptionFromIntent;
    boolean isPromoFromIntent;
    boolean inStockFromIntent;
    boolean isNewProductFromIntent;
    String productDiscountFromIntent;

    final String[] promotionChoice = {"1%"};
    final String[] priceChoice = {"0"};
    final String[] sbmtStockAvailability = new String[1];
    final String[] sbmtPromotionSpinner = new String[1];
    final String[] itemNameValue = new String[1];
    final String[] itemDescriptionValue = new String[1];
    final String[] newPrice = new String[1];

    private static final int galleryPick = 1;
    private ImageView imgViewUploadedImage;

    private String saveCurrentDate;
    private String saveCurrentTime;
    private final String nextRestockTiming = "";
    private String downloadImageUrl;
    private UUID productRandomUUID;
    private int productHashfromUUID;
    private StorageReference storageRefProductImages;
    private DatabaseReference dbRefProduct;
    private ProgressDialog loadingBar;
    private final Boolean isFavourite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ///// Receiving Intents /////
        Bundle resultIntent = getIntent().getExtras();
        storageRefProductImages = FirebaseStorage.getInstance().getReference().child("imageURL");
        dbRefProduct = FirebaseDatabase.getInstance().getReference().child("Product_List");
        if (resultIntent != null) {
            productNameFromIntent = resultIntent.getString(ProductAttributes.NAME,"Product Name");
            productIdFromIntent = resultIntent.getString(ProductAttributes.PRODUCT_ID);
            productPriceFromIntent = resultIntent.getString(ProductAttributes.PRICE,"null");
            productPriceFromIntent = productPriceFromIntent.substring(1);
            productDiscountFromIntent = resultIntent.getString(ProductAttributes.DISCOUNT, "0%");
            isPromoFromIntent = resultIntent.getBoolean(ProductAttributes.IS_PROMO,false);
            inStockFromIntent = resultIntent.getBoolean(ProductAttributes.STOCK,false);
            isNewProductFromIntent = resultIntent.getBoolean(ProductAttributes.IS_NEW, true);
            imageUri = Uri.parse(resultIntent.getString(ProductAttributes.IMAGE_URL));
            productDescriptionFromIntent = resultIntent.getString(ProductAttributes.DESCRIPTION, "Product Description");
        }
        else {
            productPriceFromIntent = "null";
            productDiscountFromIntent = "0%";
            isNewProductFromIntent = true;
            imageUri = null;
        }

        String[] todayDate = CalendarPicker.getTodayInit();
        restockDay = todayDate[0];
        restockMonth = todayDate[1];
        restockYear = todayDate[2];

        if (isNewProductFromIntent) {
            restockDay = "null";
            restockMonth = "null";
            restockYear = "null";
            setContentView(R.layout.activity_admin_form_add);
        }
        else {
            setContentView(R.layout.activity_admin_form_edit);
        }

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        loadingBar = new ProgressDialog(this);


        /*----------------------------------------------------------------------------*/

        //////////// INIT VARIABLES ///////////////
        TextView txtViewPriceAfterPromo = findViewById(R.id.txtViewPriceAfterPromo);
        EditText editTextName = findViewById(R.id.editTextName);
        ImageButton btnBack = findViewById(R.id.btnBack);
        EditText itemDescription = findViewById(R.id.editDescription);
        Button btnSubmit = findViewById(R.id.btnSubmit);
        EditText editTextPrice = findViewById(R.id.editTextPrice);
        Button btnDate;
        imgViewUploadedImage = findViewById(R.id.imgViewUploadedImage);

        //////////// INIT COMPONENTS ///////////////
        // Get Item Name
        if (productNameFromIntent != null) {
            itemNameValue[0] = productNameFromIntent;
            editTextName.setText(itemNameValue[0]);
        }
        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                itemNameValue[0] = editable.toString();
            }
        });

        // Get Item Image
        Picasso.get().load(imageUri).placeholder(R.drawable.placeholder_product_pic).into(imgViewUploadedImage);
        imgViewUploadedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNewProductFromIntent) {
                    openGallery();
                }
            }
        });

        /// Get Item Description
        itemDescription.setText(productDescriptionFromIntent);
        itemDescriptionValue[0] = productDescriptionFromIntent;
        itemDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                itemDescriptionValue[0] = editable.toString();
            }
        });
        Log.d(DEBUG, "isPromo : " + isPromoFromIntent);
        Log.d(DEBUG, "inStock : " + inStockFromIntent);

        //// Init Spinner -> Stock Availability ////
        Spinner spinnerStockAvailability = findViewById(R.id.spinnerStockAvailability);
        ArrayAdapter<CharSequence> adapterStockAvailability = ArrayAdapter.createFromResource(this, R.array.arrSpinnerStockAvailability, android.R.layout.simple_spinner_item);
        adapterStockAvailability.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerStockAvailability.setAdapter(adapterStockAvailability);

        // Default Stock Availability Position
        String[] spinnerStockAvailOptions = getResources().getStringArray(R.array.arrSpinnerStockAvailability);
        String outStock = spinnerStockAvailOptions[0];
        String inAvailability = spinnerStockAvailOptions[1];
        ArrayAdapter SelectorAdaptor = (ArrayAdapter) spinnerStockAvailability.getAdapter();

        int outStockPos = SelectorAdaptor.getPosition(outStock);
        int inAvailabilityPos = SelectorAdaptor.getPosition(inAvailability);
        if (!inStockFromIntent) {
            spinnerStockAvailability.setSelection(outStockPos);
        } else if (inStockFromIntent) {
            spinnerStockAvailability.setSelection(inAvailabilityPos);
        } else {
            Log.d(DEBUG, "There exists an error in Selecting the Spinner for Stock Avail!");
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
        if (!isNewProductFromIntent) {
            //// Init Spinner -> Next Restock Timing ////
            Spinner spinnerRestockTime = findViewById(R.id.spinnerNextRestockTime);
            ArrayAdapter<CharSequence> adapterRestockTime = ArrayAdapter.createFromResource(this, R.array.arrNextRestockTime, android.R.layout.simple_spinner_item);
            adapterRestockTime.setDropDownViewResource(android.R.layout.simple_spinner_item);
            spinnerRestockTime.setAdapter(adapterRestockTime);

            //// Init Calendar Selector -> Next Restock Date ////
            btnDate = findViewById(R.id.btnDate);
            CalendarPicker.initDatePicker(this, btnDate);
            btnDate.setText(CalendarPicker.getTodayDate());

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
        Spinner spinnerPromotion = findViewById(R.id.spinnerPromotion);
        ArrayAdapter<CharSequence> adapterPromotion = ArrayAdapter.createFromResource(this, R.array.arrPromotion, android.R.layout.simple_spinner_item);
        adapterPromotion.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerPromotion.setAdapter(adapterPromotion);

        // Default Promotion Position
        String[] spinnerPromoOptions = getResources().getStringArray(R.array.arrPromotion);
        ArrayAdapter selectorPromoAdaptor = (ArrayAdapter) spinnerPromotion.getAdapter();
        for (int i = 0; i < spinnerPromoOptions.length; i++ ) {
            if (productDiscountFromIntent.equals(spinnerPromoOptions[i])) {
                int chosenPromo = selectorPromoAdaptor.getPosition(spinnerPromoOptions[i]);
                spinnerPromotion.setSelection(chosenPromo);
                promotionChoice[0] = productDiscountFromIntent;
            }
        }

        //// Init Price ////
        if ((!productPriceFromIntent.equals("null"))) {
            editTextPrice.setText(productPriceFromIntent);
            priceChoice[0] = productPriceFromIntent;
            String newPromotedValue = new PromotionHelper(priceChoice[0], promotionChoice[0]).promoChange();
            txtViewPriceAfterPromo.setText(newPromotedValue);
            newPrice[0] = productPriceFromIntent;
        }

        // Setting Promotion //
        spinnerPromotion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                promotionChoice[0] = spinnerPromotion.getSelectedItem().toString();
                if (!priceChoice[0].isEmpty()) {
                    String newPromotedValue = new PromotionHelper(priceChoice[0], promotionChoice[0]).promoChange();
                    txtViewPriceAfterPromo.setText(newPromotedValue);
                    sbmtPromotionSpinner[0] = spinnerPromotion.getSelectedItem().toString();
                    newPrice[0] = newPromotedValue;
            } else {
                txtViewPriceAfterPromo.setText(priceChoice[0]);
                sbmtPromotionSpinner[0] = "null";
            }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //// Init Edit Text -> Price Value ////
        editTextPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                priceChoice[0] = editable.toString();
                if (!priceChoice[0].isEmpty()) {
                    String newPromotedValue = new PromotionHelper(priceChoice[0], promotionChoice[0]).promoChange();
                    txtViewPriceAfterPromo.setText(newPromotedValue);
                    newPrice[0] = newPromotedValue;
                    Log.d(DEBUG, "item name is: " + itemNameValue[0]);
                } else {
                    txtViewPriceAfterPromo.setText(R.string.placeholder_price);
                }
            }
        });

        //// Init Buttons ////
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /*----------------------------------------------------------------*/
        // SUBMITTING NEW VARIABLES
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Checking if product details are properly entered before submitting
                if (imgViewUploadedImage == null || imageUri == null) {
                    Toast toast = Toast.makeText(AdminFormActivity.this, "Product image is mandatory!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if (TextUtils.isEmpty(itemDescriptionValue[0])) {
                    Toast.makeText(AdminFormActivity.this, "Please enter product description!", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(newPrice[0])) {
                    Toast.makeText(AdminFormActivity.this, "Please enter product price!", Toast.LENGTH_LONG).show();
                }
                else if (TextUtils.isEmpty(itemNameValue[0])) {
                    Toast.makeText(AdminFormActivity.this, "Please enter product name!", Toast.LENGTH_LONG).show();
                }
                else {
                    if (isNewProductFromIntent) {
                        Log.d(DEBUG,"Item Name: " + itemNameValue[0]);
                        // check if the string name already exists
                        productRandomUUID = UUID.nameUUIDFromBytes(itemNameValue[0].getBytes());
                        productHashfromUUID = productRandomUUID.hashCode();
                        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Product_List");
                        DatabaseReference userNameRef = rootRef.child(Integer.toString(productHashfromUUID));
                        ValueEventListener eventListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(!dataSnapshot.exists()) {
                                    Log.d(DATABASE_TAG, "Creating product");
                                    storeNewProductInformation(productHashfromUUID);
                                } else {
                                    Toast.makeText(AdminFormActivity.this, "Product already exists", Toast.LENGTH_LONG).show();
                                    Log.d(DATABASE_TAG, "databaseError.getMessage()");
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.d(DATABASE_TAG, "databaseError.getMessage()");
                            }
                        };
                        userNameRef.addListenerForSingleValueEvent(eventListener);

                        finish();

                    } else {
                        Double discountPercent = Double.valueOf(promotionChoice[0].substring(0, promotionChoice[0].length() - 1));
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Product_List").child(productIdFromIntent);
                        reference.child("productName").setValue(itemNameValue[0]);
                        reference.child("price").setValue(newPrice[0]);
                        if (sbmtStockAvailability[0].equals("No Stock")){
                            reference.child("inStock").setValue(false);
                            reference.child("isPromo").setValue(false);
                        }
                        if (sbmtStockAvailability[0].equals("Promotion")){
                            reference.child("isPromo").setValue(true);
                            reference.child("inStock").setValue(true);
                        }
                        if (sbmtStockAvailability[0].equals("Available")){
                            reference.child("inStock").setValue(true);
                            reference.child("isPromo").setValue(false);
                        }
                        if (discountPercent != 0){
                            reference.child("isPromo").setValue(true);
                        }
                        reference.child("discountPercent").setValue(Double.valueOf(promotionChoice[0].substring(0, promotionChoice[0].length() - 1)));
                        reference.child("nextRestockTime").setValue((sbmtRestockTime[0])+" "+restockDay+" "+ restockMonth +" "+ restockYear);
                        reference.child("description").setValue(itemDescriptionValue[0]);
                        Toast.makeText(AdminFormActivity.this, "Product details have been updated!", Toast.LENGTH_LONG).show();

                        finish();
                    }
                }

                // DEBUGGING
                Log.d(DEBUG, "item name is: " + itemNameValue[0]);
                Log.d(DEBUG, "item description is :" + itemDescriptionValue[0]) ;
                Log.d(DEBUG, "New price is: " + newPrice[0]);
                Log.d(DEBUG, "Stock Status is: " + sbmtStockAvailability[0]);
                Log.d(DEBUG, "Promotion Value is: " + sbmtPromotionSpinner[0]);

                Log.d(DEBUG, "Restock Time is: " + sbmtRestockTime[0]);
                Log.d(DEBUG, "Restock day is: " + restockDay);
                Log.d(DEBUG, "Restock month is: " + restockMonth);
                Log.d(DEBUG, "Restock year is: " + restockYear);

                Log.d(DEBUG, "Is a new Product?: " + isNewProductFromIntent);
                Log.d(DEBUG, "Is this on promo?: " + !sbmtPromotionSpinner[0].equals("0%"));
            }
        });
    }

    private void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, galleryPick);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == galleryPick && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            Log.d(DEBUG, "new image uri is " + imageUri);
            imgViewUploadedImage.setImageURI(imageUri);
        }
    }

    private void storeNewProductInformation(int productHashfromUUID) {
        if(!this.isFinishing()) {
            loadingBar.setTitle("Add New Product");
            loadingBar.setMessage("Dear Admin, please wait while we are adding the new product.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
        }

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        //TODO Change Product Key

        Log.d(DEBUG, "Image URI:" + imageUri);
        final StorageReference filePath = storageRefProductImages.child(imageUri.getLastPathSegment() + productHashfromUUID + ".jpg");
        final UploadTask uploadTask = filePath.putFile(imageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(AdminFormActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminFormActivity.this, "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            downloadImageUrl = task.getResult().toString();
                            Toast.makeText(AdminFormActivity.this, "got the Product image Url Successfully...", Toast.LENGTH_SHORT).show();
                            saveProductInfoToDatabase();
                        }
                    }
                });
            }
        });
    }

    private void saveProductInfoToDatabase() {
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("date", saveCurrentDate);
        productMap.put("description", itemDescriptionValue[0]);
        productMap.put("imageURL", downloadImageUrl);
        productMap.put("productID", Long.toString(productHashfromUUID));
        productMap.put("productName", itemNameValue[0]);
        productMap.put("price", newPrice[0]);
        productMap.put("time", saveCurrentTime);
        productMap.put("nextRestockTime", nextRestockTiming);
        productMap.put("discountPercent", Double.valueOf(promotionChoice[0].substring(0, promotionChoice[0].length() - 1)));
        productMap.put("isFavourite", isFavourite);
        productMap.put("inStock", !sbmtStockAvailability[0].equals("No Stock"));
        productMap.put("isPromo", !sbmtPromotionSpinner[0].equals("0%"));

        dbRefProduct.child(Integer.toString(productHashfromUUID)).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    loadingBar.dismiss();
                    Toast.makeText(AdminFormActivity.this, "Product is added successfully..", Toast.LENGTH_SHORT).show();
                }
                else {
                    loadingBar.dismiss();
                    String message = task.getException().toString();
                    Toast.makeText(AdminFormActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}