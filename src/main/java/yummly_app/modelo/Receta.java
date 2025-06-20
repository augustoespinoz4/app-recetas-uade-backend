package yummly_app.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Receta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Receta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReceta;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoReceta estado = EstadoReceta.pendiente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_usuario")
    @JsonIgnore
    private Usuario usuario;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Lob
    private String descripcion;

    @Column(nullable = false)
    private int cantidadPersonas = 1;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(nullable = false)
    private boolean publico = true;

    @ManyToOne
    @JoinColumn(name = "categoria", referencedColumnName = "nombre")
    private CategoriaReceta categoria;

    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecetaIngrediente> ingredientes = new ArrayList<>();

    @ManyToMany(mappedBy = "recetas")
    @JsonIgnore
    private List<ListaRecetasIntentar> listas = new ArrayList<>();
    
    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("numeroPaso ASC")
    private List<PasoReceta> pasos = new ArrayList<>();
    
    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MultimediaReceta> multimedia = new ArrayList<>();
    
    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ValoracionReceta> valoraciones = new ArrayList<>();
    
    public enum EstadoReceta {
        pendiente,
        aprobada,
        rechazada;
    }
}

