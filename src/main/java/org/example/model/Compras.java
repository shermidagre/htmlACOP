package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "Compras") // Se recomienda minúsculas y plural
public class Compras {

    @Id // Marca el campo como Clave Primaria
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id; // Recomendable usar Long para IDs de JPA



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false) // Columna en la tabla 'compras' que guarda el ID del usuario
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "juego_id", nullable = false) // Columna en la tabla 'compras' que guarda el ID del juego
    private Juego juego;


    public Compras (){
    }

    public Compras(Usuario usuario, Juego juego) {
        this.usuario = usuario;
        this.juego = juego;
    }

    @JsonProperty("id")
    public Long getId() { // Cambiado a Long
        return id;
    }

    public void setId(Long id) { // Cambiado a Long
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Juego getJuego() {
        return juego;
    }

    public void setJuego(Juego juego) {
        this.juego = juego;
    }
}