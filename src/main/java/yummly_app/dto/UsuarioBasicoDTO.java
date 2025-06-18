package yummly_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioBasicoDTO {
    private Long idUsuario;
    private String alias;
    private String nombre;
    private String apellido;
}
