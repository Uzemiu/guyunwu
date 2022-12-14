package com.example.guyunwu.repository;

import com.example.guyunwu.ui.explore.comment.Comment;
import lombok.Data;
import org.xutils.DbManager;
import org.xutils.db.Selector;

@Data
public class CommentQuery extends BaseQuery<Comment> {

    private Integer articleId;

    @Override
    public Selector<Comment> toSelector(DbManager dbManager, Class<Comment> c) {
        Selector<Comment> selector = super.toSelector(dbManager, c);
        if (articleId != null) {
            selector.where("article_id", "=", articleId);
        }
        return selector;
    }
}
