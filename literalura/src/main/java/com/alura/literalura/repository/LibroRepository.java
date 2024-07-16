import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    // Método para buscar libros por idioma
    List<Libro> findByIdioma(String idioma);

    // Método para contar libros por título y autor
    long countByTituloAndAutor(String titulo, String autor);

    // Método para buscar libros por título y autor
    List<Libro> findByTituloAndAutor(String titulo, String autor);
}