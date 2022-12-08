package com.example.guyunwu.ui.user;

import com.example.guyunwu.repository.BaseQuery;
import com.example.guyunwu.repository.BookRepository;
import com.example.guyunwu.repository.Pageable;
import com.example.guyunwu.ui.user.book.Author;
import com.example.guyunwu.ui.user.book.Book;

import org.xutils.db.Selector;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BookDataProvider {
    private static final String[][] INTRODUCE = {
            {"大白鹭，翱翔于天际。\n" +
                    "纤细的长脖，轻盈的翅膀。\n" +
                    "在清澈的江水上，悠然自得地漫步。\n" +
                    "它的声音清脆，如同细水流淌。\n" +
                    "没有什么能阻挡它的飞翔，它是自由的。", "庄子"},
            {"乞力马扎罗山，雄峙于天际。\n" +
                    "白云缭绕，如同巨象的裘皮。\n" +
                    "在山附近，大象穿梭于山林。\n" +
                    "它们踏着威武的步伐，踏碎了树枝和石头。\n" +
                    "它们的脚步壮阔，威严而庄严。\n" +
                    "在这片神奇的土地上，它们是王者。", "老子"},
            {"迈阿密海滩，阳光明媚。\n" +
                    "青蓝的海水，碧绿的海滩。\n" +
                    "在海洋大道上，汽车和人们穿梭。\n" +
                    "他们去到海边，享受阳光和清凉的海风。\n" +
                    "这里是生活的天堂，也是梦想的家园。\n" +
                    "在美国佛罗里达州，你可以找到幸福和自由。", "世说新语"},
            {"空山不见人，但闻人语响。\n" +
                    "返景入深林，复照青苔上。", "古文观止"},
    };

    private static final String[] IMAGES = {
            "https://bing.com/th?id=OHR.TangleCreekFalls_ZH-CN4281148652_1920x1080.jpg&qlt=100",
            "https://bing.com/th?id=OHR.GreatEgret_ZH-CN4088261519_1920x1080.jpg&qlt=100",
            "https://bing.com/th?id=OHR.BambooTreesIndia_ZH-CN3943852151_1920x1080.jpg&qlt=100",
            "https://bing.com/th?id=OHR.KilimanjaroElephants_ZH-CN3779609103_1920x1080.jpg&qlt=100",
            "https://bing.com/th?id=OHR.MiamiDT_ZH-CN3528760113_1920x1080.jpg&qlt=100",
            "https://bing.com/th?id=OHR.BraidedRiverDelta_ZH-CN3352462511_1920x1080.jpg&qlt=100",
            "https://bing.com/th?id=OHR.QingmingCandle2020_ZH-CN6775701680_1920x1080.jpg&qlt=100",
            "https://bing.com/th?id=OHR.AntarcticaDay_ZH-CN5719164468_1920x1080.jpg&qlt=100",
            "https://bing.com/th?id=OHR.RovinjCroatia_ZH-CN5459110500_1920x1080.jpg&qlt=100",
            "https://bing.com/th?id=OHR.HeronGiving_ZH-CN5229629007_1920x1080.jpg&qlt=100",
            "https://bing.com/th?id=OHR.RedPlanetDay_ZH-CN4913018041_1920x1080.jpg&qlt=100",
            "https://bing.com/th?id=OHR.Cecropia_ZH-CN4236630074_1920x1080.jpg&qlt=100"
            ,
    };

    private static final BookRepository bookRepository = new BookRepository();

    public static List<Book> getBooks() {
        Selector.OrderBy orderBy = new Selector.OrderBy("id", true);
        Pageable pageable = new Pageable(1, 10, Collections.singletonList(orderBy));
        List<Book> books = bookRepository.query(new BaseQuery<>(), pageable);

        for (int i = 0; i < INTRODUCE.length; i++) {
            String[] book = INTRODUCE[i];
            int imageIndex = Math.min(i, IMAGES.length - 1);
            books.add(new Book(
                    i,
                    IMAGES[imageIndex],
                    book[1],
                    new Author(),
                    book[0],
                    book[0],
                    LocalDateTime.now().minusDays(i),
                    0L,
                    0L,
                    "class",
                    Arrays.asList("tag1", "tag2")));
        }
        return books;
    }
}
