package zoushihui.bawei.www.recycler_view;

import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HashMap<String, String> hashMap = new HashMap<>();
    private String url = "http://result.eolinker.com/k2BaduF2a6caa275f395919a66ab1dfe4b584cc60685573?uri=gj";
    private FragBean fragBean;
    private RecyleAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshWidget;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mSwipeRefreshWidget = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);
        recyclerView.addItemDecoration(new MyRecyclerView(MainActivity.this));
        mSwipeRefreshWidget.setColorSchemeColors(Color.BLUE, Color.BLACK,
                Color.RED, Color.YELLOW);
        // 这句话是为了，第一次进入页面的时候显示加载进度条
//        mSwipeRefreshWidget.setProgressViewOffset(false, 0, (int) TypedValue
//                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
//                        .getDisplayMetrics()));
//        mSwipeRefreshWidget.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//            }
//        });
        initHttp();
        initHttpT();

    }

    private void initHttpT() {
        //创建适配器
        adapter = new RecyleAdapter(this);
        gridLayoutManager = new GridLayoutManager(this, 3);
        int a = 3;
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(a, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);

    }

    private void initHttp() {
        RequestParams params = new RequestParams();
        params.setUri(url);
        if (hashMap != null) {
            Iterator<String> iterator = hashMap.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = hashMap.get(key);
                params.addQueryStringParameter(key, value);
            }
        }
        x.http().get(params, new Callback.CommonCallback<String>() {


            @Override
            public void onSuccess(String result) {
                System.out.println("123123" + result);
                Gson gson = new Gson();
                fragBean = gson.fromJson(result, FragBean.class);
                adapter.setPresenter(fragBean);
                adapter.setData(fragBean.getResult().getData());
                recyclerView.setAdapter(adapter);
                initPullRefresh();
                initLoadMoreListener();
            }


            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void initPullRefresh() {
        mSwipeRefreshWidget.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        List<String> headDatas = new ArrayList<String>();
//                        for (int i = 20; i <30 ; i++) {
//
//                            headDatas.add("Heard Item "+i);
//                        }

                        List<FragBean.ResultBean.DataBean> data = fragBean.getResult().getData();
                        adapter.AddHeaderItem(data);

                        //刷新完成
                        mSwipeRefreshWidget.setRefreshing(false);
                        Toast.makeText(MainActivity.this, "更新了 " + data.size() + " 条目数据", Toast.LENGTH_SHORT).show();
                    }

                }, 3000);
            }
        });
    }

    private void initLoadMoreListener() {
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                        List<String> headDatas = new ArrayList<String>();
//                        for (int i = 20; i <30 ; i++) {
//
//                            headDatas.add("Heard Item "+i);
//                        }

                            List<FragBean.ResultBean.DataBean> data = fragBean.getResult().getData();
                            adapter.AddFootItem(data);
                            Toast.makeText(MainActivity.this, "更新了 " + data.size() + " 条目数据", Toast.LENGTH_SHORT).show();
                        }

                    }, 3000);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                //最后一个可见的ITEM
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });
    }


}
