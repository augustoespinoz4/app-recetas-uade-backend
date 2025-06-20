package yummly_app.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import yummly_app.modelo.CategoriaReceta;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecetaRespuestaDTO {
    private Long idReceta;
    private String titulo;
    private String descripcion;
    private int cantidadPersonas;
    private boolean publico;
    private CategoriaReceta categoria;
    private LocalDateTime fechaCreacion;

    private UsuarioBasicoDTO usuario;
    private List<IngredienteRecetaDTO> ingredientes;
}

