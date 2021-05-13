package com.game.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.game.entity.Player;
import com.game.entity.Race;

import java.util.Date;

@JsonAutoDetect
public class PlayerDto {

    private Long id;

    private String name;

    private String title;

    private String race;

    private String profession;

    private Integer experience;

    private Integer level;

    private Integer untilNextLevel;

    private Date birthday;

    private Boolean banned;

    public PlayerDto(Player player) {
        this.id = player.getId();
        this.name = player.getName();
        this.title = player.getTitle();
        this.race = player.getRace().name();
    }
}
