package com.example.yuvrajtalukdar.expensemonitor;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class viewPageAdapter extends PagerAdapter {
    Activity activity;
    Activity help_activity;
    int[] images;
    LayoutInflater inflater;

    public viewPageAdapter(Activity activity, int[] images) {
        this.activity = activity;
        this.images = images;
    }

    public int getCount() {
        return this.images.length;
    }

    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        this.inflater = (LayoutInflater) this.activity.getApplicationContext().getSystemService("layout_inflater");
        View itemView = this.inflater.inflate(C0302R.layout.help_img_display, container, false);
        ImageView image = (ImageView) itemView.findViewById(C0302R.id.imageView);
        DisplayMetrics dis = new DisplayMetrics();
        this.activity.getWindowManager().getDefaultDisplay().getMetrics(dis);
        int height = dis.heightPixels;
        int width = dis.widthPixels;
        image.setMinimumHeight(height);
        image.setMinimumWidth(width);
        image.setImageResource(this.images[position]);
        container.addView(itemView);
        return itemView;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }
}
