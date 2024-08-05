package org.acme;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.Random;

@ApplicationScoped
public class IdGenerator {

    public Integer generateId() {
        return new Random().ints().findFirst().orElseThrow();
    }
}
