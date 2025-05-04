package tr.edu.duzce.mf.bm.bm470captcha.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import tr.edu.duzce.mf.bm.bm470captcha.entity.User;
import tr.edu.duzce.mf.bm.bm470captcha.exception.UserException;

import java.util.List;

@Service
@Transactional
public class UserVerificationService {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Kullanıcı adı ve şifre ile giriş doğrulaması yapar.
     *
     * @param username Kullanıcının girdiği kullanıcı adı
     * @param password Kullanıcının girdiği şifre (düz metin)
     * @return Giriş başarılı ise true, değilse false
     */
    public boolean verifyLogin(String username, String password) {
        try {
            // CriteriaBuilder başlat
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            // User için bir CriteriaQuery oluştur
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);

            // WHERE u.username = :username
            Predicate usernamePredicate = cb.equal(root.get("username"), username);
            cq.select(root).where(usernamePredicate);

            // Sorguyu çalıştır
            List<User> users = entityManager.createQuery(cq).getResultList();

            if (users.isEmpty()) {
                throw new UserException("Kullanıcı adı bulunamadı.");
            }

            User user = users.get(0);

            // BCrypt ile şifre kontrolü yap
            if (!BCrypt.checkpw(password, user.getPassword())) {
                throw new UserException("Yanlış şifre.");
            }

            return true;
        } catch (Exception e) {
            throw new UserException("Kullanıcı doğrulama işlemi sırasında bir hata oluştu.", e);
        }
    }

    /**
     * Yeni kullanıcı kaydeder (şifreyi hashleyerek).
     */
    public void registerUser(String username, String password) {
        try {
            // Şifreyi BCrypt ile hashle
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            // Yeni kullanıcı oluştur ve kaydet
            User user = new User();
            user.setUsername(username);
            user.setPassword(hashedPassword);

            entityManager.persist(user);
        } catch (Exception e) {
            throw new UserException("Kullanıcı kaydetme işlemi sırasında bir hata oluştu.", e);
        }
    }
}
