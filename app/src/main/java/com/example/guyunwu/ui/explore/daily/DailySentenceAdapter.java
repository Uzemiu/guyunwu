package com.example.guyunwu.ui.explore.daily;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.guyunwu.R;
import org.xutils.x;

import java.util.Calendar;
import java.util.List;

public class DailySentenceAdapter extends RecyclerView.Adapter<DailySentenceAdapter.ViewHolder> {

    private final List<DailySentence> dailySentenceList;

    public DailySentenceAdapter(List<DailySentence> dailySentenceList) {
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

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DailySentence daily = dailySentenceList.get(position);
        holder.dailySentence.setText(daily.getContent());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(daily.getTime());
        holder.dailyDay.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        holder.dailyYearMonth.setText(calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月");
        holder.dailyFrom.setText("-  " + daily.getAuthor() + "  " + daily.getSource());

        String dailyImageUrl = TextUtils.isEmpty(daily.getImageUrl())
                ? "https://bing.com/th?id=OHR.BambooTreesIndia_ZH-CN3943852151_1920x1080.jpg&qlt=100"
                : daily.getImageUrl();
        x.image().bind(holder.dailyImage, dailyImageUrl);
    }

    @Override
    public int getItemCount() {
        return dailySentenceList.size();
    }
}
