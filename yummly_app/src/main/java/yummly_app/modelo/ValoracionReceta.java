package yummly_app.modelo;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Valoracion_Receta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValoracionReceta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_valoracion")
    private Long idValoracion;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_receta")
    private Receta receta;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(nullable = false)
    private int puntaje;  // De 1 a 5

    @Lob
    private String comentario;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoValoracion estado = EstadoValoracion.pendiente;

    @Column(name = "fecha_valoracion", nullable = false)
    private LocalDateTime fechaValoracion = LocalDateTime.now();

    public enum EstadoValoracion {
        pendiente,
        aprobado,
        rechazado;
    }
}
