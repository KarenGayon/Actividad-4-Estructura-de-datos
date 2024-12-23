
package GestionBiblioteca2;

import java.util.Scanner;

class NodoLibro {
    int codigo;
    String titulo;
    String autor;
    int cantidad;
    NodoLibro izquierda, derecha;

    public NodoLibro(int codigo, String titulo, String autor, int cantidad) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.cantidad = cantidad;
        this.izquierda = null;
        this.derecha = null;
    }
}

class BibliotecaArbol {
    private NodoLibro raiz;

    public BibliotecaArbol() {
        this.raiz = null;
    }


    public void insertarLibro(int codigo, String titulo, String autor, int cantidad) {
        raiz = insertar(raiz, codigo, titulo, autor, cantidad);
    }

    private NodoLibro insertar(NodoLibro nodo, int codigo, String titulo, String autor, int cantidad) {
        if (nodo == null) {
            return new NodoLibro(codigo, titulo, autor, cantidad);
        }
        if (codigo < nodo.codigo) {
            nodo.izquierda = insertar(nodo.izquierda, codigo, titulo, autor, cantidad);
        } else if (codigo > nodo.codigo) {
            nodo.derecha = insertar(nodo.derecha, codigo, titulo, autor, cantidad);
        } else {
            nodo.cantidad += cantidad; // Si ya existe el libro, aumenta la cantidad
        }
        return nodo;
    }


    private NodoLibro actualizarCantidad(int codigo, int cantidad, boolean esDevolucion) {
        NodoLibro libro = buscarLibro(codigo);
        if (libro != null) {
            if (esDevolucion || libro.cantidad >= cantidad) {
                libro.cantidad += esDevolucion ? cantidad : -cantidad;
                return libro;
            } else {
                System.out.println("Cantidad insuficiente de ejemplares disponibles.");
            }
        } else {
            System.out.println("Libro no encontrado.");
        }
        return null;
    }

    public NodoLibro buscarLibro(int codigo) {
        return buscar(raiz, codigo);
    }

    private NodoLibro buscar(NodoLibro nodo, int codigo) {
        if (nodo == null || nodo.codigo == codigo) {
            return nodo;
        }
        if (codigo < nodo.codigo) {
            return buscar(nodo.izquierda, codigo);
        } else {
            return buscar(nodo.derecha, codigo);
        }
    }


    public void prestarLibro(int codigo, int cantidad) {
        NodoLibro libro = actualizarCantidad(codigo, cantidad, false);
        if (libro != null) {
            System.out.println("Préstamo realizado: " + cantidad + " ejemplares de '" + libro.titulo + "'");
        }
    }

    public void devolverLibro(int codigo, int cantidad) {
        NodoLibro libro = actualizarCantidad(codigo, cantidad, true);
        if (libro != null) {
            System.out.println("Devolución realizada: " + cantidad + " ejemplares de '" + libro.titulo + "'");
        }
    }



    private void mostrarEnOrden(NodoLibro nodo, StringBuilder sb) {
        if (nodo != null) {
            mostrarEnOrden(nodo.izquierda, sb);
            sb.append("Código: ").append(nodo.codigo)
                    .append(" | Título: ").append(nodo.titulo)
                    .append(" | Autor: ").append(nodo.autor)
                    .append(" | Cantidad: ").append(nodo.cantidad).append("\n");
            mostrarEnOrden(nodo.derecha, sb);
        }
    }

    public void mostrarInventario() {
        if (raiz == null) {
            System.out.println("La biblioteca está vacía.");
        } else {
            System.out.println("---- Inventario de la biblioteca en orden de código ----");
            StringBuilder sb = new StringBuilder();
            mostrarEnOrden(raiz, sb);
            System.out.print(sb.toString());
        }
    }



    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        BibliotecaArbol biblioteca = new BibliotecaArbol();
        int opcion;
        do {
            System.out.println("----- Sistema de Gestión de Biblioteca -----");
            System.out.println("1. Insertar Libro");
            System.out.println("2. Buscar Libro por Código");
            System.out.println("3. Prestar Libro");
            System.out.println("4. Devolver Libro");
            System.out.println("5. Mostrar Inventario");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = entrada.nextInt();
            entrada.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el código del libro: ");
                    int codigo = entrada.nextInt();
                    entrada.nextLine();
                    System.out.print("Ingrese el título del libro: ");
                    String titulo = entrada.nextLine();
                    System.out.print("Ingrese el autor del libro: ");
                    String autor = entrada.nextLine();
                    System.out.print("Ingrese la cantidad de ejemplares: ");
                    int cantidad = entrada.nextInt();
                    biblioteca.insertarLibro(codigo, titulo, autor, cantidad);
                    System.out.println("Libro agregado a la biblioteca.");
                    break;

                case 2:
                    System.out.print("Ingrese el código del libro a buscar: ");
                    codigo = entrada.nextInt();
                    NodoLibro libro = biblioteca.buscarLibro(codigo);
                    if (libro != null) {
                        System.out.println("Libro encontrado: Código: " + libro.codigo + " | Título: " + libro.titulo + " | Autor: " + libro.autor + " | Cantidad: " + libro.cantidad);
                    } else {
                        System.out.println("Libro no encontrado.");
                    }
                    break;

                case 3:
                    System.out.print("Ingrese el código del libro a prestar: ");
                    codigo = entrada.nextInt();
                    System.out.print("Ingrese la cantidad a prestar: ");
                    cantidad = entrada.nextInt();
                    biblioteca.prestarLibro(codigo, cantidad);
                    break;

                case 4:
                    System.out.print("Ingrese el código del libro a devolver: ");
                    codigo = entrada.nextInt();
                    System.out.print("Ingrese la cantidad a devolver: ");
                    cantidad = entrada.nextInt();
                    biblioteca.devolverLibro(codigo, cantidad);
                    break;

                case 5:
                    biblioteca.mostrarInventario();
                    break;

                case 6:
                    System.out.println("Cerrando el sistema.");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 6);
        entrada.close();
    }
}