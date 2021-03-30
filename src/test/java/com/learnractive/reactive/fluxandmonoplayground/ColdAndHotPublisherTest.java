package com.learnractive.reactive.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class ColdAndHotPublisherTest {
    @Test
    void coldPublisherTest() throws InterruptedException {
        var stringFlux = Flux.just("A", "B", "C", "D", "E", "F").delayElements(Duration.ofSeconds(1));
        stringFlux.subscribe(s -> System.out.println("Subscriber 1: "+s));// emits the value from begin
        Thread.sleep(2000);
        stringFlux.subscribe(s -> System.out.println("Subscriber 2: "+s));// emits the value from begin
        Thread.sleep(4000);
    }
    @Test
    void hotPublisherTest() throws InterruptedException {
        var stringFlux = Flux.just("A", "B", "C", "D", "E", "F").delayElements(Duration.ofSeconds(1));
//        stringFlux.subscribe(s -> System.out.println("Subscriber 1: "+s));// emits the value from begin
//        Thread.sleep(2000);
//        stringFlux.subscribe(s -> System.out.println("Subscriber 2: "+s));// emits the value from begin
//        Thread.sleep(4000);
        var connectableFlux = stringFlux.publish();
        connectableFlux.connect();
        connectableFlux.subscribe(s -> System.out.println("Sub 1 : "+s));
        Thread.sleep(3000);
        connectableFlux.subscribe(s -> System.out.println("Sub 2 : "+s));// dose not emit the values beginning
        Thread.sleep(4000);
    }
}
