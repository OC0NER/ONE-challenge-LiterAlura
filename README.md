# `LiterAlura - Challenge Backend Alura - Oracle Next Education`
---
## 📋 `OBJETIVO`: 
Desarrollar un Catálogo de Libros que ofrezca interacción textual (vía consola) con los usuarios, proporcionando 5 opciones de interacción. Los libros se buscan a través de la API Gutendex (https://gutendex.com/), se tratan y se guardan en una base de datos relacional.

## 🖥️ `Tecnologías utilizadas`
- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
---
## 🛠️ `Configuración del entorno`

### Requisitos

* Java JDK 21 o superior
* Maven 4 o superior
* Spring Boot 3.2.3
* Postgres 16 o superior
* IDE IntelliJ IDEA (opcional)


### Variables de Entorno

Se requiere configurar las siguientes variables de entorno:

<table border="1">
    <tr style="text-align: center;">
        <td>*VARIABLE*</td>
        <td>*DESCRIPCIÓN*</td>
    </tr>
    <tr>
        <td>DB_HOST</td>
        <td>Ruta y puerto de la base de datos</td>
    </tr>
    <tr>
        <td>DB_NAME</td>
        <td>Nombre de la base de datos</td>
    </tr>
    <tr>
        <td>DB_USER</td>
        <td>Usuario de la base de datos</td>
    </tr>
    <tr>
        <td>DB_PASSWORD</td>
        <td>Contraseña de acceso a la base de datos</td>
    </tr>
</table>

---

## 👨‍💻 `Funcionamiento`
### Inicio
Se muestra el menú con las posibles opciones

![1  Inicio](https://github.com/OC0NER/challenge-literatura/assets/154689355/cdb0fc1b-c58a-475b-9600-84ab70940fe8)

* Si ingresas una opción no valida la app te lo indica y te pide volver a intentarlo

### Opcion 1 

La app te solicita el nombre del libro que deseas buscar, realiza la petición a la API, al obtener la respuesta verifica si el libro ya se encuentra registrado en la base de datos, de ser así la app te lo hace saber y si no, la app trata los datos y los guarda en la base de datos para lo cual se utiliza una relacion `Many to many ` entre libros y autores, adicional a ésto se crea una tabla auxiliar para guardar la lista de idiomas de cada libro.

![1 1 Buscar libro por titulo](https://github.com/OC0NER/challenge-literatura/assets/154689355/9e450fec-75f0-4e21-ac3a-db5154f05b53)

### Opcion 2 

Con ésta opción se muestran los libros que se guardaron en la base de datos mediante la opción 1

![1 2 Mostrar libros guardados](https://github.com/OC0NER/challenge-literatura/assets/154689355/cc924f72-6a94-40bb-8291-e9d4b3f76868)

### Opcion 3 

Con ésta opción se muestran los autores correspondientes a los libros que se guardaron en la base de datos mediante la opción 1, cabe mencionar que para cada libro se pueden guardar varios autores y viceversa.

![1 3 Mostrar autores guardados](https://github.com/OC0NER/challenge-literatura/assets/154689355/5116197e-fe83-4d5c-8b2d-18797700357e)

### Opcion 4 

La app te solicita ingresar un año para filtrar los autores guardados en la base de datos y mostrarte los autores que estaban vivos en el año seleccionado.

![1 4 Mostrar autores vivos en un año determinado](https://github.com/OC0NER/challenge-literatura/assets/154689355/b50c23cf-1531-4c60-856a-2aba3ae618be)

### Opcion 5 

Por último la app te muestra la lista de idiomas de los libros que se encuentran registrados en la base de datos para que puedas elegir un código de idioma y la app filtrará los libros y te mostrará los que tengan el idioma seleccionado.

![1 5 Mostrar libros de un idioma](https://github.com/OC0NER/challenge-literatura/assets/154689355/32b55e91-0c10-4b7b-8a54-787665aa506d)
