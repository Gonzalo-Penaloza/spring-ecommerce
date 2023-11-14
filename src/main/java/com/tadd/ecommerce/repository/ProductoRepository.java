package com.tadd.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tadd.ecommerce.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer>{

}
