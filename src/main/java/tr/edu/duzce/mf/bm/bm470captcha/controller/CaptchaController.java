package tr.edu.duzce.mf.bm.bm470captcha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tr.edu.duzce.mf.bm.bm470captcha.entity.Captcha;
import tr.edu.duzce.mf.bm.bm470captcha.service.CaptchaService;

import java.util.Base64;

@Controller
@RequestMapping("/captcha")
public class CaptchaController {

    @Autowired
    private CaptchaService captchaService;

    private Captcha currentCaptcha; // Kullanıcıya gösterilen captcha'yı burada tutacağız

    @GetMapping
    public String getCaptcha(Model model) {
        // Servisten rastgele bir captcha getir
        currentCaptcha = captchaService.getRandomCaptcha();
        if (currentCaptcha == null) {
            model.addAttribute("error", "Hiç captcha bulunamadı!");
            return "captchaError"; // bir hata sayfası gösterebilirsin
        }

        // Captcha resmini base64 ile encode edip sayfaya gönderelim
        String base64Image = Base64.getEncoder().encodeToString(currentCaptcha.getImage());
        model.addAttribute("captchaImage", base64Image);
        model.addAttribute("captchaId", currentCaptcha.getId());

        return "captcha"; // captcha.jsp veya captcha.html sayfası
    }

    @PostMapping
    public String submitCaptcha(@RequestParam("captchaInput") String captchaInput,
                                @RequestParam("captchaId") Long captchaId,
                                Model model) {
        // Kullanıcının gönderdiği input ile doğru cevabı kıyasla
        boolean isValid = captchaService.validateCaptcha(captchaId, captchaInput);

        model.addAttribute("captchaValid", isValid);
        return "captchaResult"; // sonucu gösterecek sayfa
    }
}
