package com.alexander.sistema_cerro_verde_backend.compras.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alexander.sistema_cerro_verde_backend.compras.entity.CategoriasProductos;
import com.alexander.sistema_cerro_verde_backend.compras.service.ICategoriasProductosService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class CategoriasProductosController {
    @Autowired
    private ICategoriasProductosService serviceCategoriasProductos;
    @GetMapping("/categoriasproductos")
    public List<CategoriasProductos> buscarTodos(){
        return serviceCategoriasProductos.buscarTodos(); //findAll
    }
    @PostMapping("/categoriasproductos")
    public CategoriasProductos guardar (@RequestBody CategoriasProductos categoriaproducto){
        serviceCategoriasProductos.guardar(categoriaproducto);
        return categoriaproducto;
    }
    @PutMapping("/categoriasproductos")
    public CategoriasProductos modificar(@RequestBody CategoriasProductos categoriaproducto) {
        serviceCategoriasProductos.modificar(categoriaproducto);
        return categoriaproducto;
    }
    @GetMapping("/categoriasproductos/{id}")
    public Optional<CategoriasProductos> buscarId(@PathVariable("id") Integer id_categoria) {
        return serviceCategoriasProductos.buscarId(id_categoria);
    }
    @DeleteMapping("/categoriasproductos/{id}")
    public String eliminar(@PathVariable("id") Integer id_categoria){
        serviceCategoriasProductos.eliminar(id_categoria);
        return "Proveedor eliminado";
    }
}