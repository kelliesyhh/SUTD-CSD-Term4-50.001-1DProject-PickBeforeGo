package com.example.PickBeforeGo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.PickBeforeGo.MainActivity;
import com.example.PickBeforeGo.R;
import com.example.PickBeforeGo.activities.ProductScreenActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private Integer[] imgId = {R.id.img1,R.id.img2,R.id.img3,R.id.img4,R.id.img5};
    private ArrayList<View> productsArray = new ArrayList<>();

    private static final String NAME = "name";
    private static final String IMAGE_URL = "image_url";
    private static final String DESCRIPTION = "description";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View homeView = inflater.inflate(R.layout.fragment_home, container, false);

        // Set up the array to render in scrollview
        for (int i = 0; i < 5; i++){
            View itemView = homeView.findViewById(imgId[i]);
            productsArray.add(itemView);
        }

        // Go through all elements of the productsarray (comprises of Views of multiple products)
        // TODO: change this to go thru the promoProductArrayList from MainActivity instead
        for (View itemView: productsArray){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ProductScreenActivity.class);

                    // TODO: set productname properly based on whatever was in the array of products?
                    // for now, it will pass in the productname that was clicked
                    TextView txtProductName = itemView.findViewById(R.id.txtProductName);
                    intent.putExtra(NAME, txtProductName.getText().toString());

                    // TODO: set img.findViewById(R.id.img) to imageurl (use picasso)
                    // TODO: set description properly based on whatever was in the array of products? howwwwwww
                    String imageUrl = "https://firebasestorage.googleapis.com/v0/b/pickbeforegop.appspot.com/o/imageURL%2Fraw%3A%2Fstorage%2Femulated%2F0%2FDownload%2Fchocolate.pngMar%2028%2C%20202214%3A43%3A52%20PM.jpg?alt=media&token=2d6c4b4a-c1f3-4c6e-a8f0-a0e1a9320f58";
                    String description = "123.5g | Brand: Oreo";
                    intent.putExtra(IMAGE_URL, imageUrl);
                    intent.putExtra(DESCRIPTION, description);
                    startActivity(intent);
                }
            });
        }

        return homeView;
    }
}