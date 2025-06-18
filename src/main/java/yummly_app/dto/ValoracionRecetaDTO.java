package yummly_app.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import yummly_app.modelo.ValoracionReceta;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValoracionRecetaDTO {
    private UsuarioBasicoDTO usuario;
    private int puntaje;
    private String comentario;
    private ValoracionReceta.EstadoValoracion estado;
    private LocalDateTime fechaValoracion;
}
