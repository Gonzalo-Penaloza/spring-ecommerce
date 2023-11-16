package com.tadd.ecommerce.controller;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tadd.ecommerce.model.Producto;
import com.tadd.ecommerce.model.Usuario;
import com.tadd.ecommerce.service.ProductoService;
import com.tadd.ecommerce.service.UploadFileService;

@Controller
@RequestMapping("/productos")
public class ProductoController {

	private final Logger log = LoggerFactory.getLogger(ProductoController.class);
	@Autowired
	private ProductoService productoService;
	@Autowired
	private UploadFileService upload;

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
	public String save(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {
		log.info("Este es el objeto producto {}", producto);
		Usuario usuario = new Usuario(1, "", "", "", "", "", "", "");
		producto.setUsuario(usuario);

		// Imagenes
		if (producto.getId() == null) {
			String nombreImagen = upload.saveImage(file);
			producto.setImagen(nombreImagen);
		}

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
	public String update(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {
		Producto p = new Producto();
		p = productoService.get(producto.getId()).get();

		if (file.isEmpty()) { // editamos el producto pero no cambiamos la imagem
			producto.setImagen(p.getImagen());
		} else {// cuando se edita tbn la imagen
				// eliminar cuando no sea la imagen por defecto
			if (!p.getImagen().equals("default.jpg")) {
				upload.deleteImage(p.getImagen());
			}
			String nombreImagen = upload.saveImage(file);
			producto.setImagen(nombreImagen);
		}
		
		producto.setUsuario(p.getUsuario());
		productoService.update(producto);
		
		return "redirect:/productos";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {
		Producto p = productoService.get(id).get();

		String nombreImagen = p.getImagen();

		if (!nombreImagen.equals("default.jpg")) {
			upload.deleteImage(nombreImagen);
		}

		productoService.delete(id);

		return "redirect:/productos";
	}
}
