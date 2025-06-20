package yummly_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompletarRegistroDTO {
    private String nombre;
    private String apellido;
    private String contrasena;
}

