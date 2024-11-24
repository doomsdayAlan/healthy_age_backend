package com.healthyage.healthyage.util;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthyage.healthyage.domain.entity.Medicacion;
import com.healthyage.healthyage.domain.entity.Notificacion;
import com.healthyage.healthyage.service.MedicacionService;
import com.healthyage.healthyage.service.NotificacionService;
import com.healthyage.healthyage.service.TratamientoService;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParseoSeguroUtil {
    public static Medicacion safeObtenerMedicacion(MedicacionService medicacionService, String idMedicacion) {
        try {
            return medicacionService.obtenerMedicacion(idMedicacion);
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }

    public static Stream<String> safeParseMedicaciones(ObjectMapper objectMapper, String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<String>>() {
            }).stream();
        } catch (JsonProcessingException e) {
            return Stream.empty();
        }
    }

    public static Notificacion safeObtenerNotificacionPorMedicacion(NotificacionService notificacionService, String idMedicacion) {
        try {
            return notificacionService.obtenerNotificacionPorMedicacion(idMedicacion);
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }

    public static Stream<Notificacion> safeObtenerNotificacionesPorParametro(NotificacionService notificacionService, String parametro, String valor) {
        try {
            return notificacionService.obtenerNotificacionesPorParametro(parametro, valor).stream();
        } catch (InterruptedException | ExecutionException e) {
            return Stream.empty();
        }
    }

    public static void safeBorrarTratamiento(TratamientoService tratamientoService, String idTratamiento) {
        try {
            tratamientoService.borrarTratamiento(idTratamiento);
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    public static void safeBorrarMedicacion(MedicacionService medicacionService, String idMedicacion) {
        try {
            medicacionService.borrarMedicacion(idMedicacion);
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    public static void safeBorrarNotificacion(NotificacionService notificacionService, String idNotificacion) {
        try {
            notificacionService.borrarNotificacion(idNotificacion);
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getLocalizedMessage());
        }
    }
}
