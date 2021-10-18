package ru.otus.architect.socialnetwork.utils;

import lombok.Getter;

public enum EntityType {
    PERSON("P");

    @Getter
    private final String seqType;

    EntityType(String seqType) {
        this.seqType = seqType;
    }
}
