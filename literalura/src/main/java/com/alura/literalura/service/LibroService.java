import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    private static final String GUTENDEX_URL = "https://gutendex.com/books?search=";

    @Transactional
    public Libro buscarORegistrarLibro(String titulo) {
        // Buscar el libro en la base de datos
        Libro libroExistente = libroRepository.findByTitulo(titulo);

        if (libroExistente != null) {
            return libroExistente;
        } else {
            // Si no está en la base de datos, buscar en Gutendex API
            RestTemplate restTemplate = new RestTemplate();
            String url = GUTENDEX_URL + titulo;

            GutendexResponse gutendexResponse = restTemplate.getForObject(url, GutendexResponse.class);

            if (gutendexResponse != null && gutendexResponse.getResults() != null && !gutendexResponse.getResults().isEmpty()) {
                GutendexBook gutendexBook = gutendexResponse.getResults().get(0); // Tomamos el primer resultado

                Libro nuevoLibro = new Libro();
                nuevoLibro.setTitulo(gutendexBook.getTitle());
                nuevoLibro.setAutor(gutendexBook.getAuthors().get(0)); // Suponemos que toma el primer autor de la lista
                nuevoLibro.setIdioma(gutendexBook.getLanguages().get(0)); // Suponemos que toma el primer idioma de la lista
                nuevoLibro.setDescargas(gutendexBook.getDownloads());

                return libroRepository.save(nuevoLibro);
            } else {
                throw new LibroNoEncontradoException("El libro '" + titulo + "' no fue encontrado en Gutendex.");
            }
        }
    }

    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    public List<Autor> listarAutores() {
        return autorRepository.findAll();
    }

    public List<Autor> listarAutoresVivosEnAño(int año) {
        return autorRepository.findByAñoNacimientoLessThanEqual(año);
    }

    public List<Libro> listarLibrosPorIdioma(String idioma) {
        return libroRepository.findByIdioma(idioma);
    }
}