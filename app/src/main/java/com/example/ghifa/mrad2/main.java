package com.example.ghifa.mrad2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class main extends AppCompatActivity {

    RecyclerView mRecyclerView;

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef= mFirebaseDatabase.getReference("Produk");
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Produk, ViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Produk, ViewHolder>(
                Produk.class,
                R.layout.row_produk,
                ViewHolder.class,
                mRef
        ) {
            @Override
            protected void populateViewHolder(ViewHolder viewHolder, Produk model, int position) {

                viewHolder.setDetails(getApplicationContext(), model.getName(), model.getDesc(), model.getImage(), model.getDesc());
            }
        };

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}
