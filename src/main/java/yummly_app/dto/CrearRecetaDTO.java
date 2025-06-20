package yummly_app.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrearRecetaDTO {
    private Long idUsuario;
    private String titulo;
    private String descripcion;
    private int cantidadPersonas;
    private boolean publico;
    private String categoria; // nombre de la categoría

    private List<IngredienteRecetaDTO> ingredientes;
    private List<PasoRecetaDTO> pasos;
    private List<MultimediaRecetaDTO> multimediaReceta;
}
