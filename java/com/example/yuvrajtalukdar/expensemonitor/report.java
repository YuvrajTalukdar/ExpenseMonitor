package com.example.yuvrajtalukdar.expensemonitor;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class report extends Fragment implements OnItemSelectedListener {
    private static String TAG = "MainActivity";
    static int f39s = 0;
    ArrayList<String> category_arraylist = new ArrayList();
    Spinner category_spinner;
    ArrayAdapter<String> category_spinner_adapter;
    ArrayList<database_class> dbc_arraylist = new ArrayList();
    ArrayList expenditure_pre_category = new ArrayList();
    ArrayList<String> menuitems_category = new ArrayList();
    ArrayList<String> menuitems_month = new ArrayList();
    ArrayList<String> menuitems_year = new ArrayList();
    ArrayAdapter<String> monthAdapter;
    int month_selected;
    Spinner month_spinner;
    PieChart pieChart;
    int position_in_menuitem_category = 0;
    ArrayAdapter<String> yearAdapter;
    ArrayList year_arraylist = new ArrayList();
    int year_selected;
    Spinner year_spinner;

    class C03141 implements OnItemSelectedListener {
        C03141() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            if (report.f39s != 0) {
                report.this.month_selected = position;
                report.this.set_total();
                report.this.reset_pie_chart();
            }
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    class C03152 implements OnItemSelectedListener {
        C03152() {
        }

        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (report.f39s == 0) {
                report.f39s++;
                return;
            }
            try {
                report.this.year_selected = Integer.parseInt(parent.getItemAtPosition(position).toString());
            } catch (Exception e) {
                Log.i("ERROR", e.toString());
            }
            report.this.set_total();
            report.this.reset_pie_chart();
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    class C03163 implements OnItemSelectedListener {
        C03163() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            report.this.position_in_menuitem_category = position;
            report.this.set_total();
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(C0302R.layout.fragment_report, container, false);
        MainActivity mainactivity = (MainActivity) getActivity();
        this.dbc_arraylist = mainactivity.return_dbc_array_list();
        this.category_arraylist = mainactivity.return_category_list_from_record_database();
        try {
            prepare_data();
        } catch (Exception e) {
            Log.i("ERROR", e.toString());
        }
        this.month_spinner = (Spinner) view.findViewById(C0302R.id.month_spinner);
        this.monthAdapter = new ArrayAdapter(mainactivity, 17367048, this.menuitems_month);
        this.month_spinner.setOnItemSelectedListener(this);
        this.monthAdapter.setDropDownViewResource(17367049);
        this.month_spinner.setAdapter(this.monthAdapter);
        this.year_spinner = (Spinner) view.findViewById(C0302R.id.year_spinner);
        this.yearAdapter = new ArrayAdapter(mainactivity, 17367049, this.menuitems_year);
        this.year_spinner.setOnItemSelectedListener(this);
        this.yearAdapter.setDropDownViewResource(17367049);
        this.year_spinner.setAdapter(this.yearAdapter);
        this.category_spinner = (Spinner) view.findViewById(C0302R.id.category_spinner);
        this.category_spinner_adapter = new ArrayAdapter(mainactivity, 17367048, this.menuitems_category);
        this.category_spinner.setOnItemSelectedListener(this);
        this.category_spinner_adapter.setDropDownViewResource(17367049);
        this.category_spinner.setAdapter(this.category_spinner_adapter);
        this.pieChart = (PieChart) view.findViewById(C0302R.id.pie_chart1);
        Description des = new Description();
        des.setTextSize(15.0f);
        des.setText("Spending in % per category");
        this.pieChart.setDescription(des);
        this.pieChart.setElevation(20.0f);
        this.pieChart.setRotationEnabled(true);
        this.pieChart.setUsePercentValues(true);
        this.pieChart.setCenterTextColor(ViewCompat.MEASURED_STATE_MASK);
        this.pieChart.setHoleRadius(25.0f);
        this.pieChart.setCenterText("Expenditure per category");
        this.pieChart.setCenterTextSize(10.0f);
        this.pieChart.setDrawEntryLabels(true);
        this.pieChart.setEntryLabelTextSize(20.0f);
        this.pieChart.setEntryLabelColor(SupportMenu.CATEGORY_MASK);
        this.pieChart.setTransparentCircleRadius(30.0f);
        addDataSet();
        f39s = 0;
        this.month_spinner.setOnItemSelectedListener(new C03141());
        this.year_spinner.setOnItemSelectedListener(new C03152());
        this.category_spinner.setOnItemSelectedListener(new C03163());
        Calendar calendar = Calendar.getInstance();
        this.month_selected = 2;
        this.year_selected = 1;
        this.month_spinner.setSelection(this.monthAdapter.getPosition(Integer.toString(this.month_selected)));
        this.year_spinner.setSelection(this.yearAdapter.getPosition(Integer.toString(this.year_selected)));
        return view;
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void prepare_data() {
        int a;
        int b = 0;
        this.year_arraylist.clear();
        for (a = 0; a < this.dbc_arraylist.size(); a++) {
            int c;
            int year1 = ((database_class) this.dbc_arraylist.get(a)).year;
            for (c = 0; c < this.year_arraylist.size(); c++) {
                if (year1 == ((Integer) this.year_arraylist.get(c)).intValue()) {
                    b++;
                }
            }
            if (b == 0) {
                this.year_arraylist.add(Integer.valueOf(year1));
            } else {
                b = 0;
            }
        }
        prepare_month_data();
        prepare_year_data();
        prepare_category_data();
        float total = 0.0f;
        for (a = 0; a < this.category_arraylist.size(); a++) {
            for (c = 0; c < this.dbc_arraylist.size(); c++) {
                if (((String) this.category_arraylist.get(a)).toString().equals(((database_class) this.dbc_arraylist.get(c)).category)) {
                    total += ((database_class) this.dbc_arraylist.get(c)).cost;
                }
            }
            this.expenditure_pre_category.add(Float.valueOf(total));
            total = 0.0f;
        }
    }

    public void prepare_month_data() {
        this.menuitems_month.clear();
        this.menuitems_month.add("Select month");
        this.menuitems_month.add("January");
        this.menuitems_month.add("February");
        this.menuitems_month.add("March");
        this.menuitems_month.add("April");
        this.menuitems_month.add("May");
        this.menuitems_month.add("June");
        this.menuitems_month.add("July");
        this.menuitems_month.add("August");
        this.menuitems_month.add("September");
        this.menuitems_month.add("October");
        this.menuitems_month.add("November");
        this.menuitems_month.add("December");
    }

    public void prepare_year_data() {
        this.menuitems_year.clear();
        this.menuitems_year.add("Select year");
        for (int a = 0; a < this.year_arraylist.size(); a++) {
            this.menuitems_year.add(Integer.toString(((Integer) this.year_arraylist.get(a)).intValue()));
        }
    }

    public void prepare_category_data() {
        this.menuitems_category.add("All Categories");
        for (int a = 0; a < this.category_arraylist.size(); a++) {
            this.menuitems_category.add(this.category_arraylist.get(a));
        }
    }

    public void set_total() {
        float total;
        int a;
        if (((String) this.menuitems_category.get(this.position_in_menuitem_category)).equals("All Categories")) {
            total = 0.0f;
            a = 0;
            while (a < this.dbc_arraylist.size()) {
                if (((database_class) this.dbc_arraylist.get(a)).month == this.month_selected && ((database_class) this.dbc_arraylist.get(a)).year == this.year_selected) {
                    total += ((database_class) this.dbc_arraylist.get(a)).cost;
                }
                ((TextView) getView().findViewById(C0302R.id.costview)).setText(Float.toString(total));
                a++;
            }
            return;
        }
        total = 0.0f;
        a = 0;
        while (a < this.dbc_arraylist.size()) {
            if (((database_class) this.dbc_arraylist.get(a)).month == this.month_selected && ((database_class) this.dbc_arraylist.get(a)).year == this.year_selected && ((database_class) this.dbc_arraylist.get(a)).category.equals(this.menuitems_category.get(this.position_in_menuitem_category))) {
                total += ((database_class) this.dbc_arraylist.get(a)).cost;
            }
            ((TextView) getView().findViewById(C0302R.id.costview)).setText(Float.toString(total));
            a++;
        }
    }

    public void reset_pie_chart() {
        float total = 0.0f;
        this.expenditure_pre_category.clear();
        for (int a = 0; a < this.category_arraylist.size(); a++) {
            int c = 0;
            while (c < this.dbc_arraylist.size()) {
                if (((String) this.category_arraylist.get(a)).toString().equals(((database_class) this.dbc_arraylist.get(c)).category) && ((database_class) this.dbc_arraylist.get(c)).year == this.year_selected && ((database_class) this.dbc_arraylist.get(c)).month == this.month_selected) {
                    total += ((database_class) this.dbc_arraylist.get(c)).cost;
                }
                c++;
            }
            this.expenditure_pre_category.add(Float.valueOf(total));
            total = 0.0f;
        }
        addDataSet();
    }

    private void addDataSet() {
        this.pieChart.animateXY(1400, 1400);
        ArrayList<PieEntry> yEntrys = new ArrayList();
        for (int i = 0; i < this.expenditure_pre_category.size(); i++) {
            if (((Float) this.expenditure_pre_category.get(i)).floatValue() != 0.0f) {
                yEntrys.add(new PieEntry(((Float) this.expenditure_pre_category.get(i)).floatValue(), (String) this.category_arraylist.get(i)));
            }
        }
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Spending");
        pieDataSet.setSliceSpace(2.0f);
        pieDataSet.setValueTextSize(20.0f);
        ArrayList colors = new ArrayList();
        for (int colour = 0; colour < 7; colour++) {
            Random random = new Random();
            colors.add(Integer.valueOf(Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))));
        }
        PieData pieData = new PieData(pieDataSet);
        pieDataSet.setColors((List) colors);
        this.pieChart.setData(pieData);
        this.pieChart.invalidate();
    }

    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
