package jp.ac.shohoku.calendar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText addName;
    EditText addAge;
    Button addBtn;
    Button updateBtn;
    Button deleteBtn;
    Button deleteAllBtn;
    Button showBtn;

    TextView disp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //部品の取得
        addName = (EditText)findViewById(R.id.addDate);
        addAge = (EditText)findViewById(R.id.addItem);
        addBtn = (Button)findViewById(R.id.addBtn);
        updateBtn = (Button)findViewById(R.id.updateBtn);
        deleteBtn = (Button)findViewById(R.id.deleteBtn);
        deleteAllBtn = (Button)findViewById(R.id.deleteAllBtn);
        showBtn = (Button)findViewById(R.id.showBtn);
        disp = (TextView)findViewById(R.id.disp);

        //データベースの生成
        MyDbHelper hlp = new MyDbHelper(this);
        final SQLiteDatabase db = hlp.getReadableDatabase(); //finalをつけるのに注意

        //データを追加
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues val = new ContentValues();
                val.put("name", addName.getText().toString());
                val.put("age", addAge.getText().toString());
                db.insert("person", null, val);
            }
        });

        //データの更新
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //nameとageを取得
                String name = addName.getText().toString();
                String age = addAge.getText().toString();

                //ContentValuesに値を入れる
                ContentValues val = new ContentValues();
                val.put("age", age);

                //データベースを更新
                db.update("person", val, "name=?", new String[]{name});
            }
        });

        //データの削除
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //名前を取得
                String name = addName.getText().toString();

                //ContentValuesに値を入れる
                ContentValues val = new ContentValues();
                val.put("name", name);

                //データベースを更新
                db.delete("person", "name=?", new String[]{name});
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
                Cursor c = db.query("person", new String[]{"name", "age"}, null, null, null, null, null);
                boolean mov = c.moveToFirst();
                while(mov){
                    disp.append(c.getString(0) + ": " + c.getInt(1) + "\n");
                    mov = c.moveToNext();
                }
                c.close();
            }
        });
        @Override
        public void onItemClick Object AdapterView;
        Object View;
        (AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getApplicationContext(), ContentActivity.class);
            intent.putExtra("date", mCalendarAdapter.getItem(position).toString());
            startActivity(intent);
        }
    }
}