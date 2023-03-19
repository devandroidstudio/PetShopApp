package com.example.appanimals.Admin.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appanimals.Activity.DetailActivity;
import com.example.appanimals.Admin.DetailScheduling;
import com.example.appanimals.Fragment.SearchFragment;
import com.example.appanimals.Model.Product;
import com.example.appanimals.Model.Scheduling;
import com.example.appanimals.R;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SchedulingAdapter extends RecyclerView.Adapter<SchedulingAdapter.SchedulingViewHolder> {
    private ArrayList<Scheduling> arrayList;
    private Context context;
    private IClickListener mIClickListener;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Scheduling");
    public interface IClickListener{
        void onClickShoData(Scheduling scheduling);
    }

    public SchedulingAdapter(ArrayList<Scheduling> arrayList, Context context, IClickListener mIClickListener) {
        this.arrayList = arrayList;
        this.context = context;
        this.mIClickListener = mIClickListener;
    }

    @NonNull
    @Override
    public SchedulingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scheduling,parent,false);
        return new SchedulingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SchedulingViewHolder holder, int position) {
        Scheduling scheduling = arrayList.get(position);
        if (scheduling == null){
            return;
        }
        holder.tvUserName.setText(scheduling.getNameCustomer());
        holder.tvAnimal.append(scheduling.getName());
        holder.tvTime.setText(scheduling.getDate());
        holder.tvTime.setText(scheduling.getTime());
        holder.tvProcedure.setText(scheduling.getProcedure());
        holder.layoutForegroundScheduling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIClickListener.onClickShoData(scheduling);
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

    public class SchedulingViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserName,tvAnimal,tvProcedure,tvDate,tvTime;
        MaterialCardView layoutForegroundScheduling;
        public SchedulingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tv_name_customer_scheduling_admin);
            tvAnimal = itemView.findViewById(R.id.tv_name_animal_scheduling_admin);
            tvProcedure = itemView.findViewById(R.id.tv_procedure_scheduling_admin);
            tvDate = itemView.findViewById(R.id.tv_date_scheduling_admin);
            tvTime = itemView.findViewById(R.id.tv_time_scheduling_admin);
            layoutForegroundScheduling = itemView.findViewById(R.id.layout_foreground_scheduling_admin);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, DetailScheduling.class);
//                    intent.putExtra("Scheduling",arrayList.get(getAdapterPosition()));
//                    Pair[] pairs = new Pair[4];
//                    pairs[0] = new Pair<View,String>(tvAnimal,"name_scheduling_admin");
//                    pairs[1] = new Pair<View,String>(tvProcedure,"procedure_scheduling_trans");
//                    pairs[2] = new Pair<View,String>(tvDate,"date_scheduling_admin");
//                    pairs[3] = new Pair<View,String>(tvTime,"time_scheduling_admin");
//                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context,pairs);
//                    context.startActivity(intent,options.toBundle());
//                }
//            });
        }
    }
    public void release(){
        context = null;
    }

    public void removeItem(int index, String name){
        arrayList.remove(index);
        reference.child(name).removeValue();
        notifyItemRemoved(index);
    }
    public void undoItem(Scheduling scheduling, int index,String name){
        arrayList.add(index,scheduling);
        reference.child(name).setValue(scheduling);
        notifyItemInserted(index);
    }

}
