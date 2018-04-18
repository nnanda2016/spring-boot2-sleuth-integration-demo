package com.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.*;


/**
 * Configures the web routes.
 * 
 * @author Niranjan Nanda
 */
@Configuration
@EnableWebFlux
public class RouterConfigs {
	
	@Autowired
	private UserApiHandler userApiHandler;
	
	@Bean
	public RouterFunction<ServerResponse> routes() {
		return RouterFunctions
			.route(healthCheckPredicate(), request -> ServerResponse.ok().build())
			.andNest(emptyPathPredicate(), nestedBaseRoutes())
			;
	}

	private RequestPredicate healthCheckPredicate() {
		return RequestPredicates.GET("/health/check");
	}
	
	private RequestPredicate emptyPathPredicate() {
		return RequestPredicates.path("");
	}
	
	private RouterFunction<ServerResponse> nestedBaseRoutes() {
		return RouterFunctions
				.route(getByIdPredicate(), userApiHandler::getById)
				;
	}
	
	private RequestPredicate getByIdPredicate() {
		return RequestPredicates.GET("/*/{id}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON_UTF8))
				;
	}
}
