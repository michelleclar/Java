package org.blog.user;

import org.blog.auth.service.AuthService;
import org.blog.user.model.User;
import org.blog.user.model.response.AuthSuccessResponse;
import org.blog.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserResource {
    private final UserService userService;
    private final AuthService authService;

    public UserResource(UserService userService, AuthService authService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthSuccessResponse> register(@RequestBody User user) {
        User registeredUser = userService.register(user);
        String jwtToken = authService.generateToken(registeredUser);

        AuthSuccessResponse authSuccessResponse = new AuthSuccessResponse().setToken(jwtToken).setExpiresIn(authService.getExpirationTime());
        return ResponseEntity.ok(authSuccessResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthSuccessResponse> authenticate(@RequestBody User user) {
        User authenticatedUser = userService.login(user);

        String jwtToken = authService.generateToken(authenticatedUser);

        AuthSuccessResponse authSuccessResponse = new AuthSuccessResponse().setToken(jwtToken).setExpiresIn(authService.getExpirationTime());

        return ResponseEntity.ok(authSuccessResponse);
    }

    @GetMapping("/me")
    public ResponseEntity<User> me(@RequestHeader("Authorization") String token) {
        User user = authService.extractClaim(token.substring(7), claims -> {
            User u = new User();
            u.setEmail(claims.get("email", String.class));
            u.setUsername(claims.getSubject());
            return u;
        });

        return ResponseEntity.ok(user);
    }

}
