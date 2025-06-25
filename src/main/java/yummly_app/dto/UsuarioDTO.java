package yummly_app.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long idUsuario;
    private String alias;
    private String email;
    private String tipoUsuario;
    private String estadoRegistro;
    private LocalDateTime fechaRegistro;
    private String nombre;
    private String apellido;
}
