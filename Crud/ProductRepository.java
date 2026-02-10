import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para gestionar Productos
 * Implementa interfaces segregadas según las operaciones que realiza
 * Demuestra el ISP: clases que no necesitan todas las operaciones no dependen
 * de interfaces innecesarias
 */
public class ProductRepository implements ICreatable<Product>, IReadable<Product>, IUpdatable<Product>, IDeletable {

    private List<Product> products = new ArrayList<>();
    private int nextId = 1;

    /**
     * Crea un nuevo producto en el repositorio
     * 
     * @param product el producto a crear
     * @return true si se creó correctamente
     */
    @Override
    public boolean create(Product product) {
        product.setId(nextId++);
        return products.add(product);
    }

    /**
     * Obtiene un producto por su id
     * 
     * @param id el identificador del producto
     * @return el producto o null si no existe
     */
    @Override
    public Product getById(int id) {
        Optional<Product> product = products.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
        return product.orElse(null);
    }

    /**
     * Obtiene todos los productos
     * 
     * @return lista de todos los productos
     */
    @Override
    public List<Product> getAll() {
        return new ArrayList<>(products);
    }

    /**
     * Verifica si un producto existe
     * 
     * @param id el identificador a verificar
     * @return true si existe, false en caso contrario
     */
    @Override
    public boolean exists(int id) {
        return products.stream().anyMatch(p -> p.getId() == id);
    }

    /**
     * Actualiza un producto existente
     * 
     * @param product el producto con datos actualizados
     * @return true si se actualizó correctamente
     */
    @Override
    public boolean update(Product product) {
        Optional<Product> existing = products.stream()
                .filter(p -> p.getId() == product.getId())
                .findFirst();

        if (existing.isPresent()) {
            int index = products.indexOf(existing.get());
            products.set(index, product);
            return true;
        }
        return false;
    }

    /**
     * Elimina un producto por su id
     * 
     * @param id el identificador del producto a eliminar
     * @return true si se eliminó correctamente
     */
    @Override
    public boolean delete(int id) {
        return products.removeIf(p -> p.getId() == id);
    }
}
