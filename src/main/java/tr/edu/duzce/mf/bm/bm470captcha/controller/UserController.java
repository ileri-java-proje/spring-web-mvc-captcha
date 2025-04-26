package tr.edu.duzce.mf.bm.bm470captcha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tr.edu.duzce.mf.bm.bm470captcha.service.UserVerificationService;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserVerificationService userVerificationService;

    @Autowired
    public UserController(UserVerificationService userVerificationService) {
        this.userVerificationService = userVerificationService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login"; // login.html veya login.jsp gibi bir view dönecek
    }

    @PostMapping("/login")
    public String loginUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model) {

        boolean isAuthenticated = userVerificationService.verifyLogin(username, password);

        if (isAuthenticated) {
            return "redirect:/home"; // Giriş başarılıysa ana sayfaya yönlendir
        } else {
            model.addAttribute("error", "Kullanıcı adı veya şifre hatalı!");
            return "login"; // Hatalı girişte login sayfasına dön
        }
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "register"; // register.html gibi bir view dönecek
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model) {

        try {
            userVerificationService.registerUser(username, password);
            return "redirect:/user/login"; // Kayıt başarılıysa login sayfasına yönlendir
        } catch (Exception e) {
            model.addAttribute("error", "Kayıt sırasında hata oluştu: " + e.getMessage());
            return "register";
        }
    }
}
