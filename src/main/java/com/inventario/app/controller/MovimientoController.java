package com.inventario.app.controller;

import com.inventario.app.entity.Movimiento;
import com.inventario.app.entity.Producto;
import com.inventario.app.repository.MovimientoRepository;
import com.inventario.app.repository.ProductoRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {
    private final MovimientoRepository movRepo;
    private final ProductoRepository prodRepo;

    public MovimientoController(MovimientoRepository movRepo, ProductoRepository prodRepo) {
        this.movRepo = movRepo; this.prodRepo = prodRepo;
    }

    @GetMapping
    public List<Movimiento> list() { return movRepo.findAll(); }

    @PostMapping
    @Transactional
    public Movimiento create(@RequestBody Movimiento m) {
        // adjust stock
        Producto p = prodRepo.findById(m.getProducto().getId()).orElseThrow();
        if ("SALIDA".equalsIgnoreCase(m.getTipo())) {
            if (p.getStock() < m.getCantidad()) throw new RuntimeException("Stock insuficiente");
            p.setStock(p.getStock() - m.getCantidad());
        } else {
            p.setStock(p.getStock() + m.getCantidad());
        }
        prodRepo.save(p);
        return movRepo.save(m);
    }
}