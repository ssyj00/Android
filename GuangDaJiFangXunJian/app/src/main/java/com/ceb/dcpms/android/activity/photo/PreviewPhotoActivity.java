package com.ceb.dcpms.android.activity.photo;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.ceb.dcpms.android.Constants;
import com.ceb.dcpms.android.R;
import com.ceb.dcpms.android.activity.BaseActivity;
import com.ceb.dcpms.android.view.photo.HackyViewPager;
import com.ceb.dcpms.android.view.photo.PhotoView;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>PreviewPhotoActivity.java</p>
 * <p>图片查看界面</p>
 *
 * @author		孙广智(tony.u.sun@163.com)
 * @version		0.0.1
 * <table style="border:1px solid gray;">
 * <tr>
 * <th width="100px">版本号</th><th width="100px">动作</th><th width="100px">修改人</th><th width="100px">修改时间</th>
 * </tr>
 * <!-- 以 Table 方式书写修改历史 -->
 * <tr>
 * <td>0.0.1</td><td>创建类</td><td>sunguangzhi</td><td>2014-12-16 下午1:06:40</td>
 * </tr>
 * <tr>
 * <td>XXX</td><td>XXX</td><td>XXX</td><td>XXX</td>
 * </tr>
 * </table>
*/
public class PreviewPhotoActivity extends BaseActivity {
	
	
	/**
	 * 分页控件
	 */
	private HackyViewPager mViewPager;
	/**
	 * 图片浏览开始位置
	 */
	private int position = 0;
	/**
	 * 等待圈
	 */
	private ProgressBar progressBar;

	private PreviewPhotoPageerAdapter adapter;
	private String url;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview_photo);
		
		progressBar = (ProgressBar) findViewById(R.id.progressBar);

		if(getIntent() != null){
			Bundle extras = getIntent().getExtras();
			url = extras.getString(Constants.Tag.data);
		}

		findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

		mViewPager = (HackyViewPager) findViewById(R.id.viewPager);

		new Thread(){
			public void run(){

				List<Drawable> drawables = new ArrayList<Drawable>();
				Drawable dr = new ColorDrawable(getResources().getColor(android.R.color.transparent));
				drawables.add(dr);

				adapter = new PreviewPhotoPageerAdapter(drawables);
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						progressBar.setVisibility(View.GONE);
						mViewPager.setAdapter(adapter);
					}
				});
			}
		}.start();
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
	}

	class PreviewPhotoPageerAdapter extends PagerAdapter {
		
		private List<Drawable> list = null;
		
		public PreviewPhotoPageerAdapter(List<Drawable> list){
			this.list = list;
		}

		@Override
		public int getCount() {
			return list.size();
		}
		
		public void remove(int position){
			list.remove(position);
		}
		
		@Override
		public View instantiateItem(ViewGroup container, final int position) {
			final PhotoView photoView = new PhotoView(container.getContext());
			
			if(list.get(position) instanceof BitmapDrawable)
				photoView.setImageDrawable(list.get(position));
			else {
				Glide
				.with(PreviewPhotoActivity.this)
				.load(url)
				.into(photoView);
			}

			container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

			return photoView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
		
	}

}
