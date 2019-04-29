package com.example.yuvrajtalukdar.expensemonitor;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import java.util.ArrayList;

public class catagories extends Fragment {
    private static TextView f38t;
    String category_name = "";
    private ListView list_view1;
    category_communication_interface listener;

    class C03072 implements OnClickListener {
        C03072() {
        }

        public void onClick(View view) {
            catagories.this.edit(view);
        }
    }

    public interface category_communication_interface {
        void send_to_main(int i, String str);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(C0302R.layout.fragment_catagories, container, false);
        try {
            ((AdView) view.findViewById(C0302R.id.adView)).loadAd(new Builder().build());
        } catch (Exception e) {
            Log.i("ERROR", e.toString());
        }
        this.list_view1 = (ListView) view.findViewById(C0302R.id.listView);
        ArrayList a = new ArrayList();
        final ArrayAdapter x = new ArrayAdapter(getContext(), C0302R.layout.list_view1_display, ((MainActivity) getActivity()).return_catagory_list());
        this.list_view1.setAdapter(x);
        this.category_name = "";
        this.list_view1.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                catagories.this.category_name = (String) x.getItem(position);
                catagories.this.edit(view);
                catagories.this.category_name = "";
            }
        });
        ((FloatingActionButton) view.findViewById(C0302R.id.floatingActionButton)).setOnClickListener(new C03072());
        return view;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (category_communication_interface) context;
    }

    public void edit(View view) {
        Intent i = new Intent(getActivity(), edit_catagory.class);
        i.putExtra("category_name", this.category_name);
        startActivityForResult(i, 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == -1 && data != null) {
            String content = data.getStringExtra("content");
            int action_sino = data.getIntExtra("action_sino", 3);
            if (action_sino == 0 || action_sino == 1 || action_sino == 2) {
                this.listener.send_to_main(action_sino, content);
                return;
            } else {
                Log.i("ERROR", "INTEGER NOT FOUND!!!!!!!!!!!!!!!!");
                return;
            }
        }
        Log.i("Close", "edit_catagory CLOSED");
    }
}
