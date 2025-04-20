package com.alexander.sistema_cerro_verde_backend.service;
import java.util.List;
import java.util.Set;
import com.alexander.sistema_cerro_verde_backend.entity.Usuarios;

public interface IUsuariosService {
        public Usuarios guardarUsuario(Usuarios usuario) throws Exception;
        public Usuarios obtenerUsuario(String username);
        public void eliminarUsuario(Integer id);
        public Usuarios actualizarUsuario(Integer id, Usuarios usuarioActualizado) throws Exception;
        public List<Usuarios> obtenerTodosUsuarios();
        public boolean existeUsuario(String username);
        public void cambiarContraseña(Integer id, String nuevaContraseña) throws Exception;
            }
    
