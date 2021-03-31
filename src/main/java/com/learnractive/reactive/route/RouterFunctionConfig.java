package com.learnractive.reactive.route;

import com.learnractive.reactive.handeller.SampleHandelerFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class RouterFunctionConfig {
    @Bean
    public RouterFunction<ServerResponse> route(SampleHandelerFunction handelerFunction) {
        return RouterFunctions
                .route(GET("/functional/flux")
                                .and(accept(MediaType.APPLICATION_JSON)),
                        handelerFunction::flux);
    }
}
