package com.learnractive.reactive.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequestMapping("api/")
public class FluxAndMonoController {
    @GetMapping(value = "/flux")
    public ResponseEntity<Flux<Integer>> returnFlux(){
        return ResponseEntity.ok().body(Flux.just(1,2,3,4)
                .delayElements(Duration.ofSeconds(1))
                .log());
    }
    @GetMapping(value = "/fluxstrem",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Long> returnFluxStrem(){
        return Flux.interval(Duration.ofSeconds(2))
                .log();
    }
}
