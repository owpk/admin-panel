package com.game.controller.rest;

import com.game.entity.Player;
import com.game.service.PlayerService;
import com.game.utils.PlayerSpecificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/players")
public class PlayerController {
    private PlayerService playerService;

    @Autowired
    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("alive")
    public String isAlive() {
        return "hello!";
    }

    @GetMapping
    public List<Player> getPlayersList(@RequestParam(defaultValue = "5", name = "size") Integer pageSize,
                                       @RequestParam(defaultValue = "1", name = "page") Integer page,
                                       @RequestParam Map<String, String> params) {
        Specification<Player> specs = new PlayerSpecificationResolver(params).resolve();
        if (page < 1) page = 1;
        Page<Player> playerPage = playerService.findAll(specs, page - 1, pageSize);
        return playerPage.getContent();
    }

    @GetMapping("count")
    public Integer getPlayersCount(@RequestParam Map<String, String> params) {
        Specification<Player> specs = new PlayerSpecificationResolver(params).resolve();
        return playerService.findAll(specs, Pageable.unpaged()).getNumberOfElements();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Player createPlayer(@RequestBody Player player) {
        return playerService.saveOrUpdate(player);
    }

    @GetMapping("/{id}")
    public Player getPlayer(@PathVariable Long id) {
        return playerService.findById(id);
    }

    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Player updatePlayer(@PathVariable Long id, @RequestBody Player player) {
        return playerService.update(id, player);
    }
}