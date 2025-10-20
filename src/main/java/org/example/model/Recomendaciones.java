package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Recomendaciones")
public class Recomendaciones {

    @Id
    private Long id;

    public Recomendaciones(long id) {
        this.id = id;
    }

    // Dejo un constructor vacio para hibernate
    public Recomendaciones (){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
