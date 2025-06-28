package com.nube.sistema_hoteles.service.compras.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nube.sistema_hoteles.entity.compras.CategoriasProductos;
import com.nube.sistema_hoteles.repository.compras.CategoriasProductosRepository;
import com.nube.sistema_hoteles.service.compras.ICategoriasProductosService;

@Service
public class CategoriasProductosService implements ICategoriasProductosService{

    @Autowired
    private CategoriasProductosRepository repoCategoriasProductos;

    @Override
    public List<CategoriasProductos> buscarTodos(){ //Buscar todas las categorias
        return repoCategoriasProductos.findAll();
    }

    @Override
    public void guardar (CategoriasProductos categoriaproducto){ //Guardar categoria
        repoCategoriasProductos.save(categoriaproducto);
    }

    @Override
    public void modificar(CategoriasProductos categoriaproducto){ //Modificar la categoria
        repoCategoriasProductos.save(categoriaproducto);
    }

    @Override
    public Optional<CategoriasProductos> buscarId(Integer id_categoria){ //Buscar por id la categoria
        return repoCategoriasProductos.findById(id_categoria);
    }

    @Override
    public void eliminar(Integer id_categoria){ //Eliminar la categoria
        repoCategoriasProductos.deleteById(id_categoria);
    }
}