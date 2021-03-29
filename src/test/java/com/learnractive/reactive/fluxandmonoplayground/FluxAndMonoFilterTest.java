package com.learnractive.reactive.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

public class FluxAndMonoFilterTest {
    List<String> names = List.of("adam", "anam", "jack","jenny");

    @Test
    void filterTest() {
        var stringFlux = Flux.fromIterable(names)
                .filter(s -> s.startsWith("a"))
                .log();
        StepVerifier.create(stringFlux)
                    .expectNext("adam","anam")
                     .verifyComplete();
    }
    @Test
    void filterTest_length() {
        var stringFlux = Flux.fromIterable(names)
                .filter(s -> s.length() > 4)
                .log();
        StepVerifier.create(stringFlux)
                .expectNext("jenny")
                .verifyComplete();
    }
}
