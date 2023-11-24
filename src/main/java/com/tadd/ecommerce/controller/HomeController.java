package com.tadd.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tadd.ecommerce.model.DetalleOrden;
import com.tadd.ecommerce.model.Orden;
import com.tadd.ecommerce.model.Producto;
import com.tadd.ecommerce.service.ProductoService;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	private ProductoService productoService;
	
	List<DetalleOrden> detalles = new ArrayList<>();
	Orden orden = new Orden();

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
	public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, ModelMap model) {
		DetalleOrden detalleOrden = new DetalleOrden();
		Producto producto = new Producto();
		double sumaTotal;
		
		Optional<Producto> optionalProducto = productoService.get(id);
		
		if(optionalProducto.isPresent()) producto = optionalProducto.get();

		detalleOrden.setCantidad(cantidad);
		detalleOrden.setNombre(producto.getNombre());
		detalleOrden.setPrecio(producto.getPrecio());
		detalleOrden.setTotal(producto.getPrecio() * cantidad);
		detalleOrden.setProducto(producto);
		
		detalles.add(detalleOrden);
		
		sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();
		
		orden.setTotal(sumaTotal);
		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);
		
		return "usuario/carrito";
	}

}
