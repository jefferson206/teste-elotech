package com.elotech.avaliacao.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome não pode ser vazio")
    private String nome;

    @CPF(message = "CPF informado é inválido")
    @NotBlank(message = "O CPF não pode ser vazio")
    private String cpf;

    @NotNull(message = "A data de nascimento não pode ser vazio")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "data_nascimento")
    @Past(message = "Data não pode ser maior que a data de hoje")
    private Date dataDeNascimento;

    @OneToMany(fetch = FetchType.EAGER)
    @NotNull(message = "É necessário selecionar pelo menos um Contato")
    @JoinTable(
            name = "pessoa_contato",
            joinColumns = @JoinColumn(name = "pessoa_id"),
            inverseJoinColumns = @JoinColumn(name = "contato_id")
    )
    private Set<Contato> contato = new HashSet<>();
}
