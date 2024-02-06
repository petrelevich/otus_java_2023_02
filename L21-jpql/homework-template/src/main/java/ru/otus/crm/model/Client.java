package ru.otus.crm.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @SequenceGenerator(name = "client_gen", sequenceName = "client_seq",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Phone> phones;


    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public Client(Long id, String name, Address address, List <Phone> phones) {
        this.id=id;
        this.name = name;
        this.address = address;
        this.phones = phones;
        for (Phone phone : phones){
            phone.setClient(this);
        }
    }

    public Client(String name, Address address, List <Phone> phones) {
        this.name = name;
        this.address = address;
        this.phones = phones;
        for (Phone phone : phones){
            phone.setClient(this);
        }
    }

    @Override
    public Client clone() {
         Client clientClone = new Client(this.id, this.name, this.address, new ArrayList<>(this.phones));
        for (Phone phone : clientClone.getPhones()){
            phone.setClient(clientClone);
        }
        return clientClone;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", phones=" + phones +
                '}';
    }
}
