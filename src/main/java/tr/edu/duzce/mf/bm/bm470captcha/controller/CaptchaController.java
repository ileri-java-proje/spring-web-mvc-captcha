package tr.edu.duzce.mf.bm.bm470captcha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tr.edu.duzce.mf.bm.bm470captcha.entity.Captcha;
import tr.edu.duzce.mf.bm.bm470captcha.service.CaptchaService;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/captcha")
public class CaptchaController {

    @Autowired
    private CaptchaService captchaService;

    /**
     * Rastgele bir captcha getirir.
     */
    @GetMapping
    public Map<String, Object> getCaptcha() {
        Captcha captcha = captchaService.getRandomCaptcha();

        Map<String, Object> response = new HashMap<>();
        if (captcha == null) {
            response.put("success", false);
            response.put("message", "Hiç captcha bulunamadı!");
            return response;
        }

        // Captcha resmini base64 encode et
        String base64Image = Base64.getEncoder().encodeToString(captcha.getImage());

        response.put("success", true);
        response.put("captchaId", captcha.getId());
        response.put("captchaImage", base64Image);

        return response;
    }

    /**
     * Kullanıcının captcha girişini doğrular.
     */
    @PostMapping("/validate")
    public Map<String, Object> validateCaptcha(@RequestParam("captchaId") Long captchaId,
                                               @RequestParam("captchaInput") String captchaInput) {
        boolean isValid = captchaService.validateCaptcha(captchaId, captchaInput);

        Map<String, Object> response = new HashMap<>();
        response.put("success", isValid);

        if (isValid) {
            response.put("message", "Captcha doğru!");
        } else {
            response.put("message", "Captcha yanlış!");
        }

        return response;
    }
}
