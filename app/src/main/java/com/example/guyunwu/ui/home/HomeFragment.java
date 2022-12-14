package com.example.guyunwu.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.guyunwu.R;
import com.example.guyunwu.api.BaseResponse;
import com.example.guyunwu.api.LearnRequest;
import com.example.guyunwu.api.RequestModule;
import com.example.guyunwu.api.ScheduleRequest;
import com.example.guyunwu.api.resp.ScheduleResp;
import com.example.guyunwu.api.resp.TodayScheduleResp;
import com.example.guyunwu.databinding.FragmentHomeBinding;
import com.example.guyunwu.ui.explore.daily.DailySentenceActivity;
import com.example.guyunwu.ui.explore.lecture.LectureActivity;
import com.example.guyunwu.ui.home.schedule.UpdateScheduleActivity;
import com.example.guyunwu.ui.home.wordbook.WordBookActivity;
import com.example.guyunwu.ui.user.myBook.MyBookActivity;
import com.example.guyunwu.util.SharedPreferencesUtil;
import org.jetbrains.annotations.NotNull;
import org.xutils.x;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private static final String WITHOUT_FINISH = com.example.guyunwu.ui.home.LearnWithoutFinishFragment.class.getName();

    private static final String FINISH = com.example.guyunwu.ui.home.LearnFinishFragment.class.getName();

    private static final String TAG = "HomeFragment";

    private FragmentHomeBinding binding;

    private Fragment mLastShowFragment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initRouter();
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        initFragment();
        initView();
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void switchTo(String tab, Bundle bundle) {
        FragmentManager fragmentManager;
        try {
            fragmentManager = getChildFragmentManager();
        } catch (Exception e) {
            // 不要问为什么要抓异常
            return;
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = Fragment.instantiate(getActivity(), tab);
        fragment.setArguments(bundle);
        fragmentTransaction.add(R.id.fragment_learn_finish, fragment, tab);
        mLastShowFragment = fragment;

        fragmentTransaction.commitAllowingStateLoss();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (fragmentManager.findFragmentByTag(FINISH) != null) {
            fragmentTransaction.remove(fragmentManager.findFragmentByTag(FINISH));
        }
        if (fragmentManager.findFragmentByTag(WITHOUT_FINISH) != null) {
            fragmentTransaction.remove(fragmentManager.findFragmentByTag(WITHOUT_FINISH));
        }
        fragmentTransaction.commitNow();
    }

    private void initView() {
        if (!SharedPreferencesUtil.contain("scheduleId")) {
            // 还没有计划呢
            binding.bookTitle.setText("当前还未添加计划");
            binding.learned.setText("0");
            binding.all.setText("0");
            binding.dayRemained.setText("0");
            if (SharedPreferencesUtil.contain("needToReview")) {
                SharedPreferencesUtil.remove("needToReview");
            }
            if (SharedPreferencesUtil.contain("needToLearn")) {
                SharedPreferencesUtil.remove("needToLearn");
            }

        } else {
            ScheduleRequest scheduleRequest = RequestModule.SCHEDULE_REQUEST;
            scheduleRequest.getSchedule(SharedPreferencesUtil.getLong("scheduleId", 0))
                    .enqueue(new Callback<BaseResponse<ScheduleResp>>() {
                        @Override
                        public void onResponse(Call<BaseResponse<ScheduleResp>> call,
                                               Response<BaseResponse<ScheduleResp>> response) {
                            BaseResponse<ScheduleResp> body = response.body();
                            if (body == null || body.getCode() != 200) {
                                onFailure(call, new Throwable("获取计划失败"));
                            } else {
                                if (binding != null) {
                                    ScheduleResp scheduleResp = body.getData();
                                    binding.bookTitle.setText(scheduleResp.getBook().getName());
                                    binding.learned.setText(String.valueOf(scheduleResp.getLearned()));
                                    binding.all.setText(String.valueOf(scheduleResp.getAll()));
                                    binding.processBar.setMax(scheduleResp.getAll());
                                    binding.processBar.setProgress(scheduleResp.getLearned());
                                    int dayRemained = (int) Math.ceil((double) (scheduleResp.getAll() - scheduleResp.getLearned())
                                            / SharedPreferencesUtil.getInt("wordsPerDay", 10));
                                    binding.dayRemained.setText(String.valueOf(dayRemained));
                                    x.image().bind(binding.bookImage, scheduleResp.getBook().getCoverImage());
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResponse<ScheduleResp>> call, Throwable t) {
                            Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "onFailure: ", t);
                            if (binding != null) {
                                binding.bookTitle.setText("当前还未添加计划");
                                binding.learned.setText("0");
                                binding.all.setText("0");
                                binding.dayRemained.setText("0");
                            }
                        }
                    });

            LearnRequest learnRequest = RequestModule.LEARN_REQUEST;

            learnRequest.todaySchedule().enqueue(new Callback<BaseResponse<TodayScheduleResp>>() {
                @Override
                public void onResponse(Call<BaseResponse<TodayScheduleResp>> call,
                                       Response<BaseResponse<TodayScheduleResp>> response) {
                    BaseResponse<TodayScheduleResp> body1 = response.body();
                    if (body1 == null || body1.getCode() != 200) {
                        onFailure(call, new Throwable("登录失败"));
                    } else {
                        TodayScheduleResp todayScheduleResp = body1.getData();
                        int learn = todayScheduleResp.getLearn();
                        int review = todayScheduleResp.getReview();
                        int hasLearn = todayScheduleResp.getHasLearned();
                        Bundle bundle = new Bundle();
                        if (learn == 0 && review == 0) {
                            // 完成了！！！
                            bundle.putInt("hasLearned", hasLearn);
                            switchTo(FINISH, bundle);
                        } else {
                            bundle.putInt("learn", learn);
                            bundle.putInt("review", review);
                            switchTo(WITHOUT_FINISH, bundle);
                        }
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse<TodayScheduleResp>> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onFailure: ", t);
                }
            });

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initRouter() {
        binding.schedule.setOnClickListener(v -> {
            if (!SharedPreferencesUtil.contain("scheduleId")) {
                // 还没有计划呢
                Intent toMyBookPage = new Intent();
                toMyBookPage.setClass(getActivity(), MyBookActivity.class);
                startActivity(toMyBookPage);
            } else {
                Intent toSchedulePage = new Intent();
                toSchedulePage.setClass(getActivity(), UpdateScheduleActivity.class);
                startActivity(toSchedulePage);
            }
        });
        binding.dailySentence.setOnClickListener(v -> {
            Intent toDailyPage = new Intent();
            toDailyPage.setClass(getActivity(), DailySentenceActivity.class);
            startActivity(toDailyPage);
        });
        binding.lecture.setOnClickListener(v -> {
            Intent toLecture = new Intent();
            toLecture.setClass(getActivity(), LectureActivity.class);
            startActivity(toLecture);
        });
        binding.ecnu.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://www.ecnu.edu.cn/");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
        binding.guyunActivity.setOnClickListener(v -> {
            Intent toBlankPage = new Intent();
            toBlankPage.setClass(getActivity(), BlankActivity.class);
            startActivity(toBlankPage);
        });
        binding.wordBook.setOnClickListener(v -> {
            Intent toWordBookPage = new Intent();
            toWordBookPage.setClass(getActivity(), WordBookActivity.class);
            startActivity(toWordBookPage);
        });
    }

}
