package yummly_app.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private Long idUsuario;
    private String alias;
    private String email;
    private String tipoUsuario;
    private String estadoRegistro;
    private String nombre;
    private String apellido;
}