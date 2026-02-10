/**
 * CLASE PRINCIPAL - EJEMPLO DE USO DEL SISTEMA CRUD CON ISP
 * 
 * Este programa demuestra la implementación del Principio de Segregación de
 * Interfaces (ISP):
 * - Las interfaces están segregadas por responsabilidad (Create, Read, Update,
 * Delete)
 * - Cada servicio implementa SOLO las interfaces que necesita
 * - Los clientes no dependen de métodos que no usan
 * 
 * BENEFICIOS DEL ISP:
 * ✓ Mayor cohesión: cada interfaz tiene un propósito específico
 * ✓ Menor acoplamiento: las clases no dependen de interfaces innecesarias
 * ✓ Mayor flexibilidad: fácil crear servicios especializados (solo lectura,
 * solo escritura)
 * ✓ Mejor testabilidad: se pueden mockear fácilmente las interfaces necesarias
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║  SISTEMA CRUD CON PRINCIPIO DE SEGREGACIÓN DE INTERFACES  ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");

        // ============== INICIALIZACIÓN DE REPOSITORIOS ==============
        ProductRepository productRepository = new ProductRepository();
        UserRepository userRepository = new UserRepository();

        // ============== INICIALIZACIÓN DE SERVICIOS ==============
        // Cada servicio SOLO implementa las interfaces que necesita
        ProductReadService productReadService = new ProductReadService(productRepository);
        ProductWriteService productWriteService = new ProductWriteService(productRepository);

        UserReadService userReadService = new UserReadService(userRepository);
        UserWriteService userWriteService = new UserWriteService(userRepository);

        // ============== INICIALIZACIÓN DEL CONTROLADOR ==============
        CRUDController controller = new CRUDController(
                productReadService,
                productWriteService,
                userReadService,
                userWriteService);

        // ============== DEMOSTRACIONES ==============

        System.out.println("\n█ CREANDO PRODUCTOS...");
        controller.createProduct("Laptop", "Computadora portátil de alto rendimiento", 1200.00, 10);
        controller.createProduct("Mouse", "Mouse inalámbrico ergonómico", 25.50, 50);
        controller.createProduct("Teclado", "Teclado mecánico RGB", 89.99, 30);

        System.out.println("\n█ CREANDO USUARIOS...");
        controller.createUser("admin", "admin@example.com", "admin123", "ADMIN", true);
        controller.createUser("usuario1", "user1@example.com", "pass123", "USER", true);
        controller.createUser("usuario2", "user2@example.com", "pass456", "USER", false);

        System.out.println("\n█ LEYENDO PRODUCTOS...");
        controller.readProduct(1);
        controller.readProduct(2);
        controller.readProduct(99); // No existe

        System.out.println("\n█ LISTANDO TODOS LOS PRODUCTOS...");
        controller.readAllProducts();

        System.out.println("\n█ LEYENDO USUARIOS...");
        controller.readUser(1);
        controller.readUser(2);
        controller.readUser(99); // No existe

        System.out.println("\n█ LISTANDO TODOS LOS USUARIOS...");
        controller.readAllUsers();

        System.out.println("\n█ ACTUALIZANDO PRODUCTOS...");
        controller.updateProduct(1, "Laptop Gaming", "Laptop de última generación para gaming", 1500.00, 8);
        controller.updateProduct(2, "Mouse Gamer", "Mouse gamer con 12,000 DPI", 35.99, 45);

        System.out.println("\n█ ACTUALIZANDO USUARIOS...");
        controller.updateUser(2, "usuario2_actualizado", "newuser2@example.com", "newpass", "MODERATOR", true);

        System.out.println("\n█ VERIFICANDO EXISTENCIA...");
        System.out.println("¿Existe producto 1? " + controller.productExists(1));
        System.out.println("¿Existe producto 999? " + controller.productExists(999));
        System.out.println("¿Existe usuario 2? " + controller.userExists(2));
        System.out.println("¿Existe usuario 999? " + controller.userExists(999));

        System.out.println("\n█ ESTADO ANTES DE ELIMINAR...");
        controller.readAllProducts();
        controller.readAllUsers();

        System.out.println("\n█ ELIMINANDO DATOS...");
        controller.deleteProduct(3); // Elimina el teclado
        controller.deleteUser(3); // Elimina el tercer usuario

        System.out.println("\n█ ESTADO DESPUÉS DE ELIMINAR...");
        controller.readAllProducts();
        controller.readAllUsers();

        // ============== EXPLICACIÓN DEL ISP ==============
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║             PRINCIPIO DE SEGREGACIÓN DE INTERFACES         ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");

        System.out.println("✓ ICreatable<T>  → Solo para CREAR nuevas entidades");
        System.out.println("✓ IReadable<T>   → Solo para LEER entidades (sin modificar)");
        System.out.println("✓ IUpdatable<T>  → Solo para ACTUALIZAR entidades existentes");
        System.out.println("✓ IDeletable     → Solo para ELIMINAR entidades\n");

        System.out.println("BENEFICIOS DEMOSTRADOS:");
        System.out.println("─────────────────────────────");
        System.out.println("1. ProductReadService SOLO depende de IReadable<Product>");
        System.out.println("   → No necesita métodos de creación, actualización o eliminación\n");

        System.out.println("2. ProductWriteService SOLO depende de ICreatable, IUpdatable e IDeletable");
        System.out.println("   → No necesita métodos de lectura\n");

        System.out.println("3. Los clientes reciben EXACTAMENTE lo que necesitan");
        System.out.println("   → Menor acoplamiento");
        System.out.println("   → Mayor cohesión");
        System.out.println("   → Más fácil de testear y mantener\n");

        System.out.println("4. Fácil crear nuevos servicios especializados");
        System.out.println("   → Servicios solo de lectura");
        System.out.println("   → Servicios solo de escritura");
        System.out.println("   → Servicios con validaciones específicas\n");

        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                    EJECUCIÓN COMPLETADA                    ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }
}
