package com.example.app_book.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_book.Detail_Doctor_AD;
import com.example.app_book.Detail_LichHen;
import com.example.app_book.Model.Model_Notification;
import com.example.app_book.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Adapter_Notification extends RecyclerView.Adapter<Adapter_Notification.Myholder>{
        Context context;
        List<Model_Notification> modelNotifications;
private DatabaseReference user, noti;
        String myUid;
public Adapter_Notification(Context context, List<Model_Notification> modelNotifications) {
        this.context = context;
        this.modelNotifications = modelNotifications;
        myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        noti = FirebaseDatabase.getInstance().getReference("Notification");
        user = FirebaseDatabase.getInstance().getReference("Users");
        }

@NonNull
@Override
public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notification, parent,false);
        return new Myholder(view);
        }

@Override
public void onBindViewHolder(@NonNull Myholder holder, int position) {
        //get data
        String hisUID = modelNotifications.get(position).getIdsender();
        String content = modelNotifications.get(position).getContent();
        String time = modelNotifications.get(position).getMa_Noti();
        String idp = modelNotifications.get(position).getIppost();
        String Type = modelNotifications.get(position).getType();

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(time));
        String datetime = DateFormat.format("dd/MM/yyyy hh:mm aa", calendar).toString();
        holder.timetv.setText(datetime);
        holder.contenttv.setText(content);
        holder.title.setText(modelNotifications.get(position).getTitle());
        final Query ref = user.orderByChild("uid").equalTo(hisUID);
        ref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot ds: snapshot.getChildren()){
                String aa = ""+ ds.child("Name").getValue().toString();
                String imagedb = ""+ ds.child("avatar").getValue().toString();

                holder.nametv.setText(aa);
                try {
                    Picasso.get().load(imagedb).into(holder.avatar);
                }catch (Exception e){
                    Picasso.get().load(R.drawable.hi).placeholder(R.drawable.hi).into(holder.avatar);
                }

            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
      @Override
        public void onClick(View v) {
        delete(time);
        }
        });
        holder.ul.setOnClickListener(new View.OnClickListener() {
         @Override
        public void onClick(View v) {
//                Intent intent = new Intent(context, DetailsPostActivity.class);
//                intent.putExtra("postid", idp);
//                context.startActivity(intent);
                if(Type.equals("2")){
                    Intent intent = new Intent(context, Detail_LichHen.class);
                    intent.putExtra("Mahoso", modelNotifications.get(position).getMahoso());
                    intent.putExtra("idoctor",modelNotifications.get(position).getIdTake());
                    intent.putExtra("iduser", modelNotifications.get(position).getIdsender());
                    intent.putExtra("malichkhamDT", modelNotifications.get(position).getMaLichKhamofDT());
                    intent.putExtra("maLichen",modelNotifications.get(position).getMaLichKhamofUS());
                    intent.putExtra("key","doctor");
                    context.startActivity(intent);
                }else if(Type.equals("3")){
                    Intent intent = new Intent(context, Detail_Doctor_AD.class);
                    intent.putExtra("uid",modelNotifications.get(position).getIdsender());
                    intent.putExtra("key","kiemduyet" );
                    context.startActivity(intent);
                }
                else if(Type.equals("4")){
                    Intent intent = new Intent(context, Detail_LichHen.class);
                    intent.putExtra("Mahoso", modelNotifications.get(position).getMahoso());
                    intent.putExtra("idoctor",modelNotifications.get(position).getIdTake());
                    intent.putExtra("iduser", modelNotifications.get(position).getIdsender());
                    intent.putExtra("malichkhamDT", "null");
                    intent.putExtra("maLichen",modelNotifications.get(position).getMaLichKhamofUS());
                    intent.putExtra("key","doctor");
                    context.startActivity(intent);
                }
                }
            });
        }
        private void delete(String time) {
            noti.child(time).removeValue()
        .addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void aVoid) {
                Toast.makeText(context, "xoá thông báo thành công", Toast.LENGTH_SHORT).show();
                }
                }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
        Toast.makeText(context, "xoá thông báo thất bại", Toast.LENGTH_SHORT).show();

        }
        });

        }

@Override
public int getItemCount() {
        return modelNotifications.size();
        }

class Myholder extends RecyclerView.ViewHolder {
    ImageView avatar;
    ImageButton btn_delete;
    TextView nametv, contenttv, timetv, title;
    RelativeLayout ul;
    public Myholder(@NonNull View itemView) {
        super(itemView);
        avatar = itemView.findViewById(R.id.avatar_useruc);
        nametv = itemView.findViewById(R.id.name_noti);
        btn_delete = itemView.findViewById(R.id.delete);
        contenttv = itemView.findViewById(R.id.tvnoti);
        title = itemView.findViewById(R.id.title);

        timetv = itemView.findViewById(R.id.timenoti);
        ul = itemView.findViewById(R.id.ul);


    }
}
}