package yummly_app.modelo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecetaIngredienteId implements Serializable {
    private Long receta;
    private Long ingrediente;
}

