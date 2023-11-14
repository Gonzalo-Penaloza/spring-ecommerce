package com.tadd.ecommerce.controller;

import java.util.Optional;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public String show(ModelMap model) {
		model.addAttribute("productos", productoService.findAll());
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
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Integer id, ModelMap model) {
		Producto producto = new Producto();
		Optional<Producto> optionalProduct = productoService.get(id);
		
		producto = optionalProduct.get();
		
		log.info("Producto buscado: {}", producto);
		
		model.addAttribute("producto", producto);
		
		return "productos/edit";
	}
	
	@PostMapping("/update")
	public String update(Producto producto) {
		 productoService.save(producto);
		 
		 return "redirect:/productos";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {
		productoService.delete(id);
		
		return "redirect:/productos";
	}
}
