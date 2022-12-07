package com.example.guyunwu;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.xutils.x;

import static org.junit.Assert.*;

import com.example.guyunwu.entity.ArticleEntity;
import com.example.guyunwu.repository.ArticleRepository;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        ArticleRepository articleRepository = new ArticleRepository();
        ArticleEntity entity = new ArticleEntity();
        articleRepository.save(entity);

        assertEquals("com.example.guyunwu", appContext.getPackageName());
    }
}
