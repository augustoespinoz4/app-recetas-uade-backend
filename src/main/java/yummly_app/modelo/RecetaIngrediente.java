package yummly_app.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Receta_Ingrediente")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(RecetaIngredienteId.class)
public class RecetaIngrediente {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_receta")
    @JsonIgnore
    private Receta receta;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_ingrediente")
    private Ingrediente ingrediente;

    @Column
    private double cantidad;

    @Column(length = 50)
    private String unidadMedida;
}
