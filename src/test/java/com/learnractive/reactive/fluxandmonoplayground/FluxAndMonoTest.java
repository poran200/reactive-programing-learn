package com.learnractive.reactive.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class FluxAndMonoTest {
    @Test
    void fluxTest() {
        var stringFlux = Flux.just("Spring", "Spring boot", "Reactive spring")
//                .concatWith(Flux.error(new RuntimeException("Exception occured")))
                .concatWith(Flux.just("Spring", "Spring boot", "Reactive spring"))
                .log();
        stringFlux.subscribe(System.out::println
                , System.err::println
                , () -> System.out.println("complete flux")
        );

    }

    @Test
    void fluxTestElements_WithoutError() {
        var stringFlux = Flux.just("Spring", "Spring boot", "Reactive spring")
                .log();
        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring boot")
                .expectNext("Reactive spring")
                .verifyComplete();
    }

    @Test
    void fluxTestElements_WithError() {
        var stringFlux = Flux.just("Spring", "Spring boot", "Reactive spring")
                .concatWith(Flux.error(new RuntimeException("Exception occured")))
                .log();
        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring boot")
                .expectNext("Reactive spring")
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void fluxTestElements_count() {
        var stringFlux = Flux.just("Spring", "Spring boot", "Reactive spring")
                .concatWith(Flux.error(new RuntimeException("Exception occured")))
                .log();
        StepVerifier.create(stringFlux)
                .expectNextCount(3)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void monoTest() {
        var stringMono = Mono.just("Spring");
        StepVerifier.create(stringMono)
                .expectNext("Spring")
                .verifyComplete();
    }
}
