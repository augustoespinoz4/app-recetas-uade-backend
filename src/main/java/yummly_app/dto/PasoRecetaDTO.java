package yummly_app.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasoRecetaDTO {
    private int numeroPaso;
    private String descripcion;
    private List<MultimediaPasoDTO> multimedia;
}
