package org.blog.posts;

import org.blog.auth.service.AuthService;
import org.blog.posts.service.PostsService;
import org.carl.generated.tables.pojos.Posts;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/posts")
public class PostsResource {
    private final PostsService postsService;
    private final AuthService authService;

    public PostsResource(PostsService postsService,AuthService authService) {
        this.postsService = postsService;
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<List<Posts>> findAllByUid(@RequestParam(name = "uid", required = false) Integer uid) {
        return ResponseEntity.ok(postsService.findAllByUid(uid));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Posts> findById(@PathVariable Integer id) {
        Posts p = postsService.findById(id);
        return ResponseEntity.ok(p);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Objects> updateById(@PathVariable Integer id, @RequestBody Posts posts,@RequestHeader("Authorization") String token) {
        Integer uid = authService.extractClaim(token.substring(7), claims -> claims.get(AuthService.ID, Integer.class));
        posts.setPostId(id);
        posts.setUserId(uid);
        postsService.updateById(posts);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Objects> deleteById(@PathVariable Integer id) {
        postsService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
