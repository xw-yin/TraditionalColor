package com.ww.traditionalcolor;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ww.traditionalcolor.weight.RingProgressBar;
import com.ww.traditionalcolor.weight.ScrollNumberView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RingProgressBar c_ring;
    private RingProgressBar m_ring;
    private RingProgressBar y_ring;
    private RingProgressBar k_ring;
    private ScrollNumberView r_number;
    private ScrollNumberView g_number;
    private ScrollNumberView b_number;
    private TextView tv_name_ja;
    private TextView tv_name_en;
    private ColorAdapter colorAdapter;
    private List<ColorData> colorList;
    private LinearLayout root;
    private int startColor;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Type listType = new TypeToken<List<ColorData>>() {
        }.getType();
        String json=getJson("nipponcolor.json",this);
        //这里的json是字符串类型 = jsonArray.toString();
        colorList = new Gson().fromJson(json, listType );
        colorAdapter=new ColorAdapter(colorList,R.layout.item_color,this);
        colorAdapter.setOnItemClickListener(new ColorAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                final ColorData nipponColor = colorList.get(position);
                int rgb = Color.parseColor('#' + nipponColor.getColor());
                c_ring.setProgress(nipponColor.getC());
                m_ring.setProgress(nipponColor.getM());
                y_ring.setProgress(nipponColor.getY());
                k_ring.setProgress(nipponColor.getK());
                r_number.setNumberSetOfInt(Color.red(rgb)).startAnim();
                g_number.setNumberSetOfInt(Color.green(rgb)).startAnim();
                b_number.setNumberSetOfInt(Color.blue(rgb)).startAnim();
                decreaseTextAlpha();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tv_name_ja.setText(nipponColor.getJpname());
                        tv_name_en.setText(nipponColor.getEnname());
                    }
                },500);
                increaseTextAlpha();
                changeBg(startColor,rgb);
                startColor=rgb;
            }
        });
        recyclerView.setAdapter(colorAdapter);
    }
    private void increaseTextAlpha(){
        ObjectAnimator objectAnimatorja=ObjectAnimator.ofFloat(tv_name_ja,"alpha",0f,1f);
        objectAnimatorja.setDuration(500);
        objectAnimatorja.setStartDelay(500);
        objectAnimatorja.start();
        ObjectAnimator objectAnimatoren=ObjectAnimator.ofFloat(tv_name_en,"alpha",0f,1f);
        objectAnimatoren.setDuration(500);
        objectAnimatoren.setStartDelay(500);
        objectAnimatoren.start();
    }
    private void decreaseTextAlpha(){
        ObjectAnimator objectAnimatorja=ObjectAnimator.ofFloat(tv_name_ja,"alpha",1f,0f);
        objectAnimatorja.setDuration(500);
        objectAnimatorja.start();
        ObjectAnimator objectAnimatoren=ObjectAnimator.ofFloat(tv_name_en,"alpha",1f,0f);
        objectAnimatoren.setDuration(500);
        objectAnimatoren.start();
    }
    private void changeBg(int startColor,int endColor){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ObjectAnimator objectAnimatorStatusBar=ObjectAnimator.ofInt(getWindow(),"statusBarColor",startColor,endColor);
            objectAnimatorStatusBar.setEvaluator(new ArgbEvaluator());
            objectAnimatorStatusBar.setDuration(1000);
            objectAnimatorStatusBar.start();
        }

        ObjectAnimator objectAnimatorToolbar=ObjectAnimator.ofInt(toolbar,"backgroundColor",startColor,endColor);
        ObjectAnimator objectAnimatorbg=ObjectAnimator.ofInt(root,"backgroundColor",startColor,endColor);
        objectAnimatorbg.setEvaluator(new ArgbEvaluator());
        objectAnimatorbg.setDuration(1000);
        objectAnimatorbg.start();
        objectAnimatorToolbar.setEvaluator(new ArgbEvaluator());
        objectAnimatorToolbar.setDuration(1000);
        objectAnimatorToolbar.start();
    }
    private void initView() {
        recyclerView =findViewById(R.id.rv_list);
        Configuration config = getResources().getConfiguration();
        // 如果当前是横屏
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            recyclerView.setLayoutManager(new GridLayoutManager(this,6));
        }
        // 如果当前是竖屏
        if (config.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        }
        root=findViewById(R.id.root);
        c_ring = (RingProgressBar) findViewById(R.id.c_ring);
        m_ring = (RingProgressBar) findViewById(R.id.m_ring);
        y_ring = (RingProgressBar) findViewById(R.id.y_ring);
        k_ring = (RingProgressBar) findViewById(R.id.k_ring);

        r_number = (ScrollNumberView) findViewById(R.id.r_number);
        g_number = (ScrollNumberView) findViewById(R.id.g_number);
        b_number = (ScrollNumberView) findViewById(R.id.b_number);

        tv_name_ja = (TextView) findViewById(R.id.color_name_ja);
        tv_name_en = (TextView) findViewById(R.id.color_name_en);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    @Override
    protected void onResume() {
        super.onResume();
        int initColor=Color.parseColor("#DC9FB4");
        startColor=initColor;
        c_ring.setProgress(2);
        m_ring.setProgress(43);
        y_ring.setProgress(3);
        k_ring.setProgress(0);
        root.setBackgroundColor(initColor);
        r_number.setCurrentNumberOfInt(220);
        g_number.setCurrentNumberOfInt(159);
        b_number.setCurrentNumberOfInt(180);
        tv_name_ja.setText("撫子");
        tv_name_en.setText("NADESHIKO");
        toolbar.setBackgroundColor(initColor);
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.trans_black_75));
    }
    public static String getJson(String fileName,Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
