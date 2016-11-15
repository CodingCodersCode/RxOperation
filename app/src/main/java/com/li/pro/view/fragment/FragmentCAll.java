package com.li.pro.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.li.fragmentutils.base.BaseLazyFragment;
import com.li.pro.adapter.home.FragmentCAllAdapter;
import com.li.pro.api.URLConst;
import com.li.pro.bean.home.BeanHomeBase;
import com.li.pro.bean.home.BeanHomeResults;
import com.li.pro.view.ifragment.IFragmentCAllView;
import com.li.utils.ui.widget.XSwipeRefreshLayout;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rxop.li.com.rxoperation.R;

/**
 * Created by Administrator on 2016/11/10 0010.
 */

public class FragmentCAll extends BaseLazyFragment implements IFragmentCAllView {
    private RecyclerView rv_home_all;
    private XSwipeRefreshLayout xsrl_home_all;

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public int ftagmentLayout() {
        return R.layout.layout_fragment_c_all;
    }

    @Override
    public void initView(View view) {

        xsrl_home_all = (XSwipeRefreshLayout) view.findViewById(R.id.xsrl_home_all);
        rv_home_all = (RecyclerView) view.findViewById(R.id.rv_home_all);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rv_home_all.setLayoutManager(linearLayoutManager);
        rv_home_all.setHasFixedSize(true);
        Toast.makeText(getActivity(),getActivity()+"===",Toast.LENGTH_SHORT).show();
        FragmentCAllAdapter fragmentCAllAdapter= FragmentCAllAdapter.getInstance().init(getActivity());
        rv_home_all.setAdapter(fragmentCAllAdapter);
//        FragmentCAllPrecent.getInstance().with(this).getFragmentCAllData(10,1);
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(URLConst.URL_GANK_IO_BASE + "all/20/1").build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                BeanHomeBase beanHomeBase = new Gson().fromJson(json, BeanHomeBase.class);
                Observable.from(beanHomeBase.getResults()).
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(new Action1<BeanHomeResults>() {
                            @Override
                            public void call(BeanHomeResults beanHomeResults) {
                                FragmentCAllAdapter.getInstance().addData(beanHomeResults).refresh();
                            }
                        });
            }
        });
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

    }

    @Override
    public void getFragmentCAll(BeanHomeBase beanHomeResults) {
        System.out.print("======" + beanHomeResults);
        Toast.makeText(getActivity(), beanHomeResults + "", Toast.LENGTH_SHORT).show();
//        Observable.
//                from(beanHomeResults.getResults()).
//                subscribeOn(Schedulers.io()).
//                observeOn(AndroidSchedulers.mainThread()).
//                subscribe(new Action1<BeanHomeResults>() {
//            @Override
//            public void call(BeanHomeResults beanHomeResults) {
//                FragmentCAllAdapter.newInstance(getActivity()).addData(beanHomeResults);
//            }
//        });
    }

    @Override
    public void getFragmentCAllComplete() {

    }

    @Override
    public void getFragmentCAllError() {

    }
}
