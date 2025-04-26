package tr.edu.duzce.mf.bm.bm470captcha.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/login")
    public String getLoginPage() {
        return "login"; // login.html gibi bir view dönecek
    }

    @PostMapping("/login")
    public String loginUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model) {
        try {
            // TODO: Kullanıcı adı ve şifreyi kontrol eden servisi çağır
            // userService.authenticate(username, password);

            // Eğer başarılıysa ana sayfaya yönlendir
            return "redirect:/home";
        } catch (Exception e) {
            // TODO: Burada spesifik bir AuthenticationException vs. kullanılabilir
            model.addAttribute("error", e.getMessage());
            return "login"; // Hatalı girişte tekrar login sayfası göster
        }
    }
}
