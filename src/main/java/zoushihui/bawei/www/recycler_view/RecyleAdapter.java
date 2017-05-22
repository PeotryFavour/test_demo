package zoushihui.bawei.www.recycler_view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Title:
 * Thinking:
 * 作者：邹诗惠 on 2017/5/17 21:51
 */

public class RecyleAdapter extends RecyclerView.Adapter {

    private Context mcontext;
    private List<FragBean.ResultBean.DataBean> list = new ArrayList<>();
    private FragBean mHomePresenter;

    public RecyleAdapter(Context context) {
        this.mcontext = context;
    }

    public void setPresenter(FragBean presenter) {
        this.mHomePresenter = presenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mcontext, R.layout.home_item, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.textView.setText(list.get(position).getTitle());
        getiamgeforServer(myViewHolder.imageView, list.get(position).getThumbnail_pic_s());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<FragBean.ResultBean.DataBean> data) {
        if (data != null) {
            list.addAll(data);
        }
    }

    public void getiamgeforServer(ImageView imageView, String url) {
        x.image().bind(imageView, url);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }

        private void initListener(View itemView) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mcontext, "poistion " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void AddHeaderItem(List<FragBean.ResultBean.DataBean> list) {
        this.list.addAll(0, list);
        notifyDataSetChanged();
    }

    public void AddFootItem(List<FragBean.ResultBean.DataBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }
}
