package com.example.yuvrajtalukdar.expensemonitor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Expense_list extends Fragment {
    ArrayList f36a = new ArrayList();
    Activity ac;
    ExpandableListAdapter adapter;
    Context co;
    Context context;
    ArrayList<ArrayList<String>> data_cluttered_according_to_date = new ArrayList();
    ArrayList<database_class> dbc_array_list = new ArrayList();
    HashMap<String, ArrayList<String>> hm_colapsable_listview;
    ArrayList id = new ArrayList();
    ArrayList<String> list_header = new ArrayList();
    MainActivity mainactivity;
    MainActivity test;
    View f37v;

    class C03011 implements OnClickListener {
        C03011() {
        }

        public void onClick(View view) {
            Expense_list.this.start_expense_list_activity(view);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(C0302R.layout.fragment_expense_list, container, false);
        ((AdView) view.findViewById(C0302R.id.adView)).loadAd(new Builder().build());
        this.mainactivity = (MainActivity) getActivity();
        this.f36a = this.mainactivity.return_catagory_list();
        this.context = getContext();
        this.ac = this.mainactivity;
        start_expandable_list_view(this.mainactivity, view, this.context, this.ac);
        this.test = this.mainactivity;
        this.f37v = view;
        ((FloatingActionButton) view.findViewById(C0302R.id.floatingActionButton2)).setOnClickListener(new C03011());
        return view;
    }

    public void start_expandable_list_view(MainActivity mainactivity, View view, Context context, Activity ac) {
        mainactivity = new MainActivity();
        mainactivity = (MainActivity) getActivity();
        this.dbc_array_list.clear();
        this.id.clear();
        this.dbc_array_list = mainactivity.return_dbc_array_list();
        try {
            prepare_data_for_expandable_list_view();
        } catch (Exception e) {
            Log.i("ERROR", e.toString());
        }
        ExpandableListView expandableListView = new ExpandableListView(getContext());
        expandableListView = (ExpandableListView) view.findViewById(C0302R.id.expandableListView);
        expandableListView.setGroupIndicator(null);
        this.adapter = new ExpandableListAdapter(getContext(), this.list_header, this.hm_colapsable_listview, this.id, this.f37v, context, ac);
        this.adapter.notifyDataSetChanged();
        expandableListView.setAdapter(this.adapter);
    }

    public int month_to_days(int n, int year) {
        int mon = 0;
        for (int a = 1; a < n; a++) {
            if (a == 2) {
                if (year % 4 == 0) {
                    mon += 29;
                } else {
                    mon += 28;
                }
            } else if (a < 8) {
                if (a % 2 != 0) {
                    mon += 31;
                } else {
                    mon += 30;
                }
            } else if (a > 7) {
                if (a % 2 != 0) {
                    mon += 30;
                } else {
                    mon += 31;
                }
            }
        }
        return mon;
    }

    public void sort_according_to_date() {
        database_class[] database_class_obj_array = (database_class[]) this.dbc_array_list.toArray(new database_class[this.dbc_array_list.size()]);
        for (int a = 0; a < this.dbc_array_list.size(); a++) {
            for (int b = 0; b < this.dbc_array_list.size(); b++) {
                if (((database_class_obj_array[a].year * 365) + month_to_days(database_class_obj_array[a].month, database_class_obj_array[a].year)) + database_class_obj_array[a].day > ((database_class_obj_array[b].year * 365) + month_to_days(database_class_obj_array[b].month, database_class_obj_array[b].year)) + database_class_obj_array[b].day) {
                    database_class temp = database_class_obj_array[a];
                    database_class_obj_array[a] = database_class_obj_array[b];
                    database_class_obj_array[b] = temp;
                }
            }
        }
        this.dbc_array_list = new ArrayList(Arrays.asList(database_class_obj_array));
    }

    public void prepare_data_for_expandable_list_view() {
        int a;
        sort_according_to_date();
        this.list_header.clear();
        this.hm_colapsable_listview = new HashMap();
        String date = "";
        String date2 = "";
        int limit1 = this.dbc_array_list.size();
        int limit2 = 0;
        float total_cost = 0.0f;
        ArrayList<String> list_header2 = new ArrayList();
        ArrayList total_cost_array_list = new ArrayList();
        String x = "";
        String y = Integer.toString(((database_class) this.dbc_array_list.get(0)).day) + "/" + Integer.toString(((database_class) this.dbc_array_list.get(0)).month) + "/" + Integer.toString(((database_class) this.dbc_array_list.get(0)).year);
        int c = 0;
        for (a = 0; a < this.dbc_array_list.size(); a = (c - 1) + 1) {
            x = Integer.toString(((database_class) this.dbc_array_list.get(a)).day) + "/" + Integer.toString(((database_class) this.dbc_array_list.get(a)).month) + "/" + Integer.toString(((database_class) this.dbc_array_list.get(a)).year);
            while (x.equals(y)) {
                total_cost += ((database_class) this.dbc_array_list.get(c)).cost;
                c++;
                if (c == this.dbc_array_list.size()) {
                    total_cost_array_list.add(Float.valueOf(total_cost));
                    break;
                }
                y = Integer.toString(((database_class) this.dbc_array_list.get(c)).day) + "/" + Integer.toString(((database_class) this.dbc_array_list.get(c)).month) + "/" + Integer.toString(((database_class) this.dbc_array_list.get(c)).year);
                if (!x.equals(y)) {
                    total_cost_array_list.add(Float.valueOf(total_cost));
                }
            }
            total_cost = 0.0f;
        }
        for (a = 0; a < limit1; a++) {
            String date1 = Integer.toString(((database_class) this.dbc_array_list.get(a)).day) + "/" + Integer.toString(((database_class) this.dbc_array_list.get(a)).month) + "/" + Integer.toString(((database_class) this.dbc_array_list.get(a)).year);
            date2 = date1 + " Total ";
            if (!date.equals(date1)) {
                this.list_header.add(date2 + Float.toString(((Float) total_cost_array_list.get(limit2)).floatValue()));
                list_header2.add(date1);
                date = (String) list_header2.get(limit2);
                limit2++;
            }
        }
        ArrayList data_in_string = new ArrayList();
        String month = Integer.toString(((database_class) this.dbc_array_list.get(0)).day) + "/" + Integer.toString(((database_class) this.dbc_array_list.get(0)).month) + "/" + Integer.toString(((database_class) this.dbc_array_list.get(0)).year);
        try {
            this.data_cluttered_according_to_date.clear();
            for (a = 0; a < this.dbc_array_list.size(); a++) {
                if (month.equals(Integer.toString(((database_class) this.dbc_array_list.get(a)).day) + "/" + Integer.toString(((database_class) this.dbc_array_list.get(a)).month) + "/" + Integer.toString(((database_class) this.dbc_array_list.get(a)).year))) {
                    data_in_string.add(((database_class) this.dbc_array_list.get(a)).item + " Rs " + Float.toString(((database_class) this.dbc_array_list.get(a)).cost));
                    this.id.add(Integer.valueOf(((database_class) this.dbc_array_list.get(a)).id));
                } else {
                    month = Integer.toString(((database_class) this.dbc_array_list.get(a)).day) + "/" + Integer.toString(((database_class) this.dbc_array_list.get(a)).month) + "/" + Integer.toString(((database_class) this.dbc_array_list.get(a)).year);
                    this.data_cluttered_according_to_date.add(new ArrayList(data_in_string));
                    data_in_string.clear();
                    data_in_string.add(((database_class) this.dbc_array_list.get(a)).item + " Rs " + Float.toString(((database_class) this.dbc_array_list.get(a)).cost));
                    this.id.add(Integer.valueOf(((database_class) this.dbc_array_list.get(a)).id));
                }
            }
        } catch (Exception e) {
            Log.i("ERROR1", e.toString());
        }
        this.data_cluttered_according_to_date.add(new ArrayList(data_in_string));
        data_in_string.clear();
        this.hm_colapsable_listview.clear();
        for (a = 0; a < this.data_cluttered_according_to_date.size(); a++) {
            this.hm_colapsable_listview.put(this.list_header.get(a), this.data_cluttered_according_to_date.get(a));
        }
    }

    public void onAttach(Context context) {
        this.co = context;
        super.onAttach(context);
    }

    public void start_expense_list_activity(View view) {
        Intent i = new Intent(getActivity(), add_expense.class);
        Log.i("category list ", this.f36a.toString());
        i.putStringArrayListExtra("category_list", this.f36a);
        startActivityForResult(i, 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == -1 && data != null) {
            this.mainactivity.add_data_to_table(data.getFloatExtra("cost", -1.0f), data.getStringExtra("item"), data.getStringExtra("category"), data.getIntExtra("day", -1), data.getIntExtra("month", -1), data.getIntExtra("year", -1));
            this.adapter.clear();
            startActivity(new Intent(this.mainactivity, MainActivity.class));
            start_expandable_list_view(this.mainactivity, this.f37v, this.context, this.ac);
            return;
        }
        Log.i("Close", "edit_catagory CLOSED");
    }
}
