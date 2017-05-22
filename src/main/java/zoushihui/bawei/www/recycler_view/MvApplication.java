package zoushihui.bawei.www.recycler_view;

import android.app.Application;

import org.xutils.BuildConfig;
import org.xutils.x;

/**
 * 类的作用：
 * 类的思路：
 * 作者：岳俊飞
 * 时间：2017/5/17
 */

public class MvApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
    }
}
