package yummly_app.modelo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Recuperacion_Clave")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecuperacionClave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRecuperacion;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_usuario")
    @JsonIgnore
    private Usuario usuario;

    @Column(length = 6, nullable = false, name = "codigo_6_digitos")
    private String codigo6Digitos;

    @Column(nullable = false)
    private LocalDateTime fechaGeneracion = LocalDateTime.now();

    @Column(length = 10, nullable = false)
    private String estado;
}