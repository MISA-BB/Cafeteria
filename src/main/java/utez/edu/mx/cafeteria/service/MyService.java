package utez.edu.mx.cafeteria.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.cafeteria.controller.dto.*;
import utez.edu.mx.cafeteria.exception.CustomException;
import utez.edu.mx.cafeteria.model.*;
import utez.edu.mx.cafeteria.repository.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MyService {

    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;
    private final PlatilloRepository platilloRepository;
    private final MesaRepository mesaRepository;
    private final ReservacionRepository reservacionRepository;
    private final OcupacionRepository ocupacionRepository;
    private final OrdenRepository ordenRepository;

    public MyService(UsuarioRepository usuarioRepository,
                     CategoriaRepository categoriaRepository,
                     PlatilloRepository platilloRepository,
                     MesaRepository mesaRepository,
                     ReservacionRepository reservacionRepository,
                     OcupacionRepository ocupacionRepository,
                     OrdenRepository ordenRepository) {
        this.usuarioRepository = usuarioRepository;
        this.categoriaRepository = categoriaRepository;
        this.platilloRepository = platilloRepository;
        this.mesaRepository = mesaRepository;
        this.reservacionRepository = reservacionRepository;
        this.ocupacionRepository = ocupacionRepository;
        this.ordenRepository = ordenRepository;
    }

    public Usuario validarPermiso(Long usuarioId, Rol rolRequerido) {
        if (usuarioId == null) {
            throw new CustomException("Se requiere el ID del usuario operador (X-Usuario-Id)");
        }
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new CustomException("El usuario operador no existe"));

        if (!Boolean.TRUE.equals(usuario.getActivo())) {
            throw new CustomException("El usuario está desactivado y no puede operar el sistema");
        }
        if (usuario.getRol() == null) {
            throw new CustomException("El usuario no tiene un rol asignado");
        }
        if (rolRequerido != null && usuario.getRol() != rolRequerido) {
            throw new CustomException("Acceso denegado: esta acción requiere rol " + rolRequerido);
        }
        return usuario;
    }

    public Usuario registrarUsuario(UsuarioDTO dto) {
        if (usuarioRepository.existsByCorreo(dto.getCorreo())) {
            throw new CustomException("Ya existe un usuario registrado con ese correo");
        }
        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new CustomException("La contraseña es obligatoria al registrar un usuario");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setPassword(dto.getPassword());
        usuario.setRol(dto.getRol());
        usuario.setActivo(true);
        return usuarioRepository.save(usuario);
    }

    public Usuario modificarUsuario(Long id, UsuarioDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new CustomException("Usuario no encontrado"));

        if (!usuario.getCorreo().equalsIgnoreCase(dto.getCorreo())
                && usuarioRepository.existsByCorreo(dto.getCorreo())) {
            throw new CustomException("El correo ya está en uso por otro usuario");
        }

        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setRol(dto.getRol());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            usuario.setPassword(dto.getPassword());
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario cambiarEstadoUsuario(Long id, boolean activo) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new CustomException("Usuario no encontrado"));
        usuario.setActivo(activo);
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Categoria registrarCategoria(CategoriaDTO dto) {
        if (categoriaRepository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new CustomException("Ya existe una categoría con ese nombre");
        }
        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        return categoriaRepository.save(categoria);
    }

    public Categoria modificarCategoria(Long id, CategoriaDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CustomException("Categoría no encontrada"));
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        return categoriaRepository.save(categoria);
    }

    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    public Platillo registrarPlatillo(PlatilloDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new CustomException("La categoría especificada no existe"));

        Platillo platillo = new Platillo();
        platillo.setNombre(dto.getNombre());
        platillo.setPrecio(dto.getPrecio());
        platillo.setDisponible(dto.getDisponible() != null ? dto.getDisponible() : true);
        platillo.setCategoria(categoria);
        return platilloRepository.save(platillo);
    }

    public Platillo modificarPlatillo(Long id, PlatilloDTO dto) {
        Platillo platillo = platilloRepository.findById(id)
                .orElseThrow(() -> new CustomException("Platillo no encontrado"));
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new CustomException("La categoría especificada no existe"));

        platillo.setNombre(dto.getNombre());
        platillo.setPrecio(dto.getPrecio());
        if (dto.getDisponible() != null) {
            platillo.setDisponible(dto.getDisponible());
        }
        platillo.setCategoria(categoria);
        return platilloRepository.save(platillo);
    }

    public Platillo cambiarDisponibilidadPlatillo(Long id, boolean disponible) {
        Platillo platillo = platilloRepository.findById(id)
                .orElseThrow(() -> new CustomException("Platillo no encontrado"));
        platillo.setDisponible(disponible);
        return platilloRepository.save(platillo);
    }

    public List<Platillo> listarPlatillos() {
        return platilloRepository.findAll();
    }

    public Mesa registrarMesa(MesaDTO dto) {
        if (mesaRepository.existsByIdentificadorIgnoreCase(dto.getIdentificador())) {
            throw new CustomException("Ya existe una mesa con ese identificador");
        }
        Mesa mesa = new Mesa();
        mesa.setIdentificador(dto.getIdentificador());
        mesa.setMaxComensales(dto.getMaxComensales());
        mesa.setEstado(EstadoMesa.LIBRE);
        return mesaRepository.save(mesa);
    }

    public Mesa modificarMesa(Long id, MesaDTO dto) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new CustomException("Mesa no encontrada"));
        mesa.setIdentificador(dto.getIdentificador());
        mesa.setMaxComensales(dto.getMaxComensales());
        return mesaRepository.save(mesa);
    }

    public List<Mesa> listarMesas() {
        return mesaRepository.findAll();
    }

    @Transactional
    public Ocupacion asignarMesaAlMomento(AsignarMesaDTO dto) {
        Mesa mesa = mesaRepository.findById(dto.getMesaId())
                .orElseThrow(() -> new CustomException("La mesa no existe"));

        if (mesa.getEstado() == EstadoMesa.OCUPADA) {
            throw new CustomException("La mesa ya se encuentra ocupada");
        }
        if (dto.getNumComensales() > mesa.getMaxComensales()) {
            throw new CustomException("El número de comensales (" + dto.getNumComensales()
                    + ") excede el máximo de la mesa (" + mesa.getMaxComensales() + ")");
        }

        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime limiteUnaHora = ahora.plusHours(1);
        List<Reservacion> reservasCercanas = reservacionRepository
                .findByMesaIdAndIniciadaFalseAndCanceladaFalseAndFechaHoraBetween(
                        mesa.getId(), ahora.minusMinutes(15), limiteUnaHora
                );

        if (!reservasCercanas.isEmpty()) {
            throw new CustomException("No se puede asignar la mesa: tiene una reservación programada a menos de 1 hora ("
                    + reservasCercanas.get(0).getFechaHora() + ")");
        }

        mesa.setEstado(EstadoMesa.OCUPADA);
        mesaRepository.save(mesa);

        Ocupacion ocupacion = new Ocupacion();
        ocupacion.setTipo(TipoOcupacion.ASIGNACION);
        ocupacion.setNumComensales(dto.getNumComensales());
        ocupacion.setHoraInicio(ahora);
        ocupacion.setActiva(true);
        ocupacion.setMesa(mesa);

        return ocupacionRepository.save(ocupacion);
    }

    @Transactional
    public Reservacion registrarReservacion(RegistrarReservacionDTO dto) {
        Mesa mesa = mesaRepository.findById(dto.getMesaId())
                .orElseThrow(() -> new CustomException("La mesa no existe"));

        if (dto.getNumComensales() > mesa.getMaxComensales()) {
            throw new CustomException("El número de comensales (" + dto.getNumComensales()
                    + ") excede el máximo de la mesa (" + mesa.getMaxComensales() + ")");
        }
        if (dto.getFechaHora().isBefore(LocalDateTime.now())) {
            throw new CustomException("La fecha y hora de la reservación deben ser futuras");
        }

        List<Reservacion> conflictos = reservacionRepository
                .findByMesaIdAndIniciadaFalseAndCanceladaFalseAndFechaHoraBetween(
                        mesa.getId(),
                        dto.getFechaHora().minusHours(1),
                        dto.getFechaHora().plusHours(1)
                );
        if (!conflictos.isEmpty()) {
            throw new CustomException("Ya existe una reservación cercana para esa mesa en ese horario");
        }

        Reservacion reservacion = new Reservacion();
        reservacion.setMesa(mesa);
        reservacion.setFechaHora(dto.getFechaHora());
        reservacion.setNumComensales(dto.getNumComensales());
        reservacion.setNombreCliente(dto.getNombreCliente());
        reservacion.setTelefonoContacto(dto.getTelefonoContacto());
        reservacion.setIniciada(false);
        reservacion.setCancelada(false);

        if (mesa.getEstado() == EstadoMesa.LIBRE) {
            mesa.setEstado(EstadoMesa.RESERVADA);
            mesaRepository.save(mesa);
        }

        return reservacionRepository.save(reservacion);
    }

    @Transactional
    public Ocupacion iniciarReservacion(Long reservacionId) {
        Reservacion reservacion = reservacionRepository.findById(reservacionId)
                .orElseThrow(() -> new CustomException("La reservación no existe"));

        if (Boolean.TRUE.equals(reservacion.getCancelada())) {
            throw new CustomException("La reservación fue cancelada");
        }
        if (Boolean.TRUE.equals(reservacion.getIniciada())) {
            throw new CustomException("La reservación ya fue iniciada");
        }

        Mesa mesa = reservacion.getMesa();
        if (mesa.getEstado() == EstadoMesa.OCUPADA) {
            throw new CustomException("La mesa aún está ocupada por otros comensales");
        }

        reservacion.setIniciada(true);
        reservacionRepository.save(reservacion);

        mesa.setEstado(EstadoMesa.OCUPADA);
        mesaRepository.save(mesa);

        Ocupacion ocupacion = new Ocupacion();
        ocupacion.setTipo(TipoOcupacion.RESERVACION);
        ocupacion.setNumComensales(reservacion.getNumComensales());
        ocupacion.setHoraInicio(LocalDateTime.now());
        ocupacion.setActiva(true);
        ocupacion.setMesa(mesa);
        ocupacion.setReservacion(reservacion);

        return ocupacionRepository.save(ocupacion);
    }

    public List<Reservacion> listarReservacionesPendientes() {
        return reservacionRepository.findByIniciadaFalseAndCanceladaFalseOrderByFechaHoraAsc();
    }

    @Transactional
    public Orden tomarOrden(CrearOrdenDTO dto) {
        Mesa mesa = mesaRepository.findById(dto.getMesaId())
                .orElseThrow(() -> new CustomException("La mesa no existe"));

        if (mesa.getEstado() != EstadoMesa.OCUPADA) {
            throw new CustomException("Solo se puede tomar orden sobre una mesa ocupada");
        }

        Ocupacion ocupacion = ocupacionRepository.findByMesaIdAndActivaTrue(mesa.getId())
                .orElseThrow(() -> new CustomException("No hay una ocupación activa para esta mesa"));

        Orden orden = new Orden();
        orden.setFechaHora(LocalDateTime.now());
        orden.setEstado(EstadoOrden.RECIBIDA);
        orden.setOcupacion(ocupacion);

        List<DetalleOrden> detalles = new ArrayList<>();
        for (ItemOrdenDTO item : dto.getItems()) {
            Platillo platillo = platilloRepository.findById(item.getPlatilloId())
                    .orElseThrow(() -> new CustomException("El platillo con ID " + item.getPlatilloId() + " no existe"));

            if (!Boolean.TRUE.equals(platillo.getDisponible())) {
                throw new CustomException("El platillo '" + platillo.getNombre() + "' está agotado y no puede agregarse");
            }

            DetalleOrden detalle = new DetalleOrden();
            detalle.setPlatillo(platillo);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(platillo.getPrecio()); // Congela el precio actual del catálogo
            detalle.setNotas(item.getNotas());
            detalle.setOrden(orden);
            detalles.add(detalle);
        }

        orden.setDetalles(detalles);
        return ordenRepository.save(orden);
    }

    public SeguimientoMesaDTO consultarSeguimientoMesa(Long mesaId) {
        Mesa mesa = mesaRepository.findById(mesaId)
                .orElseThrow(() -> new CustomException("La mesa no existe"));

        SeguimientoMesaDTO seguimiento = new SeguimientoMesaDTO();
        seguimiento.setMesaId(mesa.getId());
        seguimiento.setIdentificador(mesa.getIdentificador());
        seguimiento.setMaxComensales(mesa.getMaxComensales());
        seguimiento.setEstado(mesa.getEstado());

        ocupacionRepository.findByMesaIdAndActivaTrue(mesa.getId()).ifPresent(ocupacion -> {
            seguimiento.setOcupacionId(ocupacion.getId());
            seguimiento.setTipoOcupacion(ocupacion.getTipo());
            seguimiento.setNumComensales(ocupacion.getNumComensales());
            seguimiento.setHoraInicio(ocupacion.getHoraInicio());
            seguimiento.setOrdenes(ordenRepository.findByOcupacionId(ocupacion.getId()));
        });

        List<Reservacion> pendientes = reservacionRepository
                .findByMesaIdAndIniciadaFalseAndCanceladaFalseAndFechaHoraBetween(
                        mesa.getId(),
                        LocalDateTime.now().minusHours(1),
                        LocalDateTime.now().plusDays(7)
                );
        seguimiento.setReservacionesPendientes(pendientes);

        return seguimiento;
    }

    @Transactional
    public TicketDTO terminarMesaYGenerarTicket(Long mesaId, CerrarMesaDTO dto) {
        Mesa mesa = mesaRepository.findById(mesaId)
                .orElseThrow(() -> new CustomException("La mesa no existe"));

        if (mesa.getEstado() != EstadoMesa.OCUPADA) {
            throw new CustomException("La mesa no está ocupada actualmente");
        }

        Ocupacion ocupacion = ocupacionRepository.findByMesaIdAndActivaTrue(mesa.getId())
                .orElseThrow(() -> new CustomException("No se encontró ocupación activa para cerrar"));

        List<Orden> ordenes = ordenRepository.findByOcupacionId(ocupacion.getId());

        List<ItemTicketDTO> itemsTicket = new ArrayList<>();
        double total = 0.0;

        for (Orden orden : ordenes) {
            for (DetalleOrden det : orden.getDetalles()) {
                double subtotal = det.getCantidad() * det.getPrecioUnitario();
                total += subtotal;

                itemsTicket.add(new ItemTicketDTO(
                        det.getPlatillo().getNombre(),
                        det.getCantidad(),
                        det.getPrecioUnitario(),
                        subtotal,
                        det.getNotas()
                ));
            }
        }

        LocalDateTime horaCierre = LocalDateTime.now();
        ocupacion.setHoraFin(horaCierre);
        ocupacion.setActiva(false);
        ocupacion.setTotalPagado(total);
        ocupacion.setFormaPago(dto != null && dto.getFormaPago() != null ? dto.getFormaPago() : "No especificada");
        ocupacionRepository.save(ocupacion);

        List<Reservacion> reservasFuturas = reservacionRepository
                .findByMesaIdAndIniciadaFalseAndCanceladaFalseAndFechaHoraBetween(
                        mesa.getId(), horaCierre, horaCierre.plusDays(7)
                );
        mesa.setEstado(reservasFuturas.isEmpty() ? EstadoMesa.LIBRE : EstadoMesa.RESERVADA);
        mesaRepository.save(mesa);

        TicketDTO ticket = new TicketDTO();
        ticket.setFolioOcupacion(ocupacion.getId());
        ticket.setMesa(mesa.getIdentificador());
        ticket.setNumComensales(ocupacion.getNumComensales());
        ticket.setHoraInicio(ocupacion.getHoraInicio());
        ticket.setHoraCierre(horaCierre);
        ticket.setConsumo(itemsTicket);
        ticket.setTotal(total);
        ticket.setFormaPago(ocupacion.getFormaPago());

        return ticket;
    }

    public List<Orden> listarOrdenesPendientesCocina() {
        return ordenRepository.findByOcupacionActivaTrueAndEstadoNotOrderByFechaHoraAsc(EstadoOrden.ENTREGADA);
    }

    @Transactional
    public Orden actualizarEstadoOrden(Long ordenId, EstadoOrden nuevoEstado) {
        Orden orden = ordenRepository.findById(ordenId)
                .orElseThrow(() -> new CustomException("La orden no existe"));

        if (!Boolean.TRUE.equals(orden.getOcupacion().getActiva())) {
            throw new CustomException("No se puede actualizar una orden de una mesa ya cerrada");
        }

        EstadoOrden actual = orden.getEstado();
        boolean transicionValida =
                (actual == EstadoOrden.RECIBIDA && nuevoEstado == EstadoOrden.PREPARANDO) ||
                        (actual == EstadoOrden.PREPARANDO && nuevoEstado == EstadoOrden.LISTA) ||
                        (actual == EstadoOrden.LISTA && nuevoEstado == EstadoOrden.ENTREGADA);

        if (!transicionValida) {
            throw new CustomException("Transición de estado inválida: no se puede pasar de "
                    + actual + " a " + nuevoEstado);
        }

        orden.setEstado(nuevoEstado);
        return ordenRepository.save(orden);
    }
}