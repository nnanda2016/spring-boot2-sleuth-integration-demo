package com.demo;

import com.google.common.collect.MapMaker;

import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

/**
 * TODO: Add a description
 *
 * @author Niranjan Nanda
 */
@Component
public class UserApiHandler {
    private static final Logger logger = LoggerFactory.getLogger(UserApiHandler.class);
    
    public static final String CLASS_NAME = UserApiHandler.class.getCanonicalName();
    
    private static final ConcurrentMap<String, User> kvStore = new MapMaker()
            .concurrencyLevel(Runtime.getRuntime().availableProcessors()) // # of concurrent segments = available threads
            .initialCapacity(Runtime.getRuntime().availableProcessors() * 10) // Each segment will have 10 elements
            .makeMap();
    
    public UserApiHandler() {
        User user = new User();
        user.setId("U1");
        user.setFirstName("U1_First");
        user.setLastName("U1_Last");
        kvStore.put(user.getId(), user);
        
        user = new User();
        user.setId("U2");
        user.setFirstName("U2_First");
        user.setLastName("U2_Last");
        kvStore.put(user.getId(), user);
        
        user = new User();
        user.setId("U3");
        user.setFirstName("U3_First");
        user.setLastName("U3_Last");
        kvStore.put(user.getId(), user);
        
        user = new User();
        user.setId("U4");
        user.setFirstName("U4_First");
        user.setLastName("U4_Last");
        kvStore.put(user.getId(), user);
        
        user = new User();
        user.setId("U5");
        user.setFirstName("U5_First");
        user.setLastName("U5_Last");
        kvStore.put(user.getId(), user);
    }

    public Mono<ServerResponse> getById(final ServerRequest request) {
        final String userId = request.pathVariables().get("id");
        
        return fetchUser(userId)
                .doOnError(t -> logger.info("Exception while fetching user with id '{}'", userId, t))
                .flatMap(user -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(BodyInserters.fromObject(user)))
                ;
    }

    private Mono<User> fetchUser(final String id) {
        // Simulate exception
        if (StringUtils.equals(id, "U-1")) {
            return Mono.error(new RuntimeException("Throwing RuntimeException"));
        } 
        
        final User user = kvStore.get(id);
        if (user == null) {
            return Mono.error(new RuntimeException("User with ID " + id + " was not found."));
        }
        
        return Mono.just(user);
    }
}
