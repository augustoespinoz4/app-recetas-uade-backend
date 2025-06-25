package yummly_app.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Lista_Recetas_Intentar_Detalle")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ListaRecetasIntentarDetalleId.class)
public class ListaRecetasIntentarDetalle {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_lista")
    @JsonIgnore
    private ListaRecetasIntentar lista;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_receta")
    private Receta receta;
}
