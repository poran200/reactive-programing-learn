package com.learnractive.reactive.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

import java.time.Duration;

class FluxAndMonoCombineTest {
    @Test
    void combineUsingMarge() {
        var flux1 = Flux.just("A", "B", "C");
        var flux2 = Flux.just("E", "F", "G");
        var merge = Flux.merge(flux1, flux2);
        StepVerifier.create(merge.log())
                .expectSubscription()
                .expectNextCount(6)
                .verifyComplete();

    }

    @Test
    void combineUsingMarge_with_Delay() {
        var flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1));
        var flux2 = Flux.just("E", "F", "G").delayElements(Duration.ofSeconds(1));
        var merge = Flux.merge(flux1, flux2);
        StepVerifier.create(merge.log())
                .expectSubscription()
                .expectNextCount(6)
                .verifyComplete();

    }


    @Test
    void combineUsingConcat() {
        var flux1 = Flux.just("A", "B", "C");
        var flux2 = Flux.just("E", "F", "G");
        var merge = Flux.concat(flux1, flux2);
        StepVerifier.create(merge.log())
                .expectSubscription()
                .expectNextCount(6)
                .verifyComplete();

    }

    @Test
    void combineUsingConcat_with_Delay() {
        VirtualTimeScheduler.getOrSet();
        var flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1));
        var flux2 = Flux.just("E", "F", "G").delayElements(Duration.ofSeconds(1));
        var merge = Flux.concat(flux1, flux2);
        StepVerifier.withVirtualTime(() -> merge.log())
                .expectSubscription()
                .thenAwait(Duration.ofSeconds(6))
                .expectNextCount(6)
                .verifyComplete();
//        StepVerifier.create(merge.log())
//                .expectSubscription()
//                .expectNextCount(6)
//                .verifyComplete();

    }

    @Test
    void combineUsingZip() {
        var flux1 = Flux.just("A", "B", "C");
        var flux2 = Flux.just("E", "F", "G");
        var merge = Flux.zip(flux1, flux2, String::concat);
        StepVerifier.create(merge.log())
                .expectSubscription()
                .expectNextCount(3)
                .verifyComplete();

    }


}
