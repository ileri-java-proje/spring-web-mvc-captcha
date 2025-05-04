package tr.edu.duzce.mf.bm.bm470captcha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tr.edu.duzce.mf.bm.bm470captcha.service.UserVerificationService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserVerificationService userVerificationService;

    @Autowired
    public UserController(UserVerificationService userVerificationService) {
        this.userVerificationService = userVerificationService;
    }

    /**
     * Kullanıcı girişini doğrulayan endpoint.
     */
    @PostMapping("/login")
    public Map<String, Object> loginUser(@RequestParam("username") String username,
                                         @RequestParam("password") String password) {

        boolean isAuthenticated = userVerificationService.verifyLogin(username, password);

        Map<String, Object> response = new HashMap<>();
        response.put("success", isAuthenticated);

        if (isAuthenticated) {
            response.put("message", "Giriş başarılı!");
        } else {
            response.put("message", "Kullanıcı adı veya şifre hatalı!");
        }

        return response;
    }

    /**
     * Yeni kullanıcı kaydeden endpoint.
     */
    @PostMapping("/register")
    public Map<String, Object> registerUser(@RequestParam("username") String username,
                                            @RequestParam("password") String password) {

        Map<String, Object> response = new HashMap<>();

        try {
            userVerificationService.registerUser(username, password);
            response.put("success", true);
            response.put("message", "Kayıt başarılı!");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Kayıt sırasında hata oluştu: " + e.getMessage());
        }

        return response;
    }
}
