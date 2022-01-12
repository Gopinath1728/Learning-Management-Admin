package com.example.sampleschooladmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.sampleschooladmin.Eventbus.SendMessageEvent;
import com.example.sampleschooladmin.Services.FCMSendData;
import com.example.sampleschooladmin.Services.IFCMServices;
import com.example.sampleschooladmin.Services.RetrofitFCMClient;
import com.example.sampleschooladmin.databinding.ActivityHomeBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;

    private IFCMServices ifcmServices;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarHome.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_view_teachers, R.id.nav_view_classes,
                R.id.nav_view_class_data, R.id.nav_view_class_subjects, R.id.nav_view_class_timetable,
                R.id.nav_drive_fragment, R.id.nav_time_fragment, R.id.nav_class_students,
                R.id.nav_view_student_data, R.id.nav_teacher_data, R.id.nav_announcement,
                R.id.nav_announcement_detail, R.id.nav_examinations, R.id.nav_assignment,
                R.id.nav_quiz, R.id.nav_add_quiz, R.id.nav_examinations, R.id.nav_attendance,
                R.id.nav_attendance_detail, R.id.nav_student_result)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        ifcmServices = RetrofitFCMClient.getInstance().create(IFCMServices.class);
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onStop() {
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSendMessageEvent(SendMessageEvent event) {
        Map<String, String> notiData = new HashMap<>();
        notiData.put("title", event.getTitle());
        notiData.put("content", event.getMessage());

        FCMSendData sendData = new FCMSendData(event.getToken(), notiData);
        compositeDisposable.add(ifcmServices.sendNotification(sendData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(fcmResponse -> {
                    if (fcmResponse.getSuccess() == 1) {

                    }
                }));
    }

    public void logout(MenuItem item) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}