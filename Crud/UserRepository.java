import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para gestionar Usuarios
 * Implementa interfaces segregadas según las operaciones que realiza
 * Ejemplifica ISP: las operaciones de lectura están separadas de las de
 * modificación
 */
public class UserRepository implements ICreatable<User>, IReadable<User>, IUpdatable<User>, IDeletable {

    private List<User> users = new ArrayList<>();
    private int nextId = 1;

    /**
     * Crea un nuevo usuario en el repositorio
     * 
     * @param user el usuario a crear
     * @return true si se creó correctamente
     */
    @Override
    public boolean create(User user) {
        user.setId(nextId++);
        return users.add(user);
    }

    /**
     * Obtiene un usuario por su id
     * 
     * @param id el identificador del usuario
     * @return el usuario o null si no existe
     */
    @Override
    public User getById(int id) {
        Optional<User> user = users.stream()
                .filter(u -> u.getId() == id)
                .findFirst();
        return user.orElse(null);
    }

    /**
     * Obtiene todos los usuarios
     * 
     * @return lista de todos los usuarios
     */
    @Override
    public List<User> getAll() {
        return new ArrayList<>(users);
    }

    /**
     * Verifica si un usuario existe
     * 
     * @param id el identificador a verificar
     * @return true si existe, false en caso contrario
     */
    @Override
    public boolean exists(int id) {
        return users.stream().anyMatch(u -> u.getId() == id);
    }

    /**
     * Actualiza un usuario existente
     * 
     * @param user el usuario con datos actualizados
     * @return true si se actualizó correctamente
     */
    @Override
    public boolean update(User user) {
        Optional<User> existing = users.stream()
                .filter(u -> u.getId() == user.getId())
                .findFirst();

        if (existing.isPresent()) {
            int index = users.indexOf(existing.get());
            users.set(index, user);
            return true;
        }
        return false;
    }

    /**
     * Elimina un usuario por su id
     * 
     * @param id el identificador del usuario a eliminar
     * @return true si se eliminó correctamente
     */
    @Override
    public boolean delete(int id) {
        return users.removeIf(u -> u.getId() == id);
    }
}
