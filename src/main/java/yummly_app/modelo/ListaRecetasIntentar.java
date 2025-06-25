package yummly_app.modelo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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

    @OneToMany(mappedBy = "lista", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ListaRecetasIntentarDetalle> detalles = new ArrayList<>();

}
