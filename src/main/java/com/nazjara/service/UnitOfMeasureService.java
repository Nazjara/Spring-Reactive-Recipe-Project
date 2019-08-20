package com.nazjara.service;

import com.nazjara.command.UnitOfMeasureCommand;
import reactor.core.publisher.Flux;

import java.util.Set;

public interface UnitOfMeasureService {
    Flux<UnitOfMeasureCommand> findAll();
}
