package com.learnractive.reactive.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
@WebFluxTest
class FluxAndMonoControllerTest {

    @Autowired
    WebTestClient webTestClient;
    @Test
    void returnFlux() {
        var responseEntityFlux = webTestClient.get().uri("https://localhost:8080/api/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Integer.class)
                .getResponseBody();

        StepVerifier.create(responseEntityFlux)
                 .expectSubscription()
                 .expectNext(1)
                 .expectNext(2)
                 .expectNext(3)
                 .expectNext(4)
                .verifyComplete();

    }

    @Test
    void returnFlux_Aproach_1() {
        webTestClient.get().uri("https://localhost:8080/api/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Integer.class)
                .hasSize(4);


    }
    @Test
    void returnFlux_Aproach_2() {
        var exchangeResult = webTestClient.get().uri("https://localhost:8080/api/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Integer.class)
                .returnResult();
        assertEquals(Objects.requireNonNull(exchangeResult.getResponseBody()).size(),4);
    }


    @Test
    void returnFluxStrem() {

    }
}