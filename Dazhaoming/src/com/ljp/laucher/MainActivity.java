package com.ljp.laucher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.linerlayout);
	}
	
	public void paletClickHandler(View view){
		startActivity(new Intent(MainActivity.this,ListViewActivity.class));
	}
}
