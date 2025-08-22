package com.sphuta_tms.controller;

import com.sphuta_tms.dto.InvoicingSettingsDTO;
import com.sphuta_tms.exception.ResourceNotFoundException;
import com.sphuta_tms.service.InvoicingSettingsService;
import com.sphuta_tms.util.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvoicingSettingsControllerTest {

    @Mock
    private InvoicingSettingsService service;

    @InjectMocks
    private InvoicingSettingsController controller;

    private InvoicingSettingsDTO sampleDto;

    @BeforeEach
    void setUp() {
        sampleDto = new InvoicingSettingsDTO(
                "123e4567-e89b-12d3-a456-426614174000",
                "USD",
                "TAX-123",
                new BigDecimal("0.08"),
                "INV-${yyyy}${seq:5}",
                14,
                new BigDecimal("0.05"),
                "tmpl_default",
                "logo-123"
        );
    }

    @Test
    void testGetAllSettings() {
        when(service.getAllSettings()).thenReturn(List.of(sampleDto));

        ResponseEntity<ApiResponse<List<InvoicingSettingsDTO>>> response = controller.getAllSettings();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getData().size());
        verify(service, times(1)).getAllSettings();
    }

    @Test
    void testGetSettingsByUserId_Found() {
        when(service.getSettingsByUserId(sampleDto.userId())).thenReturn(Optional.of(sampleDto));

        ResponseEntity<ApiResponse<InvoicingSettingsDTO>> response = controller.getSettingsByUserId(sampleDto.userId());

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(sampleDto.userId(), response.getBody().getData().userId());
        verify(service, times(1)).getSettingsByUserId(sampleDto.userId());
    }

    @Test
    void testGetSettingsByUserId_NotFound() {
        when(service.getSettingsByUserId("unknown")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                controller.getSettingsByUserId("unknown"));

        verify(service, times(1)).getSettingsByUserId("unknown");
    }

    @Test
    void testCreateSettings() {
        when(service.createSettings(sampleDto)).thenReturn(sampleDto);

        ResponseEntity<ApiResponse<InvoicingSettingsDTO>> response = controller.createSettings(sampleDto);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(sampleDto.userId(), response.getBody().getData().userId());
        verify(service, times(1)).createSettings(sampleDto);
    }

    @Test
    void testUpdateSettings() {
        when(service.updateSettings(sampleDto.userId(), sampleDto)).thenReturn(sampleDto);

        ResponseEntity<ApiResponse<InvoicingSettingsDTO>> response = controller.updateSettings(sampleDto.userId(), sampleDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(sampleDto.userId(), response.getBody().getData().userId());
        verify(service, times(1)).updateSettings(sampleDto.userId(), sampleDto);
    }

    @Test
    void testPatchSettings() {
        when(service.patchSettings(sampleDto.userId(), sampleDto)).thenReturn(sampleDto);

        ResponseEntity<ApiResponse<InvoicingSettingsDTO>> response = controller.patchSettings(sampleDto.userId(), sampleDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(sampleDto.userId(), response.getBody().getData().userId());
        verify(service, times(1)).patchSettings(sampleDto.userId(), sampleDto);
    }

    @Test
    void testDeleteSettings() {
        doNothing().when(service).deleteSettings(sampleDto.userId());

        ResponseEntity<ApiResponse<Void>> response = controller.deleteSettings(sampleDto.userId());

        assertEquals(200, response.getStatusCodeValue());
        verify(service, times(1)).deleteSettings(sampleDto.userId());
    }
}
