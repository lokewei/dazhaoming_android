package com.ljp.laucher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ListViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.list);
	    //绑定Layout里面的ListView
	    ImageView image=(ImageView)findViewById(R.id.ItemImage);
	    //image.setImageDrawable(R.drawable.bg_cb);
	    ListView list = (ListView) findViewById(R.id.ListView01);
	    Intent intent=getIntent();
        
        String str=intent.getStringExtra("Key");
       // Toast.makeText(ListViewActivity.this, "intent key" + str + " ", Toast.LENGTH_SHORT).show();
		
	    //生成动态数组，加入数据
	    ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
	    for(int i=0;i<10;i++)
	    {
	        HashMap<String, Object> map = new HashMap<String, Object>();
	        map.put("ItemImage", R.drawable.pageid);//图像资源的ID
	        map.put("ItemTitle", "news "+i);
	        map.put("ItemText", "照明行业资讯展现 "+i);
	        listItem.add(map);
	    }
	    //生成适配器的Item和动态数组对应的元素
	    SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//数据源
	        R.layout.list_item,//ListItem的XML实现
	        //动态数组与ImageItem对应的子项       
	        new String[] {"ItemImage","ItemTitle", "ItemText"},
	        //ImageItem的XML文件里面的一个ImageView,两个TextView ID
	        new int[] {R.id.ItemImage,R.id.ItemTitle,R.id.ItemText}
	    );
	    
	    //添加并且显示
	    list.setAdapter(listItemAdapter);
	   
	    //添加点击
	    list.setOnItemClickListener(new OnItemClickListener() {

	        @Override
	        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
	                long arg3) {
	        	Map<String, Object> map = (Map<String, Object>) arg0.getItemAtPosition(arg2);
	        	
	        	Intent intent=new Intent();
	            intent.setClass(ListViewActivity.this,ListDetailViewActivity.class);
	           // map.get("ItemImage");//图像资源的ID
		       String itemTitle= (String) map.get("ItemTitle");
		       String itemText=(String) map.get("ItemText");
	            intent.putExtra("itemTitle", itemTitle);
	            intent.putExtra("itemText", itemText);
	            startActivity(intent);
	            setTitle("点击第"+arg2+"个项目");
	        }
	    });
	   
	  //添加长按点击
	    list.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
	       
	        @Override
	        public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
	            menu.setHeaderTitle("长按菜单-ContextMenu"); 
	            menu.add(0, 0, 0, "弹出长按菜单0");
	            menu.add(0, 1, 0, "弹出长按菜单1"); 
	        }
	    });
	}

	//长按菜单响应函数
		@Override
		public boolean onContextItemSelected(MenuItem item) {
		    setTitle("点击了长按菜单里面的第"+item.getItemId()+"个项目");
		    return super.onContextItemSelected(item);
		}
}
