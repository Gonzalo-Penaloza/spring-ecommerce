package com.tadd.ecommerce.controller;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tadd.ecommerce.model.Producto;
import com.tadd.ecommerce.model.Usuario;
import com.tadd.ecommerce.service.ProductoService;


@Controller
@RequestMapping("/productos")
public class ProductoController {
	
	private final Logger log = LoggerFactory.getLogger(ProductoController.class);
	
	@Autowired
	private ProductoService productoService;
	
	@GetMapping("")
	public String show() {
		return "productos/show";
	}
	
	@GetMapping("/create")
	public String create() {
		return "productos/create";
	}
	
	@PostMapping("/save")
	public String save(Producto producto) {
		log.info("Este es el objeto producto {}",producto);
	
		Usuario usuario = new Usuario(1,"","","","","","","");
		
		producto.setUsuario(usuario);
		
		productoService.save(producto);
		
		return "redirect:/productos";
	}
}
