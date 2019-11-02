package com.github.isuperred.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.FocusHighlight;
import androidx.leanback.widget.FocusHighlightHelper;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.Presenter;

import com.github.isuperred.R;
import com.github.isuperred.bean.AppInfo;
import com.github.isuperred.presenter.AppInstalledPresenter;
import com.github.isuperred.widgets.AppVerticalGridView;
import com.github.isuperred.widgets.focus.MyItemBridgeAdapter;

import java.util.ArrayList;
import java.util.List;

public class AppInstalledActivity extends AppCompatActivity {

    private static final String TAG = "AppInstalledActivity";
    private ArrayObjectAdapter mAdapter;
    private AppVerticalGridView mVgAppInstalled;
    private TextView mTvAppNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_installed);
        initView();
        initData();
    }

    private void initView() {
        mVgAppInstalled = findViewById(R.id.vg_app_installed);
        mTvAppNumber = findViewById(R.id.tv_app_installed_number);
        mVgAppInstalled.setColumnNumbers(6);
        mVgAppInstalled.setHorizontalSpacing((int) getResources().getDimension(R.dimen.px106));
        mVgAppInstalled.setVerticalSpacing((int) getResources().getDimension(R.dimen.px40));
        mAdapter = new ArrayObjectAdapter(new AppInstalledPresenter());
        ItemBridgeAdapter itemBridgeAdapter = new MyItemBridgeAdapter(mAdapter) {

            @Override
            public MyItemBridgeAdapter.OnItemViewClickedListener getOnItemViewClickedListener() {
                return new OnItemViewClickedListener() {
                    @Override
                    public void onItemClicked(View focusView,
                                              Presenter.ViewHolder itemViewHolder,
                                              Object item) {
                        if (item instanceof AppInfo) {

                            try {
                                PackageManager packageManager = getPackageManager();
                                Intent intent = packageManager
                                        .getLaunchIntentForPackage(((AppInfo) item).packageName);
                                if (intent == null) {
                                    Toast.makeText(AppInstalledActivity.this,
                                            ((AppInfo) item).name + "未安装",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(intent);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                };
            }

            @Override
            public OnItemViewLongClickedListener getOnItemViewLongClickedListener() {
                return new OnItemViewLongClickedListener() {
                    @Override
                    public boolean onItemLongClicked(View focusView,
                                                     Presenter.ViewHolder itemViewHolder,
                                                     Object item) {
                        if (item instanceof AppInfo && !TextUtils.isEmpty(((AppInfo) item).packageName)) {
                            Uri packageURI = Uri.parse("package:" + ((AppInfo) item).packageName);
                            Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
                            uninstallIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(uninstallIntent);
                        }
                        return true;
                    }
                };
            }
        };
        mVgAppInstalled.setAdapter(itemBridgeAdapter);
        FocusHighlightHelper.setupBrowseItemFocusHighlight(itemBridgeAdapter,
                FocusHighlight.ZOOM_FACTOR_MEDIUM, false);
    }

    private void initData() {
        List<AppInfo> appInfos = getInstallApps(getApplicationContext());
        if (appInfos == null) {
            return;
        }
        mTvAppNumber.setText(String.valueOf(appInfos.size()));
        mAdapter.addAll(0, appInfos);
    }

    public List<AppInfo> getInstallApps(Context context) {
        Log.e(TAG, "getInstallApps0: ");
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> installedPackages = pm.getInstalledPackages(0);  //获取所以已安装的包

        List<AppInfo> list = new ArrayList<>();
        for (PackageInfo packageInfo : installedPackages) {
            Intent intent = pm.getLaunchIntentForPackage(packageInfo.packageName);
            if (intent == null) {
                continue;
            }
            AppInfo info = new AppInfo();
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;  //应用信息
            info.name = applicationInfo.loadLabel(pm).toString();
            info.icon = applicationInfo.loadIcon(pm);        //状态机,通过01状态来表示是否具备某些属性和功能

            info.packageName = packageInfo.packageName;
            info.versionName = packageInfo.versionName;
            info.versionCode = packageInfo.versionCode;
            int flags = applicationInfo.flags;  //获取应用标记
            info.isRom = (flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != ApplicationInfo
                    .FLAG_EXTERNAL_STORAGE;
            info.isUser = (flags & ApplicationInfo.FLAG_SYSTEM) != ApplicationInfo
                    .FLAG_SYSTEM;
//            Log.e(TAG, "getInstallApps: " + info.toString());
            list.add(info);
        }
        Log.e(TAG, "getInstallApps1: ");
        return list;
    }
}
