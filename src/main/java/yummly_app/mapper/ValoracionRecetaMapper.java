package yummly_app.mapper;

import yummly_app.dto.UsuarioBasicoDTO;
import yummly_app.dto.ValoracionRecetaDTO;
import yummly_app.modelo.ValoracionReceta;

public class ValoracionRecetaMapper {

    public static ValoracionRecetaDTO toDTO(ValoracionReceta valoracion) {
        return new ValoracionRecetaDTO(
            new UsuarioBasicoDTO(
                valoracion.getUsuario().getIdUsuario(),
                valoracion.getUsuario().getAlias()
            ),
            valoracion.getPuntaje(),
            valoracion.getComentario(),
            valoracion.getEstado(),
            valoracion.getFechaValoracion()
        );
    }
}
