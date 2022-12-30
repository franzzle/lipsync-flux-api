package com.franzzle.tooling.lipsync.api.service;

import com.franzzle.tooling.lipsync.api.validator.ValidUuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@Service
@Validated
public class LipsyncConversionServiceImpl implements LipsyncConversionService {
    @Autowired
    private RhubarbService rhubarbService;

    @Autowired
    private ConversionStatusHolder conversionStatusHolder;

    @Override
    public void convert(@ValidUuid String uuid){

        final Sinks.Many sink = conversionStatusHolder.getSink();

        Flux.interval(Duration.ofSeconds(1))
                .take(10)
                .map(i -> (int) (i + 1))
                .subscribe(i -> sink.emitNext(Integer.toString(i), new Sinks.EmitFailureHandler() {
                    @Override
                    public boolean onEmitFailure(SignalType signalType, Sinks.EmitResult emitResult) {
                        return false;
                    }
                }));


    }

}
