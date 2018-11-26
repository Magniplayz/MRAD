package com.example.ghifa.mrad2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder{

    View mView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        mView = itemView;

    }

    public void setDetails(Context ctx, String name, String harga, String desc, String image)
    {
        TextView mNameV = mView.findViewById(R.id.nameProduk);
        TextView mHargaV = mView.findViewById(R.id.hargaProduk);
        TextView mDescV = mView.findViewById(R.id.descProduk);
        ImageView mImageView = mView.findViewById(R.id.imageProduk);

        mNameV.setText(name);
        mHargaV.setText(harga);
        mDescV.setText(desc);
        Picasso.get().load(image).into(mImageView);

    }

}
