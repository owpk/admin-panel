package com.game.controller.facade;

import org.springframework.web.bind.annotation.GetMapping;

public interface BaseRestControllerApi {

    @GetMapping("alive")
    default String isAlive() {
        return "hello!";
    }
}
