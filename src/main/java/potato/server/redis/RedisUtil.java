package potato.server.redis;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * redis CRUD 메소드
 *
 * @author 정순원
 * @since 2023-08-19
 */
@Repository
public class RedisUtil {

    static final String AUTH_PREFIX = "AuthNumber_";
    private RedisTemplate redisTemplate;

    public RedisUtil(final RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    //리프레쉬토큰 관련 redis 명령어
    public void save(String key, String value, Long expiration) {
        redisTemplate.opsForValue().set(key, value, expiration, TimeUnit.MILLISECONDS);
    }

    public void update(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void deleteByKey(String key) {
        redisTemplate.delete(String.valueOf(key));
    }

    public Object findByKey(String key) {
        return redisTemplate.opsForValue().get(key).toString();
    }


    //이메일 emailAuthNumber 관련 redis 명령어
    public void saveAuthNumber(String key, String emailAuthNumber, Long expiration) {
        redisTemplate.opsForValue().set(AUTH_PREFIX + key, emailAuthNumber, expiration, TimeUnit.MILLISECONDS);
    }

    public void updateAuthNumber(String key, String emailAuthNumber) {
        redisTemplate.opsForValue().set(AUTH_PREFIX + key, emailAuthNumber);
    }

    public Object findEmailAuthNumberByKey(String key) {
        return redisTemplate.opsForValue().get(AUTH_PREFIX + key);
    }
}