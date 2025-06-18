package yummly_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredienteRecetaDTO {
    private String nombre;
    private double cantidad;
    private String unidadMedida;
}

