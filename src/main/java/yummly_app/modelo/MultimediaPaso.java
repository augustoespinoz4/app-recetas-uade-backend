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
import lombok.NoArgsConstructor;
import yummly_app.modelo.MultimediaReceta.TipoMultimedia;

@Entity
@Table(name = "Multimedia_Paso")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultimediaPaso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMultimedia;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_paso")
    @JsonIgnore
    private PasoReceta paso;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private TipoMultimedia tipo;

    @Column(nullable = false, length = 500)
    private String url;
}
