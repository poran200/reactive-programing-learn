package com.learnractive.reactive.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

class FluxAndMonoTransformTest {

    List<String> names = List.of("adam", "anam", "jack", "jenny");

    @Test
    void transformTest() {
        var nameFlux = Flux.fromIterable(names)
                .map(String::toUpperCase)
                .log();
        StepVerifier.create(nameFlux)
                .expectNext("ADAM", "ANAM", "JACK", "JENNY")
                .verifyComplete();
    }

    @Test
    void transFromUsingFlatMap() {
        var stringFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F"))
                .flatMap(s -> Flux.fromIterable(convertToList(s)))
                .log();
        StepVerifier.create(stringFlux)
                .expectNextCount(12)
                .verifyComplete();

    }

    private List<String> convertToList(String s) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Arrays.asList(s, "new Value");
    }

    @Test
    void transFromUsingFlatMap_usingParallel() {
        var stringFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F"))
                .window(2)
                .flatMap(s -> s.map(this::convertToList).subscribeOn(Schedulers.parallel()))
                .flatMap(Flux::fromIterable)
                .log();
        StepVerifier.create(stringFlux)
                .expectNextCount(12)
                .verifyComplete();

    }
    @Test
    void transFromUsingFlatMap_usingParallel_maintain_order() {
        var stringFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F"))
                .window(2)
//                .concatMap(s -> s.map(this::convertToList).subscribeOn(Schedulers.parallel()))
//                .flatMap(Flux::fromIterable)
                .flatMapSequential(s -> s.map(this::convertToList).subscribeOn(Schedulers.parallel()))
                .flatMap(Flux::fromIterable)
                .log();
        StepVerifier.create(stringFlux)
                .expectNextCount(12)
                .verifyComplete();

    }

}
