package yummly_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import yummly_app.modelo.ValoracionReceta;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CambiarEstadoValoracionDTO {
    private ValoracionReceta.EstadoValoracion nuevoEstado;
}
