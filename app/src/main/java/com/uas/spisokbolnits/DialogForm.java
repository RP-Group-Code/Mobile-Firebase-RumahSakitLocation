package com.uas.spisokbolnits;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DialogForm extends DialogFragment {
    private Object Tag;
    String NamaRs;
    String AlamatRs;
    String NoRs;
    String WaktuRs;
    String Pilih;
    String Key;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public DialogForm(String namaRs, String alamatRs, String noRs, String waktuRs, String key, String pilih) {
        NamaRs = namaRs;
        AlamatRs = alamatRs;
        NoRs = noRs;
        WaktuRs = waktuRs;
        Key = key;
        Pilih = pilih;
    }

    EditText Nama;
    EditText Alamat;
    EditText notlp;
    EditText waktuRs;

    Button btnsimpan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final  View view = inflater.inflate(R.layout.input_data, container, false);

        Nama = view.findViewById(R.id.et_nama);
        Alamat = view.findViewById(R.id.et_alamat);
        notlp = view.findViewById(R.id.et_no);
        waktuRs = view.findViewById(R.id.et_waktu);
        btnsimpan = view.findViewById(R.id.btn_input);

        Nama.setText(NamaRs);
        Alamat.setText(AlamatRs);
        notlp.setText(NoRs);
        waktuRs.setText(WaktuRs);

        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogForm dialog = new DialogForm("", "", "", "","", "");

                String nama = Nama.getText().toString();
                String alamat = Alamat.getText().toString();
                String no = notlp.getText().toString();
                String waktu = waktuRs.getText().toString();



                if (TextUtils.isEmpty(nama)) {
                    input((EditText) Nama, "Nama");
                } else if (TextUtils.isEmpty(alamat)) {
                    input((EditText) Alamat, "Alamat");
                } else if (TextUtils.isEmpty(no)) {
                    input((EditText) notlp, "notlp");
                }else if (TextUtils.isEmpty(waktu)) {
                    input((EditText) waktuRs, "Waktu");
                }
                else {

                    if (Pilih.equals("Tambah")){
                        databaseReference.child("Pesanan").push().setValue(new ClassRs(nama, alamat, no, waktu)).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Nama.setText("");
                                Alamat.setText("");
                                notlp.setText("");
                                waktuRs.setText("");
                                Toast.makeText(view.getContext(), "Data "+Nama.getText()+" Tersimpan", Toast.LENGTH_LONG).show();
//                                dialog.dismiss();

//                                startActivity(new Intent(DialogForm.this, TampilDataJualActivity.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(view.getContext(), "Data Gagal Tersimpan", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else if (Pilih.equals("Ubah")){
                        databaseReference.child("Pesanan").child(Key).setValue(new ClassRs(nama, alamat, no, waktu)).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Nama.setText("");
                                Alamat.setText("");
                                notlp.setText("");
                                waktuRs.setText("");
                                Toast.makeText(view.getContext(), "Data berhasil diubah", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                            public void onFailure(@NonNull DialogInterface dialoog, Exception e) {
                                Toast.makeText(view.getContext(), "Data gagal diubah", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
        return view;
    }


    public void onStart(){
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null){
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    private void input (EditText txt, String s) {
        txt.setError(s + " Tidak Boleh Kosong");
        txt.requestFocus();
    }

}
