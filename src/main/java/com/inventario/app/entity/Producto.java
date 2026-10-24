package com.inventario.app.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "productos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Producto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String codigo;
    private String nombre;
    private String descripcion;
    private String categoria;
    private Integer stock;
    private Integer stockMinimo;
    private Double precio;
    private String ubicacion;
}