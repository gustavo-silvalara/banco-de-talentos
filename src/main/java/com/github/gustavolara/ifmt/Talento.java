package com.github.gustavolara.ifmt;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@NamedQueries({
        @NamedQuery(name = "Talento.findAll", query = "SELECT t FROM Talento t ORDER BY t.ultimaAtualizacao"),
        @NamedQuery(name = "Talento.findByHabilidade",
                query = "SELECT t FROM Talento t WHERE t.habilidades LIKE :habilidade ORDER BY t.ultimaAtualizacao")
})
@Data
public class Talento {

    @Id
    @GeneratedValue
    private Integer id;

    @NotEmpty(message = "O nome não pode estar em vazio!")
    private String nome;

    @NotEmpty(message = "O email não pode estar em vazio!")
    private String email;

    @NotEmpty(message = "A área de atuação não pode estar vazia!")
    private String areaDeAtuacao;

    @NotEmpty(message = "As habilidades não podem estar vazias!")
    private String habilidades;

    @NotEmpty(message = "A apresentação não pode estar vazia")
    private String apresentacao;

    private LocalDateTime ultimaAtualizacao;

    @PrePersist
    @PreUpdate
    private void setUltimaAtualizacao() {
        ultimaAtualizacao = LocalDateTime.now();
    }
}
