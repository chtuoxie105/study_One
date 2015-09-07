package com.warmtel.android.slider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.scxh.slider.library.SliderLayout;
import com.scxh.slider.library.SliderTypes.BaseSliderView;
import com.scxh.slider.library.SliderTypes.TextSliderView;

public class SliderFragment extends Fragment {
	private ListView mListView;
	private View mHeaderView;
	private SliderLayout mSliderLayout;
	private SwipeRefreshLayout mSwipeRefreshLayout;
	
	public static Fragment newInstance(){
		SliderFragment fragment = new SliderFragment();
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.reference_slider_main, container, false);
		
		mListView = (ListView) v.findViewById(R.id.myslider_listview);
		
		mHeaderView = inflater.inflate(R.layout.reference_slider_item_layout, null);
		mSliderLayout = (SliderLayout) mHeaderView.findViewById(R.id.my_slider);
		
		mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swip);
		mSwipeRefreshLayout.setColorScheme(
				android.R.color.holo_blue_bright,
				android.R.color.holo_orange_dark,
				android.R.color.holo_green_dark, android.R.color.holo_red_dark);
		return v;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		HashMap<String,String> http_url_maps = getData();
		for(String name : http_url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(getActivity());
  
            textSliderView
                    .description(name)
                    .image(http_url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop);

            mSliderLayout.addSlider(textSliderView);
        }
		
		mListView.addHeaderView(mHeaderView);
		
		MyBaseAdapter mAdapter = new MyBaseAdapter(getActivity());
		mListView.setAdapter(mAdapter);
		
		mAdapter.setData(getDataContent());
		
		
		mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				
				new AsyncTask<String, Void, String>(){

					@Override
					protected String doInBackground(String... params) {
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						return "";
					}
					@Override
					protected void onPostExecute(String result) {
						super.onPostExecute(result);
						
						mSwipeRefreshLayout.setRefreshing(false); //停止刷新
					}
					
				}.execute();
			}
		});
		
	}
	public class MyBaseAdapter extends BaseAdapter {
		private Context mContext;
		private List<ContentBean> mList = new ArrayList<ContentBean>();
		private LayoutInflater mInflater;  //把xml布局文件转换成View对象

		public MyBaseAdapter(Context context) {
			mContext = context;
			mInflater = LayoutInflater.from(context);
		}
		
		/**
		 * 设置数据源，刷新适配器
		 * 
		 * @param list
		 */
		public void setData(List<ContentBean> list) {
			mList = list;
			notifyDataSetChanged();//通知刷新适配器数据
		}

		/**
		 * 返回容器中元素个数
		 */
		@Override
		public int getCount() {
			return mList.size();
		}
		/**
		 * 返回容器中指定位置的数据项
		 */
		@Override
		public Object getItem(int position) {
			return mList.get(position);
		}
		/**
		 * 返回容器中指定位置的ID
		 */
		@Override
		public long getItemId(int position) {
			return position;
		}
		
		/**
		 * 返回表示行的view
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		

			ViewHolder viewHodler = null;
			if (convertView == null) {
				//将第一个参数指定的布局文件转换成View对象,如果第二参数ViewGroup不为空，则把view对象添加到该ViewGroup中
				convertView = mInflater.inflate(R.layout.item_simple_listview1_layout, null);
				
				viewHodler = new ViewHolder();
				viewHodler.iconImg = (ImageView) convertView.findViewById(R.id.icon_img);
				viewHodler.titleTxt = (TextView) convertView.findViewById(R.id.title_txt);
				viewHodler.contentTxt = (TextView) convertView.findViewById(R.id.content_txt);
				
				convertView.setTag(viewHodler);
				
			} else {
				viewHodler = (ViewHolder) convertView.getTag();
				
			}

			ContentBean content = (ContentBean) getItem(position);
			
			//更新第一项中数据
			viewHodler.iconImg.setBackgroundResource(content.getIcon());
			viewHodler.titleTxt.setText(content.getTitle());
			viewHodler.contentTxt.setText(content.getContent());

			return convertView;
		}

		class ViewHolder {
			ImageView iconImg;
			TextView titleTxt;
			TextView contentTxt;
		}

	}
	private HashMap<String,String> getData(){
		HashMap<String,String> http_url_maps = new HashMap<String, String>();
        http_url_maps.put("习近平接受八国新任驻华大使递交国书", "http://img.my.csdn.net/uploads/201407/26/1406383291_6518.jpg");
        http_url_maps.put("天津港总裁出席发布会", "http://img.my.csdn.net/uploads/201407/26/1406383290_9329.jpg");
        http_url_maps.put("瑞海公司从消防鉴定到安评一路畅通无阻", "http://img.my.csdn.net/uploads/201407/26/1406383290_1042.jpg");
        http_url_maps.put("Airbnb高调入华 命运将如Uber一样吗？", "http://img.my.csdn.net/uploads/201407/26/1406383275_3977.jpg");
        
        return http_url_maps;
	}
	
	public List<ContentBean> getDataContent(){
		List<ContentBean> list = new ArrayList<ContentBean>();
		
		ContentBean content = new ContentBean();
		content.setIcon(R.drawable.list1);
		content.setTitle("1 避风塘");
		content.setContent("[53店通用] 代金券，每桌最多可用3张！除购");
		list.add(content);
		
		
		content = new ContentBean();
		content.setIcon(R.drawable.list2);
		content.setTitle("2 必胜客");
		content.setContent("[70店通用] 代金券，全场通用，可叠加，不限");
		list.add(content);
		
		
		content = new ContentBean();
		content.setIcon(R.drawable.list3);
		content.setTitle("3 伊秀寿司");
		content.setContent("[14店通用] 代金券，全场通用，可叠加，不限");
		list.add(content);
		
		content = new ContentBean();
		content.setIcon(R.drawable.list2);
		content.setTitle("4 必胜客1");
		content.setContent("[70店通用] 代金券，全场通用，可叠加，不限");
		list.add(content);
		
		
		content = new ContentBean();
		content.setIcon(R.drawable.list3);
		content.setTitle("5 伊秀寿司1");
		content.setContent("[14店通用] 代金券，全场通用，可叠加，不限");
		list.add(content);
		
		
		content = new ContentBean();
		content.setIcon(R.drawable.list2);
		content.setTitle("6 必胜客1");
		content.setContent("[70店通用] 代金券，全场通用，可叠加，不限");
		list.add(content);
		
		
		content = new ContentBean();
		content.setIcon(R.drawable.list3);
		content.setTitle("7 伊秀寿司2");
		content.setContent("[14店通用] 代金券，全场通用，可叠加，不限");
		list.add(content);
		
		
		content = new ContentBean();
		content.setIcon(R.drawable.list2);
		content.setTitle("8 必胜客4");
		content.setContent("[70店通用] 代金券，全场通用，可叠加，不限");
		list.add(content);
		
		
		content = new ContentBean();
		content.setIcon(R.drawable.list3);
		content.setTitle("9 伊秀寿司9");
		content.setContent("[14店通用] 代金券，全场通用，可叠加，不限");
		list.add(content);
		
		content = new ContentBean();
		content.setIcon(R.drawable.list2);
		content.setTitle("10 必胜客2");
		content.setContent("[70店通用] 代金券，全场通用，可叠加，不限");
		list.add(content);
		
		
		content = new ContentBean();
		content.setIcon(R.drawable.list3);
		content.setTitle("11 伊秀寿司6");
		content.setContent("[14店通用] 代金券，全场通用，可叠加，不限");
		list.add(content);
		
		return list;
	}
	
}
