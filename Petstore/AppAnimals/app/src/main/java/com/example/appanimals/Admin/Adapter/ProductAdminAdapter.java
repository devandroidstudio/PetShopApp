package com.example.appanimals.Admin.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appanimals.Activity.DetailActivity;
import com.example.appanimals.Admin.EditActivity;
import com.example.appanimals.Model.Product;
import com.example.appanimals.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductAdminAdapter extends RecyclerView.Adapter<ProductAdminAdapter.ProductAdminViewHolder> implements Filterable {
    private ArrayList<Product> arrayList;
    private Context context;
    private ArrayList<Product> mArrayList;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Animal");
    public interface ISendData{
        void sendDataProduct(Product product);
    }
    private ISendData iSendData;
    public ProductAdminAdapter(ArrayList<Product> arrayList, Context context,ISendData iSendData) {
        this.arrayList = arrayList;
        this.context = context;
        this.mArrayList = arrayList;
        this.iSendData = iSendData;
    }
    @NonNull
    @Override
    public ProductAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_admin,parent,false);
        return new ProductAdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdminViewHolder holder, int position) {
        Product product = arrayList.get(position);
        if (product == null){
            return;
        }
        holder.tvName.setText(product.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        holder.tvPrice.setText("$" + decimalFormat.format(product.getPrice()));
        holder.tvDescription.setMaxLines(1);
        holder.tvDescription.setEllipsize(TextUtils.TruncateAt.END);
        holder.tvDescription.setText(product.getDescription());
        for (int i = 0; i < product.getImg().size(); i++) {
            Picasso.with(context).load(product.getImg().get(0)).placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.error).into(holder.imgAnimal);
        }
        holder.tvSex.setText(product.getSex());
        holder.tvOrigin.setText(product.getOrigin());
        holder.tvAge.setText(product.getAge());
        if (product.getStatus().equals("On"))
        {
            holder.layoutForeground.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iSendData.sendDataProduct(product);
                }
            });
        }
        else {
            holder.layoutForeground.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Notification");
                    builder.setMessage("Do you want sold this product?");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            product.setStatus("On");
                            reference.child(product.getCategory()).child(product.getName()).updateChildren(product.toMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(context, "Update success", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (arrayList != null){
            return arrayList.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()){
                    arrayList = mArrayList;
                }
                else {
                    ArrayList<Product> list = new ArrayList<>();
                    for (Product product : mArrayList){
                        if (product.getName().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(product);
                        }
                    }
                    arrayList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = arrayList;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                arrayList = (ArrayList<Product>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ProductAdminViewHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvPrice,tvDescription,tvAge,tvOrigin,tvSex;
        CircleImageView imgAnimal;
        MaterialCardView layoutForeground;
        public ProductAdminViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAnimal = itemView.findViewById(R.id.img_animal_admin);
            tvName = itemView.findViewById(R.id.tv_name_animal_admin);
            tvPrice = itemView.findViewById(R.id.tv_price_animal_admin);
            tvDescription = itemView.findViewById(R.id.tv_description_animal_admin);
            tvAge = itemView.findViewById(R.id.tv_age_animal_admin);
            tvOrigin = itemView.findViewById(R.id.tv_origin_animal_admin);
            tvSex = itemView.findViewById(R.id.tv_sex_animal_admin);
            layoutForeground = itemView.findViewById(R.id.layout_foreground_animal_admin);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, EditActivity.class);
//                    intent.putExtra("informationAnimalEdit",arrayList.get(getAdapterPosition()));
//                    Pair[] pairs = new Pair[7];
//                    pairs[0] = new Pair<View,String>(imgAnimal,"image_edit_trans");
//                    pairs[1] = new Pair<View,String>(tvName,"name_edit_trans");
//                    pairs[2] = new Pair<View,String>(tvPrice,"price_edit_trans");
//                    pairs[3] = new Pair<View,String>(tvDescription,"description_edit_trans");
//                    pairs[4] = new Pair<View,String>(tvSex,"sex_edit_trans");
//                    pairs[5] = new Pair<View,String>(tvAge,"age_edit_trans");
//                    pairs[6] = new Pair<View,String>(tvOrigin,"origin_edit_trans");
//                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context,pairs);
//                    context.startActivity(intent,options.toBundle());
//                }
//            });
        }
    }

    public void updateItem(int index, String nameUserDelete, Product name)
    {
//        arrayList.remove(index);
//        arrayList.clear();
//        reference.child(name).removeValue();
//        notifyItemRemoved(index);

        name.setStatus("Off");
        reference.child(name.getCategory()).child(nameUserDelete).updateChildren(name.toMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Update success", Toast.LENGTH_SHORT).show();
            }
        });



    }
    public void undoItem(Product product, int index, String name)
    {
//        arrayList.add(index,product);
//        arrayList.clear();
        product.setStatus("On");
        reference.child(product.getCategory()).child(name).updateChildren(product.toMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Update success", Toast.LENGTH_SHORT).show();
            }
        });
//        notifyItemChanged(index);
//        notifyItemInserted(index);
    }

    public void release(){
        context = null;
    }
}
