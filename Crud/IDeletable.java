/**
 * Interface segregada para operaciones de eliminación (D de CRUD)
 * Solo contiene el método necesario para eliminar entidades
 */
public interface IDeletable {
    /**
     * Elimina una entidad por su identificador
     * 
     * @param id el identificador de la entidad a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    boolean delete(int id);
}
