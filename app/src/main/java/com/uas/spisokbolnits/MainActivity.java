package com.uas.spisokbolnits;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerAdapter recyclerAdapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference dbRS = FirebaseDatabase.getInstance().getReference();
    private ArrayList<ClassRs> listData;
    private ProgressBar pbData;
    private RecyclerView rv_view;
    FloatingActionButton add;
    private SearchView SearchData;
    private TextView DataNulli;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv_view = findViewById(R.id.rv_view);
        pbData = findViewById(R.id.pb_data);
        SearchData = findViewById(R.id.SearchDataa);
        DataNulli = findViewById(R.id.DataNull);

        add = findViewById(R.id.fab_add);
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        dbRS = firebaseDatabase.getReference("RumahSakit");

        RecyclerView.LayoutManager mLayout = new LinearLayoutManager(this);
        rv_view.setLayoutManager(mLayout);
        rv_view.setItemAnimator(new DefaultItemAnimator());
        Dialog dialog = new Dialog(this);

//        listData = new ArrayList<>();
//        recyclerAdapter = new RecyclerAdapter(listData, this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogForm dialog = new DialogForm("", "", "", "","", "Tambah");

                dialog.show(getSupportFragmentManager(), "form");
            }
        });
        showData();
    }


    private void showData(){

        if (dbRS == null){
            pbData.setVisibility(View.GONE);
            DataNulli.setVisibility(View.VISIBLE);
            rv_view.setVisibility(View.INVISIBLE);
        }
        if(dbRS != null){
            dbRS.child("Pesanan").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                    listData.clear();
                    listData = new ArrayList<>();
                    for (DataSnapshot item : snapshot.getChildren()){
//                        keys.add(item.getKey());
                        ClassRs data = item.getValue(ClassRs.class);
                        data.setKey(item.getKey());
                        listData.add(data);

                        pbData.setVisibility(View.INVISIBLE);
                        DataNulli.setVisibility(View.INVISIBLE);
                        rv_view.setVisibility(View.VISIBLE);
                        rv_view.setTranslationY(1000);
                        rv_view.animate().translationY(0).alpha(1).setDuration(1200).setStartDelay(400).start();
                    }
//
                    recyclerAdapter = new RecyclerAdapter(listData, MainActivity.this);
                    rv_view.setAdapter(recyclerAdapter);
//                    recyclerAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    pbData.setVisibility(View.INVISIBLE);
                }
            });
        }
        if (SearchData != null){
            SearchData.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    search(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);
                    return true;
                }
            });
        }
    }

    private void search(String s) {
        ArrayList<ClassRs> cList = new ArrayList<>();
        for (ClassRs object : listData)
        {
            if (object.getNamaRs().toLowerCase().contains(s.toLowerCase()))
            {
                cList.add(object);
            }
        }
        RecyclerAdapter rc = new RecyclerAdapter(cList, MainActivity.this);
        rv_view.setAdapter(rc);
    }
}