/**
 * Interface segregada para operaciones de lectura (R de CRUD)
 * Solo contiene métodos para consultar entidades sin modificarlas
 */
public interface IReadable<T> {
    /**
     * Obtiene una entidad por su identificador
     * 
     * @param id el identificador de la entidad
     * @return la entidad encontrada o null si no existe
     */
    T getById(int id);

    /**
     * Obtiene todas las entidades
     * 
     * @return lista de todas las entidades
     */
    java.util.List<T> getAll();

    /**
     * Verifica si una entidad existe por su identificador
     * 
     * @param id el identificador a verificar
     * @return true si existe, false en caso contrario
     */
    boolean exists(int id);
}
