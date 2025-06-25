package yummly_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnviarValoracionRecetaDTO {
    private Long idUsuario;
    private int puntaje;        // de 1 a 5
    private String comentario;
}

