package yummly_app.mapper;

import yummly_app.dto.auth.LoginResponseDTO;
import yummly_app.modelo.Usuario;

public class UsuarioMapper {

    public static LoginResponseDTO toLoginResponseDTO(Usuario usuario) {
        return new LoginResponseDTO(
            usuario.getIdUsuario(),
            usuario.getAlias(),
            usuario.getEmail(),
            usuario.getTipoUsuario(),
            usuario.getEstadoRegistro(),
            usuario.getNombre(),
            usuario.getApellido()
        );
    }
}
