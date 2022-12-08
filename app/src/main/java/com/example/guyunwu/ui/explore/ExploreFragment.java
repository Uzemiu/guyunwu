package com.example.guyunwu.ui.explore;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.guyunwu.R;
import com.example.guyunwu.databinding.FragmentExploreBinding;
import com.example.guyunwu.ui.explore.daily.DailySentenceActivity;
import com.example.guyunwu.ui.explore.lecture.LectureActivity;
import com.jama.carouselview.CarouselView;
import com.jama.carouselview.CarouselViewListener;
import com.jama.carouselview.enums.IndicatorAnimationType;
import com.jama.carouselview.enums.OffsetType;

import org.xutils.x;

public class ExploreFragment extends Fragment {

    private static final String TAG = "ExploreFragment";

    private FragmentExploreBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ExploreViewModel exploreViewModel =
                new ViewModelProvider(this).get(ExploreViewModel.class);

        binding = FragmentExploreBinding.inflate(inflater, container, false);

        final TextView textView = binding.textDashboard;
        exploreViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        initRouter();
        initCarousel();

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private static final String[] IMAGES = {
            "https://bing.com/th?id=OHR.TangleCreekFalls_ZH-CN4281148652_1920x1080.jpg&qlt=100","https://bing.com/th?id=OHR.GreatEgret_ZH-CN4088261519_1920x1080.jpg&qlt=100","https://bing.com/th?id=OHR.BambooTreesIndia_ZH-CN3943852151_1920x1080.jpg&qlt=100","https://bing.com/th?id=OHR.KilimanjaroElephants_ZH-CN3779609103_1920x1080.jpg&qlt=100","https://bing.com/th?id=OHR.MiamiDT_ZH-CN3528760113_1920x1080.jpg&qlt=100","https://bing.com/th?id=OHR.BraidedRiverDelta_ZH-CN3352462511_1920x1080.jpg&qlt=100","https://bing.com/th?id=OHR.QingmingCandle2020_ZH-CN6775701680_1920x1080.jpg&qlt=100","https://bing.com/th?id=OHR.AntarcticaDay_ZH-CN5719164468_1920x1080.jpg&qlt=100","https://bing.com/th?id=OHR.RovinjCroatia_ZH-CN5459110500_1920x1080.jpg&qlt=100","https://bing.com/th?id=OHR.HeronGiving_ZH-CN5229629007_1920x1080.jpg&qlt=100","https://bing.com/th?id=OHR.RedPlanetDay_ZH-CN4913018041_1920x1080.jpg&qlt=100","https://bing.com/th?id=OHR.Cecropia_ZH-CN4236630074_1920x1080.jpg&qlt=100"
            ,

    };

    private void initCarousel(){
        CarouselView carouselView = binding.exploreCarouselView;

        carouselView.setSize(IMAGES.length);
        carouselView.setResource(R.layout.image_carousel_item);
        carouselView.setAutoPlay(true);
        carouselView.hideIndicator(true);
        carouselView.setIndicatorAnimationType(IndicatorAnimationType.THIN_WORM);
        carouselView.setCarouselOffset(OffsetType.CENTER);
        carouselView.setCarouselViewListener((view, position) -> {
            // Example here is setting up a full image carousel
            ImageView imageView = view.findViewById(R.id.carousel_item_image);
            x.image().bind(imageView, IMAGES[position]);
        });
        // After you finish setting up, show the CarouselView
        carouselView.show();
    }

    private void initRouter(){
       binding.exploreToDailyPage.setOnClickListener(v -> {
            Intent toDailyPage = new Intent();
            toDailyPage.setClass(getActivity(), DailySentenceActivity.class);
            startActivity(toDailyPage);
        });
        binding.exploreToLecturePage.setOnClickListener(v -> {
            Intent toLecture = new Intent();
            toLecture.setClass(getActivity(), LectureActivity.class);
            startActivity(toLecture);
        });
    }

}
