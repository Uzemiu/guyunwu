package com.example.guyunwu.ui.explore;

import static com.example.guyunwu.util.UiUtil.isScrollToBottom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.guyunwu.R;
import com.example.guyunwu.api.ArticleRequest;
import com.example.guyunwu.api.BaseResponse;
import com.example.guyunwu.api.RequestModule;
import com.example.guyunwu.databinding.FragmentExploreBinding;
import com.example.guyunwu.repository.ArticleRepository;
import com.example.guyunwu.repository.BaseQuery;
import com.example.guyunwu.repository.Pageable;
import com.example.guyunwu.ui.explore.article.Article;
import com.example.guyunwu.ui.explore.article.ArticleAdapter;
import com.jama.carouselview.CarouselView;
import com.jama.carouselview.enums.IndicatorAnimationType;
import com.jama.carouselview.enums.OffsetType;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExploreFragment extends Fragment {

    private static final String TAG = "ExploreFragment";

    private FragmentExploreBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentExploreBinding.inflate(inflater, container, false);

        initCarousel();
        setupArticle();
        initSwipeRefresh();
        reloadArticle();

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

    private void initSwipeRefresh(){
        binding.exploreSwipeRefreshLayout.setOnRefreshListener(() -> {
            reloadArticle();
            binding.exploreSwipeRefreshLayout.setRefreshing(false);
        });
    }

    private final List<Article> articleList = new ArrayList<>();

    private int page = 0;
    private volatile boolean loading = false;
    private volatile boolean reachEnd = false;

    private synchronized void reloadArticle(){
        page = 0;
        reachEnd = false;
        articleList.clear();
        fetchArticle(true);
    }

    private synchronized void fetchArticle(boolean reload){
        if (loading || reachEnd){
            return;
        }
        loading = true;

        ArticleRequest articleRequest = RequestModule.ARTICLE_REQUEST;

        articleRequest.articles(page, 10, "explore").enqueue(new Callback<BaseResponse<List<Article>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<Article>>> call, Response<BaseResponse<List<Article>>> response) {
                BaseResponse<List<Article>> body = response.body();
                if (body != null && body.getData() != null){
                    int size = articleList.size();
                    List<Article> res = body.getData();
                    if(res.size() == 0){
                        reachEnd = true;
                    } else {
                        articleList.addAll(res);
                        if (reload){
                            binding.exploreArticlePreviewRecyclerView.getAdapter().notifyDataSetChanged();
                        } else {
                            binding.exploreArticlePreviewRecyclerView.getAdapter().notifyItemRangeInserted(size, res.size());
                        }
                    }
                }
                page++;
                loading = false;
            }

            @Override
            public void onFailure(Call<BaseResponse<List<Article>>> call, Throwable t) {
                loading = false;
            }
        });

    }

    private void setupArticle(){
        // 获取“小课堂”数据
        RecyclerView recyclerView = binding.exploreArticlePreviewRecyclerView;
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        ArticleAdapter adapter = new ArticleAdapter(articleList);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isScrollToBottom(recyclerView, 600)) {
                    fetchArticle(false);
                }
            }
        });
    }

}
