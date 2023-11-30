package com.franzzle.tooling.lipsync.api.error;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ConversionWebExceptionHandler implements WebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (ex instanceof UuidConversionException) {
            exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
            String message = ex.getMessage();
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(message.getBytes());
            return exchange.getResponse().writeWith(Mono.just(buffer)).doOnError((error) -> {
                // log the error
            });
        }
        return Mono.error(ex);
    }
}
