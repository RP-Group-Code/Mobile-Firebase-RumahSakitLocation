package com.uas.spisokbolnits;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    TextView nama,alamat,no,waktu;
    ImageView btncall;
    private Object Tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        nama = findViewById(R.id.name);
        alamat = findViewById(R.id.Alamat);
        no = findViewById(R.id.nod);
        waktu = findViewById(R.id.waktursd);
        btncall = findViewById(R.id.tlpRs);

        btncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+no.getText().toString()));
                startActivity(intent);
            }
        });

        String namaRS = getIntent().getStringExtra("nama");
        String alamatRS = getIntent().getStringExtra("alamat");
        String noRS = getIntent().getStringExtra("no");
        String waktursd = getIntent().getStringExtra("waktu");

        nama.setText(namaRS);
        alamat.setText(alamatRS);
        no.setText(noRS);
        waktu.setText(waktursd);
    }

    public void maps(View view) {
        Uri addresUri = Uri.parse("geo:0,0?q="+nama.getText().toString());
        Intent intent = new Intent(Intent.ACTION_VIEW, addresUri);
        intent.setPackage("com.google.android.apps.maps");

        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }else {
            Log.d(String.valueOf(Tag),"OpenLocation");
        }
    }
}