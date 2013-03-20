package com.start.cysz6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.start.common.BaseActivity;
import com.start.utils.StringUtils;

public class AppStart extends BaseActivity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = View.inflate(this, R.layout.activity_start, null);
		setContentView(view);
        
		//渐变展示启动屏
		AlphaAnimation aa = new AlphaAnimation(0.3f,1.0f);
		aa.setDuration(2000);
		view.startAnimation(aa);
		aa.setAnimationListener(new AnimationListener(){
			@Override
			public void onAnimationEnd(Animation arg0) {
				Intent intent = new Intent(AppStart.this, MainActivity.class);
		        startActivity(intent);
		        finish();
			}
			@Override
			public void onAnimationRepeat(Animation animation) {}
			@Override
			public void onAnimationStart(Animation animation) {}
			
		});
		
		//兼容低版本cookie（1.5版本以下，包括1.5.0,1.5.1）
		AppContext appContext = (AppContext)getApplication();
		String cookie = appContext.getProperty("cookie");
		if(StringUtils.isEmpty(cookie)) {
			String cookie_name = appContext.getProperty("cookie_name");
			String cookie_value = appContext.getProperty("cookie_value");
			if(!StringUtils.isEmpty(cookie_name) && !StringUtils.isEmpty(cookie_value)) {
				cookie = cookie_name + "=" + cookie_value;
				appContext.setProperty("cookie", cookie);
				appContext.removeProperty("cookie_domain","cookie_name","cookie_value","cookie_version","cookie_path");
			}
		}
    }
	
}
