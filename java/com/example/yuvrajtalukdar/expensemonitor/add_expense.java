package com.example.yuvrajtalukdar.expensemonitor;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.InterstitialAd;
import java.util.ArrayList;
import java.util.Calendar;

public class add_expense extends AppCompatActivity implements OnItemSelectedListener {
    private String category;
    ArrayAdapter<String> dataAdapter;
    private int day;
    private int id;
    private InterstitialAd mInterstitialAd;
    private int month;
    int signal_send_by_modify = 0;
    Spinner spinner1;
    SQLite_Handler sqlh = new SQLite_Handler(this, null);
    private int year;

    class C03031 implements OnClickListener {
        C03031() {
        }

        public void onClick(View v) {
            if (((Integer) v.getTag()).intValue() == 1) {
                add_expense.this.delete();
            } else {
                add_expense.this.close(v);
            }
        }
    }

    class C03042 implements OnClickListener {
        C03042() {
        }

        public void onClick(View v) {
            if (((Integer) v.getTag()).intValue() == 1) {
                add_expense.this.modify(v);
            } else {
                add_expense.this.add(v);
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0302R.layout.activity_add_expense);
        setRequestedOrientation(1);
        Intent i = getIntent();
        ArrayList category_list = new ArrayList();
        category_list = i.getStringArrayListExtra("category_list");
        int activity_mode = i.getIntExtra("activity_mode", 0);
        this.spinner1 = (Spinner) findViewById(C0302R.id.spinner);
        this.dataAdapter = new ArrayAdapter(this, 17367048, category_list);
        this.spinner1.setOnItemSelectedListener(this);
        this.dataAdapter.setDropDownViewResource(17367049);
        this.spinner1.setAdapter(this.dataAdapter);
        Button add_button = (Button) findViewById(C0302R.id.button3);
        Button close_button = (Button) findViewById(C0302R.id.button4);
        ActionBar toolbar;
        if (activity_mode == 1) {
            this.id = i.getIntExtra("record_id", 0);
            toolbar = getSupportActionBar();
            toolbar.setDisplayHomeAsUpEnabled(true);
            toolbar.setDisplayShowHomeEnabled(true);
            toolbar.setTitle((CharSequence) "Edit or Delete");
            add_button.setText("Save");
            add_button.setTag(Integer.valueOf(activity_mode));
            close_button.setText("Delete");
            close_button.setTag(Integer.valueOf(activity_mode));
            database_class obj = this.sqlh.get_data_according_to_id(this.id);
            this.day = obj.day;
            this.month = obj.month;
            this.year = obj.year;
            set_data_to_frame(obj);
        } else {
            toolbar = getSupportActionBar();
            toolbar.setDisplayHomeAsUpEnabled(true);
            toolbar.setDisplayShowHomeEnabled(true);
            toolbar.setTitle((CharSequence) "Add Expense");
            add_button.setText("Add");
            add_button.setTag(Integer.valueOf(activity_mode));
            close_button.setText("Close");
            close_button.setTag(Integer.valueOf(activity_mode));
        }
        close_button.setOnClickListener(new C03031());
        add_button.setOnClickListener(new C03042());
    }

    public void displayInterstitial() {
        if (this.mInterstitialAd.isLoaded()) {
            this.mInterstitialAd.show();
        }
    }

    public void set_data_to_frame(database_class obj) {
        EditText editItem = (EditText) findViewById(C0302R.id.item_edit);
        TextView dateView = (TextView) findViewById(C0302R.id.textView5);
        ((EditText) findViewById(C0302R.id.cost_edit)).setText(Float.toString(obj.cost));
        editItem.setText(obj.item);
        dateView.setText(obj.day + "/" + obj.month + "/" + obj.year);
        this.spinner1.setSelection(this.dataAdapter.getPosition(obj.category));
    }

    public void delete() {
        this.sqlh.delete_record(this.id);
        Toast.makeText(this, " record deleted ", 1).show();
        startActivity(new Intent(this, MainActivity.class));
    }

    public void modify(View v) {
        delete();
        this.signal_send_by_modify = 1;
        add(v);
        Toast.makeText(this, " record modified ", 1).show();
        startActivity(new Intent(this, MainActivity.class));
    }

    public void set_date(View view) {
        Calendar c = Calendar.getInstance();
        this.year = c.get(1);
        this.month = c.get(2);
        this.day = c.get(5);
        final TextView textView_cost = (TextView) findViewById(C0302R.id.textView5);
        new DatePickerDialog(this, new OnDateSetListener() {
            public void onDateSet(DatePicker view, int year1, int monthOfYear, int dayOfMonth) {
                add_expense.this.year = year1;
                add_expense.this.month = monthOfYear;
                add_expense.this.day = dayOfMonth;
                try {
                    textView_cost.setText(Integer.toString(add_expense.this.day) + "/" + Integer.toString(add_expense.this.month + 1) + "/" + Integer.toString(add_expense.this.year));
                } catch (Exception e) {
                    Log.i("ERROR", e.toString());
                }
            }
        }, this.year, this.month, this.day).show();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.category = parent.getItemAtPosition(position).toString();
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void add(View view) {
        EditText editText_cost = (EditText) findViewById(C0302R.id.cost_edit);
        String item = ((EditText) findViewById(C0302R.id.item_edit)).getText().toString();
        if (item.isEmpty()) {
            Toast.makeText(this, " item name is empty ", 0).show();
            return;
        }
        float cost;
        try {
            cost = Float.parseFloat(editText_cost.getText().toString());
        } catch (Exception e) {
            cost = 0.0f;
            Log.i("ERROR", e.toString());
        }
        Intent i = new Intent();
        if (this.year == 0) {
            Calendar c = Calendar.getInstance();
            this.day = c.get(5);
            this.month = c.get(2);
            this.year = c.get(1);
        }
        i.putExtra("cost", cost);
        i.putExtra("item", item);
        i.putExtra("category", this.category);
        i.putExtra("day", this.day);
        i.putExtra("month", this.month + 1);
        i.putExtra("year", this.year);
        setResult(-1, i);
        if (this.signal_send_by_modify == 1) {
            this.signal_send_by_modify = 0;
            this.sqlh.add_data(cost, item, this.category, this.day, this.month, this.year);
        }
        Toast.makeText(this, " added expense list", 1).show();
        finish();
    }

    public void close(View view) {
        onBackPressed();
    }
}
