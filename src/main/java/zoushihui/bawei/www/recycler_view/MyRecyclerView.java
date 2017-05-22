package zoushihui.bawei.www.recycler_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Title:
 * Thinking:
 * 作者：邹诗惠 on 2017/5/18 20:41
 */

public class MyRecyclerView extends RecyclerView.ItemDecoration {
    Context mContext;

    public MyRecyclerView(Context context) {
        this.mContext = context;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = parent.getChildAt(i);
            int left = view.getLeft();
            int right = view.getRight();
            int top = view.getBottom()-10;
            int bottom = view.getBottom();
            Paint paint = new Paint();
            paint.setColor(Color.BLUE);
            c.drawRect(left,top,right,bottom,paint);
        }
    }
}
