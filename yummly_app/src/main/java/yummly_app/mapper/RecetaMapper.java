package yummly_app.mapper;

import java.util.stream.Collectors;

import yummly_app.dto.IngredienteRecetaDTO;
import yummly_app.dto.RecetaRespuestaDTO;
import yummly_app.dto.UsuarioBasicoDTO;
import yummly_app.modelo.Receta;

public class RecetaMapper {
    public static RecetaRespuestaDTO toDTO(Receta receta) {
        // Aquí el mismo código que tenías en mapToDTO
        return new RecetaRespuestaDTO(
            receta.getIdReceta(),
            receta.getTitulo(),
            receta.getDescripcion(),
            receta.getCantidadPersonas(),
            receta.isPublico(),
            receta.getCategoria(),
            receta.getFechaCreacion(),
            new UsuarioBasicoDTO(
                receta.getUsuario().getIdUsuario(),
                receta.getUsuario().getAlias(),
                receta.getUsuario().getNombre(),
                receta.getUsuario().getApellido()
            ),
            receta.getIngredientes().stream()
                .map(ri -> new IngredienteRecetaDTO(
                    ri.getIngrediente().getNombre(),
                    ri.getCantidad(),
                    ri.getUnidadMedida()
                ))
                .collect(Collectors.toList())
        );
    }
}

