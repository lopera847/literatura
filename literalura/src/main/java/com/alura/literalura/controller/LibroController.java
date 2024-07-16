import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/libros")
@Validated
public class LibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping("/{titulo}")
    public Libro buscarLibroPorTitulo(@PathVariable String titulo) {
        return libroService.buscarORegistrarLibro(titulo);
    }

    @GetMapping("/")
    public List<Libro> listarLibros() {
        return libroService.listarLibros();
    }

    @GetMapping("/autores")
    public List<Autor> listarAutores() {
        return libroService.listarAutores();
    }

    @GetMapping("/idioma/{idioma}")
    public List<Libro> listarLibrosPorIdioma(@PathVariable String idioma) {
        return libroService.listarLibrosPorIdioma(idioma);
    }

    @PostMapping("/")
    public Libro guardarLibro(@RequestBody @Valid Libro libro) {
        return libroService.guardarLibro(libro);
    }

    @ExceptionHandler(LibroNoEncontradoException.class)
    public ResponseEntity<String> handleLibroNoEncontradoException(LibroNoEncontradoException ex) {
        return ResponseEntity.notFound().build();
    }



}