package com.example.crdufirebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

   // private EditText edName,edGender,edEmail,edroll;
   RecyclerView recview;
    myadapter adapter;
    FloatingActionButton fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Search here..");

        recview=(RecyclerView)findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("students"), model.class)
                        .build();

        adapter=new myadapter(options);
        recview.setAdapter(adapter);

        fb=(FloatingActionButton)findViewById(R.id.fadd);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddData.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.search_menu,menu);

        MenuItem item=menu.findItem(R.id.search);

        SearchView searchView=(SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s) {

                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void processsearch(String s)
    {
        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("students").orderByChild("course").startAt(s).endAt(s+"\uf8ff"), model.class)
                        .build();

        adapter=new myadapter(options);
        adapter.startListening();
        recview.setAdapter(adapter);

    }

//    public void process(View view) {
//        edroll = findViewById(R.id.edRoll);
//        edName = findViewById(R.id.edName);
//        edGender = findViewById(R.id.edGender);
//        edEmail = findViewById(R.id.edEmail);
//
//       String roll = edroll.getText().toString().trim();
//        String name = edName.getText().toString().trim();
//        String gender = edGender.getText().toString().trim();
//        String email = edEmail.getText().toString().trim();
//
//        dataHolder obj =new dataHolder(name,gender,email) ;
//
//        FirebaseDatabase  db = FirebaseDatabase.getInstance();
//        DatabaseReference root = db.getReference("user");
//
//        root.child(roll).setValue(obj);
//
//        edroll.setText("");
//        edName.setText("");
//        edGender.setText("");
//        edEmail.setText("");
//
//        Toast.makeText(getApplicationContext(),"Insert Success",Toast.LENGTH_SHORT).show();
//
//
////        root.setValue(edName.getText().toString());
////        edName.setText("");
////        Toast.makeText(getApplicationContext(),"Add Success ",Toast.LENGTH_SHORT).show();
//    }
}