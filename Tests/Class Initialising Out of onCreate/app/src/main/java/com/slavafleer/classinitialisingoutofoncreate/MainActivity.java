package com.slavafleer.classinitialisingoutofoncreate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Data data = new Data(2, "test");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, data.num + " " + data.text, Toast.LENGTH_LONG).show();
    }

    private class Data {

        private int num;
        private String text;

        public Data() {}

        public Data(int num, String text) {

            this.num = num;
            this.text = text;
        }
    }
}
