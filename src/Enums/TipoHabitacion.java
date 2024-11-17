package Enums;

public enum TipoHabitacion {
    SIMPLE(1000),   // Precio base: $1000 por día
    DOBLE(1500),    // Precio base: $1500 por día
    SUITE(2500);    // Precio base: $2500 por día

    private final int precioPorDia;

    TipoHabitacion(int precioPorDia) {
        this.precioPorDia = precioPorDia;
    }

    public int getPrecioPorDia() {
        return precioPorDia;
    }
}
