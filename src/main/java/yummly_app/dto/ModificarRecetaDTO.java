package yummly_app.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModificarRecetaDTO {
    private String titulo;
    private String descripcion;
    private int cantidadPersonas;
    private boolean publico;
    private String categoria;

    private List<IngredienteDTO> ingredientes;
    private List<PasoRecetaDTO> pasos;
    private List<MultimediaRecetaDTO> multimediaReceta; // multimedia de receta
}
