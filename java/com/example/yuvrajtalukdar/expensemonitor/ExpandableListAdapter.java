package com.example.yuvrajtalukdar.expensemonitor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context _context;
    Activity activity;
    private HashMap<String, ArrayList<String>> child;
    Context context;
    int group_posation;
    private List<String> header;
    ArrayList id = new ArrayList();
    View vi;

    public ExpandableListAdapter(Context context, ArrayList<String> listDataHeader, HashMap<String, ArrayList<String>> listChildData, ArrayList id_list, View view, Context c, Activity ac) {
        this._context = context;
        this.header = listDataHeader;
        this.child = listChildData;
        this.vi = view;
        this.context = c;
        this.activity = ac;
        this.id = id_list;
    }

    public Object getChild(int groupPosition, int childPosititon) {
        return ((ArrayList) this.child.get(this.header.get(groupPosition))).get(childPosititon);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return (long) childPosition;
    }

    public void clear() {
        this.header.clear();
        this.child.clear();
    }

    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            convertView = ((LayoutInflater) this._context.getSystemService("layout_inflater")).inflate(C0302R.layout.childs, parent, false);
        }
        TextView child_text = (TextView) convertView.findViewById(C0302R.id.childs);
        child_text.setTextSize(24.0f);
        child_text.setText("    " + childText);
        child_text.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(ExpandableListAdapter.this.activity, add_expense.class);
                ArrayList<String> x = new ArrayList();
                MainActivity m = ExpandableListAdapter.this.activity;
                x = m.return_catagory_list();
                i.putExtra("record_id", ExpandableListAdapter.this.figure_out_id(childText, groupPosition));
                i.putStringArrayListExtra("category_list", x);
                i.putExtra("activity_mode", 1);
                ExpandableListAdapter.this.context.startActivity(i);
                m.get_full_database_to_database_class();
            }
        });
        return convertView;
    }

    public int figure_out_id(String s, int group_position) {
        int record_id = -1;
        int counter = -1;
        ArrayList<String> data = new ArrayList();
        int a = 0;
        while (a < this.header.size()) {
            data = (ArrayList) this.child.get(this.header.get(a));
            for (int b = 0; b < data.size(); b++) {
                counter++;
                if (s.equals(data.get(b)) && a == group_position) {
                    record_id = ((Integer) this.id.get(counter)).intValue();
                }
            }
            a++;
        }
        return record_id;
    }

    public int getChildrenCount(int groupPosition) {
        return ((ArrayList) this.child.get(this.header.get(groupPosition))).size();
    }

    public Object getGroup(int groupPosition) {
        return this.header.get(groupPosition);
    }

    public int getGroupCount() {
        return this.header.size();
    }

    public long getGroupId(int groupPosition) {
        return (long) groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            convertView = ((LayoutInflater) this._context.getSystemService("layout_inflater")).inflate(C0302R.layout.header, parent, false);
        }
        TextView header_text = (TextView) convertView.findViewById(C0302R.id.header);
        header_text.setTextSize(30.0f);
        header_text.setTextColor(ContextCompat.getColor(header_text.getContext(), C0302R.color.colorAccent));
        header_text.setText(headerTitle);
        if (isExpanded) {
            header_text.setTypeface(null, 1);
            header_text.setCompoundDrawablesWithIntrinsicBounds(0, 0, C0302R.drawable.up, 0);
            this.group_posation = groupPosition;
        } else {
            header_text.setTypeface(null, 0);
            header_text.setCompoundDrawablesWithIntrinsicBounds(0, 0, C0302R.drawable.down, 0);
        }
        return convertView;
    }

    public boolean hasStableIds() {
        return false;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
