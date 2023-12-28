package com.conexa.saude.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "paciente_id") // Nome da coluna no banco de dados
    private Paciente paciente;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agendamento that = (Agendamento) o;
        return Objects.equals(id, that.id) && Objects.equals(dataHora, that.dataHora) && Objects.equals(paciente, that.paciente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dataHora, paciente);
    }

    @Override
    public String toString() {
        return "Agendamento{" +
                "id=" + id +
                ", dataHora=" + dataHora +
                ", paciente=" + paciente +
                '}';
    }
}
