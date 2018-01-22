package com.kubepay.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.kubepay.entity.base.Auditable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper=true)
@NoArgsConstructor
@Entity
@Table(name = "tbl_user")
@EntityListeners(AuditingEntityListener.class)
public class User extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = -2591785309420078663L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "First name is required")
    private String firstName;

    @Column(nullable = false)
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Column(nullable = false)
    @NotBlank(message = "Email is required")
    private String email;

    public User(final String firstName, final String lastName, final String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

}
