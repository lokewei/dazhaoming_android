package com.ljp.laucher;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ljp.laucher.util.Configure;
import com.ljp.laucher.util.DateAdapter;
import com.ljp.laucher.util.DragGrid;
import com.ljp.laucher.util.ScrollLayout;

public class MiLaucherActivity extends Activity{

	/** GridView. */
	private LinearLayout linear;private RelativeLayout relate;
	private DragGrid gridView;
	private ScrollLayout lst_views;
	//TextView tv_page;// int oldPage=1;
	private ImageView  delImage,runImage;
	LinearLayout.LayoutParams param;

	TranslateAnimation left, right;
	Animation up, down;

	public static final int PAGE_SIZE = 8;
	ArrayList<DragGrid> gridviews = new ArrayList<DragGrid>();
	
	ArrayList<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();//全部数据的集合集lists.size()==countpage;
	ArrayList<String> lstDate = new ArrayList<String>();//每一页的数据
	
	SensorManager sm;SensorEventListener lsn;boolean isClean =false;Vibrator vibrator;int rockCount=0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		for (int i = 0; i < 10; i++) {
			lstDate.add("" + i);
		}
		
		init();initData();

		for (int i = 0; i < Configure.countPages; i++) {
			lst_views.addView(addGridView(i));
		}
		
		lst_views.setPageListener(new ScrollLayout.PageListener() {
			@Override
			public void page(int page) {
				setCurPage(page);
			}
		});
		
		runImage = (ImageView) findViewById(R.id.run_image);
		runImage.setBackgroundColor(Color.BLACK);
		//runImage.setVisibility(0x00000004);//
		runAnimation();
		delImage = (ImageView) findViewById(R.id.dels);
		relate.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("LongClick");
				return false;
			}
		});
		vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);   
		sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		Sensor sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		lsn = new SensorEventListener() {
			public void onSensorChanged(SensorEvent e) {
				if (e.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
					if(!isClean&&rockCount>=10){
						isClean=true;rockCount=0;
						vibrator.vibrate(100);
						CleanItems();
						return;
					}
					float newX = e.values[SensorManager.DATA_X];
					float newY = e.values[SensorManager.DATA_Y];
					float newZ = e.values[SensorManager.DATA_Z];
					//if ((newX >= 18 || newY >= 20||newZ >= 20 )&&rockCount<4) {
					if((newX >= 18 || newY >= 20||newZ >= 20 ) && rockCount%2==0){
						rockCount++;
						return;
					}if((newX <= -18 || newY <= -20||newZ <= -20 ) && rockCount%2==1){
						rockCount++;
						return;
					}
					
				}
			}
			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				// TODO Auto-generated method stub
				
			}
		};

		sm.registerListener(lsn, sensor, SensorManager.SENSOR_DELAY_GAME);
	}

	public void init() {
		relate = (RelativeLayout) findViewById(R.id.relate);
		lst_views = (ScrollLayout) findViewById(R.id.views);
		//tv_page = (TextView) findViewById(R.id.tv_page);
		//tv_page.setText("1");
		com.ljp.laucher.util.Configure.init(MiLaucherActivity.this);
		param = new LinearLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.FILL_PARENT,
				android.view.ViewGroup.LayoutParams.FILL_PARENT);
		param.rightMargin = 100;
		param.leftMargin = 20;
		if (gridView != null) {
		//	lst_views.removeAllViews();
		}
	}
	public void initData(){
		Configure.countPages = (int) Math.ceil(lstDate.size()
				/ (float) PAGE_SIZE);

		lists = new ArrayList<ArrayList<String>>();
		for(int i=0;i<Configure.countPages;i++){
			lists.add(new ArrayList<String>());
			for (int j = PAGE_SIZE * i; j < (PAGE_SIZE * (i + 1) > lstDate
				.size() ? lstDate.size() : PAGE_SIZE * (i + 1)); j++) 
				lists.get(i).add(lstDate.get(j));
		}
		boolean isLast=true;
		for(int i=lists.get(Configure.countPages-1).size();i<PAGE_SIZE;i++){
			if(isLast){lists.get(Configure.countPages-1).add(null);isLast=false;}
			else lists.get(Configure.countPages-1).add("none");
		}
	}
	public void CleanItems(){
		lstDate=new ArrayList<String>();
		for(int i=0;i<lists.size();i++){
			for(int j=0;j<lists.get(i).size();j++){
				if(lists.get(i).get(j)!=null&&!lists.get(i).get(j).equals("none")){
					lstDate.add(lists.get(i).get(j).toString());System.out.println("-->"+lists.get(i).get(j).toString());
				}
			}
		}System.out.println(lstDate.size());
		initData();
		lst_views.removeAllViews();
		gridviews = new ArrayList<DragGrid>();
		for (int i = 0; i < Configure.countPages; i++) {
			lst_views.addView(addGridView(i));
			
		}
		ViewGroup.LayoutParams params = lst_views.getLayoutParams(); 
	   // params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() – 1)); 
	    ((MarginLayoutParams)params).setMargins(10, 10, 10, 10);
	    lst_views.setLayoutParams(params);
	    
		isClean=false;
		lst_views.snapToScreen(0);
	}
	public int getFristNonePosition(ArrayList<String> array){
		for(int i=0;i<array.size();i++){
			if(array.get(i)!=null &&
					array.get(i).toString().equals("none")){
				return i;
			}
		}
		return -1;
	}
	public int getFristNullPosition(ArrayList<String> array){
		for(int i=0;i<array.size();i++){
			if(array.get(i)==null){
				return i;
			}
		}
		return -1;
	}
	public LinearLayout addGridView(int i) {
		//if (lists.get(i).size() < PAGE_SIZE)
		//	lists.get(i).add(null);
		
		linear = new LinearLayout(MiLaucherActivity.this);
		gridView = new DragGrid(MiLaucherActivity.this);
		gridView.setAdapter(new DateAdapter(gridView,MiLaucherActivity.this, lists
				.get(i)));
		gridView.setNumColumns(2);
		gridView.setHorizontalSpacing(0);
		gridView.setVerticalSpacing(0);
		final int ii = i;
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				// TODO Auto-generated method stub
				/*arg1.getContext().getText(resId)*/
				//Toast.makeText(MiLaucherActivity.this, "你选择了" + (arg2 + 1) + " 号图片", Toast.LENGTH_SHORT).show();
				//Intent intent= new Intent(MiLaucherActivity.this,ListViewActivity.this);
				Intent intent=new Intent();
	            intent.setClass(MiLaucherActivity.this,ListViewActivity.class);
	            intent.putExtra("Key", "NeuglsWorkStudio");
	            startActivity(intent);
			}
		});
		gridView.setSelector(R.anim.grid_light);
		gridView.setPageListener(new DragGrid.G_PageListener() {
			@Override
			public void page(int cases, int page) {
				switch (cases) {
				case 0:// 滑动页面
					lst_views.snapToScreen(page);
					setCurPage(page);
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							Configure.isChangingPage = false;
						}
					}, 800);
					break;
				case 1:// 删除按钮上来
					delImage.setBackgroundResource(R.drawable.del);
					delImage.setVisibility(0);
					delImage.startAnimation(up);
					break;
				case 2:// 删除按钮变深
					delImage.setBackgroundResource(R.drawable.del_check);
					Configure.isDelDark = true;
					break;
				case 3:// 删除按钮变淡
					delImage.setBackgroundResource(R.drawable.del);
					Configure.isDelDark = false;
					break;
				case 4:// 删除按钮下去
					delImage.startAnimation(down);
					break;
				case 5:// 松手动作
					delImage.startAnimation(down);
					// Configure.isDelRunning = false;
					lists.get(Configure.curentPage).add(Configure.removeItem,
							null);
					lists.get(Configure.curentPage).remove(
							Configure.removeItem + 1);
					((DateAdapter) ((gridviews.get(Configure.curentPage))
							.getAdapter())).notifyDataSetChanged();
					break;
				}
			}
		});
		gridView.setOnItemChangeListener(new DragGrid.G_ItemChangeListener() {
			@Override
			public void change(int from, int to, int count) {
				String toString = (String) lists.get(
						Configure.curentPage - count).get(from);

				lists.get(Configure.curentPage - count).add(from,
						(String) lists.get(Configure.curentPage).get(to));
				lists.get(Configure.curentPage - count).remove(from + 1);
				lists.get(Configure.curentPage).add(to, toString);
				lists.get(Configure.curentPage).remove(to + 1);

				((DateAdapter) ((gridviews.get(Configure.curentPage - count))
						.getAdapter())).notifyDataSetChanged();
				((DateAdapter) ((gridviews.get(Configure.curentPage))
						.getAdapter())).notifyDataSetChanged();
			}
		});
		gridviews.add(gridView);
		linear.addView(gridviews.get(i), param);
		return linear;
	}

	public void runAnimation() {
		down = AnimationUtils.loadAnimation(MiLaucherActivity.this,
				R.anim.del_down);
		up = AnimationUtils
				.loadAnimation(MiLaucherActivity.this, R.anim.del_up);
		down.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				delImage.setVisibility(8);
			}
		});

		right = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0f,
				Animation.RELATIVE_TO_PARENT, -1f,
				Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT,
				0f);
		left = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1f,
				Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT,
				0f, Animation.RELATIVE_TO_PARENT, 0f);
		right.setDuration(25000);
		left.setDuration(25000);
		right.setFillAfter(true);
		left.setFillAfter(true);

		right.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				//runImage.startAnimation(left);
			}
		});
		left.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				//runImage.startAnimation(right);
			}
		});
		//runImage.startAnimation(right);
	}

	public void setCurPage(final int page) {
		Animation a = AnimationUtils.loadAnimation(MiLaucherActivity.this,
				R.anim.scale_in);
		a.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				/*tv_page.setText((page + 1) + "");
				tv_page.startAnimation(AnimationUtils.loadAnimation(
						MiLaucherActivity.this, R.anim.scale_out));*/
			}
		});
		//tv_page.startAnimation(a);

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		sm.unregisterListener(lsn);
	}
	
}













