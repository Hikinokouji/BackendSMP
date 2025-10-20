package by.backendsmp.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class StreamStatusRepository {
    private final RedisTemplate<String, Object> redisTemplate;
    private final SetOperations<String, Object> setOps;

    public StreamStatusRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.setOps = redisTemplate.opsForSet();
    }

    public void setStatus(String streamKey, String status) {
        String key = "stream:" + streamKey;
        redisTemplate.opsForValue().set(key, status);

        if ("LIVE".equals(status)) {
            setOps.add("streams:live", streamKey);
        } else {
            setOps.remove("streams:live", streamKey);
        }
    }

    public String getStatus(String streamKey) {
        Object value = redisTemplate.opsForValue().get("stream:" + streamKey);
        return value != null ? value.toString() : "OFFLINE";
    }

    public Set<Object> getAllActive() {
        return setOps.members("streams:live");
    }

    public void deleteStatus(String streamKey) {
        redisTemplate.delete("stream:" + streamKey);
        setOps.remove("streams:live", streamKey);
    }
}
