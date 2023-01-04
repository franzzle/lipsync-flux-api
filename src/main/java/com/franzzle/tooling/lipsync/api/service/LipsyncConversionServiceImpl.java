package com.franzzle.tooling.lipsync.api.service;

import com.franzzle.tooling.lipsync.api.ProgressLineParser;
import com.franzzle.tooling.lipsync.api.SinkWrapperRegistry;
import com.franzzle.tooling.lipsync.api.service.model.ProgressLine;
import com.franzzle.tooling.lipsync.api.service.model.RhubarbDTO;
import com.franzzle.tooling.lipsync.api.validator.ValidUuid;
import io.reactivex.Observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.io.File;

@Service
@Validated
public class LipsyncConversionServiceImpl implements LipsyncConversionService {
    @Autowired
    private RhubarbService rhubarbService;

    @Autowired
    private ProgressLineParser progressLineParser;

    @Autowired
    private SinkWrapperRegistry sinkWrapperRegistry;

    @Value("${wav.storage.dir:/wavStorageDir}")
    private String storageDir;

    @Override
    public Void convert(@ValidUuid String uuid) {
        final RhubarbDTO rhubarbDTO = new RhubarbDTO();
        rhubarbDTO.setSourceUuid(uuid);
        rhubarbDTO.setSourceInputPath(storageDir);
        rhubarbDTO.setDestinationOuputPath(new File(storageDir, "destLipsync").getAbsolutePath());

        final Observable<String> rhubarbService = this.rhubarbService.waveTolipSync(rhubarbDTO);
        rhubarbService.subscribe(
                progressStatusLine -> {
                    System.out.println(progressStatusLine);
                    final ProgressLine progressLine = progressLineParser.parse(progressStatusLine);
                    if (progressLine.getType().equals("progress") && progressLine.getValue() != null) {
                        sinkWrapperRegistry.getSink(uuid).getSink().emitNext(progressLine.getValue(), (signalType, emitResult) -> false);
                    }
                    if (progressLine.getType().equals("success")) {
                        sinkWrapperRegistry.getSink(uuid).getSink().emitNext(1, (signalType, emitResult) -> false);
                    }

                },
                throwable -> {
                    sinkWrapperRegistry.getSink(uuid).getSink().emitError(throwable, (signalType, emitResult) -> true);
//                    sinkWrapperRegistry.removeSink(uuid);
                },
                () -> {
                    sinkWrapperRegistry.getSink(uuid).getSink().emitComplete((signalType, emitResult) -> false);
//                    sinkWrapperRegistry.removeSink(uuid);
                });

        return null;
    }

//    @Override
//    public SinkWrapper getSink(String uuid) {
//        if (sinkWrapperRegistry.containsKey(uuid)) {
//            return sinkWrapperRegistry.get(uuid);
//        }
//        throw new RuntimeException(String.format("Sink for %s is not found in registry", uuid));
//    }
}
