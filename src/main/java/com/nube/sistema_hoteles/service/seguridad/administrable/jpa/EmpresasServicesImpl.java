package com.nube.sistema_hoteles.service.seguridad.administrable.jpa;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.nube.sistema_hoteles.entity.seguridad.Empresas;
import com.nube.sistema_hoteles.repository.seguridad.administrable.EmpresasRepository;
import com.nube.sistema_hoteles.repository.seguridad.administrable.SucursalesRepository;
import com.nube.sistema_hoteles.service.seguridad.administrable.EmpresasService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EmpresasServicesImpl implements EmpresasService {
    @Autowired
    private EmpresasRepository repository;

    @Autowired
    private SucursalesRepository sucursalRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Empresas> buscarTodos() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Empresas guardar(Empresas empresa){
        return repository.save(empresa);
    }
    
    @Override
    @Transactional
    public Empresas guardar(MultipartFile logo, String nombre, String encargado, String ruc, String direccion) {
        try {
            String nombreArchivo = UUID.randomUUID() + "_" + logo.getOriginalFilename();
            Path ruta = Paths.get("C:/Users/HP/Desktop/GITHUB/LP/backend lp/src/main/resources/static/img"+ nombreArchivo); 
            /*Path ruta = Paths.get("/home/hotel/public_html/logos/" + nombreArchivo);*/

            Files.copy(logo.getInputStream(), ruta);
    
            Empresas empresa = new Empresas();
            empresa.setNombre(nombre);
            empresa.setRuc(ruc);
            empresa.setEncargado(encargado);
            empresa.setDireccion(direccion);
            empresa.setLogo(nombreArchivo);
    
            return repository.save(empresa);
    
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo", e);
        }
    }

    @Override
    @Transactional
    public void modificar(Integer id, MultipartFile logo, String nombre, String encargado, String ruc, String direccion) {
        Empresas empresa = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Empresa no encontrada"));

        empresa.setNombre(nombre);
        empresa.setEncargado(encargado);
        empresa.setRuc(ruc);
        empresa.setDireccion(direccion);

        if (logo != null && !logo.isEmpty()) {
            try {
                String nombreArchivo = UUID.randomUUID() + "_" + logo.getOriginalFilename();
                Path ruta = Paths.get("/home/usuario/public_html/logos/" + nombreArchivo);
                Files.copy(logo.getInputStream(), ruta);

                empresa.setLogo(nombreArchivo); // solo si se cambia
            } catch (IOException e) {
                throw new RuntimeException("Error al guardar el nuevo logo", e);
            }
        }

        repository.save(empresa);
    }

    @Override
    public Empresas modificar(Empresas empresa) {
        if (empresa == null) {
            throw new IllegalArgumentException("La habitación no puede ser nula");
        }

        if (empresa.getId() == null) {
            throw new IllegalArgumentException("El ID de la habitación es requerido");
        }

        return repository.findById(empresa.getId())
            .map(existente -> {
                existente.setNombre(empresa.getNombre());
                existente.setRuc(empresa.getRuc());
                existente.setDireccion(empresa.getDireccion());
                return repository.save(existente);
            })
            .orElseThrow(() -> new EntityNotFoundException(
                "Tipo de habitacion no encontrada con ID: " + empresa.getId()
            ));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Empresas> buscarId(Integer id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        Empresas empresa = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        Integer sucursalesActivas = sucursalRepository.contarSucursalesActivasPorEmpresa(id);
        if (Optional.ofNullable(sucursalesActivas).orElse(0) > 0){
        throw new ResponseStatusException(
            HttpStatus.CONFLICT, "No se puede eliminar: la empresa tiene sucursales activas"
        );
        }
            
        empresa.setEstado(0); 
        repository.save(empresa);
    }

}
