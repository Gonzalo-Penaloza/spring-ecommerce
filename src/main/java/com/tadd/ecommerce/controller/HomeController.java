package com.tadd.ecommerce.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tadd.ecommerce.model.Producto;
import com.tadd.ecommerce.service.ProductoService;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	private ProductoService productoService;

	@GetMapping("")
	public String home(ModelMap model) {

		List<Producto> productos = productoService.findAll();

		model.addAttribute("productos", productos);

		return "usuario/home";
	}
	
	@GetMapping("/producto/{id}")
	public String productoHome(@PathVariable Integer id, ModelMap model) {
		Producto p = new Producto();
		Optional<Producto> resp = productoService.get(id);
		
		if(resp.isPresent()) {
			p = resp.get();			
			model.addAttribute("producto", p);
		}
		
		return "usuario/productohome";
	}
	
	@PostMapping("/cart")
	public String addCart() {
		return "usuario/carrito";
	}

}
