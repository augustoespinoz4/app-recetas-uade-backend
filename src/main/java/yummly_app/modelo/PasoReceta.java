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
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Paso_Receta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasoReceta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paso")
    private Long idPaso;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_receta")
    @JsonIgnore
    private Receta receta;

    @Column(name = "numero_paso", nullable = false)
    private int numeroPaso;

    @Lob
    @Column(nullable = false)
    private String descripcion;
    
    @OneToMany(mappedBy = "paso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MultimediaPaso> multimedia = new ArrayList<>();

}
