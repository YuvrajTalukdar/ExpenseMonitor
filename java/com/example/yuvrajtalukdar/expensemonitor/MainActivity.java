package com.example.yuvrajtalukdar.expensemonitor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import com.apps.yuvraj.expensemonitor.catagories.category_communication_interface;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.InterstitialAd;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnNavigationItemSelectedListener, category_communication_interface {
    public ArrayList f45a = new ArrayList();
    public ArrayList<database_class> dbc_array_list = new ArrayList();
    private InterstitialAd mInterstitialAd;
    private SQLite_Handler sqlh = new SQLite_Handler(this, null);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0302R.layout.activity_main);
        setRequestedOrientation(1);
        Toolbar toolbar1 = (Toolbar) findViewById(C0302R.id.toolbar1);
        setSupportActionBar(toolbar1);
        DrawerLayout drawer = (DrawerLayout) findViewById(C0302R.id.drawer_layout);
        ActionBarDrawerToggle toggle1 = new ActionBarDrawerToggle(this, drawer, toolbar1, C0302R.string.navigation_drawer_open, C0302R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle1);
        toggle1.syncState();
        ((NavigationView) findViewById(C0302R.id.nav_view)).setNavigationItemSelectedListener(this);
        get_full_database_to_database_class();
        setTitle("Expense List");
        this.f45a = this.sqlh.get_category_list();
        if (this.f45a.size() == 0) {
            this.sqlh.add_category("Other");
            startActivity(new Intent(this, help.class));
        }
        set_fragment(C0302R.id.expense_list);
    }

    public void prepareAd() {
        this.mInterstitialAd = new InterstitialAd(this);
        this.mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        this.mInterstitialAd.loadAd(new Builder().build());
    }

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(C0302R.id.drawer_layout);
        if (drawer.isDrawerOpen((int) GravityCompat.START)) {
            drawer.closeDrawer((int) GravityCompat.START);
            return;
        }
        Intent startmain = new Intent("android.intent.action.MAIN");
        startmain.addCategory("android.intent.category.HOME");
        startmain.setFlags(268435456);
        startActivity(startmain);
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == C0302R.id.catagory) {
            set_fragment(C0302R.id.catagory);
            setTitle("Categories");
        } else if (id == C0302R.id.expense_list) {
            set_fragment(C0302R.id.expense_list);
            setTitle("Expense List");
        } else if (id == C0302R.id.report) {
            set_fragment(C0302R.id.report);
            setTitle("Expense Report");
        } else if (id == C0302R.id.export_to_cvs) {
            startActivity(new Intent(this, export_to_cvs.class));
        }
        ((DrawerLayout) findViewById(C0302R.id.drawer_layout)).closeDrawer((int) GravityCompat.START);
        return true;
    }

    public void set_fragment(int id) {
        Fragment fragment;
        FragmentTransaction transaction;
        if (id == C0302R.id.catagory) {
            this.f45a = this.sqlh.get_category_list();
            fragment = new catagories();
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(C0302R.id.FrameLayout1, fragment);
            transaction.commit();
        } else if (id == C0302R.id.expense_list) {
            get_full_database_to_database_class();
            fragment = new Expense_list();
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(C0302R.id.FrameLayout1, fragment);
            transaction.commit();
        } else if (id == C0302R.id.report) {
            fragment = new report();
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(C0302R.id.FrameLayout1, fragment);
            transaction.commit();
        }
    }

    public void send_to_main(int action_sino, String content) {
        if (action_sino == 0) {
            add_category(content);
        } else if (action_sino == 1) {
            delete_category(content);
        } else if (action_sino == 2) {
            set_fragment(C0302R.id.catagory);
        }
    }

    public void add_data_to_table(float cost, String item, String category, int day, int month, int year) {
        try {
            long s = this.sqlh.add_data(cost, item, category, day, month, year);
            sync_database_class_with_database(cost, item, category, day, month, year);
            if (s > 0) {
                Toast.makeText(this, "data added to database", 1).show();
            }
        } catch (Exception e) {
            Log.i("ERROR", e.toString());
        }
    }

    public void add_category(String content) {
        try {
            if (this.sqlh.add_category(content) > 0) {
                Toast.makeText(this, content + " added to category list", 1).show();
            }
            set_fragment(C0302R.id.catagory);
        } catch (Exception e) {
            Log.i("ERROR", e.toString());
        }
    }

    public void delete_category(String content) {
        try {
            if (this.sqlh.delete_category(content) == 1) {
                Toast.makeText(this, content + "deleted from category list", 1).show();
            }
            set_fragment(C0302R.id.catagory);
        } catch (Exception e) {
            Log.i("ERROR", e.toString());
        }
    }

    public ArrayList return_catagory_list() {
        return this.f45a;
    }

    public ArrayList return_category_list_from_record_database() {
        ArrayList x = new ArrayList();
        return this.sqlh.return_categorylist_from_record_database();
    }

    public ArrayList<database_class> return_dbc_array_list() {
        return this.dbc_array_list;
    }

    public void get_full_database_to_database_class() {
        this.dbc_array_list.clear();
        this.dbc_array_list = this.sqlh.get_data();
    }

    public void sync_database_class_with_database(float cost, String item, String category, int day, int month, int year) {
        float f = cost;
        String str = item;
        String str2 = category;
        int i = day;
        int i2 = month;
        int i3 = year;
        this.dbc_array_list.add(new database_class(f, str, str2, i, i2, i3, ((database_class) this.dbc_array_list.get(this.dbc_array_list.size() - 1)).id + 1));
    }
}
