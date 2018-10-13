package com.mhacks11app.mhacks11app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;


public class LocationCreatePage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_create_page);

        Button doneButton = findViewById(R.id.done_button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gatherInputs();
            }
        });
    }

    private void gatherInputs() {
        // Reference checkboxes and do stuff
        CheckBox busyCheckbox = findViewById(R.id.busy_Check);
        boolean isBusy = busyCheckbox.isChecked();

        // Do this for all inputs
        Toast.makeText(this, "Busy checked: " + isBusy, Toast.LENGTH_SHORT).show();
    }
}
