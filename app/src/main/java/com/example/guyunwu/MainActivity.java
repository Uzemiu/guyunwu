package com.example.guyunwu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.splashscreen.SplashScreen;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.guyunwu.databinding.ActivityMainBinding;
import com.example.guyunwu.exception.handler.ExceptionHandler;
import com.example.guyunwu.ui.explore.article.PublishArticleActivity;
import com.example.guyunwu.ui.home.calendar.CalendarActivity;
import com.example.guyunwu.ui.init.LoginActivity;
import com.example.guyunwu.ui.user.setting.SettingActivity;
import com.example.guyunwu.util.SharedPreferencesUtil;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import org.jetbrains.annotations.NotNull;
import org.xutils.x;

import java.util.Calendar;

import static androidx.navigation.ui.NavigationUI.onNavDestinationSelected;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ActivityMainBinding binding;

    public static MainActivity instance;

    private Menu menu;

    private int currentFragment = R.id.navigation_home;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = this;
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);

        x.Ext.init(getApplication());
        new ExceptionHandler(getApplicationContext()).register();
        SharedPreferences sharedPreferences = getSharedPreferences("Guyunwu", MODE_PRIVATE);
        SharedPreferencesUtil.setSharedPreferences(sharedPreferences);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_explore, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // 重写导航栏监听事件用于改变ActionBar
        navView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == this.currentFragment) {
                if (item.getItemId() == R.id.navigation_explore) {
                    Intent toPublishArticle = new Intent(this, PublishArticleActivity.class);
                    startActivity(toPublishArticle);
                }
                return false;
            }
            this.currentFragment = item.getItemId();
            onPrepareOptionsMenu(menu);
            return onNavDestinationSelected(item, navController);
        });
        initSettings();
        if (SharedPreferencesUtil.getString("token", "").length() == 0) {
            Intent toLoginPage = new Intent();
            toLoginPage.setClass(this, LoginActivity.class);
            startActivity(toLoginPage);
            finish();
        }
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("currentFragment", currentFragment + "");

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String c = savedInstanceState.getString("currentFragment");
            currentFragment = Integer.parseInt(c);
        }
    }

    private void initSettings() {
        if (!SharedPreferencesUtil.contain("hasTranslation")) {
            SharedPreferencesUtil.putBoolean("hasTranslation", true);
        }
        if (!SharedPreferencesUtil.contain("hasNotification")) {
            SharedPreferencesUtil.putBoolean("hasNotification", false);
        }
        if (!SharedPreferencesUtil.contain("notificationTime")) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 10);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            SharedPreferencesUtil.putLong("notificationTime", calendar.getTime().getTime());
        }
        if (!SharedPreferencesUtil.contain("darkMode")) {
            SharedPreferencesUtil.putBoolean("darkMode", false);
        }
        boolean isDarkMode = SharedPreferencesUtil.getBoolean("darkMode", false);
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        this.menu = menu;
        onPrepareOptionsMenu(menu);
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
                menuItem.setIconSize(120);
                menuItem.setTitle("");
                break;
            case R.id.navigation_notifications:
                MenuItem setting = menu.add(Menu.NONE, R.drawable.ic_user_setting_24dp, 1, "");
                setting.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                setting.setIcon(R.drawable.ic_user_setting_24dp);
                break;
            case R.id.navigation_home:
                MenuItem signIn = menu.add(Menu.NONE, R.drawable.ic_home_signin_24dp, 1, "");
                signIn.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                signIn.setIcon(R.drawable.ic_home_signin_24dp);
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
            case R.drawable.ic_home_signin_24dp:
                Intent signInPage = new Intent();
                signInPage.setClass(this, CalendarActivity.class);
                startActivity(signInPage);
                break;
        }
        return false;
    }
}
