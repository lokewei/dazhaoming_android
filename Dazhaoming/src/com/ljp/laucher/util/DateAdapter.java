package com.ljp.laucher.util;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ljp.laucher.R;

public class DateAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<String> lstDate;
	private TextView txtAge;
	private DragGrid dragGrid;
	public DateAdapter(DragGrid dragGrid,Context mContext, ArrayList<String> list) {
		this.context = mContext;
		lstDate = list;
		this.dragGrid=dragGrid;
	}

	@Override
	public int getCount() {
		return lstDate.size();
	}

	@Override
	public Object getItem(int position) {
		return lstDate.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void exchange(int startPosition, int endPosition) {
		Object endObject = getItem(endPosition);
		Object startObject = getItem(startPosition);
		lstDate.add(startPosition, (String) endObject);
		lstDate.remove(startPosition + 1);
		lstDate.add(endPosition, (String) startObject);
		lstDate.remove(endPosition + 1);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(R.layout.item, null);
		txtAge = (TextView) convertView.findViewById(R.id.txt_userAge);
		if(lstDate.get(position)==null){
			//txtAge.setText("+");
			//txtAge.setBackgroundResource(R.drawable.red);
		}
		else if(lstDate.get(position).equals("none")){
			txtAge.setText("");
			txtAge.setBackgroundDrawable(null);
		}else txtAge.setText("Item" + lstDate.get(position));
		 /* android:layout_width="405dp"	
	            android:layout_height="105dp"	*/
		//View item = super.getView(i, convertView, parent);
		
		//Configure.screenHeight;
		//convertView.setLayoutParams(new DragGrid.LayoutParams((Configure.screenWidth-200)/2, (Configure.screenHeight/4)));
		DragGrid.LayoutParams param = new DragGrid.LayoutParams(
	                android.view.ViewGroup.LayoutParams.FILL_PARENT,
	                dragGrid.getHeight()/4);
	        convertView.setLayoutParams(param);

		return convertView;
	}

}
