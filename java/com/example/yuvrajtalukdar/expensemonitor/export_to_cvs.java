package com.example.yuvrajtalukdar.expensemonitor;

import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.apps.yuvraj.expensemonitor.create_folder_dialogbox.OnMyDialogResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class export_to_cvs extends AppCompatActivity {
    public final String[] EXTERNAL_PERMS = new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"};
    public final int EXTERNAL_REQUEST = 138;
    private File curFile;
    public ArrayList<database_class> dbc_array_list = new ArrayList();
    private List<String> fileList = new ArrayList();
    private List<String> fileListDirectory = new ArrayList();
    private ListView file_explore;
    private int file_explorer_depth_degree = 0;
    private EditText file_name;
    private TextView folder_name;
    private Button new_folder;
    private File root;
    private Button save;
    private SQLite_Handler sqlh = new SQLite_Handler(this, null);
    private Button up;

    class C03091 implements OnItemClickListener {
        C03091() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            File selected = new File((String) export_to_cvs.this.fileListDirectory.get(position));
            if (selected.isDirectory()) {
                export_to_cvs.this.file_explorer_depth_degree = export_to_cvs.this.file_explorer_depth_degree + 1;
                export_to_cvs.this.ListDir(selected);
            }
        }
    }

    class C03102 implements OnClickListener {
        C03102() {
        }

        public void onClick(View v) {
            try {
                if (export_to_cvs.this.file_explorer_depth_degree > 0) {
                    export_to_cvs.this.file_explorer_depth_degree = export_to_cvs.this.file_explorer_depth_degree - 1;
                    export_to_cvs.this.ListDir(export_to_cvs.this.curFile.getParentFile());
                }
            } catch (Exception e) {
                Log.i("ERROR", "Cannot go further back");
            }
        }
    }

    class C03113 implements OnClickListener {
        C03113() {
        }

        public void onClick(View v) {
            export_to_cvs.this.export_database_to_csv(export_to_cvs.this.file_name.getText().toString());
            Toast.makeText(export_to_cvs.this, "Exported Successfully", 1).show();
            export_to_cvs.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0302R.layout.activity_export_to_cvs);
        setRequestedOrientation(1);
        ActionBar toolbar = getSupportActionBar();
        toolbar.setDisplayHomeAsUpEnabled(true);
        toolbar.setDisplayShowHomeEnabled(true);
        setTitle("Save To..");
        this.root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        this.curFile = this.root;
        this.folder_name = (TextView) findViewById(C0302R.id.Folder_Name);
        this.up = (Button) findViewById(C0302R.id.up);
        this.new_folder = (Button) findViewById(C0302R.id.new_folder);
        this.save = (Button) findViewById(C0302R.id.export_button);
        this.file_name = (EditText) findViewById(C0302R.id.csv_name);
        this.file_explore = (ListView) findViewById(C0302R.id.file_explorer);
        this.file_explore.setOnItemClickListener(new C03091());
        this.up.setOnClickListener(new C03102());
        this.save.setOnClickListener(new C03113());
        final Activity a = this;
        this.new_folder.setOnClickListener(new OnClickListener() {

            class C04211 implements OnMyDialogResult {
                C04211() {
                }

                public void finish(String result) {
                    File new_dir = new File(export_to_cvs.this.curFile.getPath().toString() + "/" + result);
                    new_dir.mkdir();
                    export_to_cvs.this.file_explorer_depth_degree = export_to_cvs.this.file_explorer_depth_degree + 1;
                    export_to_cvs.this.ListDir(new_dir);
                }
            }

            public void onClick(View v) {
                create_folder_dialogbox dialog = new create_folder_dialogbox(a);
                dialog.setDialogResult(new C04211());
                dialog.show();
            }
        });
        requestForPermission();
    }

    public void export_database_to_csv(String csvfilename) {
        this.dbc_array_list.clear();
        this.dbc_array_list = this.sqlh.get_data();
        File output_file = new File(new File(this.curFile.getPath()), csvfilename + ".csv");
        try {
            output_file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(output_file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append("ID,Item Name,Cost,Category,Date\n");
            for (int a = 0; a < this.dbc_array_list.size(); a++) {
                myOutWriter.append(Integer.toString(((database_class) this.dbc_array_list.get(a)).id) + "," + ((database_class) this.dbc_array_list.get(a)).item + "," + Float.toString(((database_class) this.dbc_array_list.get(a)).cost) + "," + ((database_class) this.dbc_array_list.get(a)).category + "," + Integer.toString(((database_class) this.dbc_array_list.get(a)).day) + "/" + Integer.toString(((database_class) this.dbc_array_list.get(a)).month) + "/" + Integer.toString(((database_class) this.dbc_array_list.get(a)).year) + "\n");
            }
            myOutWriter.close();
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean requestForPermission() {
        boolean isPermissionOn = true;
        if (VERSION.SDK_INT >= 23) {
            if (canAccessExternalSd()) {
                ListDir(this.curFile);
            } else {
                isPermissionOn = false;
                requestPermissions(this.EXTERNAL_PERMS, 138);
                if (!canAccessExternalSd()) {
                    Toast.makeText(this, "Permission not granted", 0).show();
                    onBackPressed();
                }
            }
        }
        return isPermissionOn;
    }

    public boolean canAccessExternalSd() {
        return hasPermission("android.permission.WRITE_EXTERNAL_STORAGE");
    }

    private boolean hasPermission(String perm) {
        return ContextCompat.checkSelfPermission(this, perm) == 0;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int index = 0;
        Map<String, Integer> PermissionsMap = new HashMap();
        for (String permission : permissions) {
            PermissionsMap.put(permission, Integer.valueOf(grantResults[index]));
            index++;
        }
        if (((Integer) PermissionsMap.get("android.permission.WRITE_EXTERNAL_STORAGE")).intValue() == 0 && ((Integer) PermissionsMap.get("android.permission.READ_EXTERNAL_STORAGE")).intValue() == 0) {
            startActivity(new Intent(this, export_to_cvs.class));
            return;
        }
        Toast.makeText(this, "Storage permission not granted", 1).show();
        finish();
    }

    void ListDir(File f) {
        this.curFile = f;
        File[] files = f.listFiles();
        this.fileList.clear();
        this.fileListDirectory.clear();
        for (File file : files) {
            this.fileList.add(file.getName());
            this.fileListDirectory.add(file.getPath());
        }
        this.file_explore.setAdapter(new ArrayAdapter(this, C0302R.layout.list_view1_display, this.fileList));
        if (this.file_explorer_depth_degree == 0) {
            this.folder_name.setText("Storage");
        } else {
            this.folder_name.setText(this.curFile.getName());
        }
    }
}
