package com.conexa.saude.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

import java.util.Objects;
import java.util.UUID;
@Entity
public class Medico {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String email;
    private String senha;
    @Transient
    private String confirmacaoSenha;
    private String especialidade;
    private String cpf;
    private String dataNascimento;
    private String telefone;

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfirmacaoSenha() {
        return confirmacaoSenha;
    }

    public void setConfirmacaoSenha(String confirmacaoSenha) {
        this.confirmacaoSenha = confirmacaoSenha;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medico medico = (Medico) o;
        return Objects.equals(id, medico.id) && Objects.equals(email, medico.email) && Objects.equals(senha, medico.senha) && Objects.equals(confirmacaoSenha, medico.confirmacaoSenha) && Objects.equals(especialidade, medico.especialidade) && Objects.equals(cpf, medico.cpf) && Objects.equals(dataNascimento, medico.dataNascimento) && Objects.equals(telefone, medico.telefone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, senha, confirmacaoSenha, especialidade, cpf, dataNascimento, telefone);
    }

    @Override
    public String toString() {
        return "Medico{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", confirmacaoSenha='" + confirmacaoSenha + '\'' +
                ", especialidade='" + especialidade + '\'' +
                ", cpf='" + cpf + '\'' +
                ", dataNascimento='" + dataNascimento + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }


}
