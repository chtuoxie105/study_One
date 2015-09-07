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

        //���ַ�ʽ��������
        
        //���ر���
        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("GitOnWay", "http://gitonway.blog.163.com/");
        
        //��������
        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("���籭-A",R.drawable.a);
        file_maps.put("���籭-B",R.drawable.b);
        file_maps.put("���籭-C",R.drawable.c);
        file_maps.put("���籭-D", R.drawable.d);
        
        

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // ��ʼ���õ�Ƭҳ��
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setOnSliderClickListener(this);

            //���Ҫ���ݵ�����
            textSliderView.getBundle()
                    .putString("extra",name);

           mDemoSlider.addSlider(textSliderView);
        }
        
        
//      �õ�Ƭ�л���ʽ  
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
//      ָʾ��λ��  
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
//      ����ָʾ����ʽ  
//      mDemoSlider.setCustomIndicator(your view);
//      �õ�Ƭѭ��  
//      mDemoSlider.startAutoCycle();
//      ֹͣѭ��
        mDemoSlider.stopAutoCycle();
//      ����ָʾ������ʾ���  
        mDemoSlider.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
//      ���ûõ�Ƭ��ת��ʱ��  
//      mDemoSlider.setSliderTransformDuration(5000, null);
//      �����Զ���õ�Ƭ�������ʾ��ʽ  
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
//      �õ�Ƭ�л�ʱ��  
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
