package workout.workoutspring.global.security.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import workout.workoutspring.exception.TokenException;
import workout.workoutspring.exception.dto.ErrorCode;
import workout.workoutspring.global.security.domain.entity.RefreshToken;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {

    private final EntityManager em;

    public void save(RefreshToken token) {
        em.persist(token);
    }

    public RefreshToken find(String username) {
        try {
            RefreshToken refreshToken = em.createQuery("select r from RefreshToken r where r.username = :username and r.expired = false ", RefreshToken.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return refreshToken;
        } catch (NoResultException e) {
//            throw new NoResultException("해당 유저의 유효한 refreshtoken이 없습니다." + username);
            throw new TokenException(ErrorCode.NO_TOKEN);
        }
    }

}
