package com.kroger.merchandising.magicdatareader.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "store_price_events")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StorePriceFailedEvent implements Serializable {

    @Id
    @Column(name = "event_id", nullable = false)
    private UUID eventId;

    @NotNull
    @Column(name = "event_message", nullable = false)
    @Basic
    private byte[] eventMessage;

    @Column(name = "is_event_published")
    private boolean eventPublished;

    @Column(name = "division_id")
    @Size(min = 3, max = 3)
    private String divisionId;

    @Column(name = "created_timestamp")
    private LocalDateTime createdTimeStamp;

    @Column(name = "updated_timestamp")
    private LocalDateTime updatedTimeStamp;

}
