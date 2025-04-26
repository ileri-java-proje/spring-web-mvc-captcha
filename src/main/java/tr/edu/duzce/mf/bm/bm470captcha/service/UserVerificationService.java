package tr.edu.duzce.mf.bm.bm470captcha.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import tr.edu.duzce.mf.bm.bm470captcha.entity.User;

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
            User user = entityManager.createQuery(
                            "SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();

            if (user == null) {
                return false;
            }

            // BCrypt kullanarak şifre kontrolü yapılıyor
            return BCrypt.checkpw(password, user.getPassword());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Yeni kullanıcı kaydeder (şifreyi hashleyerek).
     */
    public void registerUser(String username, String password) {
        // Şifreyi BCrypt ile hashle
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);

        entityManager.persist(user);
    }
}
