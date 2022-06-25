package com.example.app_book.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_book.Detail_LichHen;
import com.example.app_book.Model.Model_LichKhamUS;
import com.example.app_book.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class Adapter_lichkhamDT extends RecyclerView.Adapter<Adapter_lichkhamDT.Myholder> {
    Context context;
    String iddt;
    List<Model_LichKhamUS> userList;
    int countcmt = 0;
    private DatabaseReference benhanh;
    private DatabaseReference user;
    public Adapter_lichkhamDT(Context context, List<Model_LichKhamUS> userList) {
        this.context = context;
        this.userList = userList;
        benhanh = FirebaseDatabase.getInstance().getReference("Patient");

    }
    @NonNull
    @Override
    public Adapter_lichkhamDT.Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lichkham_of_us, parent,false);

        return new Adapter_lichkhamDT.Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_lichkhamDT.Myholder holder, int i) {
        Model_LichKhamUS Model_LichKhamUS = userList.get(i);
        if (Model_LichKhamUS == null){
            return;
        }
        //get data
        iddt = userList.get(i).getIddoctor();
        String idus = userList.get(i).getUid();
        String tinhtrang = userList.get(i).getTrangthai();
        holder.tvv1.setText("Lịch Hẹn: "+userList.get(i).getNgaykham());
        holder.tvv2.setText("Vào Lúc: "+userList.get(i).getThoiGian() + " / "+ userList.get(i).getBuoi());
        holder.tvv3.setText("Tên Hồ Sơ: "+userList.get(i).getTenHoSo());
        holder.tvv5.setText("Mô Tả: "+userList.get(i).getMoTa());
        if(tinhtrang.equals("old")){
            holder.huy.setVisibility(View.GONE);
            holder.chitiet.setVisibility(View.GONE);
            holder.dakham.setVisibility(View.VISIBLE);
            holder.dakham.setText("Đã Khám");
        }
        holder.chitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tinhtrang.equals("new")){
                    Intent intent = new Intent(context, Detail_LichHen.class);
                    intent.putExtra("Mahoso", userList.get(i).getMaHoSo());
                    intent.putExtra("idoctor",userList.get(i).getIddoctor());
                    intent.putExtra("iduser",userList.get(i).getUid());
                    intent.putExtra("malichkhamDT",userList.get(i).getMaLichKhamofDT());
                    intent.putExtra("maLichen",userList.get(i).getMaLichKhamofUS());
                    intent.putExtra("key","doctor");
                    context.startActivity(intent);
                }

            }
        });
        holder.huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imae = userList.get(i).getHinhanh();
                String uid = userList.get(i).getMaLichKhamofUS();
                begindelete(uid, imae);
            }
        });
        holder.tvv4.setText("Địa Chỉ: Tại phòng khám của bạn");
        final Query ref = benhanh.orderByChild("uid").equalTo(idus);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    String aa = ""+ ds.child("Name").getValue().toString();
                    String bbb = ""+ ds.child("diachi").getValue().toString();
                    String ccc = ""+ ds.child("STD").getValue().toString();
                    String imagedb = ""+ ds.child("avatar").getValue().toString();

                    holder.tvv6.setText("Patient: "+ aa);
                    holder.tvv7.setText("SĐT: "+ ccc);

                    try {
                        Picasso.get().load(imagedb).into(holder.vatta);
                    }catch (Exception e){
                        Picasso.get().load(R.drawable.hi).placeholder(R.drawable.hi).into(holder.vatta);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void begindelete(String pid, String imae) {
        if (imae.equals("null")){
            DeletewithNoimg(pid, imae);
        }else{
            Deletewithimg(pid, imae);
        }


    }

    private void Deletewithimg(String pid, String imae) {
        ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Đang Xoá...");
        pd.show();
        StorageReference picref = FirebaseStorage.getInstance().getReferenceFromUrl(imae);
        picref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Query fquery = FirebaseDatabase.getInstance().getReference("LichHenchitiet").orderByChild("MaLichKhamofUS").equalTo(pid);
                fquery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds: snapshot.getChildren()){
                            ds.getRef().removeValue();
                        }

//                        Toast.makeText(context, "Xoá thành công.", Toast.LENGTH_SHORT).show();
//                        pd.dismiss();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(context, "Xoá thất bại.", Toast.LENGTH_SHORT).show();
//                        pd.dismiss();
                    }
                });
                Query fquery1 = FirebaseDatabase.getInstance().getReference("SoLuongĐKy").orderByChild("MaLichKhamofUS").equalTo(pid);
                fquery1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds: snapshot.getChildren()){
                            ds.getRef().removeValue();
                            String idtake = ""+ ds.child("iduser").getValue().toString();
                            String timestamp = String.valueOf(System.currentTimeMillis());
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("idsender", iddt);
                            hashMap.put("idTake", idtake);
                            hashMap.put("Ma_Noti", timestamp);
                            hashMap.put("title", "Thông báo hủy lịch hẹn ");
                            hashMap.put("ippost", " ");
                            hashMap.put("type", "1");
                            hashMap.put("content", "Vừa có một lịch hẹn bị hủy");
                            DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("Notification");
                            ref3.child(timestamp).setValue(hashMap);
                        }

                        Toast.makeText(context, "Xoá thành công.", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(context, "Xoá thất bại.", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                });

            }
        });
    }

    private void DeletewithNoimg(String pid, String imae) {
        ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Đang Xoá...");
        pd.show();
        Query fquery = FirebaseDatabase.getInstance().getReference("LichHenchitiet").orderByChild("MaLichKhamofUS").equalTo(pid);
        fquery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    ds.getRef().removeValue();
                }
//                Toast.makeText(context, "Xoá thành công.", Toast.LENGTH_SHORT).show();
//                pd.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(context, "Xoá thất bại.", Toast.LENGTH_SHORT).show();
//                ProgressDialog pd = new ProgressDialog(context);
//                pd.setMessage("Đang Xoá...");
//                pd.dismiss();
            }
        });
        Query fquery1 = FirebaseDatabase.getInstance().getReference("SoLuongĐKy").orderByChild("MaLichKhamofUS").equalTo(pid);
        fquery1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    ds.getRef().removeValue();
                    String idtake = ""+ ds.child("iduser").getValue().toString();
                    String timestamp = String.valueOf(System.currentTimeMillis());
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("idsender", iddt);
                    hashMap.put("idTake", idtake);
                    hashMap.put("Ma_Noti", timestamp);
                    hashMap.put("title", "Thông báo hủy lịch hẹn ");
                    hashMap.put("ippost", " ");
                    hashMap.put("type", "1");
                    hashMap.put("content", "Vừa có một lịch hẹn bị hủy");
                    DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("Notification");
                    ref3.child(timestamp).setValue(hashMap);
                }

                Toast.makeText(context, "Xoá thành công.", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Xoá thất bại.", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
    }
    @Override
    public int getItemCount() {

        if (userList != null){
            return userList.size();
        }

        return 0;
    }

    class Myholder extends RecyclerView.ViewHolder {
        Button huy, chitiet ;
        ImageView vatta;
        TextView tvv1, tvv2, tvv3,tvv4,tvv5,tvv6,tvv7,dakham;

        public Myholder(@NonNull View itemView) {
            super(itemView);
            tvv1 = itemView.findViewById(R.id.tvv1);
            tvv2 = itemView.findViewById(R.id.tvv2);
            tvv3 = itemView.findViewById(R.id.tvv3);
            tvv4 = itemView.findViewById(R.id.tvv4);
            tvv5 = itemView.findViewById(R.id.tvv5);
            tvv6 = itemView.findViewById(R.id.tvv6);
            tvv7 = itemView.findViewById(R.id.tvv7);
            huy = itemView.findViewById(R.id.huylichkham);
            chitiet = itemView.findViewById(R.id.follow);
            dakham = itemView.findViewById(R.id.follow2);
            vatta = itemView.findViewById(R.id.avatar_useruc);
        }
    }

}