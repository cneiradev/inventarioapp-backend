package com.inventario.app.controller;

import com.inventario.app.entity.Producto;
import com.inventario.app.repository.ProductoRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {
    private final ProductoRepository repo;
    public ProductoController(ProductoRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Producto> list() { return repo.findAll(); }

    @PostMapping
    public Producto create(@RequestBody Producto p) { return repo.save(p); }
}