package com.nazjara.service;

import com.nazjara.command.UnitOfMeasureCommand;
import com.nazjara.converter.UnitOfMeasureToUnitOfMeasureCommand;
import com.nazjara.repository.UnitOfMeasureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    @Autowired
    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository,
                                    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }


    @Override
    public Set<UnitOfMeasureCommand> findAll() {
        return StreamSupport.stream(unitOfMeasureRepository.findAll().toIterable().spliterator(), false)
        .map(unitOfMeasureToUnitOfMeasureCommand::convert)
        .collect(Collectors.toSet());
    }
}