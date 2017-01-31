package org.fiole.mapofthings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.fiole.mapofthings.adapters.MarkerGridImageAdapter;

public class ChooseMarkerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_marker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fillMarkers();


    }

    private void fillMarkers() {
        GridView gridView = (GridView) findViewById(R.id.choose_marker_grid);
        gridView.setAdapter(new MarkerGridImageAdapter(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView parent, View v, int position, long id)
            {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("markerId", position + 1);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }


}
