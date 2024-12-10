package com.digitinary.customers.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "customers")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    @Column(nullable = false)
    @Size(min = 3, max = 100)
    private String name;
	
    @Column(name = "legal_id", nullable = false, unique = true)
    @Size(min = 7, max = 7)
    private String legalId;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "type_id", nullable = false)
	private CustomerType type;

	@Column(nullable = false)
	private String address;
	
}
