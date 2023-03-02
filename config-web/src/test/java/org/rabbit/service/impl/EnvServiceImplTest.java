package org.rabbit.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.rabbit.mapper.EnvMapper;

import static org.mockito.Mockito.*;

class EnvServiceImplTest {
    @Mock
    EnvMapper envMapper;
    @InjectMocks
    EnvServiceImpl envServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        when(envMapper.exists(any(), any(), any(), any())).thenReturn(true);

        Assertions.assertThrowsExactly(IllegalArgumentException.class, ()->envServiceImpl.save(1L, "envName"));
    }
}
