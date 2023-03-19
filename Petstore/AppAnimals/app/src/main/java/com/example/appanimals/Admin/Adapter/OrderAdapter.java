package com.example.appanimals.Admin.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appanimals.Model.Account;
import com.example.appanimals.Model.Cart;
import com.example.appanimals.Model.InfoCustomer;
import com.example.appanimals.Model.Product;
import com.example.appanimals.R;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private ArrayList<InfoCustomer> arrayList;
    private Context context;
    private IClickShowDataOrderListener mIClickShowDataOrderListener;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("InformationCustomer");
    public interface IClickShowDataOrderListener{
        void onClickShowDataOrder(InfoCustomer infoCustomer);
    }

    public OrderAdapter(ArrayList<InfoCustomer> arrayList, Context context, IClickShowDataOrderListener mIClickShowDataOrderListener) {
        this.arrayList = arrayList;
        this.context = context;
        this.mIClickShowDataOrderListener = mIClickShowDataOrderListener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order,parent,false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        InfoCustomer infoCustomer = arrayList.get(position);
        if (infoCustomer == null){
            return;
        }
        holder.tvName.setText(infoCustomer.getUsername());
        for (int i = 0; i < infoCustomer.getCarts().size(); i++) {
            holder.tvNameAnimal.append((infoCustomer.getCarts().get(i).getName()+" "));
            holder.tvNameAnimal.setMaxLines(1);
            holder.tvNameAnimal.setEllipsize(TextUtils.TruncateAt.END);
        }
        holder.tvTime.setText(infoCustomer.getDate());
        holder.materialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIClickShowDataOrderListener.onClickShowDataOrder(infoCustomer);
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

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvNameAnimal,tvTime;
        MaterialCardView materialCardView;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name_order);
            tvNameAnimal = itemView.findViewById(R.id.tv_name_animal_order);
            tvTime = itemView.findViewById(R.id.tv_time_order);
            materialCardView = itemView.findViewById(R.id.layout_order_admin);
        }
    }

    public void release(){
        context = null;
    }

    public void removeItem(int index,String name){
        arrayList.remove(index);
        reference.child(name).removeValue();
        notifyItemRemoved(index);
    }
    public void undoItem(InfoCustomer infoCustomer, int index,String name){
        arrayList.add(index,infoCustomer);
        reference.child(name).setValue(infoCustomer);
        notifyItemInserted(index);
    }
}
