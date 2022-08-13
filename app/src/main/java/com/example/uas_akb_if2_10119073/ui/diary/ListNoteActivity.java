package com.example.uas_akb_if2_10119073.ui.diary;
/*
    NIM  : 10119073
    Nama : Aghnia Dewi Mahiranie
    Kelas: IF-2
 */
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.uas_akb_if2_10119073.R;
import com.example.uas_akb_if2_10119073.model.DBHelper;
import com.example.uas_akb_if2_10119073.ui.about.ViewPagerActivity;
import com.example.uas_akb_if2_10119073.ui.profile.ProfileActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListNoteActivity extends AppCompatActivity {

    String[] daftar, daftar2, daftar3, daftar4, daftar5, id;
    ListView listView;
    int kategori;
    private Cursor cursor;
    DBHelper dbHelper;
    public static ListNoteActivity ln;

    //Date dt = new Date();
    //SimpleDateFormat date = new SimpleDateFormat("dd-MMM-yyyy");
    //SimpleDateFormat time = new SimpleDateFormat("HH:mm a");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_note);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.CatatanHarian);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ProfileId:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.CatatanHarian:
                        startActivity(new Intent(getApplicationContext(), CategoryActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.About:
                        startActivity(new Intent(getApplicationContext(), ViewPagerActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        Intent getKategori = getIntent();
        kategori = getKategori.getIntExtra("kategori", 0);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListNoteActivity.this, NotesActivity.class);
                intent.putExtra("kategori", kategori);
                startActivity(intent);
            }
        });

        ln = this;
        dbHelper = new DBHelper(this);
        RefreshListNote();
    }

    public void RefreshListNote() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM note WHERE id_kategori = "+kategori, null);
        id = new String[cursor.getCount()];
        daftar = new String[cursor.getCount()];
        daftar2 = new String[cursor.getCount()];
        daftar3 = new String[cursor.getCount()];
        daftar4 = new String[cursor.getCount()];
        daftar5 = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int cc=0; cc < cursor.getCount(); cc++){
            cursor.moveToPosition(cc);
            id[cc] = cursor.getString(0).toString();
            daftar[cc] = cursor.getString(1).toString();
            daftar2[cc] = cursor.getString(2).toString();
            daftar3[cc] = cursor.getString(3).toString();
            daftar4[cc] = cursor.getString(4).toString();
            daftar5[cc] = cursor.getString(5).toString();
        }

        listView = findViewById(R.id.listNote);

        MyAdapter adapter = new MyAdapter();
        listView.setAdapter(adapter);
        listView.setSelected(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final int selection = Integer.parseInt(id[arg2]);
                Intent in = new Intent(getApplicationContext(), UpdateActivity.class);
                in.putExtra("id", selection);
                startActivity(in);
            }});

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id2) {
                final int which_item = position;

                AlertDialog.Builder builder = new AlertDialog.Builder(ListNoteActivity.this);
                builder.setTitle("Delete Note");
                builder.setMessage("Yakin menghapus note ini?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.execSQL("delete from note where id ="+ id[position]);
                        RefreshListNote();
                    }
                });
                builder.setNegativeButton("No", null);
                builder.create().show();
                return true;
            }
        });
    }

    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return cursor.getCount();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.item_notes, parent, false);

            TextView noteTitle = convertView.findViewById(R.id.text_title);
            TextView noteDesc = convertView.findViewById(R.id.text_content);
            TextView noteDate = convertView.findViewById(R.id.text_date);
            TextView noteTime = convertView.findViewById(R.id.text_time);

            noteDate.setText(daftar[position]);
            noteTime.setText(daftar2[position]);
            noteTitle.setText(daftar3[position]);
            noteDesc.setText(daftar4[position]);

            return convertView;
        }
    }
}