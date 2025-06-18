package yummly_app.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Multimedia_Receta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultimediaReceta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMultimedia;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_receta")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Receta receta;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private TipoMultimedia tipo;

    @Column(nullable = false, length = 500)
    private String url;

    public enum TipoMultimedia {
        foto,
        video
    }
}
