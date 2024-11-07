package com.proyecto.turisteando.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tourist_plan")
public class TouristPlanEntity {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, unique = true, nullable = false)
    @Size(min = 3, max = 100, message = "El título debe tener entre 3 y 100 caracteres")
    private String title;

    @Column(length = 500, nullable = false)
    private String description;

    private Double price;

    // Campos  para el plan turístico con relaciones de entidades pendientes
    private String seller;

    @ManyToOne()
    @JoinColumn(name = "city_id", nullable = false)
    private CityEntity city;

    @ManyToOne()
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;
  
    @OneToMany(mappedBy = "touristPlan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("touristPlan")
    @Size(min = 1, max = 5, message = "Debe haber entre 1 y 5 imágenes")
    private List<ImageEntity> images;

    private LocalDate availabilityStartDate;
    private LocalDate availabilityEndDate;
    private Integer capacity;
    private String duration; // Duración del plan turístico (por ejemplo, "3 días" o "1 noche")
    private boolean foodIncluded;
    private boolean wifiIncluded;
    private boolean petsFriendly;
    private boolean disabilityAccess;
    private boolean isActive;

    @CreationTimestamp()
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp()
    @Column(insertable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate(){
        this.isActive = true;
    }

}
