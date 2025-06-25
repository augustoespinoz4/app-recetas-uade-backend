package yummly_app.mapper;

import java.util.stream.Collectors;

import yummly_app.dto.IngredienteRecetaDTO;
import yummly_app.dto.MultimediaPasoDTO;
import yummly_app.dto.MultimediaRecetaDTO;
import yummly_app.dto.PasoRecetaDTO;
import yummly_app.dto.RecetaRespuestaDTO;
import yummly_app.dto.UsuarioBasicoDTO;
import yummly_app.modelo.Receta;

public class RecetaMapper {
    public static RecetaRespuestaDTO toDTO(Receta receta) {
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
                receta.getUsuario().getAlias()
            ),
            receta.getIngredientes().stream()
                .map(ri -> new IngredienteRecetaDTO(
                    ri.getIngrediente().getNombre(),
                    ri.getCantidad(),
                    ri.getUnidadMedida()
                ))
                .collect(Collectors.toList()),

            // PASOS con MULTIMEDIA
            receta.getPasos().stream()
                .map(p -> new PasoRecetaDTO(
                    p.getNumeroPaso(),
                    p.getDescripcion(),
                    p.getMultimedia().stream()
                        .map(m -> new MultimediaPasoDTO(
                            m.getUrl(),
                            m.getTipo()
                        ))
                        .collect(Collectors.toList())
                ))
                .collect(Collectors.toList()),

            // MULTIMEDIA GENERAL DE LA RECETA
            receta.getMultimedia().stream()
                .map(m -> new MultimediaRecetaDTO(
                    m.getUrl(),
                    m.getTipo()
                ))
                .collect(Collectors.toList())
        );
    }
}


