package tr.edu.duzce.mf.bm.bm470captcha.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.edu.duzce.mf.bm.bm470captcha.entity.Captcha;

public interface ICaptchaRepository extends JpaRepository<Captcha, Long> {


}
