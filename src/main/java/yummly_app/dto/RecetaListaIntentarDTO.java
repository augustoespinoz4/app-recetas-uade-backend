package yummly_app.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecetaListaIntentarDTO {
    private Long idUsuario;
    private Long idReceta;
}

