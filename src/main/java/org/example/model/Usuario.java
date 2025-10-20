package org.example.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.example.controller.JuegoController;
import org.example.model.Juego;
import org.example.service.JuegoService;
import java.util.List;

@Entity
@Table(name = "Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Column(nullable = false)
    private String contraseña;

    @Column(nullable = true)
    private String nome;

    @Column(nullable = true)
    private Integer idade;

    @Column(nullable = true)
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Compras> compras;

    @Column(nullable = true)
    private List<String> generosGustados;

    // constructor vacio para hibernate:
    public Usuario() {
    }

    public Usuario(String nome, int idade, String email, String contraseña) {
        this.nome = nome;
        this.contraseña = contraseña;
        this.idade = idade;
        this.email = email;
    }


    // Getters e Setters



    public static Long getId() {
        return getId();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Compras> getCompras() {
        return compras;
    }
    public void setCompras(List<Compras> compras) {
        this.compras = compras;
    }
    public List<String> getGenerosGustados() {
        return generosGustados;
    }

    public void setGenerosGustados(List<String> generosGustados) {
        this.generosGustados = generosGustados;
    }
}
