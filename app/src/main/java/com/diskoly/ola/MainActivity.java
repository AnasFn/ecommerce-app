package com.diskoly.ola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.models.SlideModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

  HashMap<String,String> hashMap;
  ArrayList<HashMap<String,String>> arrayList=new ArrayList<>();
    String img_string;

    ArrayList<ArrayList<SlideModel>> rootArrayList=new ArrayList<>();
    ArrayList<SlideModel> imageList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridView=findViewById(R.id.gridView);





        String url = "https://dummyjson.com/products";

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray=response.getJSONArray("products");
                    for (int x =0; x<jsonArray.length(); x++){
                        JSONObject jsonObject=jsonArray.getJSONObject(x);
                        String mew= jsonObject.getString("price");
                        String thumbnail= jsonObject.getString("thumbnail");
                        String title= jsonObject.getString("title");
                        String des= jsonObject.getString("description");
                        String rating= jsonObject.getString("rating");
                        String discount= jsonObject.getString("discountPercentage");

                        JSONArray images=jsonObject.getJSONArray("images");
                        imageList=new ArrayList<>();
                        for (int b=0; b<images.length(); b++){

                             img_string=images.getString(b);
                             imageList.add(new SlideModel(img_string, null));


                        }
                        rootArrayList.add(imageList);


//                        String mew= jsonObject.getString("title");

                        hashMap=new HashMap<>();
                        hashMap.put("price", mew);
                        hashMap.put("thumbnail", thumbnail);
                        hashMap.put("title", title);
                        hashMap.put("des", des);
                        hashMap.put("rating", rating);
                        hashMap.put("discount", discount);
                        arrayList.add(hashMap);


                    }

                    MyAdapter myAdapter =new MyAdapter();
                    gridView.setAdapter(myAdapter);




                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

RequestQueue queue=Volley.newRequestQueue(MainActivity.this);
queue.add(jsonObjectRequest);




    }

    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater= getLayoutInflater();
            View myView= layoutInflater.inflate(R.layout.item, parent, false);

            CardView itemCard=myView.findViewById(R.id.ItemCard);

            itemCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ProDetails.imageList2 = rootArrayList.get(position);
                    startActivity(new Intent(MainActivity.this, ProDetails.class));
                }
            });


            TextView tv_price, tv_title, tv_rating, tv_discount;

            tv_price=myView.findViewById(R.id.price);
            tv_title=myView.findViewById(R.id.title);
            tv_rating=myView.findViewById(R.id.rating);
            tv_discount=myView.findViewById(R.id.discount);
            ImageView imageView=myView.findViewById(R.id.imageView);

            HashMap<String,String>hashMap=arrayList.get(position);
            String price=hashMap.get("price");
            String img_url= hashMap.get("thumbnail");
            String title= hashMap.get("title");
            String rateing= hashMap.get("rating");
            String des= hashMap.get("des");
            String discount= hashMap.get("discount");

            tv_price.setText("$"+price);
            tv_title.setText(title+", "+des);
            tv_rating.setText(rateing);
            tv_discount.setText("-"+discount+"%");

            Picasso.get().load(img_url).into(imageView);





            return myView;
        }
    }




}


//    FragmentTransaction hometrans= getSupportFragmentManager().beginTransaction();
//        hometrans.replace(R.id.content, new HomeFragment());
//                hometrans.commit();
//
//                binding.bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//@Override
//public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
//
//        switch (item.getItemId()){
//        case R.id.home:
//        transaction.replace(R.id.content, new HomeFragment());
//        break;//
//        case R.id.notifications:
//        transaction.replace(R.id.content, new NotificationFr());
//        break;//
//        case R.id.settings:
//        transaction.replace(R.id.content, new SettingFr());
//        break;//
//        //
//        }
//
//        transaction.commit();
//
//        return true;
//        }
//        });

