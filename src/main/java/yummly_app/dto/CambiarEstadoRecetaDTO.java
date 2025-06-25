package yummly_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import yummly_app.modelo.Receta;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CambiarEstadoRecetaDTO {
    private Receta.EstadoReceta nuevoEstado;
}
