package Clases;

import Interfaces.Jsonable;

import java.util.ArrayList;
import java.util.List;

public class GestorColeccion<T extends Jsonable> {
    private ArrayList<T> elementos;

    public GestorColeccion() {
        this.elementos = new ArrayList<>();
    }

    // Método para agregar un elemento a la colección
    public void agregarElemento(T elemento) {
        elementos.add(elemento);
    }

    // Método para eliminar un elemento de la colección
    public void eliminarElemento(T elemento) {
        elementos.remove(elemento);
    }

    // Método para obtener un elemento de la colección por índice
    public T obtenerElemento(int indice) {
        if (indice >= 0 && indice < elementos.size()) {
            return elementos.get(indice);
        }
        return null;
    }

    // Método para obtener todos los elementos de la colección
    public ArrayList<T> obtenerTodos() {
        return new ArrayList<>(elementos); // Retorna una copia de la lista para evitar modificaciones externas
    }
    public boolean estaVacia(){
        return elementos.isEmpty();
    }
}
