package com.example.guyunwu.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.guyunwu.api.BaseResponse;
import com.example.guyunwu.api.LearnRequest;
import com.example.guyunwu.api.RequestModule;
import com.example.guyunwu.api.resp.TodayScheduleResp;
import com.example.guyunwu.databinding.FragmentLearnWithoutFinishBinding;
import com.example.guyunwu.ui.home.study.LearnActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LearnWithoutFinishFragment extends Fragment {

    private static final String TAG = "LearnWithoutFinishFragment";

    private FragmentLearnWithoutFinishBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLearnWithoutFinishBinding.inflate(inflater, container, false);
        initBinding();
        initView();
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
    }

    private void initBinding() {
        binding.btnStartLearn.setOnClickListener(v -> {
            Intent toLearnPage = new Intent();
            toLearnPage.setClass(getActivity(), LearnActivity.class);
            startActivity(toLearnPage);
        });
    }

    private void initView() {
        LearnRequest learnRequest = RequestModule.LEARN_REQUEST;

        learnRequest.todaySchedule().enqueue(new Callback<BaseResponse<TodayScheduleResp>>() {
            @Override
            public void onResponse(Call<BaseResponse<TodayScheduleResp>> call, Response<BaseResponse<TodayScheduleResp>> response) {
                BaseResponse<TodayScheduleResp> body1 = response.body();
                if (body1 == null || body1.getCode() != 200) {
                    onFailure(call, new Throwable("登录失败"));
                } else {
                    TodayScheduleResp todayScheduleResp = body1.getData();
                    int learn = todayScheduleResp.getLearn();
                    int review = todayScheduleResp.getReview();
                    binding.needToReview.setText(review + " ");
                    binding.needToLearn.setText(learn + " ");
                    binding.minutes.setText(String.valueOf((learn + review) / 2));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<TodayScheduleResp>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: ", t);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
