package by.backendsmp.controller.system;

import by.backendsmp.service.system.StreamStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/streams")
@RequiredArgsConstructor
public class StreamStatusController {
    private final StreamStatusService streamStatusService;

    @GetMapping("/{streamKey}/status")
    public String getStreamStatus(@PathVariable String streamKey){
        return streamStatusService.getStatus(streamKey);
    }

    @GetMapping("/active")
    public Set<Object> getActiveStreams() {
        return streamStatusService.getAllActive();
    }
}
