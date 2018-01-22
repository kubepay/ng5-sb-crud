package com.kubepay.entity.base;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class Auditable<U> {

    @CreatedBy
    @Column(nullable = false, updatable = false)
    private U createdBy;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    //@Temporal(TIMESTAMP)
    private ZonedDateTime created;

    @LastModifiedBy
    @Column(nullable = false)
    private U modifiedBy;

    @LastModifiedDate
    @Column(nullable = false)
    //@Temporal(TIMESTAMP)
    private ZonedDateTime modified;

//    @PrePersist
//    public void prePersist(final U target) {
//        log.info("Persisting: " + target);
//    }
//
//    @PreUpdate
//    public void preUpdate(final U target) {
//        log.info("Updating: " + target);
//    }
//
//    @PreRemove
//    public void preRemove(final U target) {
//        log.info("Deleteing: " + target);
//    }

}
