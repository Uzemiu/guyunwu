package com.example.guyunwu.ui.explore.daily;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guyunwu.R;

import org.xutils.x;

import java.time.LocalDateTime;
import java.util.List;

public class DailySentenceAdapter extends RecyclerView.Adapter<DailySentenceAdapter.ViewHolder>{

    private final List<DailySentence> dailySentenceList;

    public DailySentenceAdapter(List<DailySentence> dailySentenceList){
        this.dailySentenceList = dailySentenceList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View dailySentenceView;
        TextView dailySentence;
        TextView dailyDay;
        TextView dailyYearMonth;
        TextView dailyFrom;
        ImageView dailyImage;

        public ViewHolder(View view) {
            super(view);
            dailySentenceView = view;
            dailySentence = view.findViewById(R.id.daily_sentence);
            dailyDay = view.findViewById(R.id.daily_day);
            dailyYearMonth = view.findViewById(R.id.daily_year_month);
            dailyFrom = view.findViewById(R.id.daily_from);
            dailyImage = view.findViewById(R.id.daily_image);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_sentence, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        Object a = view.findViewById(R.id.daily_day);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DailySentence daily = dailySentenceList.get(position);
        holder.dailySentence.setText(daily.getSentence());
        holder.dailyDay.setText(String.valueOf(daily.getDate().getDayOfMonth()));
        holder.dailyYearMonth.setText(daily.getDate().getYear() + "年" + daily.getDate().getMonthValue() + "月");
        holder.dailyFrom.setText(daily.getFrom());
        x.image().bind(holder.dailyImage, daily.getImageUrl());
//        holder.dailyImage.setImageURI(Uri.parse(daily.getImageUrl()));
    }

    @Override
    public int getItemCount() {
        return dailySentenceList.size();
    }
}
