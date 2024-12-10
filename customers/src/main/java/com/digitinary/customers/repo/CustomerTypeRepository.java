package com.digitinary.customers.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digitinary.customers.entity.CustomerType;

@Repository
public interface CustomerTypeRepository extends JpaRepository<CustomerType, Long> {

}
