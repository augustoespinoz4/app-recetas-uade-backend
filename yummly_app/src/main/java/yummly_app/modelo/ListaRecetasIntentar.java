package yummly_app.modelo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Lista_Recetas_Intentar")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaRecetasIntentar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLista;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_usuario")
    @JsonIgnore
    private Usuario usuario;

    @Column(length = 100)
    private String nombreLista;

    @ManyToMany
    @JoinTable(
        name = "Lista_Recetas_Intentar_Detalle",
        joinColumns = @JoinColumn(name = "id_lista"),
        inverseJoinColumns = @JoinColumn(name = "id_receta")
    )
    private List<Receta> recetas = new ArrayList<>();
}
