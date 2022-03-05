package com.uas.spisokbolnits;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private Object Tag;

    private List<ClassRs> clist;
    public Activity activity;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();



    public RecyclerAdapter(List<ClassRs> clist, Activity activity) {
        this.clist = clist;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.card_view_data, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull  RecyclerAdapter.MyViewHolder holder, int position) {
        ClassRs cData = clist.get(position);
        holder.nama.setText(cData.getNamaRs());
        holder.no.setText(cData.getNoRs());
        holder.alamat.setText(cData.getAlamatRs());
        holder.waktu.setText(cData.getWaktuRs());
//        holder.maps.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Uri addresUri = Uri.parse("geo:0,0?q="+holder.alamat.getText().toString());
//                Intent intent = new Intent(Intent.ACTION_VIEW, addresUri);
//                intent.setPackage("com.google.android.apps.maps");
//                if(intent.resolveActivity(activity.getPackageManager()) != null){
//                    activity.startActivity(intent);
//                }else {
//                    Log.d(String.valueOf(Tag),"LokasiRS");
//                }
//            }
//        });

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, DetailActivity.class);
                intent.putExtra("nama", cData.getNamaRs());
                intent.putExtra("no", cData.getNoRs());
                intent.putExtra("alamat", cData.getAlamatRs());
                intent.putExtra("waktu", cData.getWaktuRs());
                activity.startActivity(intent);
            }
        });

        holder.hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference.child("Pesanan").child(cData.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(activity, "Data "+cData.getNamaRs()+" Barhasil Dihapus", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Toast.makeText(activity, "Data Gagal Dihapus", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }).setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setMessage("Apakah Yakin Ingin Menghapus Data "+"'"+cData.getNamaRs()+"'");
                builder.show();
            }
        });
        holder.card_view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder jendelapesan = new AlertDialog.Builder(activity);
                jendelapesan.setMessage("Apakah Anda Ingin Mengubah Data ?");
                jendelapesan.setCancelable(true);

                jendelapesan.setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FragmentManager manager = ((AppCompatActivity)activity).getSupportFragmentManager();
                        DialogForm dialogForm = new DialogForm(
                                cData.getNamaRs(),
                                cData.getAlamatRs(),
                                cData.getNoRs(),
                                cData.getWaktuRs(),
                                cData.getKey(),
                                "Ubah");
                        dialogForm.show(manager, "form");
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                jendelapesan.show();
                return true;
//
//
//                FragmentManager manager = ((AppCompatActivity)activity).getSupportFragmentManager();
//                DialogForm dialogForm = new DialogForm(
//                        cData.getNama_pemesan(),
//                        cData.getNama_barang(),
//                        cData.getAlamat(),
//                        cData.getJumlah(),
//                        cData.getKey(),
//                        "Ubah");
//
//                dialogForm.show(manager, "form");
//                return true;
            }
        });
        return;

    }

    @Override
    public int getItemCount() {
        return (clist == null) ? 0 : clist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nama, no, alamat, waktu;
        CardView card_view;
        ImageView icon_tlp;
        ImageView hapus;
        Button maps;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.nama);
            no = itemView.findViewById(R.id.noRs);
            alamat = itemView.findViewById(R.id.alamat);
            waktu = itemView.findViewById(R.id.waktu);
            card_view = itemView.findViewById(R.id.cardview_data);
//            icon_tlp = itemView.findViewById(R.id.icon_tlp);
            hapus = itemView.findViewById(R.id.icon_hapus);
//            maps = itemView.findViewById(R.id.icon_maps);
        }
//        public void carimaps(View view){
//            Uri addresUri = Uri.parse("geo:0,0?q="+alamat.getText().toString());
//            Intent intent = new Intent(Intent.ACTION_VIEW, addresUri);
//            intent.setPackage("com.google.android.apps.maps");
//            if(intent.resolveActivity(activity.getPackageManager()) != null){
//                activity.startActivity(intent);
//            }else {
//                Log.d(String.valueOf(Tag),"LokasiRS");
//            }
//
//        }

    }
}
