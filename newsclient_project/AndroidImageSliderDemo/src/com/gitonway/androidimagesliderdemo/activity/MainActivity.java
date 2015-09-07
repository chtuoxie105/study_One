package com.gitonway.androidimagesliderdemo.activity;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.androidimagesliderdemo.R;
import com.gitonway.androidimagesliderdemo.widget.imageslider.SliderLayout;
import com.gitonway.androidimagesliderdemo.widget.imageslider.Animations.DescriptionAnimation;
import com.gitonway.androidimagesliderdemo.widget.imageslider.Indicators.PagerIndicator;
import com.gitonway.androidimagesliderdemo.widget.imageslider.SliderTypes.BaseSliderView;
import com.gitonway.androidimagesliderdemo.widget.imageslider.SliderTypes.TextSliderView;


public class MainActivity extends Activity implements BaseSliderView.OnSliderClickListener{

	
    private SliderLayout mDemoSlider;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        mDemoSlider = (SliderLayout)findViewById(R.id.slider);

        //两种方式加载数据
        
        //加载本地
        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("GitOnWay", "http://gitonway.blog.163.com/");
        
        //加载网络
        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("世界杯-A",R.drawable.a);
        file_maps.put("世界杯-B",R.drawable.b);
        file_maps.put("世界杯-C",R.drawable.c);
        file_maps.put("世界杯-D", R.drawable.d);
        
        

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // 初始化幻灯片页面
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setOnSliderClickListener(this);

            //添加要传递的数据
            textSliderView.getBundle()
                    .putString("extra",name);

           mDemoSlider.addSlider(textSliderView);
        }
        
        
//      幻灯片切换方式  
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
//      指示符位置  
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
//      定义指示器样式  
//      mDemoSlider.setCustomIndicator(your view);
//      幻灯片循环  
//      mDemoSlider.startAutoCycle();
//      停止循环
        mDemoSlider.stopAutoCycle();
//      设置指示器的显示与否  
        mDemoSlider.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
//      设置幻灯片的转化时间  
//      mDemoSlider.setSliderTransformDuration(5000, null);
//      用来自定义幻灯片标题的显示方式  
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
//      幻灯片切换时间  
        mDemoSlider.setDuration(3000);
        
        
        
        
        
        ListView mListView = (ListView)findViewById(R.id.transformers);
        mListView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.type)));
        
        
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDemoSlider.setPresetTransformer(((TextView) view).getText().toString());
            }
        });


    }
    
    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }


}
