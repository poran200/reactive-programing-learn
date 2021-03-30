package com.learnractive.reactive.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

 class FluxAndMnonoWithTimeTest {
    @Test
    void infiniteSequeence() throws InterruptedException {
        var longFlux = Flux.interval(Duration.ofMillis(100)).log();// start from 0 --> ............
        longFlux.subscribe((e)-> System.out.println(e));

        Thread.sleep(3000);
    }
     @Test
     void infiniteSequeenceTest() throws InterruptedException {
         var longFlux = Flux.interval(Duration.ofMillis(100))
                 .take(3)
                 .log();// start from 0 --> ............
         StepVerifier.create(longFlux)
                 .expectSubscription()
                 .expectNext(0L,1L,2L)
                 .verifyComplete();
     }
     @Test
     void infiniteSequeenceMap() throws InterruptedException {
         var longFlux = Flux.interval(Duration.ofMillis(100))
                 .map(Long::intValue)
                 .take(3)
                 .log();// start from 0 --> ............
         StepVerifier.create(longFlux)
                 .expectSubscription()
                 .expectNext(0,1,2)
                 .verifyComplete();
     }
     @Test
     void infiniteSequeenceMap_withDelay() throws InterruptedException {
         var longFlux = Flux.interval(Duration.ofMillis(100))
                 .delayElements(Duration.ofSeconds(1))
                 .map(Long::intValue)
                 .take(3)
                 .log();// start from 0 --> ............
         StepVerifier.create(longFlux)
                 .expectSubscription()
                 .expectNext(0,1,2)
                 .verifyComplete();
     }
}
