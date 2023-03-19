package com.example.appanimals.Admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appanimals.Model.Product;
import com.example.appanimals.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PhotoArrayAdminAdapter extends RecyclerView.Adapter<PhotoArrayAdminAdapter.PhotoArrayAdminViewHolder> {
    private ArrayList<String> arrayList;
    private Context context;

    public PhotoArrayAdminAdapter(ArrayList<String> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public PhotoArrayAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_edit,parent,false);
        return new PhotoArrayAdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoArrayAdminViewHolder holder, int position) {
       String product = arrayList.get(position);
       if (product == null){
           return;
       }
        Picasso.with(context).load(product).placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.error).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        if (arrayList != null){
            return arrayList.size();
        }
        return 0;
    }

    public class PhotoArrayAdminViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public PhotoArrayAdminViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_show);
        }
    }

}
