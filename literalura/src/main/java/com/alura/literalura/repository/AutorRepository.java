import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    // Método para buscar autores por nombre completo
    Autor findByNombreAndApellido(String nombre, String apellido);

    // Método para listar todos los autores ordenados por apellido y nombre
    List<Autor> findAllByOrderByApellidoAscNombreAsc();

    // Método para buscar autores vivos en un año específico
    @Query("SELECT a FROM Autor a WHERE :anno BETWEEN a.añoNacimiento AND a.añoFallecimiento")
    List<Autor> findAutoresVivosEnAnno(@Param("anno") int anno);
}