package com.example.challengeliteratura.principal;

import com.example.challengeliteratura.model.*;
import com.example.challengeliteratura.repository.AutorRepository;
import com.example.challengeliteratura.repository.LibroRepository;
import com.example.challengeliteratura.service.ConsumoAPI;
import com.example.challengeliteratura.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL = "https://gutendex.com/books/?search=";
    private Optional<Autor> autor;
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraOpciones() {

        var opcion = 0;
        while (opcion != 9) {
            System.out.println(
                    """
                            \n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                                            ** BUSCADOR DE LIBROS **
                            1) Buscar libro por título
                            2) Mostrar libros guardados
                            3) Mostrar autores guardados
                            4) Mostrar autores vivos en un año determinado
                            5) Mostrar libros por idioma
                            9) Salir
                            ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                                            * Selecciona una opción:
                            """
            );
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();
                switch (opcion) {
                    case 1:
                        buscarPorTitulo();
                        break;
                    case 2:
                        mostrarLibrosGuardados(libroRepository.findAllByOrderByTituloAsc());
                        break;
                    case 3:
                        mostrarAutoresGuardados(autorRepository.findAllByOrderByNombreAsc());
                        break;
                    case 4:
                        mostrarAutoresVivosSegunAnio();
                        break;
                    case 5:
                        mostrarLibrosPorIdioma();
                        break;
                    case 9:
                        System.out.println("\n******* HASTA LUEGO *******\n");
                        break;
                    default:
                        System.out.println("\nOpción inválida, intenta de nuevo:");
                }
            } catch (Exception e) {
                System.out.println("Error del tipo: " + e.getMessage());
                scanner.next();
            }
        }
    }

    private void buscarPorTitulo() {
        System.out.println("\n¿Cuál es el nombre del libro? ");
        var busquedaLibro = scanner.nextLine();
        var json = consumoAPI.obtenerDatos(URL + busquedaLibro.toLowerCase().replace(" ", "%20"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);

        Optional<DatosLibro> libroBuscado = datosBusqueda.results().stream()
                .filter(l -> l.titulo().toUpperCase().contains(busquedaLibro.toUpperCase()))
                .findFirst();

        if (libroBuscado.isPresent()) {

            Libro libro = new Libro(libroBuscado.get());

            if (libroRepository.findByTitulo(libro.getTitulo()).isPresent()) {
                System.out.println("*** Éste libro ya se encuentra registrado ***");
            } else {
                var formato = """
                        \n
                        -------------------------
                             Libro encontrado:
                        -------------------------
                        Titulo   : %s
                        Autor    : %s
                        Idiomas  : %s
                        Descargas: %s
                        """;

                System.out.printf(
                        (formato) + "%n", libroBuscado.get().titulo(),
                        libroBuscado.get().autores(),
                        libroBuscado.get().idiomas(),
                        libroBuscado.get().descargas()
                );

                for (int i = 0; i < libroBuscado.get().autores().size(); i++) {

                    //Valida si el autor ya se encuentra en la base de datos
                    autor = autorRepository.findByNombre(libroBuscado.get().autores().get(i).nombre());

                    if (autor.isPresent()) {
                        libro.setAutores(autor.get());
                    } else {
                        Autor autorNuevo = new Autor(libroBuscado.get().autores().get(i));
                        autorNuevo.setLibros(libro);
                        libro.setAutores(autorNuevo);
                        autorRepository.save(autorNuevo);
                    }
                }
                libroRepository.save(libro);

                System.out.println("\n*** El libro se guardó correctamente ***\n");

            }

        } else System.out.println("**** Libro no en contrado ****");
    }

    private void mostrarLibrosGuardados(List<Libro> listaLibros) {
        var formato = """
                \n
                -------------------------
                        Libro:
                -------------------------
                Titulo   : %s
                Autor    : %s
                Idiomas  : %s
                Descargas: %s
                """;

        if (listaLibros.isEmpty()) {
            System.out.println("\n*** No se encontraron libros ***\n");
        } else {
            listaLibros.forEach(l -> System.out.printf(
                    (formato) + "%n", l.getTitulo(),
                    l.getAutores().stream()
                            .map(a -> a.getNombre())
                            .collect(Collectors.joining(" | ", "[", "]")),
                    l.getIdiomas(),
                    l.getDescargas()
            ));
        }


    }

    private void mostrarAutoresGuardados(List<Autor> listaAutores) {

        var formato = """
                \n
                -------------------------
                        Autor:
                -------------------------
                Nombre            : %s
                Año Nacimiento    : %s
                Año Fallecimiento : %s
                Libros            : %s
                """;

        listaAutores.forEach(a -> System.out.printf(
                (formato) + "%n", a.getNombre(),
                (a.getNacimiento() == null ? "N/D" : a.getNacimiento()),
                (a.getFallecimiento() == null ? "N/D" : a.getFallecimiento()),
                libroRepository.obtenerLibrosPorAutor(a.getId()).stream()
                        .map(l -> l.getTitulo())
                        .collect(Collectors.joining(" | ", "[", "]"))
        ));
    }

    private void mostrarAutoresVivosSegunAnio() {
        try {
            System.out.println("\nIngresa un año: ");
            int anio = scanner.nextInt();
            List<Autor> autores = autorRepository.obtenerAutoresPorAnio(anio);

            if (autores.isEmpty()) {
                System.out.println("\nNo se encontraron autores vivos en el año: " + anio);
            } else {
                var formato = """
                        \n
                        -------------------------------------------
                            Autores vivos para el año: %s
                        -------------------------------------------
                        """;
                System.out.printf(
                        (formato), anio
                );

                var formato1 = """
                        \n
                        -------------------------
                                Autor:
                        -------------------------
                        Nombre            : %s
                        Año Nacimiento    : %s
                        Año Fallecimiento : %s
                        Libros            : %s
                        """;

                autores.forEach(a -> System.out.printf(
                        (formato1) + "%n", a.getNombre(),
                        (a.getNacimiento() == null ? "N/D" : a.getNacimiento()),
                        (a.getFallecimiento() == null ? "N/D" : a.getFallecimiento()),
                        libroRepository.obtenerLibrosPorAutor(a.getId()).stream()
                                .map(l -> l.getTitulo())
                                .collect(Collectors.joining(" | ", "[", "]"))
                ));
            }


        } catch (Exception e) {
            System.out.println("\n *** El dato ingresado no es válido *** ");
            scanner.next();
        }
    }

    private void mostrarLibrosPorIdioma() {
        var listaIdiomas = libroRepository.obtenerListaIdiomas();
//        System.out.println(listaIdiomas);
        System.out.println("\n");
        listaIdiomas.forEach(i -> {
            switch (i) {
                case "en":
                    System.out.println("en - Inglés");
                    break;
                case "es":
                    System.out.println("es - Español");
                    break;
                case "fr":
                    System.out.println("fr - Francés");
                    break;
                case "it":
                    System.out.println("it - Italiano");
                    break;
                case "de":
                    System.out.println("de - Alemán");
                    break;
            }
        });
        System.out.println("\nIngresa un código de idioma: ");
        String idioma = scanner.nextLine();

        if (listaIdiomas.contains(idioma)){
            List<Libro> libros = libroRepository.findAll().stream()
                    .filter(l -> l.getIdiomas().contains(idioma))
                    .collect(Collectors.toList());
            var formato = """
                \n
                -------------------------
                        Libro:
                -------------------------
                Titulo   : %s
                Autor    : %s
                Idiomas  : %s
                Descargas: %s
                """;

            libros.forEach(l -> System.out.printf(
                    (formato) + "%n", l.getTitulo(),
                    l.getAutores().stream()
                            .map(a -> a.getNombre())
                            .collect(Collectors.joining(" | ", "[", "]")),
                    l.getIdiomas(),
                    l.getDescargas()
            ));
        } else {
            System.out.println("\n*** El código de idioma que ingresaste es inválido ***");
        }



    }




}
