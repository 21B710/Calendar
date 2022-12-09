package jp.ac.shohoku.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ContentActivity extends AppCompatActivity {
    private String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        TextView dateView = findViewById(R.id.date);

        Intent intent = getIntent();
        currentDate = intent.getStringExtra("date");
        dateView.setText(currentDate);
    }
}
