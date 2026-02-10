/**
 * Interface segregada para operaciones de creación (C de CRUD)
 * Solo contiene el método necesario para crear entidades
 */
public interface ICreatable<T> {
    /**
     * Crea una nueva entidad en la persistencia
     * 
     * @param entity la entidad a crear
     * @return true si se creó correctamente, false en caso contrario
     */
    boolean create(T entity);
}
