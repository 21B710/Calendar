package jp.ac.shohoku.calendar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class ContentActivity extends AppCompatActivity{
    TextView disp;
    EditText addItem;
    EditText adddate;
    Button addBtn;
    Button updateBtn;
    Button deleteBtn;
    Button deleteAllBtn;
    Button showBtn;
    private String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        TextView dateView = findViewById(R.id.date);

        Intent intent = getIntent();
        currentDate = intent.getStringExtra("date");
        dateView.setText(currentDate);

        //部品の取得
        disp = (TextView) findViewById(R.id.disp);
        adddate = (EditText) findViewById(R.id.adddate);
        addItem = (EditText) findViewById(R.id.addItem);
        addBtn = (Button)findViewById(R.id.addBtn);
        updateBtn = (Button)findViewById(R.id.updateBtn);
        deleteBtn = (Button)findViewById(R.id.deleteBtn);
        deleteAllBtn = (Button)findViewById(R.id.deleteAllBtn);
        showBtn = (Button)findViewById(R.id.showBtn);

        //データを追加
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues val = new ContentValues();
                val.put("date" , adddate.getText().toString());
                val.put("item", addItem.getText().toString());
                db.insert("person", null, val);
            }
        });

        //データの更新
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //nameとageを取得
                String date = adddate.getText().toString();
                String item = addItem.getText().toString();

                //ContentValuesに値を入れる
                ContentValues val = new ContentValues();
                val.put("item", item);

                //データベースを更新
                db.update("person", val, "date=?", new String[]{item});
            }
        });

        //データの削除
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //持ち物を取得
                String date = adddate.getText().toString();

                //ContentValuesに値を入れる
                ContentValues val = new ContentValues();
                val.put("date", date);

                //データベースを更新
                db.delete("person", "item=?", new String[]{date});
            }
        });

        //データの全削除
        deleteAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.delete("person", null, null);
            }
        });

        //データを表示
        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TextViewをリセットする
                disp.setText(null);
                Cursor c = db.query("person", new String[]{"date", "item"}, null, null, null, null, null);
                boolean mov = c.moveToFirst();
                while(mov){
                    disp.append(c.getString(0) + ": " + c.getInt(1) + "\n");
                    mov = c.moveToNext();
                }
                c.close();
            }
        });

    }

    //データベースの生成
    MyDbHelper hlp = new MyDbHelper(this);
    final SQLiteDatabase db = hlp.getReadableDatabase(); //finalをつけるのに注意




}
