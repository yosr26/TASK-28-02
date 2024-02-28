package com.yosrabroug.banking.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class ObjectValidatorException extends RuntimeException{
    @Getter
    private final Set<String> violations;

    @Getter
    private final String violationSource;

}
