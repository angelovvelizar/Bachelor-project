package com.unwe.thesis.skylimit.model.entity;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "modified_18118014")
    @UpdateTimestamp
    private LocalDateTime modified18118014;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getModified18118014() {
        return modified18118014;
    }

    public void setModified18118014(LocalDateTime modified18118014) {
        this.modified18118014 = modified18118014;
    }
}
