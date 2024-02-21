package ru.otus.crm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "address")
@Setter
public class Address {
    @Id
    @UuidGenerator
    private UUID id;
    @Column(name = "street", nullable = false)
    private String street;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private Client client;

    public Address(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                //", client=" + client.getName() +
                '}';
    }

    public Address(UUID id, String street) {
        this.id = id;
        this.street = street;
    }
}
