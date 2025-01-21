package org.electronic_home_manager.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

/**
 * Abstract base entity class that provides a common identifier for all entities.
 * This class is designed to be extended by other entity classes.
 * It includes an auto-generated primary key.
 */
@MappedSuperclass
public abstract class BaseEntity {

    /**
     * The unique identifier for the entity.
     * This field is automatically generated using the {@link GenerationType#IDENTITY} strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Retrieves the unique identifier of the entity.
     *
     * @return The unique identifier (ID).
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the entity.
     *
     * @param id The unique identifier (ID).
     */
    public void setId(Long id) {
        this.id = id;
    }
}
