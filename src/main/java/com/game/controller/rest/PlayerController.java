package com.game.controller.rest;

import com.game.entity.Player;
import com.game.exception.ValidationException;
import com.game.service.PlayerService;
import com.game.utils.CustomPlayerValidator;
import com.game.utils.PlayerSpecificationResolver;
import com.game.utils.ReflectionEntityUtils;
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
    public List<Player> getPlayersList(@RequestParam(defaultValue = "0", name = "pageNumber") Integer page,
                                       @RequestParam(defaultValue = "3", name = "pageSize") Integer pageSize,
                                       @RequestParam Map<String, String> params) {
        Specification<Player> specs = new PlayerSpecificationResolver(params).resolve();
        if (page < 0) page = 0;
        Page<Player> playerPage = playerService.findAll(specs, page, pageSize);
        return playerPage.getContent();
    }

    @GetMapping("count")
    public Integer getPlayersCount(@RequestParam Map<String, String> params) {
        Specification<Player> specs = new PlayerSpecificationResolver(params).resolve();
        return playerService.findAll(specs, Pageable.unpaged()).getNumberOfElements();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Player createPlayer(@RequestBody Player player) {
        if (!ReflectionEntityUtils.isEmpty(player)) {
            CustomPlayerValidator.validate(player);
            CustomPlayerValidator.evalAndSetPlayerLevel(player);
            return playerService.saveOrUpdate(player);
        }
        throw new ValidationException("Empty body");
    }

    @GetMapping("/{id}")
    public Player getPlayer(@PathVariable Long id) {
        validateId(id);
        return playerService.findById(id);
    }

    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Player updatePlayer(@PathVariable Long id, @RequestBody Player player) {
        validateId(id);
        CustomPlayerValidator.validate(player);
        CustomPlayerValidator.evalAndSetPlayerLevel(player);
        return playerService.update(id, player);
    }

    @DeleteMapping("/{id}")
    public void deletePlayer(@PathVariable Long id) {
        validateId(id);
        playerService.delete(id);
    }

    private void validateId(Long id) throws ValidationException {
        if (id == null || id < 1)
            throw new ValidationException("" + id);
    }
}