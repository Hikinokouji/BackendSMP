package by.backendsmp.service.system;

import by.backendsmp.repository.StreamStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class StreamStatusService {
    private final StreamStatusRepository repository;

    public void markLive(String streamKey) {
        repository.setStatus(streamKey, "LIVE");
    }

    public void markOffline(String streamKey) {
        repository.setStatus(streamKey, "OFFLINE");
    }

    public String getStatus(String streamKey) {
        return repository.getStatus(streamKey);
    }

    public Set<Object> getAllActive() {
        return repository.getAllActive();
    }
}
