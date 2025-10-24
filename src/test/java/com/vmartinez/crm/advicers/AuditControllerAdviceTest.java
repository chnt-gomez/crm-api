package com.vmartinez.crm.advicers;

import com.vmartinez.crm.entity.Audit;
import com.vmartinez.crm.repository.AuditRepository;
import com.vmartinez.crm.util.DummyController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DummyController.class)
public class AuditControllerAdviceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuditRepository auditRepository;

    @Test
    void shouldSaveALlEntriesToAuditTable() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test")).andExpect(status().isOk());

        verify(auditRepository, atLeastOnce()).save(any(Audit.class));

    }

    @Test
    void shouldNotSaveNotExistingCalls() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/nonexistent")).andExpect(status().isNotFound());

        verify(auditRepository, never()).save(any(Audit.class));
    }
}
