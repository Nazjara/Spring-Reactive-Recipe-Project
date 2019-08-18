package com.nazjara.repository;

import com.nazjara.model.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureRepositoryIT {

    public static final String EACH = "Each";

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Before
    public void setUp() throws Exception {
        unitOfMeasureRepository.deleteAll().block();
    }

    @Test
    public void testSaveUom() throws Exception {
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setDescription(EACH);

        unitOfMeasureRepository.save(uom).block();

        Long count = unitOfMeasureRepository.count().block();

        assertEquals(Long.valueOf(1L), count);

    }

    @Test
    public void testFindByDescription() throws Exception {
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setDescription(EACH);

        unitOfMeasureRepository.save(uom).block();

        UnitOfMeasure fetchedUOM = unitOfMeasureRepository.findByDescription(EACH).block();

        assertEquals(EACH, fetchedUOM.getDescription());

    }
}