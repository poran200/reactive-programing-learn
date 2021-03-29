package com.learnractive.reactive.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.function.Supplier;

class FluxAndMonoFactoryTest {
    List<String> names = List.of("adam", "anam", "jack");

    @Test
    void fluxUsingIterable() {
        var stringFlux = Flux.fromIterable(names)
                .log();
        StepVerifier.create(stringFlux)
                .expectNext("adam", "anam", "jack")
                .verifyComplete();
    }

    @Test
    void fluxUsingArray() {
        String[] names = new String[]{"adam", "anam", "jack"};
        var stringFlux = Flux.fromArray(names);
        StepVerifier.create(stringFlux)
                .expectNext("adam", "anam", "jack")
                .verifyComplete();
    }

    @Test
    void fluxUsingStream() {
        var fromStream = Flux.fromStream(names.stream());
        StepVerifier.create(fromStream)
                .expectNext("adam", "anam", "jack")
                .verifyComplete();
    }

    @Test
    void monoUsingJustOrEmpty() {
        var objectMono = Mono.justOrEmpty(null);
        StepVerifier.create(objectMono.log())
                .verifyComplete();
    }

    @Test
    void monoUsingSupplier() {
        Supplier<String> stringSupplier = () -> "adam";
        var stringMono = Mono.fromSupplier(stringSupplier);
        StepVerifier.create(stringMono.log())
                .expectNext("adam")
                .verifyComplete();
    }

    @Test
    void fluxUsingRange() {
        var integerFlux = Flux.range(1, 5).log();
        StepVerifier.create(integerFlux)
                .expectNext(1, 2, 3, 4, 5)
                .verifyComplete();
    }
}
