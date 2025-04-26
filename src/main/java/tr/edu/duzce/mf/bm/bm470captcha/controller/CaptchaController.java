package tr.edu.duzce.mf.bm.bm470captcha.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/captcha")
public class CaptchaController {

    @GetMapping("/captcha")
    public String getCaptcha(Model model) {
        // TODO: Captcha görselini oluşturacak servisi çağır
        // model.addAttribute("captchaImage", captchaService.generateCaptchaImage());
        return "captcha"; // captcha.html veya .jsp gibi bir view dönecek
    }

    @PostMapping("/captcha")
    public String submitCaptcha(@RequestParam("captchaInput") String captchaInput, Model model) {
        // TODO: Kullanıcının girdiği captcha'yı doğrulayacak servisi çağır
        // boolean isValid = captchaService.validateCaptcha(captchaInput);
        // model.addAttribute("captchaValid", isValid);
        return "captchaResult"; // sonucu gösterecek bir view dönecek
    }
}
