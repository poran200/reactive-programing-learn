package com.learnractive.reactive.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class FluxAndMonoBackPressureTest {
    @Test
    void backPressureTest() {
        var integerFlux = Flux.range(1, 10).log();
        StepVerifier.create(integerFlux)
                .expectSubscription()
                .thenRequest(1)
                .expectNext(1)
                .thenRequest(1)
                .expectNext(2)
                .thenCancel()
                .verify();

    }

    @Test
    void backPressure() {
        var integerFlux = Flux.range(1, 10).log();
        integerFlux.subscribe((integer) -> System.out.println("integer = " + integer)
                , System.err::println
                , () -> System.out.println("Done")
                , (subscription -> subscription.request(2)));

    }
    @Test
    void backPressure_Cancel() {
        var integerFlux = Flux.range(1, 10).log();
        integerFlux.subscribe((integer) -> System.out.println("integer = " + integer)
                , System.err::println
                , () -> System.out.println("Done")
                , (Subscription::cancel));
    }
    @Test
    void customize_backPressure_Cancel() {
        var integerFlux = Flux.range(1, 10).log();
        integerFlux.subscribe(new BaseSubscriber<>() {
            @Override
            protected void hookOnNext(Integer value) {
                request(1);
                System.out.println("Value received is : " + value);
                if (value == 4) {
                    cancel();
                }
            }
        });
    }


}
