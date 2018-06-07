package com.example.alialzantot.movieapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Person c1 = new Person(0,"Oreo", null);
        Person c2 = new Person(0,"Oreo", null);
        Person c3 = new Person(0,"Oreo", null);
        Person c4 = new Person(0,"Oreo", null);
        Person c5 = new Person(0,"Oreo", null);
        Person c6 = new Person(0,"Oreo", null);
        final Person[] personsArray =  {c1,c2,c3,c4,c5,c6,c1,c2,c3,c4,c5,c6};
        CustomAdapter customAdapter=new CustomAdapter(this,R.layout.single_row,R.id.name,personsArray);
        ListView listView=findViewById(R.id.myList);
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                
            }
        });

    }
}
