package com.example.android.animation_assignment4;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ChildReport extends AppCompatActivity {
    TableLayout tableLayout;

    DateBaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_report);

        mydb=new DateBaseHelper(this);
        Cursor cursor=mydb.getAllDataC();
        tableLayout=(TableLayout) findViewById(R.id.table_layout);
        TableRow rowCol=new TableRow(this);
        TextView t_idCol=new TextView(this);
        TextView t_usernameCol=new TextView(this);
        TextView t_levelCol=new TextView(this);
        TextView t_stageCol=new TextView(this);
        t_idCol.setText("ID");
        t_usernameCol.setText("Username");
        t_levelCol.setText("Level");
        t_stageCol.setText("Stage");
        t_idCol.setWidth(150);
        t_usernameCol.setWidth(150);
        t_levelCol.setWidth(150);
        t_stageCol.setWidth(150);

        t_idCol.setBackgroundColor(Color.CYAN);
        t_usernameCol.setBackgroundColor(Color.CYAN);
        t_levelCol.setBackgroundColor(Color.CYAN);
        t_stageCol.setBackgroundColor(Color.CYAN);
        rowCol.addView(t_idCol);
        rowCol.addView(t_usernameCol);
        rowCol.addView(t_levelCol);
        rowCol.addView(t_stageCol);
        tableLayout.addView(rowCol);


        String id;
        String username;
        int level;
        int stage;
        while (cursor.moveToNext()){
            TableRow row=new TableRow(this);
            TextView t_id=new TextView(this);
            TextView t_username=new TextView(this);
            TextView t_level=new TextView(this);
            TextView t_stage=new TextView(this);
            t_id.setWidth(150);
            t_username.setWidth(150);
            t_level.setWidth(150);
            t_stage.setWidth(150);

            t_id.setBackgroundColor(Color.GRAY);
            t_username.setBackgroundColor(Color.WHITE);
            t_level.setBackgroundColor(Color.GRAY);
            t_stage.setBackgroundColor(Color.WHITE);

            id=cursor.getString(0);
            username=cursor.getString(1);
            level=cursor.getInt(3);
            stage=cursor.getInt(4);
            t_id.setText(id);
            t_username.setText(username);
            t_level.setText(level+"");
            t_stage.setText(stage+"");
            row.addView(t_id);
            row.addView(t_username);
            row.addView(t_level);
            row.addView(t_stage);
            tableLayout.addView(row);
        }

    }

}
