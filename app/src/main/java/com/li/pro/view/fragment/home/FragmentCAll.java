package com.li.pro.view.fragment.home;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.li.fragmentutils.base.BaseLazyFragment;
import com.li.pro.adapter.home.FragmentCAllAdapter;
import com.li.pro.bean.home.BeanHomeResults;
import com.li.pro.present.home.FragmentCAllPrecent;
import com.li.pro.view.ifragment.home.IFragmentCAllView;
import com.li.utils.ui.preload.PreLoader;
import com.li.utils.ui.widget.XSwipeRefreshLayout;

import rxop.li.com.rxoperation.R;

/**
 * Created by Administrator on 2016/11/10 0010.
 */

public class FragmentCAll extends BaseLazyFragment implements IFragmentCAllView, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView rv_home_all;
    private XSwipeRefreshLayout xsrl_home_all;

    @Override
    public int ftagmentLayout() {
        return R.layout.layout_fragment_c_all;
    }

    @Override
    public void initView(View view) {
        xsrl_home_all = (XSwipeRefreshLayout) view.findViewById(R.id.xsrl_home_all);
        rv_home_all = (RecyclerView) view.findViewById(R.id.rv_home_all);
    }


    @Override
    public String setToolBarTitle() {
        return null;
    }

    @Override
    public boolean isHideActionBar() {
        return false;
    }

    @Override
    public boolean isShowBackArrow() {
        return false;
    }

    @Override
    public int setLeftCornerLogo() {
        return 0;
    }

    @Override
    public void getFragmentCAllStart() {
        FragmentCAllAdapter.getInstance().clearAllData().refresh();
        PreLoader.getInstance(getActivity()).start();
    }

    @Override
    public void getFragmentCAll(BeanHomeResults beanHomeResults) {
        FragmentCAllAdapter.getInstance().addData(beanHomeResults).refresh();
    }

    @Override
    public void getFragmentCAllComplete() {
        PreLoader.getInstance(getActivity()).stop();
        xsrl_home_all.setRefreshing(false);
    }

    @Override
    public void getFragmentCAllError() {
    }

    @Override
    public void onRefresh() {
        FragmentCAllPrecent.getInstance().with(this).getFragmentCAllData(10, 1);
    }


    @Override
    protected void lazyFetchData() {
        Toast.makeText(getActivity(),  "initLazyView", Toast.LENGTH_SHORT);
        xsrl_home_all.setOnRefreshListener(this);
        xsrl_home_all.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
        xsrl_home_all.setRefreshing(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rv_home_all.setLayoutManager(linearLayoutManager);
        rv_home_all.setHasFixedSize(true);

        FragmentCAllAdapter fragmentCAllAdapter = FragmentCAllAdapter.getInstance().init(getActivity());
        rv_home_all.setAdapter(fragmentCAllAdapter);
        FragmentCAllPrecent.getInstance().with(this).getFragmentCAllData(10, 1);
    }
}