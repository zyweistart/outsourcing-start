package com.start.common;

import android.app.Activity;
import android.os.Bundle;

/**
 * 基本Activity
 * @author start
 */
public abstract class BaseActivity extends Activity {

	protected final String TAG=this.getClass().getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//添加Activity到堆栈
		AppManager.getAppManager().addActivity(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		//结束Activity&从堆栈中移除
		AppManager.getAppManager().finishActivity(this);
	}
	
}