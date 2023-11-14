package com.tadd.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tadd.ecommerce.model.Producto;
import com.tadd.ecommerce.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService {

	@Autowired
	private ProductoRepository productoRepository;
	
	@Override
	public Producto save(Producto producto) {		
		return productoRepository.save(producto);
	}

	@Override
	public Optional<Producto> get(Integer id) {
		return productoRepository.findById(id);
	}

	@Override
	public void update(Producto producto) {
		productoRepository.save(producto);	
	}

	@Override
	public void delete(Integer id) {
		productoRepository.deleteById(id);
		
	}

	@Override
	public List<Producto> findAll() {
		return productoRepository.findAll();
	}
	
}