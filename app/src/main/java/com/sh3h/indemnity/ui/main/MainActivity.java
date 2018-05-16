package com.sh3h.indemnity.ui.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuItem;

import com.sh3h.indemnity.R;
import com.sh3h.indemnity.databinding.ActivityMainBinding;
import com.sh3h.indemnity.ui.base.ParentActivity;
import com.sh3h.indemnity.ui.setting.SettingActivity;
import com.sh3h.mobileutil.util.ApplicationsUtil;

import javax.inject.Inject;

public class MainActivity extends ParentActivity implements MainMvpView{

    @Inject
    MainPresenter mPresenter;

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        getActivityComponent().inject(this);
        mPresenter.attachView(this);
        mBinding.include.toolbar.setNavigationIcon(R.mipmap.ic_menu_home);
        mBinding.include.toolbar.setTitle(R.string.app_name);
        setSupportActionBar( mBinding.include.toolbar);
        mBinding.setPresenter(mPresenter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search:
                Snackbar.make(mBinding.container, R.string.menu_search, Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.action_notification:
                Snackbar.make(mBinding.container, R.string.menu_notifications, Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.action_about:
                Snackbar.make(mBinding.container, R.string.menu_about_us, Snackbar.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        mPresenter.loadWords();
    }

    @Override
    public void onToast(int resId) {
        ApplicationsUtil.showMessage(this, resId);
    }
}
