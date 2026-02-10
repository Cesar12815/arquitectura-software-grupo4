/**
 * Interface segregada para operaciones de actualización (U de CRUD)
 * Solo contiene el método necesario para modificar entidades existentes
 */
public interface IUpdatable<T> {
    /**
     * Actualiza una entidad existente
     * 
     * @param entity la entidad con los datos actualizados
     * @return true si se actualizó correctamente, false en caso contrario
     */
    boolean update(T entity);
}
