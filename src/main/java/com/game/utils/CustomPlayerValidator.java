package com.game.utils;

import com.game.entity.Player;
import com.game.exception.ValidationException;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Calendar;
import java.util.Date;

public class CustomPlayerValidator {
    public static final int TITLE_LENGTH = 30;
    public static final int NAME_LENGTH = 12;
    public static final int EXP_MAX_VAL = 10_000_000;
    public static final int YEAR_MIN = 2000;
    public static final int YEAR_MAX = 3000;

    public static void validate(Player player) throws ValidationException {
        if (player == null) throw new ValidationException("Invalid player data is null");
        if (player.getBanned() == null)
            player.setBanned(false);
        validateName(player.getName());
        validateTitle(player.getTitle());
        validateExp(player.getExperience());
        validateBirthday(player.getBirthday());
    }

    public static void validateName(String name) {
        if (validateStringLength(name, NAME_LENGTH))
            throw new ValidationException("Invalid player name: " + name);
    }

    public static void validateTitle(String title) {
        if (validateStringLength(title, TITLE_LENGTH))
            throw new ValidationException("Invalid player title: " + title);
    }

    public static void validateExp(Integer exp) {
        if (exp != null && (exp < 0 || exp > EXP_MAX_VAL))
            throw new ValidationException("Invalid experience value: " + exp);
    }

    public static void validateBirthday(Date bd) {
        if (bd != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(bd);
            int year = cal.get(Calendar.YEAR);
            if (year < YEAR_MIN || year > YEAR_MAX)
                throw new ValidationException("Invalid birthday value: " + bd);
        }
    }

    private static boolean validateStringLength(String field, int size) {
        return field != null && (field.isEmpty() || field.length() > size);
    }

    public static void evalAndSetPlayerLevel(Player player) {
        if (player != null) {
            Integer exp = player.getExperience();
            if (exp != null) {
                int lvl = (int) ((Math.sqrt(2500 + 200 * exp) - 50) / 100);
                int expToNextLvl = 50 * (lvl + 1) * (lvl + 2) - exp;
                player.setLevel(lvl);
                player.setUntilNextLevel(expToNextLvl);
            }
        }
    }
}
