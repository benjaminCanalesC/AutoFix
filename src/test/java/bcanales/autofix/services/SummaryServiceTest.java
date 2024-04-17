package bcanales.autofix.services;

import bcanales.autofix.dtos.VehicleBrandRepairAverageDto;
import bcanales.autofix.entities.*;
import bcanales.autofix.repositories.RepairRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class SummaryServiceTest {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private SummaryService summaryService;

    @Autowired
    private RepairRepository repairRepository;

}
