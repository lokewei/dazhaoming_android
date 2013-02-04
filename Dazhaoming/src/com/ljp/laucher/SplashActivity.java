package com.ljp.laucher;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
/**
 * 闪屏
 * @author walkingp
 * @date:2011-12
 *
 */
public class SplashActivity extends Activity{
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		//设置延迟，播放登陆界面
		new Handler().postDelayed(new Runnable(){
			public void run() {
				RedirectMainActivity();
			}
		},3000);
	}
	/**
	 * 跳转
	 */
	private void RedirectMainActivity(){
		Intent i = new Intent();
		i.setClass(SplashActivity.this,MainActivity.class);
		startActivity(i);
		SplashActivity.this.finish();
	}
}
