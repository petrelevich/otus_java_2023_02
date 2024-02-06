package ru.otus.crm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "phones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone")
    private String phone;


    @ManyToOne()
    @JoinColumn(name = "client_id")
    private Client client;
    public Phone(String phone) {
        this.phone = phone;
    }
    public Phone(Long id, String phone) {
        this.id=id;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", client=" + client.getName() +
                '}';
    }
}
