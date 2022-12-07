package com.example.guyunwu;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.guyunwu.databinding.ActivityMainBinding;
import com.example.guyunwu.exception.handler.ExceptionHandler;
import com.example.guyunwu.ui.user.setting.SettingActivity;
import com.example.guyunwu.util.NotificationUtil;
import com.example.guyunwu.util.NotifyObject;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import org.xutils.x;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static androidx.navigation.ui.NavigationUI.onNavDestinationSelected;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ActivityMainBinding binding;

    private Menu menu;

    private int currentFragment;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_explore, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        x.Ext.init(getApplication());
        new ExceptionHandler(getApplicationContext()).register();
        // 重写导航栏监听事件用于改变ActionBar
        navView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == this.currentFragment) {
                if (item.getItemId() == R.id.navigation_explore) {
                    Intent toDailyPage = new Intent(this, SettingActivity.class);
                    startActivity(toDailyPage);
                }
                return false;
            }
            this.currentFragment = item.getItemId();
            onPrepareOptionsMenu(menu);
            return onNavDestinationSelected(item, navController);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        this.menu = menu;
        return true;
    }

    @SuppressLint({"NonConstantResourceId", "ResourceType"})
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();

        BottomNavigationItemView menuItem = findViewById(R.id.navigation_explore);
        menuItem.setIconSize(80);

        switch (this.currentFragment) {
            case R.id.navigation_explore:
                // 底部“发现”变为“➕”
                BottomNavigationView navigation = findViewById(R.id.nav_view);
                navigation.getMenu().getItem(1).setChecked(true);

                menuItem.setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.menu_add));
                menuItem.setIconSize(160);
                menuItem.setTitle("");
                break;
            case R.id.navigation_notifications:
                MenuItem setting = menu.add(Menu.NONE, R.drawable.ic_user_setting_24dp, 1, "");
                setting.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                setting.setIcon(R.drawable.ic_user_setting_24dp);
                break;
            case R.id.navigation_home:

                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.drawable.ic_user_setting_24dp:
                Intent settingPage = new Intent();
                settingPage.setClass(this, SettingActivity.class);
                startActivity(settingPage);
                break;
        }
        return false;
    }
}
