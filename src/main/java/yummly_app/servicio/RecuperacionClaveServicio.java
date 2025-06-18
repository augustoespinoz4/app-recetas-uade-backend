package yummly_app.servicio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yummly_app.dao.RecuperacionClaveDAO;
import yummly_app.modelo.RecuperacionClave;
import yummly_app.modelo.Usuario;

@Service
public class RecuperacionClaveServicio {

    @Autowired
    private RecuperacionClaveDAO recuperacionClaveDAO;

    @Autowired
    private EmailServicio emailServicio;

    private static final int DURACION_CODIGO_MINUTOS = 10;

    // Genera código, guarda entidad y envía mail
    public void iniciarRecuperacionClave(Usuario usuario) {
        String codigo = generarCodigo6Digitos();

        RecuperacionClave rc = new RecuperacionClave();
        rc.setUsuario(usuario);
        rc.setCodigo6Digitos(codigo);
        rc.setFechaGeneracion(LocalDateTime.now());
        rc.setEstado("pendiente");

        recuperacionClaveDAO.guardar(rc);

        emailServicio.enviarCodigoRecuperacion(usuario.getEmail(), codigo);
    }

    // Genera un código aleatorio de 6 dígitos
    private String generarCodigo6Digitos() {
        Random random = new Random();
        int codigo = 100000 + random.nextInt(900000);
        return String.valueOf(codigo);
    }

    // Valida el código: que exista, sea "pendiente" y no esté expirado
    public boolean validarCodigo(Usuario usuario, String codigoIngresado) {
        List<RecuperacionClave> codigosUsuario = recuperacionClaveDAO.buscarPorIdUsuario(usuario.getIdUsuario());

        for (RecuperacionClave rc : codigosUsuario) {
            if (rc.getCodigo6Digitos().equals(codigoIngresado) && rc.getEstado().equals("pendiente")) {
                if (!estaExpirado(rc)) {
                    return true;
                } else {
                    // Marcar como expirado si ya pasó el tiempo
                    rc.setEstado("expirado");
                    recuperacionClaveDAO.guardar(rc);
                    return false;
                }
            }
        }
        return false;
    }

    // Verifica si el código ya expiró según la fecha de generación
    private boolean estaExpirado(RecuperacionClave rc) {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime fechaLimite = rc.getFechaGeneracion().plusMinutes(DURACION_CODIGO_MINUTOS);
        return ahora.isAfter(fechaLimite);
    }

    // Marca el código como usado (cuando el usuario cambia la contraseña correctamente)
    public void marcarCodigoComoUsado(Usuario usuario, String codigo) {
        List<RecuperacionClave> codigosUsuario = recuperacionClaveDAO.buscarPorIdUsuario(usuario.getIdUsuario());

        for (RecuperacionClave rc : codigosUsuario) {
            if (rc.getCodigo6Digitos().equals(codigo) && rc.getEstado().equals("pendiente")) {
                rc.setEstado("usado");
                recuperacionClaveDAO.guardar(rc);
                break;
            }
        }
    }
}