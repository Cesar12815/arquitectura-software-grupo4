/**
 * SERVICIO DE REPORTES - Solo implementa IReadable
 * 
 * BENEFICIO DEL ISP:
 * Este servicio SOLO necesita leer datos, por lo que SOLO depende de IReadable<Product>.
 * No necesita (ni debe depender de) métodos de creación, actualización o eliminación.
 * 
 * Esto garantiza que:
 * ✓ ReportService no puede (accidentalmente) modificar datos
 * ✓ Si alguien quiere modificar ReportService, no puede agregar métodos de escritura
 * ✓ Es claro para quien use esta clase que solo hace lectura
 */
public class ProductReportService implements IReadable<Product> {
    
    private IReadable<Product> productRepository;

    public ProductReportService(IReadable<Product> productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Genera un reporte con todos los productos
     */
    public void generateFullReport() {
        System.out.println("\n═══════════════════════════════════════════════════");
        System.out.println("           REPORTE DE INVENTARIO COMPLETO           ");
        System.out.println("═══════════════════════════════════════════════════\n");

        for (Product product : getAll()) {
            System.out.printf(
                "ID: %d | Nombre: %-15s | Precio: $%.2f | Stock: %d%n",
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock()
            );
        }
        System.out.println();
    }

    /**
     * Genera reporte de productos con stock bajo
     */
    public void generateLowStockReport() {
        System.out.println("\n═══════════════════════════════════════════════════");
        System.out.println("         REPORTE DE PRODUCTOS CON STOCK BAJO         ");
        System.out.println("═══════════════════════════════════════════════════\n");

        int lowStockCount = 0;
        for (Product product : getAll()) {
            if (product.getStock() < 20) {
                System.out.printf(
                    "⚠ %s - Stock: %d unidades%n",
                    product.getName(),
                    product.getStock()
                );
                lowStockCount++;
            }
        }

        if (lowStockCount == 0) {
            System.out.println("✓ No hay productos con stock bajo");
        }
        System.out.println();
    }

    /**
     * Genera reporte de productos más caros
     */
    public void generatePriceReport() {
        System.out.println("\n═══════════════════════════════════════════════════");
        System.out.println("         REPORTE DE PRODUCTOS POR PRECIO            ");
        System.out.println("═══════════════════════════════════════════════════\n");

        getAll().stream()
                .sorted((p1, p2) -> Double.compare(p2.getPrice(), p1.getPrice()))
                .forEach(p -> System.out.printf("%-15s: $%.2f%n", p.getName(), p.getPrice()));
        System.out.println();
    }

    // ============== IMPLEMENTACIÓN DE IReadable ==============

    @Override
    public Product getById(int id) {
        return productRepository.getById(id);
    }

    @Override
    public java.util.List<Product> getAll() {
        return productRepository.getAll();
    }

    @Override
    public boolean exists(int id) {
        return productRepository.exists(id);
    }
}
