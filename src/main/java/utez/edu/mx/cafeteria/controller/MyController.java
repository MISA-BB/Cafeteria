package utez.edu.mx.cafeteria.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.cafeteria.controller.dto.*;
import utez.edu.mx.cafeteria.model.*;
import utez.edu.mx.cafeteria.service.MyService;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class MyController {

    private final MyService service;

    public MyController(MyService service) {
        this.service = service;
    }

    @PostMapping("/usuarios/bootstrap")
    public ResponseEntity<Usuario> registrarPrimerAdministrador(@RequestBody @Valid UsuarioDTO payload) {
        payload.setRol(Rol.ADMINISTRADOR);
        return ResponseEntity.status(201).body(service.registrarUsuario(payload));
    }

    @PostMapping("/usuarios")
    public ResponseEntity<Usuario> registrarUsuario(
            @RequestHeader("X-Usuario-Id") Long operadorId,
            @RequestBody @Valid UsuarioDTO payload) {
        service.validarPermiso(operadorId, Rol.ADMINISTRADOR);
        return ResponseEntity.status(201).body(service.registrarUsuario(payload));
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> modificarUsuario(
            @RequestHeader("X-Usuario-Id") Long operadorId,
            @PathVariable Long id,
            @RequestBody @Valid UsuarioDTO payload) {
        service.validarPermiso(operadorId, Rol.ADMINISTRADOR);
        return ResponseEntity.ok(service.modificarUsuario(id, payload));
    }

    @PatchMapping("/usuarios/{id}/estado")
    public ResponseEntity<Usuario> cambiarEstadoUsuario(
            @RequestHeader("X-Usuario-Id") Long operadorId,
            @PathVariable Long id,
            @RequestParam boolean activo) {
        service.validarPermiso(operadorId, Rol.ADMINISTRADOR);
        return ResponseEntity.ok(service.cambiarEstadoUsuario(id, activo));
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> listarUsuarios(
            @RequestHeader("X-Usuario-Id") Long operadorId) {
        service.validarPermiso(operadorId, Rol.ADMINISTRADOR);
        return ResponseEntity.ok(service.listarUsuarios());
    }

    @PostMapping("/categorias")
    public ResponseEntity<Categoria> registrarCategoria(
            @RequestHeader("X-Usuario-Id") Long operadorId,
            @RequestBody @Valid CategoriaDTO payload) {
        service.validarPermiso(operadorId, Rol.ADMINISTRADOR);
        return ResponseEntity.status(201).body(service.registrarCategoria(payload));
    }

    @PutMapping("/categorias/{id}")
    public ResponseEntity<Categoria> modificarCategoria(
            @RequestHeader("X-Usuario-Id") Long operadorId,
            @PathVariable Long id,
            @RequestBody @Valid CategoriaDTO payload) {
        service.validarPermiso(operadorId, Rol.ADMINISTRADOR);
        return ResponseEntity.ok(service.modificarCategoria(id, payload));
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<Categoria>> listarCategorias(
            @RequestHeader("X-Usuario-Id") Long operadorId) {
        service.validarPermiso(operadorId, null);
        return ResponseEntity.ok(service.listarCategorias());
    }

    @PostMapping("/platillos")
    public ResponseEntity<Platillo> registrarPlatillo(
            @RequestHeader("X-Usuario-Id") Long operadorId,
            @RequestBody @Valid PlatilloDTO payload) {
        service.validarPermiso(operadorId, Rol.ADMINISTRADOR);
        return ResponseEntity.status(201).body(service.registrarPlatillo(payload));
    }

    @PutMapping("/platillos/{id}")
    public ResponseEntity<Platillo> modificarPlatillo(
            @RequestHeader("X-Usuario-Id") Long operadorId,
            @PathVariable Long id,
            @RequestBody @Valid PlatilloDTO payload) {
        service.validarPermiso(operadorId, Rol.ADMINISTRADOR);
        return ResponseEntity.ok(service.modificarPlatillo(id, payload));
    }

    @PatchMapping("/platillos/{id}/disponibilidad")
    public ResponseEntity<Platillo> cambiarDisponibilidadPlatillo(
            @RequestHeader("X-Usuario-Id") Long operadorId,
            @PathVariable Long id,
            @RequestParam boolean disponible) {
        service.validarPermiso(operadorId, Rol.ADMINISTRADOR);
        return ResponseEntity.ok(service.cambiarDisponibilidadPlatillo(id, disponible));
    }

    @GetMapping("/platillos")
    public ResponseEntity<List<Platillo>> listarPlatillos(
            @RequestHeader("X-Usuario-Id") Long operadorId) {
        service.validarPermiso(operadorId, null);
        return ResponseEntity.ok(service.listarPlatillos());
    }

    @PostMapping("/mesas")
    public ResponseEntity<Mesa> registrarMesa(
            @RequestHeader("X-Usuario-Id") Long operadorId,
            @RequestBody @Valid MesaDTO payload) {
        service.validarPermiso(operadorId, Rol.ADMINISTRADOR);
        return ResponseEntity.status(201).body(service.registrarMesa(payload));
    }

    @PutMapping("/mesas/{id}")
    public ResponseEntity<Mesa> modificarMesa(
            @RequestHeader("X-Usuario-Id") Long operadorId,
            @PathVariable Long id,
            @RequestBody @Valid MesaDTO payload) {
        service.validarPermiso(operadorId, Rol.ADMINISTRADOR);
        return ResponseEntity.ok(service.modificarMesa(id, payload));
    }

    @GetMapping("/mesas")
    public ResponseEntity<List<Mesa>> listarMesas(
            @RequestHeader("X-Usuario-Id") Long operadorId) {
        service.validarPermiso(operadorId, null);
        return ResponseEntity.ok(service.listarMesas());
    }


    @PostMapping("/mesas/asignar")
    public ResponseEntity<Ocupacion> asignarMesaAlMomento(
            @RequestHeader("X-Usuario-Id") Long operadorId,
            @RequestBody @Valid AsignarMesaDTO payload) {
        service.validarPermiso(operadorId, Rol.MESERO);
        return ResponseEntity.status(201).body(service.asignarMesaAlMomento(payload));
    }

    @PostMapping("/reservaciones")
    public ResponseEntity<Reservacion> registrarReservacion(
            @RequestHeader("X-Usuario-Id") Long operadorId,
            @RequestBody @Valid RegistrarReservacionDTO payload) {
        service.validarPermiso(operadorId, Rol.MESERO);
        return ResponseEntity.status(201).body(service.registrarReservacion(payload));
    }

    @PatchMapping("/reservaciones/{id}/iniciar")
    public ResponseEntity<Ocupacion> iniciarReservacion(
            @RequestHeader("X-Usuario-Id") Long operadorId,
            @PathVariable Long id) {
        service.validarPermiso(operadorId, Rol.MESERO);
        return ResponseEntity.ok(service.iniciarReservacion(id));
    }

    @GetMapping("/reservaciones")
    public ResponseEntity<List<Reservacion>> listarReservacionesPendientes(
            @RequestHeader("X-Usuario-Id") Long operadorId) {
        service.validarPermiso(operadorId, Rol.MESERO);
        return ResponseEntity.ok(service.listarReservacionesPendientes());
    }

    @PostMapping("/ordenes")
    public ResponseEntity<Orden> tomarOrden(
            @RequestHeader("X-Usuario-Id") Long operadorId,
            @RequestBody @Valid CrearOrdenDTO payload) {
        service.validarPermiso(operadorId, Rol.MESERO);
        return ResponseEntity.status(201).body(service.tomarOrden(payload));
    }

    @GetMapping("/mesas/{id}/seguimiento")
    public ResponseEntity<SeguimientoMesaDTO> consultarSeguimientoMesa(
            @RequestHeader("X-Usuario-Id") Long operadorId,
            @PathVariable Long id) {
        service.validarPermiso(operadorId, Rol.MESERO);
        return ResponseEntity.ok(service.consultarSeguimientoMesa(id));
    }

    @PostMapping("/mesas/{id}/cerrar")
    public ResponseEntity<TicketDTO> terminarMesaYGenerarTicket(
            @RequestHeader("X-Usuario-Id") Long operadorId,
            @PathVariable Long id,
            @RequestBody(required = false) CerrarMesaDTO payload) {
        service.validarPermiso(operadorId, Rol.MESERO);
        return ResponseEntity.ok(service.terminarMesaYGenerarTicket(id, payload));
    }


    @GetMapping("/cocina/ordenes")
    public ResponseEntity<List<Orden>> listarOrdenesPendientesCocina(
            @RequestHeader("X-Usuario-Id") Long operadorId) {
        service.validarPermiso(operadorId, Rol.COCINA);
        return ResponseEntity.ok(service.listarOrdenesPendientesCocina());
    }

    @PatchMapping("/cocina/ordenes/{id}/estado")
    public ResponseEntity<Orden> actualizarEstadoOrden(
            @RequestHeader("X-Usuario-Id") Long operadorId,
            @PathVariable Long id,
            @RequestBody @Valid ActualizarEstadoOrdenDTO payload) {
        service.validarPermiso(operadorId, Rol.COCINA);
        return ResponseEntity.ok(service.actualizarEstadoOrden(id, payload.getEstado()));
    }
}