package com.start.cysz6;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SerialNumberAdapter  extends BaseAdapter{

	private Context mContext;
	private List<Map<String,String>> mDataList;
	
	public SerialNumberAdapter(Context context,List<Map<String,String>> dataList){
		this.mContext=context;
		this.mDataList=dataList;
	}
	
	@Override
	public int getCount() {
		return mDataList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ListItemView  listItemView;
		if (convertView == null) {
			//获取list_item布局文件的视图
			convertView = LayoutInflater.from(mContext).inflate(com.start.cysz6.R.layout.listivew_item_serialnumber, null);
			
			listItemView = new ListItemView();
			//获取控件对象
			listItemView.title=(TextView)convertView.findViewById(R.id.listview_item_serialnumber_title);
			
			//设置控件集到convertView
			convertView.setTag(listItemView);
		}else {
			listItemView = (ListItemView)convertView.getTag();
		}	
		//设置文字和图片
		Map<String,String> data = mDataList.get(position);
		listItemView.title.setText(data.get("title"));
		
		return convertView;
	}
	
	static class ListItemView{
        public TextView title;
	    public TextView author;
	    public TextView date;
	    public TextView count;
	    public ImageView flag;
	}  

}
