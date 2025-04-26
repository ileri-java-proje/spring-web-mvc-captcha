package tr.edu.duzce.mf.bm.bm470captcha.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.edu.duzce.mf.bm.bm470captcha.entity.Captcha;

import java.util.List;
import java.util.Random;

@Service
@Transactional
public class CaptchaService {

    @PersistenceContext
    private EntityManager entityManager;

    private Random random = new Random();

    /**
     * Veritabanından rastgele bir Captcha döner.
     */
    public Captcha getRandomCaptcha() {
        List<Captcha> captchas = entityManager
                .createQuery("SELECT c FROM Captcha c", Captcha.class)
                .getResultList();

        if (captchas.isEmpty()) {
            return null; // veya exception fırlatılabilir
        }

        int randomIndex = random.nextInt(captchas.size());
        return captchas.get(randomIndex);
    }

    /**
     * Kullanıcının girdiği cevap ile doğru Captcha değerini karşılaştırır.
     *
     * @param captchaId   doğrulama yapılacak Captcha'nın id'si
     * @param userInput   kullanıcının yazdığı değer
     * @return true -> doğru girdi, false -> yanlış girdi
     */
    public boolean validateCaptcha(Long captchaId, String userInput) {
        Captcha captcha = entityManager.find(Captcha.class, captchaId);
        if (captcha == null) {
            return false;
        }
        return captcha.getTextValue().equalsIgnoreCase(userInput.trim());
    }
}
