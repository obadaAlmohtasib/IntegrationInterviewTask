package com.digitinary.accounts.entity;

import com.digitinary.accounts.constants.AccountStatus;
import com.digitinary.accounts.constants.AccountType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "accounts")
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    @Column(name = "account_no", nullable = false, unique = true)
    @Size(min = 10, max = 10)
    private String accountNumber;

    @Column(nullable = false)
    private Double balance;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType type;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;
    
    @Version
    private Long version;
	
}
