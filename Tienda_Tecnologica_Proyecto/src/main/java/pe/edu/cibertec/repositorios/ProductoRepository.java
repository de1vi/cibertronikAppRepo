package pe.edu.cibertec.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.cibertec.modelos.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
	
	List<Producto> findByNombreContaining(String nombre);
	List<Producto> findByCategoria_NombreContaining(String nombreCategoria);

}
