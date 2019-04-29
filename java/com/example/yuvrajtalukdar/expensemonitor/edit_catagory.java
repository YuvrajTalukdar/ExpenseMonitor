package com.example.yuvrajtalukdar.expensemonitor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class edit_catagory extends AppCompatActivity {
    int add_button_action_status = 0;
    String category_name = "";
    String original_text = "";
    private SQLite_Handler sqlh = new SQLite_Handler(this, null);

    class C03081 implements OnClickListener {
        C03081() {
        }

        public void onClick(View v) {
            if (edit_catagory.this.add_button_action_status == 0) {
                edit_catagory.this.add(v);
            } else if (edit_catagory.this.add_button_action_status == 1) {
                edit_catagory.this.edit();
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0302R.layout.activity_edit_catagory);
        setRequestedOrientation(1);
        this.category_name = getIntent().getStringExtra("category_name");
        ActionBar toolbar = getSupportActionBar();
        toolbar.setDisplayHomeAsUpEnabled(true);
        toolbar.setDisplayShowHomeEnabled(true);
        toolbar.setTitle((CharSequence) "Edit category list");
        EditText edittext = (EditText) findViewById(C0302R.id.editText);
        edittext.setText("");
        Button save_button = (Button) findViewById(C0302R.id.button1);
        if (!this.category_name.equals("")) {
            save_button.setText("Edit");
            String s = "";
            System.out.println("");
            edittext.setText(this.category_name);
            this.original_text += this.category_name;
            this.category_name = "";
            this.add_button_action_status = 1;
        }
        save_button.setOnClickListener(new C03081());
    }

    public void edit() {
        String new_text = ((EditText) findViewById(C0302R.id.editText)).getText().toString();
        this.sqlh.delete_category(this.original_text);
        this.sqlh.add_category(new_text);
        send_data_and_go_back("", 2);
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

    public void add(View view) {
        send_data_and_go_back(((EditText) findViewById(C0302R.id.editText)).getText().toString(), 0);
    }

    public void remove(View view) {
        send_data_and_go_back(((EditText) findViewById(C0302R.id.editText)).getText().toString(), 1);
    }

    public void close(View view) {
        finish();
    }

    public void send_data_and_go_back(String content, int action_sino) {
        Intent i = new Intent();
        i.putExtra("content", content);
        i.putExtra("action_sino", action_sino);
        setResult(-1, i);
        finish();
    }
}
