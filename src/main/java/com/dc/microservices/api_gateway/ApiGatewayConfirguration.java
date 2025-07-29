package com.dc.microservices.api_gateway;

import java.util.function.Function;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfirguration {

    @Bean
     public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path("/get")
                    .filters(f -> f
                        .addRequestHeader("MyHeader", "MyURI")
                        .addRequestParameter("Param", "MyValue"))
                    .uri("http://httpbin.org:80"))

                .route(p1 -> p1.path("/CURRENCY-EXCHANGE/**")
                    .filters(f -> f.rewritePath("/CURRENCY-EXCHANGE/(?<remaining>.*)", "/${remaining}")) 
                    .uri("lb://CURRENCY-EXCHANGE"))

                .route(p2 -> p2.path("/CURRENCY-CONVERSION/**")
                    .filters(f -> f.rewritePath("/CURRENCY-CONVERSION/(?<remaining>.*)", "/${remaining}"))
                    .uri("lb://CURRENCY-CONVERSION"))
                .route(p2 -> p2.path("/CURRENCY-CONVERSION-FEIGN/**")
                    .filters(f -> f.rewritePath("/CURRENCY-CONVERSION-FEIGN/(?<remaining>.*)", "/${remaining}"))
                    .uri("lb://CURRENCY-CONVERSION"))

                .build();
    }
}
