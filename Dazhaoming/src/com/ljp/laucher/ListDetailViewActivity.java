package com.ljp.laucher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ListDetailViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.listdetail);
		 Intent intent=getIntent();
		 //String itemTitle=intent.getStringExtra("itemTitle");
		 //String itemText=intent.getStringExtra("itemText");
		 TextView itemTitle=(TextView)findViewById(R.id.detailTitle);
		 TextView itemText=(TextView)findViewById(R.id.detailText);
		 itemTitle.setText(intent.getStringExtra("itemTitle"));
		 itemText.setText("Community "+
"Links to the Android discussion groups and information about other ways to collaborate with other developers."+ 
"Device Dashboard"+
"Device distribution data, grouped by various dimensions such as screen size and Android platform version. "+
"More Quick development tips, troubleshooting information, and frequently asked questions (FAQs)");
	       // Toast.makeText(ListViewActivity.this, "intent key" + str + " ", Toast.LENGTH_SHORT).show();
	}

}
