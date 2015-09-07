package com.example.newsclient.adapter;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;
import com.example.newsclient.R;
import com.example.newsclient.bean.HttpGetDataBeanOne;
import com.example.newsclient.httpclient.HttpGetBitmapTool;
import com.example.newsclient.httpclient.HttpGetBitmapTool.BitmapNotifi;
import com.example.newsclient.slidingmenu.activity.RequestManager;

public class ListContentHeadAdapter extends BaseAdapter {
	private List<HttpGetDataBeanOne> mList = new ArrayList<HttpGetDataBeanOne>();
	private static final int LIST_ITEM_CONTENT = 2;// 表示ListAdapter显示的数据有2中类型
	private static final int LIST_ITEM_CONTENT_ONE = 0;// 区分类型的:第一种类型
	private static final int LIST_ITEM_CONTENT_TWO = 1;// 区分类型的:第二种类型
	private LayoutInflater infalter;
	private HttpGetBitmapTool mHttpGetBitmapTool;

	public ListContentHeadAdapter(Context context) {
		infalter = LayoutInflater.from(context);
		mHttpGetBitmapTool = HttpGetBitmapTool.setIntanceCaseBitmap();
	}

	public int getCount() {
		return mList.size();
	}

	public void setData(List<HttpGetDataBeanOne> list) {
		mList = list;
		notifyDataSetChanged();
	}

	public void addData(List<HttpGetDataBeanOne> list) {
		mList.addAll(list);
		notifyDataSetChanged();
	}

	public int getViewTypeCount() {
		return LIST_ITEM_CONTENT;
	}

	public int getItemViewType(int position) {
		HttpGetDataBeanOne bean = (HttpGetDataBeanOne) getItem(position);
		if (bean.getImgStringList() != null) {
		}
		if (bean.getImgStringList() != null) {
			return LIST_ITEM_CONTENT_TWO;
		} else {
			return LIST_ITEM_CONTENT_ONE;
		}
	}

	public Object getItem(int position) {
		return mList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	public View getOneView(int position, View convertView, ViewGroup parent) {

		final ItemOneView mItemOneView;
		if (convertView == null) {
			convertView = infalter.inflate(
					R.layout.list_item_content_one_layout, null);
			mItemOneView = new ItemOneView();
			mItemOneView.itemOneImg = (NetworkImageView) convertView
					.findViewById(R.id.list_item_one_img);
			mItemOneView.itemOneTitleTxt = (TextView) convertView
					.findViewById(R.id.list_item_one_title);
			mItemOneView.itemOneContentTxt = (TextView) convertView
					.findViewById(R.id.list_item_one_content);
			mItemOneView.itemOneFollowTxt = (TextView) convertView
					.findViewById(R.id.list_item_one_follow);
			convertView.setTag(mItemOneView);
		} else {
			mItemOneView = (ItemOneView) convertView.getTag();
		}
		HttpGetDataBeanOne bean = (HttpGetDataBeanOne) getItem(position);

		mItemOneView.itemOneTitleTxt.setText(bean.getTitle());
		mItemOneView.itemOneContentTxt.setText(bean.getDigest());
		String reply = bean.getReplyCount();

		if (reply != null) {
			mItemOneView.itemOneFollowTxt.setText(bean.getReplyCount() + "人跟帖");
		} else {
			mItemOneView.itemOneFollowTxt.setText("");
		}
		String urls = bean.getImgsrc();

		newWorkImageView(urls, mItemOneView.itemOneImg);

		return convertView;
	}

	@SuppressLint("InflateParams")
	public View getTwoView(int position, View convertView, ViewGroup parent) {
		final ItemTwoView mItemOneView;
		if (convertView == null) {
			convertView = infalter.inflate(
					R.layout.list_item_content_two_layout, null);
			mItemOneView = new ItemTwoView();
			mItemOneView.itemTwoImgOne = (NetworkImageView) convertView
					.findViewById(R.id.list_item_two_img_one);
			mItemOneView.itemTwoImgTwo = (NetworkImageView) convertView
					.findViewById(R.id.list_item_two_img_two);
			mItemOneView.itemTwoImgThree = (NetworkImageView) convertView
					.findViewById(R.id.list_item_two_img_three);
			mItemOneView.itemTwoTitleTxt = (TextView) convertView
					.findViewById(R.id.list_item_two_title);
			mItemOneView.itemTwoFollowTxt = (TextView) convertView
					.findViewById(R.id.list_item_two_follow);

			convertView.setTag(mItemOneView);
		} else {
			mItemOneView = (ItemTwoView) convertView.getTag();
		}
		HttpGetDataBeanOne bean = (HttpGetDataBeanOne) getItem(position);

		mItemOneView.itemTwoTitleTxt.setText(bean.getTitle());
		if (!bean.getReplyCount().equals("")) {
			mItemOneView.itemTwoFollowTxt.setText(bean.getReplyCount() + "人跟帖");
		}
		String urls = bean.getImgsrc();
		String[] imgUrlList = bean.getImgStringList();
		// 横向的3张图片
		newWorkImageView(urls, mItemOneView.itemTwoImgOne);
		newWorkImageView(imgUrlList[0], mItemOneView.itemTwoImgTwo);
		newWorkImageView(imgUrlList[1], mItemOneView.itemTwoImgThree);
		// onRequestByImageLoader(urls, mItemOneView.itemTwoImgOne);
		// onRequestByImageLoader(imgUrlList[0], mItemOneView.itemTwoImgTwo);
		// onRequestByImageLoader(imgUrlList[1], mItemOneView.itemTwoImgThree);
		// AsyncImg(urls, mItemOneView.itemTwoImgOne);
		// AsyncImg(imgUrlList[0], mItemOneView.itemTwoImgTwo);
		// AsyncImg(imgUrlList[1], mItemOneView.itemTwoImgThree);

		return convertView;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (getItemViewType(position) == LIST_ITEM_CONTENT_ONE) {
			convertView = getOneView(position, convertView, parent);
		} else {
			convertView = getTwoView(position, convertView, parent);
		}
		return convertView;
	}

	/** 封装取图片：含地址和控件参数 */
	public void AsyncImg(String imgUrl, final ImageView imgView) {
		mHttpGetBitmapTool.creatThreadByBitmap(imgUrl, new BitmapNotifi() {
			public void getBitmapByhttp(Bitmap bitmap) {
				imgView.setImageBitmap(bitmap);
			}

			public void getBitmapFail(String resfial) {

			}
		});
	}

	/** volley框架的第二种图片加载方式，但是有错位的错误 **/
//	private void onRequestByImageLoader(String url, ImageView imgView) {
//		ImageLoader loader = RequestManager.getImageLoader();
//
//		ImageListener listener = ImageLoader.getImageListener(imgView,
//				R.drawable.moren_bitmap, android.R.drawable.ic_input_delete);
//
//		loader.get(url, listener, 0, 0);// 获取图片
//	}

	/** volley框架的自定义控件，来加载图片，有缓存和防止图片错位的处理 */
	public void newWorkImageView(String url, NetworkImageView netImgageView) {
		if (url != null && !url.equals("")) {
			netImgageView.setDefaultImageResId(R.drawable.news_bg1);
			netImgageView.setErrorImageResId(R.drawable.item_img_fail);
			netImgageView.setImageUrl(url, RequestManager.getImageLoader());
		}
	}

	public class ItemOneView {
		public NetworkImageView itemOneImg;
		public TextView itemOneTitleTxt;
		public TextView itemOneContentTxt;
		public TextView itemOneFollowTxt;
	}

	public class ItemTwoView {
		public NetworkImageView itemTwoImgOne;
		public NetworkImageView itemTwoImgTwo;
		public NetworkImageView itemTwoImgThree;
		public TextView itemTwoTitleTxt;
		public TextView itemTwoFollowTxt;
	}

}
