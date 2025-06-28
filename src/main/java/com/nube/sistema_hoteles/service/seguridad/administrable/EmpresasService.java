package com.nube.sistema_hoteles.service.seguridad.administrable;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.nube.sistema_hoteles.entity.seguridad.Empresas;

public interface EmpresasService {

    List<Empresas> buscarTodos();
    
    Empresas guardar(MultipartFile logo, String nombre, String encargado, String ruc, String direccion);

    Empresas guardar(Empresas empresa);

    Optional<Empresas> buscarId(Integer id);

    Empresas modificar(Empresas empresa);
    void modificar(Integer id, MultipartFile logo, String nombre, String encargado, String ruc, String direccion);

    void eliminar(Integer id);
}
