package yummly_app.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "Categoria_Receta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaReceta {
    @Id
    @Column(length = 50)
    private String nombre;
}

