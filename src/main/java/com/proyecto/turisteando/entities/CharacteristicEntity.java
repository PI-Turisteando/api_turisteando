
package com.proyecto.turisteando.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "characteristic")
public class CharacteristicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "El nombre de la caracterìstica no puede estar vacía")
    private String name;


    @Column(nullable = false) // it has no effect, but it is good to have it for clarity
    private Byte status;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private ImageEntity image;

    @ManyToMany(mappedBy = "characteristic")
    @JsonBackReference
    private List<TouristPlanEntity> touristPlan;


    @PrePersist
    protected void onCreate() {
        this.status = 1;
    }
}

