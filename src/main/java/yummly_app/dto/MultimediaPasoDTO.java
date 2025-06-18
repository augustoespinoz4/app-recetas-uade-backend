package yummly_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import yummly_app.modelo.MultimediaReceta.TipoMultimedia;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultimediaPasoDTO {
    private String url;
    private TipoMultimedia tipo; //
}
