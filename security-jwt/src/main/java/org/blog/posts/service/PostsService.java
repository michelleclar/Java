package org.blog.posts.service;

import org.blog.user.model.User;
import org.carl.engine.DB;
import org.carl.generated.tables.pojos.Posts;
import org.carl.generated.tables.records.PostsRecord;
import org.jooq.UpdateSetFirstStep;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static org.carl.generated.Tables.POSTS;

@Repository
public class PostsService {
    final DB DB;

    public PostsService(DB db) {
        this.DB = db;
    }


    public List<Posts> findAllByUid(Integer uid) {
        return DB.get(dsl -> dsl.select(POSTS.POST_ID, POSTS.TITLE).from(POSTS).where(POSTS.USER_ID.eq(uid)).orderBy(POSTS.LAST_MODIFIED.asc()).fetchInto(Posts.class));
    }

    public Posts findById(Integer id) {
        return DB.get(dsl -> dsl.select(POSTS.POST_ID, POSTS.TITLE).from(POSTS).where(POSTS.POST_ID.eq(id)).fetchOneInto(Posts.class));
    }

    public void updateById(Posts posts) {
        DB.run(dsl -> {
            UpdateSetFirstStep<PostsRecord> update = dsl.update(POSTS);
            if (posts.getContext() != null)
                update.set(POSTS.CONTEXT, posts.getContext());
            if (posts.getTitle() != null)
                update.set(POSTS.TITLE, posts.getTitle());
            update.set(POSTS.LAST_MODIFIED, LocalDateTime.now()).where(POSTS.POST_ID.eq(posts.getPostId())).and(POSTS.USER_ID.eq(posts.getUserId())).execute();
        });
    }

    public void deleteById(Integer id) {
        DB.run(dsl -> {
            dsl.delete(POSTS).where(POSTS.POST_ID.eq(id)).execute();
        });
    }
}
