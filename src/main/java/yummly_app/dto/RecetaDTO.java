package yummly_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import yummly_app.modelo.CategoriaReceta;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecetaDTO {
    private Long idUsuario; // solo requerido al crear
    private String titulo;
    private String descripcion;
    private int cantidadPersonas;
    private boolean publico;
    private CategoriaReceta categoria;
}
