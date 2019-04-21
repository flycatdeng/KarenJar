package com.karenjar.demo;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseTabListAty extends ListActivity {
    private static final String DEEP_LIST_INTENT_EXTRA = "com.dandy.demo.list.Path";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new SimpleAdapter(this, getData(),
                android.R.layout.simple_list_item_1,
                new String[]{"title"},
                new int[]{android.R.id.text1}));
        getListView().setTextFilterEnabled(true);
    }

    protected List<Map<String, Object>> getData() {
        Intent intent = getIntent();
        String prefix = intent.getStringExtra(DEEP_LIST_INTENT_EXTRA);//从其他activity进入到该类（或子类）时带进的参数，用以表示更深层次的list
        if (prefix == null) {
            prefix = "";
        }
        List<Map<String, Object>> myData = new ArrayList<Map<String, Object>>();

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);//一定要配置这个action，要不然下面的代码找不到对应的配置
        mainIntent.addCategory(getListCategoryStr());//根据分类标签来获得该类的列表项
        PackageManager pm = getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(mainIntent, 0);
        if (null == list)
            return myData;
        String[] prefixPath;
        String prefixWithSlash = prefix;
        if (prefix.equals("")) {
            prefixPath = null;
        } else {
            prefixPath = prefix.split("/");
            prefixWithSlash = prefix + "/";
        }
        int len = list.size();
        Map<String, Boolean> entries = new HashMap<String, Boolean>();
        for (int i = 0; i < len; i++) {
            ResolveInfo info = list.get(i);
            CharSequence labelSeq = info.loadLabel(pm);
            String label = labelSeq != null ? labelSeq.toString() : info.activityInfo.name;

            if (prefixWithSlash.length() == 0 || label.startsWith(prefixWithSlash)) {
                String[] labelPath = label.split("/");
                String nextLabel = prefixPath == null ? labelPath[0] : labelPath[prefixPath.length];
                if ((prefixPath != null ? prefixPath.length : 0) == labelPath.length - 1) {
                    addItem(myData, nextLabel, activityIntent(
                            info.activityInfo.applicationInfo.packageName,
                            info.activityInfo.name));
                } else {
                    if (entries.get(nextLabel) == null) {
                        addItem(myData, nextLabel, browseIntent(prefix.equals("") ? nextLabel : prefix + "/" + nextLabel));
                        entries.put(nextLabel, true);
                    }
                }
            }
        }
        Collections.sort(myData, sDisplayNameComparator);
        return myData;
    }

    /**
     * 排序
     */
    private final static Comparator<Map<String, Object>> sDisplayNameComparator =
            new Comparator<Map<String, Object>>() {
                private final Collator collator = Collator.getInstance();

                public int compare(Map<String, Object> map1, Map<String, Object> map2) {
                    return collator.compare(map1.get("title"), map2.get("title"));
                }
            };

    /**
     * 跳转的到具体activity
     *
     * @param pkg
     * @param componentName
     * @return
     */
    protected Intent activityIntent(String pkg, String componentName) {
        Intent result = new Intent();
        result.setClassName(pkg, componentName);
        return result;
    }

    /**
     * 在当前页面添加一项列表数据
     *
     * @param data   结果集
     * @param name   在列表中显示的名称
     * @param intent 点击响应
     */
    protected void addItem(List<Map<String, Object>> data, String name, Intent intent) {
        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("title", name);
        temp.put("intent", intent);
        data.add(temp);
    }

    /**
     * 将接下来的路劲继续跳转到activity
     *
     * @param path
     * @return
     */
    protected Intent browseIntent(String path) {
        Intent result = new Intent();
        result.setClass(this, this.getClass());
        result.putExtra(DEEP_LIST_INTENT_EXTRA, path);
        return result;
    }

    /**
     * 点击叶子条目，跳转对应的activity
     *
     * @param l
     * @param v
     * @param position
     * @param id
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Map<String, Object> map = (Map<String, Object>) l.getItemAtPosition(position);
        Intent intent = (Intent) map.get("intent");
        startActivity(intent);
    }

    /**
     * 该字符串主要用于在AndroidManifest.xml中配置的Label字符串
     * 从而得到要显示在哪个list activity里
     *
     * @return
     */
    public abstract String getListCategoryStr();
}
