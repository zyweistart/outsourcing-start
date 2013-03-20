package com.start.cysz6;

import greendroid.widget.MyQuickAction;
import greendroid.widget.QuickActionGrid;
import greendroid.widget.QuickActionWidget;
import greendroid.widget.QuickActionWidget.OnQuickActionClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.start.common.BaseActivity;
import com.start.common.UIHelper;
import com.start.utils.StringUtils;
import com.start.widget.PullToRefreshListView;
import com.start.widget.ScrollLayout;
/**
 * 主界面
 * @author start
 * 
 */
public class MainActivity extends BaseActivity {

	private int mCurSel;
	private int mViewCount;
	private ScrollLayout mScrollLayout;
	private RadioButton[] mButtons;
	private String[] mHeadTitles;
	
	private ImageView mHeadLogo;
	private TextView mHeadTitle;
	private ProgressBar mHeadProgress;
	private ImageButton mHead_search;
	
	private RadioButton rbFirst;
	private RadioButton rbSecond;
	private RadioButton rbThird;
	private RadioButton rbFourth;
	private ImageView fbSetting;
	
	private QuickActionWidget mGrid;//快捷栏控件
	
	private AppContext appContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		appContext = (AppContext)getApplication();
		//网络连接判断
        if(!appContext.isNetworkConnected())
        	//UIHelper.ToastMessage(this, R.string.network_not_connected);
        //初始化登录
        appContext.initLoginInfo();
		
		initHeadView();
		initFootBar();
		initPageScroll();
//		initFrameButton();
//        initBadgeView();
        initQuickActionGrid();
		initFrameData();
		
		//检查新版本
        //启动轮询通知信息
	}

	@Override
    protected void onResume() {
	    	super.onResume();
	    	if(mViewCount == 0){
	    		mViewCount = 4;
	    	}
	    	if(mCurSel == 0 && !rbFirst.isChecked()) {
	    		rbFirst.setChecked(true);
	    		rbSecond.setChecked(false);
	    		rbThird.setChecked(false);
	    		rbFourth.setChecked(false);
	    	}
	    	//设置是否可左右滚动
	    	mScrollLayout.setIsScroll(true);
    }
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	/**
     * 初始化头部视图
     */
    private void initHeadView(){
	    	mHeadLogo = (ImageView)findViewById(R.id.main_head_logo);
	    	mHeadTitle = (TextView)findViewById(R.id.main_head_title);
	    	mHeadProgress = (ProgressBar)findViewById(R.id.main_head_progress);
	    	
	    	mHead_search = (ImageButton)findViewById(R.id.main_head_search);
    		mHead_search.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.i(TAG,"搜索");
				Intent intent=new Intent(MainActivity.this,SettingActivity.class);
				startActivity(intent);
			}
		});
    }
    
    /**
     * 初始化底部栏
     */
    private void initFootBar(){
    		rbFirst = (RadioButton)findViewById(R.id.main_footbar_news);
    		rbSecond = (RadioButton)findViewById(R.id.main_footbar_question);
    		rbThird = (RadioButton)findViewById(R.id.main_footbar_tweet);
    		rbFourth = (RadioButton)findViewById(R.id.main_footbar_active);
	    	
	    	fbSetting = (ImageView)findViewById(R.id.main_footbar_setting);
	    	fbSetting.setOnClickListener(new View.OnClickListener() {
	    		public void onClick(View v) {    			
	    			//展示快捷栏&判断是否登录&是否加载文章图片
	    			mGrid.show(v);
	    		}
	    	});    	
    }
    
    /**
     * 初始化水平滚动翻页
     */
    private void initPageScroll(){
	    	mScrollLayout = (ScrollLayout) findViewById(R.id.main_scrolllayout);
	    	
	    	LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main_linearlayout_footer);
	    	mHeadTitles = getResources().getStringArray(R.array.head_titles);
	    	mViewCount = mScrollLayout.getChildCount();
	    	mButtons = new RadioButton[mViewCount];
	    	
	    	for(int i = 0; i < mViewCount; i++){
	    		mButtons[i] = (RadioButton) linearLayout.getChildAt(i*2);
	    		mButtons[i].setTag(i);
	    		mButtons[i].setChecked(false);
	    		mButtons[i].setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						int pos = (Integer)(v.getTag());
						//点击当前项刷新
			    			if(mCurSel == pos) {
				    			switch (pos) {
								case 0://第一页
									Log.i(TAG, "第一页");
									break;
								case 1://第二页
									Log.i(TAG, "第二页");
									break;
								case 2://第三页
									Log.i(TAG, "第三页");
									break;
								case 3://第四页
									Log.i(TAG, "第四页");
									break;
								}
			    			}
						mScrollLayout.snapToScreen(pos);
					}
				});
	    	}
	    	
	    	//默认设置第一显示屏
	    	mCurSel = 0;
	    	mButtons[mCurSel].setChecked(true);
    	
	    	mScrollLayout.SetOnViewChangeListener(new ScrollLayout.OnViewChangeListener() {
			public void OnViewChange(int viewIndex) {
				//切换列表视图-如果列表数据为空：加载数据
				switch (viewIndex) {
					case 0://第一页
						Log.i(TAG, "第一页");
						break;
					case 1://第二页
						Log.i(TAG, "第二页");
						break;
					case 2://第三页
						Log.i(TAG, "第三页");
						break;
					case 3://第四页
						Log.i(TAG, "第四页");
						break;
				}
				setCurPoint(viewIndex);
			}
		});
    }
    
    /**
     * 设置底部栏当前焦点
     * @param index
     */
    private void setCurPoint(int index){
	    	if (index < 0 || index > mViewCount - 1 || mCurSel == index){
	    		return;
	    	}
	    	//前一个按钮选中状态设为false
	    	mButtons[mCurSel].setChecked(false);
	    	//当前按钮选中状态设为false
	    	mButtons[index].setChecked(true);  
	    	//设置当前页面的标题
	    	mHeadTitle.setText(mHeadTitles[index]);
	    	//切换为当前索引
	    	mCurSel = index;
	    	
	    	mHead_search.setVisibility(View.GONE);
		//动态显示不同界面的头部元素
	    	if(index == 0){
	    		mHeadLogo.setImageResource(R.drawable.frame_logo_news);
	    		mHead_search.setVisibility(View.VISIBLE);
	    	}else if(index == 1){
	    		mHeadLogo.setImageResource(R.drawable.frame_logo_news);
	    		mHead_search.setVisibility(View.VISIBLE);
	    	}else if(index == 2){
	    		mHeadLogo.setImageResource(R.drawable.frame_logo_news);
	    		mHead_search.setVisibility(View.VISIBLE);
	    	}else if(index == 3){
	    		mHeadLogo.setImageResource(R.drawable.frame_logo_news);
	    		mHead_search.setVisibility(View.VISIBLE);
		}
    }

	private void initQuickActionGrid() {
		mGrid = new QuickActionGrid(this);
        mGrid.addQuickAction(new MyQuickAction(this, R.drawable.ic_menu_login, R.string.main_menu_login));
        mGrid.addQuickAction(new MyQuickAction(this, R.drawable.ic_menu_myinfo, R.string.main_menu_myinfo));
        mGrid.addQuickAction(new MyQuickAction(this, R.drawable.ic_menu_software, R.string.main_menu_software));
//        mGrid.addQuickAction(new MyQuickAction(this, R.drawable.ic_menu_search, R.string.main_menu_search));
        mGrid.addQuickAction(new MyQuickAction(this, R.drawable.ic_menu_setting, R.string.main_menu_setting));
        mGrid.addQuickAction(new MyQuickAction(this, R.drawable.ic_menu_exit, R.string.main_menu_exit));
        
        mGrid.setOnQuickActionClickListener(new OnQuickActionClickListener() {
            public void onQuickActionClicked(QuickActionWidget widget, int position) {
            	
            }
        });
	}
    
    private SerialNumberAdapter serialNumberAdapter;
    private View serialNumberFooter;
    private TextView serialNumberFootMore;
    private ProgressBar serialNumberFootProgress;
    private PullToRefreshListView serialNumberRefreshListView;
//    private Handler serialNumberHandler;
//    private int serialNumberSumData;
    
    private List<Map<String,String>> serialNumberDataList=new ArrayList<Map<String,String>>();
    
    /**
     * 初始化主体框架中的数据
     */
    private void initFrameData() {
    	
    		serialNumberAdapter=new SerialNumberAdapter(this, serialNumberDataList);
    		serialNumberFooter = getLayoutInflater().inflate(R.layout.listview_footer, null);
    		
    		serialNumberFootMore = (TextView)serialNumberFooter.findViewById(R.id.listview_foot_more);
    		serialNumberFootProgress = (ProgressBar)serialNumberFooter.findViewById(R.id.listview_foot_progress);
    		serialNumberRefreshListView = (PullToRefreshListView)findViewById(R.id.frame_listview_first);
    		serialNumberRefreshListView.addFooterView(serialNumberFooter);//添加底部视图  必须在setAdapter前
    		serialNumberRefreshListView.setAdapter(serialNumberAdapter); 
    		serialNumberRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    			
	        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	        		//点击头部、底部栏无效
	        		if(position == 0 || view == serialNumberFooter){
	        			return;
	        		}
	        		Log.i(TAG,"点击了");
	        	}
		});
    		serialNumberRefreshListView.setOnScrollListener(new AbsListView.OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				serialNumberRefreshListView.onScrollStateChanged(view, scrollState);
				
				//数据为空--不用继续下面代码了
				if(serialNumberDataList.isEmpty()){
					return;
				}
				
				//判断是否滚动到底部
				boolean scrollEnd = false;
				try {
					if(view.getPositionForView(serialNumberFooter) == view.getLastVisiblePosition()){
						scrollEnd = true;
					}
				} catch (Exception e) {
					scrollEnd = false;
				}
				
				int state = StringUtils.toInt(serialNumberRefreshListView.getTag());
				if(scrollEnd && state==UIHelper.LISTVIEW_DATA_MORE){
					serialNumberRefreshListView.setTag(UIHelper.LISTVIEW_DATA_LOADING);
					serialNumberFootMore.setText(R.string.load_ing);
					serialNumberFootProgress.setVisibility(View.VISIBLE);
					//当前pageIndex
//					int pageIndex = mDataItemList.size()/8;
//					loadSerialNumberData(curNewsCatalog, pageIndex, lvNewsHandler, UIHelper.LISTVIEW_ACTION_SCROLL);
					Log.i(TAG,"onScrollStateChanged");
				}
			}
			public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
				serialNumberRefreshListView.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
			}
		});
    		serialNumberRefreshListView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
            public void onRefresh() {
            	Log.i(TAG,"setOnRefreshListener");
//            		loadSerialNumberData(curNewsCatalog, 0, lvNewsHandler, UIHelper.LISTVIEW_ACTION_REFRESH);
            }
        });					
    		
	}
    
    /**
     * 获取listview的初始化Handler
     * @param lv
     * @param adapter
     * @return
     */
//    private Handler getLvHandler(final PullToRefreshListView lv,final BaseAdapter adapter,final TextView more,final ProgressBar progress,final int pageSize){
//    	return new Handler(){
//			public void handleMessage(Message msg) {
//				if(msg.what >= 0){
//					if(msg.what < pageSize){
//						lv.setTag(UIHelper.LISTVIEW_DATA_FULL);
//						adapter.notifyDataSetChanged();
//						more.setText(R.string.load_full);
//					}else if(msg.what == pageSize){
//						lv.setTag(UIHelper.LISTVIEW_DATA_MORE);
//						adapter.notifyDataSetChanged();
//						more.setText(R.string.load_more);
//					}
//				}else if(msg.what == -1){
//					//有异常--显示加载出错 & 弹出错误消息
//					lv.setTag(UIHelper.LISTVIEW_DATA_MORE);
//					more.setText(R.string.load_error);
////					((AppException)msg.obj).makeToast(MainActivity.this);
//				}
//				if(adapter.getCount()==0){
//					lv.setTag(UIHelper.LISTVIEW_DATA_EMPTY);
//					more.setText(R.string.load_empty);
//				}
//				progress.setVisibility(ProgressBar.GONE);
//				mHeadProgress.setVisibility(ProgressBar.GONE);
//				if(msg.arg1 == UIHelper.LISTVIEW_ACTION_REFRESH){
//					lv.onRefreshComplete(getString(R.string.widget_pull_to_refresh_update) + TimeUtils.getSysTimeLong());
//					lv.setSelection(0);
//				}else if(msg.arg1 == UIHelper.LISTVIEW_ACTION_CHANGE_CATALOG){
//					lv.onRefreshComplete();
//					lv.setSelection(0);
//				}
//			}
//		};
//    }
    
    private void loadNewData(final int pageIndex,final Handler handler,final int action){ 
		mHeadProgress.setVisibility(ProgressBar.VISIBLE);		
		new Thread(){
			public void run() {				
				Message msg = new Message();
				boolean isRefresh = false;
				if(action == UIHelper.LISTVIEW_ACTION_REFRESH || action == UIHelper.LISTVIEW_ACTION_SCROLL)
					isRefresh = true;
				try {					
					Map<String,String> list=new HashMap<String,String>();		
					msg.what = list.size();
					msg.obj = list;
	            } catch (Exception e) {
	            	e.printStackTrace();
	            	msg.what = -1;
	            	msg.obj = e;
	            }
				msg.arg1 = action;
				msg.arg2 = UIHelper.LISTVIEW_DATATYPE_NEWS;
                	handler.sendMessage(msg);
			}
		}.start();
	} 
    /**
	 * 创建menu 停用原生菜单
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}
	
	/**
	 * 菜单被显示之前的事件
	 */
	public boolean onPrepareOptionsMenu(Menu menu) {
		return true;
	}

	/**
	 * 处理menu的事件
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
//		int item_id = item.getItemId();
		return true;
	}
	
	/**
	 * 监听返回--是否退出程序
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean flag = true;
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			//是否退出应用
			UIHelper.Exit(this);
		}else if(keyCode == KeyEvent.KEYCODE_MENU){
			//展示快捷栏&判断是否登录
			Log.i(TAG,"打开菜单项");
		}else if(keyCode == KeyEvent.KEYCODE_SEARCH){
			//展示搜索页
			Log.i(TAG,"打开搜索页");
		}else{
			flag = super.onKeyDown(keyCode, event);
		}
		return flag;
	}
}
