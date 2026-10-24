package com.inventario.app.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimientos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Movimiento {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Producto producto;
    private String tipo; // entrada/salida
    private Integer cantidad;
    private LocalDateTime fecha;
    @ManyToOne
    private Usuario usuario;
    private String motivo;
}