package com.elotech.avaliacao.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Builder
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "contato")
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome não pode ser vazio")
    private String nome;

    @NotBlank(message = "Telefone não pode ser vazio")
    private String telefone;

    @NotBlank(message = "E-mail não pode ser vazio")
    @Email(message = "Email inválido")
    private String email;

}
