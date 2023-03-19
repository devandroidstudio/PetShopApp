package com.example.appanimals.Admin.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appanimals.Model.Account;
import com.example.appanimals.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DetailProfileAdapter extends RecyclerView.Adapter<DetailProfileAdapter.DetailProfileViewHolder> {
    private ArrayList<Account> arrayList;
    private Context context;
    private IClickShowDataProfile mIClickShowDataProfile;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Account");
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public interface IClickShowDataProfile{
        void onClickShoDataProfile(Account account);
        void onClickResetPassword(Account account);
    }

    public DetailProfileAdapter(ArrayList<Account> arrayList, Context context, IClickShowDataProfile mIClickShowDataProfile) {
        this.arrayList = arrayList;
        this.context = context;
        this.mIClickShowDataProfile = mIClickShowDataProfile;
    }

    @NonNull
    @Override
    public DetailProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile,parent,false);
        return new DetailProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailProfileViewHolder holder, int position) {
        Account account = arrayList.get(position);
        if (account == null){
            return;
        }
        holder.tvRole.setText("Username: "+account.getRole());
        holder.tvFullName.setText("FullName: "+account.getFullName());
        holder.tvEmail.setText("Email: "+account.getEmail());
        holder.tvEmail.setMaxLines(1);
        holder.tvEmail.setEllipsize(TextUtils.TruncateAt.END);
        holder.aSwitch.setChecked(false);
        holder.materialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIClickShowDataProfile.onClickShoDataProfile(account);
            }
        });
        if (account.getStatus().equals("On"))
        {
            holder.aSwitch.setChecked(true);
        }
        holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked){
                    account.setStatus("Off");
                    reference.child(account.getFullName()).updateChildren(account.UpdateAccount()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(context, "Account be changed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context,holder.imageButton);
                popupMenu.getMenuInflater().inflate(R.menu.menu_popup,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId())
                        {
                            case R.id.reset_password:
                                mIClickShowDataProfile.onClickResetPassword(account);
                                break;
                            case R.id.disable_account:

                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }
    public void release(){
        context = null;
    }
    @Override
    public int getItemCount() {
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    public class DetailProfileViewHolder extends RecyclerView.ViewHolder {
        TextView tvRole,tvFullName,tvEmail;
        MaterialCardView materialCardView;
        ImageButton imageButton;
        SwitchCompat aSwitch;
        public DetailProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRole = itemView.findViewById(R.id.tv_role_profile_admin);
            tvFullName = itemView.findViewById(R.id.tv_name_profile_admin);
            tvEmail = itemView.findViewById(R.id.tv_email_profile_admin);
            materialCardView = itemView.findViewById(R.id.layout_detail_profile);
            imageButton = itemView.findViewById(R.id.image_btn_detail_profile);
            aSwitch = itemView.findViewById(R.id.switch_profile);
        }
    }

    public void removeItem(int index, @NonNull String name){
        if (name.equals(user.getDisplayName()))
        {
            Toast.makeText(context, "You do not delete yourself", Toast.LENGTH_SHORT).show();
        }
        arrayList.remove(index);
        reference.child(name).removeValue();
        notifyItemRemoved(index);
    }
    public void undoItem(Account infoCustomer, int index, String name){
        arrayList.add(index,infoCustomer);
        reference.child(name).setValue(infoCustomer);
        notifyItemInserted(index);
    }
}
