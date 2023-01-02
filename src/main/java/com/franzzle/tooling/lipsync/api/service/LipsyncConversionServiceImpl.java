package com.franzzle.tooling.lipsync.api.service;

import com.franzzle.tooling.lipsync.api.service.model.RhubarbDTO;
import com.franzzle.tooling.lipsync.api.validator.ValidUuid;
import io.reactivex.Observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
@Validated
public class LipsyncConversionServiceImpl implements LipsyncConversionService {
    @Autowired
    private RhubarbService rhubarbService;

    @Value("${wav.storage.dir:/wavStorageDir}")
    private String storageDir;

    @Autowired
    private Map<String, SinkWrapper> sinkWrapperRegistry = new HashMap<>();

    @Override
    public void convert(@ValidUuid String uuid){
        sinkWrapperRegistry.put(uuid, new SinkWrapper(uuid));

        final RhubarbDTO rhubarbDTO = new RhubarbDTO();
        rhubarbDTO.setSourceUuid(uuid);
        rhubarbDTO.setSourceInputPath(storageDir);
        rhubarbDTO.setDestinationOuputPath(new File(storageDir, "destLipsync").getAbsolutePath());

        final Observable<String> lipsyncConversionProcessLine
                = rhubarbService.waveTolipSync(rhubarbDTO);

//        lipsyncConversionProcessLine.subscribe(
//                progressStatusLine -> {
//                    sinkWrapperRegistry.get(uuid).getSink().emitNext();
//                },
//                throwable -> Assertions.fail(throwable.getMessage()),
//                () -> {
//                    final String lastLineInList = lastProgressLineOutput.get(lastProgressLineOutput.size() - 1);
//                    Assertions.assertTrue(lastLineInList.contains("Application terminating normally."));
//                });


        Flux.interval(Duration.ofSeconds(1))
                .take(10)
                .map(i -> (int) (i + 1))
                .subscribe(i -> sinkWrapperRegistry.get(uuid).getSink()
                        .emitNext(Integer.toString(i), (signalType, emitResult) -> false));

    }

    @Override
    public SinkWrapper getSink(String uuid) {
        if(sinkWrapperRegistry.containsKey(uuid)){
            return sinkWrapperRegistry.get(uuid);
        }
        throw new RuntimeException(String.format("Sink for %s is not found in registry", uuid));
    }
}
