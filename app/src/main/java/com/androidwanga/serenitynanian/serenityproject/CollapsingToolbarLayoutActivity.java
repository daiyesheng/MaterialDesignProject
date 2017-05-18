package com.androidwanga.serenitynanian.serenityproject;

import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CollapsingToolbarLayoutActivity extends AppCompatActivity {

    private Toolbar mToolbar ;
    private RecyclerView mRecycler ;
    private AppBarLayout mAppBarLayout ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapsing_toolbar_layout);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //设置actionbar上的返回箭头距离标题的具体
        mToolbar.setContentInsetStartWithNavigation(0);

        mRecycler = (RecyclerView) findViewById(R.id.collapsingToolbarLayoutRecycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        CollapsingToolbarLayoutRecyclerAdapter adapter = new CollapsingToolbarLayoutRecyclerAdapter() ;
        mRecycler.setAdapter(adapter);

        handleData(adapter);

        mAppBarLayout = (AppBarLayout) findViewById(R.id.appbarlayout);

        /**
         * 监听appBarLayout的折叠和展开状态，在初始化时就会调用一次
         */
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //appBarLayout.getHeight()   appbarlayout的总高度
                //appBarLayout.getTotalScrollRange()  appbar能够滚动的高度
                //verticalOffset 垂直的滚动距离，往上滚是负值 初始化时为0
                System.out.println("appBarheight="+appBarLayout.getHeight()+",getTotalScrollRange = [" + appBarLayout.getTotalScrollRange() + "], verticalOffset = [" + verticalOffset + "]");
            }
        });

    }

    private void handleData(CollapsingToolbarLayoutRecyclerAdapter adapter) {

        List<String> list = new ArrayList<>();
        for (int i = 0 ; i<30 ;i++) {
            list.add("进京证申请:" + i);
        }
        adapter.setData(list);
        adapter.notifyDataSetChanged();
    }


    public static class CollapsingToolbarLayoutRecyclerAdapter extends RecyclerView.Adapter<CollapsingToolbarLayoutRecyclerAdapter.MyViewHodler>{

        private List<String> list = null ;

        public void setData(List<String> data) {
            this.list = data ;
        }

        @Override
        public MyViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHodler(LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_sheet_item,null));
        }

        @Override
        public void onBindViewHolder(MyViewHodler holder, int position) {
            holder.name.setText(list.get(position));
            holder.index.setText(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list == null ? 0 :list.size();
        }



        public static class MyViewHodler extends RecyclerView.ViewHolder{

            private TextView name ;
            private TextView index ;

            public MyViewHodler(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.bottom_sheet_dialog_item_name);
                index = (TextView) itemView.findViewById(R.id.bottom_sheet_dialog_item_index);
            }
        }
    }



}
