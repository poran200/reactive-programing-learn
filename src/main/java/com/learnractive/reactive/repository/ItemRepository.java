package com.learnractive.reactive.repository;

import com.learnractive.reactive.model.Item;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ItemRepository extends ReactiveMongoRepository<Item,String> {
}
