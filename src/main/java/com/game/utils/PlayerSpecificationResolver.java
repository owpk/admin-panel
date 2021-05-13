package com.game.utils;

import com.game.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

public class PlayerSpecificationResolver {
    private static final Logger log = LoggerFactory.getLogger(PlayerSpecificationResolver.class);

    private final Map<String, String> specs;
    private Specification<Player> defaultSpec;

    public PlayerSpecificationResolver(Map<String, String> specs) {
        this.specs = specs;
        defaultSpec = Specification.where(null);
    }

    public Specification<Player> resolve() {
        appendIfContains("name", (r, cq, cb) ->
                cb.like(r.get("name"), getFormattedPattern(specs.get("name"))));

        appendIfContains("title", (r, cq, cb) ->
                cb.like(r.get("title"), getFormattedPattern(specs.get("title"))));

        appendIfContains( "race",(r, cq, cb) ->
                cb.like(r.get("race"), getFormattedPattern(specs.get("race"))));

        appendIfContains("profession", (r, cq, cb) ->
                cb.like(r.get("profession"), getFormattedPattern(specs.get("profession"))));

        appendIfContains("after", (r, cq, cb) ->
                cb.greaterThan(r.get("birthday"), specs.get("after")));

        appendIfContains("before", (r, cq, cb) ->
                cb.lessThan(r.get("birthday"), specs.get("before")));

        appendIfContains("banned", (r, cq, cb) ->
                cb.equal(r.get("banned"), specs.get("banned")));

        appendIfContains("minExperience", (r, cq, cb) ->
                cb.greaterThan(r.get("experience"), specs.get("minExperience")));

        appendIfContains("maxExperience", (r, cq, cb) ->
                cb.lessThan(r.get("experience"), specs.get("maxExperience")));

        appendIfContains("minLevel", (r, cq, cb) ->
                cb.greaterThan(r.get("level"), specs.get("minLevel")));

        appendIfContains("maxLevel", (r, cq, cb) ->
                cb.lessThan(r.get("level"), specs.get("maxLevel")));

        return defaultSpec;
    }

    private String getFormattedPattern(String value) {
        return String.format("%%%s%%", value);
    }

    // TODO set lang to java 11 and switch "specs.get(key)..." to "specs.get(key).isBlank();"
    private void appendIfContains(String key, Specification<Player> specification) {
        if (specs.containsKey(key)) {
            if (specs.get(key) != null && !specs.get(key).trim().isEmpty())
                defaultSpec = defaultSpec.and(specification);
            else log.warn("empty specification with key: {}", key);
        }
    }
}

