package com.example.appanimals.Admin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.UriMatcher;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appanimals.Activity.ImageActivity;
import com.example.appanimals.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PhotoAdminAdapter extends RecyclerView.Adapter<PhotoAdminAdapter.PhotoAdminViewHolder> {
    private Context context;
    private ArrayList<Uri> arrayList;


    public PhotoAdminAdapter(Context context, ArrayList<Uri> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public PhotoAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_edit,parent,false);
        return new PhotoAdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoAdminViewHolder holder, int position) {
        Uri photo = arrayList.get(position);
        if (photo == null){
            return;
        }
        Picasso.with(context).load(photo).resize(1280,853).placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.error).into(holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageActivity.class);
                intent.putExtra("InfoImageUri",photo.toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (arrayList != null){
            return arrayList.size();
        }
        return 0;
    }

    public class PhotoAdminViewHolder extends RecyclerView.ViewHolder {
        ImageButton img;
        public PhotoAdminViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.image_show);
        }
    }
}
