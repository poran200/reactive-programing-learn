package com.learnractive.reactive.repository;

import com.learnractive.reactive.model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

@DataMongoTest
class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;
    List<Item> items = List.of(new Item(null, "Sumsumng TV", 400.0),
            new Item(null, "LG TV", 480.00),
            new Item(null, "Apple Watch", 1000.00),
            new Item(null, "IPhone 12", 1200.00),
            new Item("ABC", "one plus 12", 900.00)
    );

    @BeforeEach
    void setUp() {
        itemRepository.deleteAll()
                .thenMany(Flux.fromIterable(items))
                .flatMap(itemRepository::save)
                .doOnNext((item -> {
                    System.out.println("item = " + item);
                }))
                .blockLast();
    }

    @Test
    void getAllItems() {
        StepVerifier.create(itemRepository.findAll())
                .expectSubscription()
                .expectNextCount(5)
                .verifyComplete();
    }

    @Test
    void getById() {
        StepVerifier.create(itemRepository.findById("ABC"))
                .expectSubscription()
                .expectNextMatches(item -> item.getPrice() == 900.00)
                .verifyComplete();
    }

    @Test
    void saveItem() {
        Item item = new Item(null, "Google Home mini", 30.0);
        var itemMono = itemRepository.save(item);
        StepVerifier.create(itemMono)
                .expectSubscription()
                .expectNextMatches(item1 -> item1.getPrice() == 30.0)
                .verifyComplete();
    }

    @Test
    void updateItemTest() {
        double newPrice = 1000.00;
        var itemMono = itemRepository.findById("ABC")
                .map(item -> {
                    item.setPrice(newPrice); // set the new price
                    return item;
                }).flatMap(item -> itemRepository.save(item)); // save item
        StepVerifier.create(itemMono).expectSubscription()
                .expectNextMatches(item -> item.getPrice() == newPrice)
                .verifyComplete();
    }

    @Test
    void deleteById() {
        var deleteMono = itemRepository.findById("ABC")// Mono<Item>
                .map(Item::getId)
                .flatMap(id -> itemRepository.deleteById(id));
        StepVerifier.create(deleteMono)
                .expectSubscription()
                .verifyComplete();
        StepVerifier.create(itemRepository.findById("ABC").log()).expectSubscription()
                .expectNextCount(0)
                .verifyComplete();
    }
}