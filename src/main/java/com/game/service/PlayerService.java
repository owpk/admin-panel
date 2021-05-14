package com.game.service;

import com.game.entity.Player;
import com.game.exception.ResourceNotFoundException;
import com.game.repository.PlayerRepository;
import com.game.utils.ReflectionEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    private PlayerRepository playerRepository;

    @Autowired
    public void setPlayerRepository(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    /**
     * Находит всех игроков согласно спецификации {@param specs}
     *
     * @param specs
     * @return
     * @see com.game.utils.PlayerSpecificationResolver
     */
    public Page<Player> findAll(Specification<Player> specs, int page, int size) {
        return findAll(specs, PageRequest.of(page, size));
    }

    /**
     * Находит всех игроков согласно спецификации {@param specs}
     *
     * @param specs
     * @param pageable
     * @return
     * @see com.game.utils.PlayerSpecificationResolver
     */
    public Page<Player> findAll(Specification<Player> specs, Pageable pageable) {
        return playerRepository.findAll(specs, pageable);
    }

    /**
     * Сохраняет {@param player} в базе данных
     *
     * @param player
     * @return
     */
    public Player saveOrUpdate(Player player) {
        return playerRepository.save(player);
    }

    /**
     * Находит игрока по id,
     * если такого игрока не существует выбрасывается исключение -
     *
     * @param id
     * @return
     * @see ResourceNotFoundException
     */
    public Player findById(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found by id: " + id));
    }

    /**
     * Находит игрока по id, обновляет все значения его полей на
     * значения полей объекта {@param updatedData} за исключением поля id,
     * если игрок не найден по id выбрасывается исключение
     *
     * @param id
     * @param updatedData
     * @return
     * @see ResourceNotFoundException
     * @see ReflectionEntityUtils#updateAllFields(Object, Object)
     */
    public Player update(Long id, Player updatedData) {
        Player original = findById(id);
        Player updated = ReflectionEntityUtils.updateAllFields(original, updatedData,
                x -> x.getName().equals("id"));
        return playerRepository.save(updated);
    }

    /**
     * Находит игрока по id и удаляет его из базы данных,
     * если такого игрока не существует выбрасывается исключение -
     *
     * @param id
     * @return
     * @see ResourceNotFoundException
     * {@link PlayerService#findById(Long)}
     */
    public Player delete(Long id) {
        Player p = findById(id);
        playerRepository.delete(p);
        return p;
    }
}
