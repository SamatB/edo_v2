package org.example.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum FilePoolType {
    MAIN("MAIN"),
    FACSIMILE("FACSIMILE");
    private final String FilePoolType;
}
