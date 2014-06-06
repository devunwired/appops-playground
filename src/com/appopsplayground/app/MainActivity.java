package com.athoc.panic.appopsplayground.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

    private PackageAdapter mAdapter;
    private AppOpsWrapper mAppOps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView list = (ListView) findViewById(R.id.list);
        mAdapter = new PackageAdapter(this);
        list.setAdapter(mAdapter);
        list.setOnItemClickListener(this);

        mAppOps = new AppOpsWrapper(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_service:
                Intent stopIntent = new Intent(this, AppOpsMonitorService.class);
                stopService(stopIntent);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PackageInfo pInfo = mAdapter.getItem(position);

        Intent startIntent = new Intent(this, DetailActivity.class);
        startIntent.putExtra(AppOpsMonitorService.EXTRA_PACKAGE, pInfo);
        startActivity(startIntent);
    }

    private static Comparator<PackageInfo> sPackageInfoComparator = new Comparator<PackageInfo>() {
        @Override
        public int compare(PackageInfo lhs, PackageInfo rhs) {
            if (lhs == null) return -1;
            if (rhs == null) return 1;

            return lhs.packageName.compareTo(rhs.packageName);
        }
    };

    private static class PackageAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private List<PackageInfo> mList;

        public PackageAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
            mList = new ArrayList<PackageInfo>();

            List<PackageInfo> list = context.getPackageManager().getInstalledPackages(0);
            Collections.sort(list, sPackageInfoComparator);
            mList.addAll(list);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public PackageInfo getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            }

            PackageInfo info = getItem(position);
            TextView textView = ((TextView) convertView);
            textView.setText(info.packageName);

            return convertView;
        }
    }
}
