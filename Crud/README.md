# SISTEMA CRUD CON PRINCIPIO DE SEGREGACIÓN DE INTERFACES (ISP)

## 📋 Descripción

Este proyecto implementa un sistema CRUD (Create, Read, Update, Delete) completo para gestionar Productos y Usuarios, aplicando el **Principio de Segregación de Interfaces (ISP)** uno de los cinco principios SOLID.

## 🎯 ¿Qué es el ISP (Interface Segregation Principle)?

**ISP**: "Los clientes no deben depender de interfaces que no utilizan"

Preferir interfaces **específicas** sobre interfaces **generales**. Cada interfaz debe tener una responsabilidad única y clara.

### Analogía:
Es como tener un control remoto separado para cada función:
- Un botón para encender/apagar
- Otro para volumen
- Otro para cambiar canal

En lugar de un único control gigante que combines todas las funciones.

## 📁 Estructura del Proyecto

```
Crud/
├── Interfaces/
│   ├── ICreatable.java          # Interfaz para CREATE
│   ├── IReadable.java           # Interfaz para READ
│   ├── IUpdatable.java          # Interfaz para UPDATE
│   └── IDeletable.java          # Interfaz para DELETE
│
├── Entities/
│   ├── Product.java             # Entidad de Producto
│   └── User.java                # Entidad de Usuario
│
├── Repositories/
│   ├── ProductRepository.java   # Gestiona persistencia de Productos
│   └── UserRepository.java      # Gestiona persistencia de Usuarios
│
├── Services/
│   ├── ProductReadService.java  # Servicio solo de lectura de Productos
│   ├── ProductWriteService.java # Servicio de escritura de Productos
│   ├── UserReadService.java     # Servicio solo de lectura de Usuarios
│   └── UserWriteService.java    # Servicio de escritura de Usuarios
│
├── Controller/
│   └── CRUDController.java      # Controlador central
│
└── Main.java                    # Clase principal con ejemplos
```

## 🔑 Interfaces Segregadas

### 1. **ICreatable<T>** - CREATE
```java
public interface ICreatable<T> {
    boolean create(T entity);
}
```
**Propósito**: Solo para crear nuevas entidades.

### 2. **IReadable<T>** - READ
```java
public interface IReadable<T> {
    T getById(int id);
    List<T> getAll();
    boolean exists(int id);
}
```
**Propósito**: Solo para consultar entidades sin modificarlas.

### 3. **IUpdatable<T>** - UPDATE
```java
public interface IUpdatable<T> {
    boolean update(T entity);
}
```
**Propósito**: Solo para actualizar entidades existentes.

### 4. **IDeletable** - DELETE
```java
public interface IDeletable {
    boolean delete(int id);
}
```
**Propósito**: Solo para eliminar entidades.

## 🏗️ Patrón de Implementación

### Repositorio (Implementa todas las interfaces)
```java
public class ProductRepository 
    implements ICreatable<Product>, 
               IReadable<Product>, 
               IUpdatable<Product>, 
               IDeletable {
    // Implementa todos los métodos
}
```

### Servicios (Implementan SOLO lo que necesitan)

**ProductReadService** - Solo lectura:
```java
public class ProductReadService implements IReadable<Product> {
    // Solo métodos de lectura
    // NO necesita create, update o delete
}
```

**ProductWriteService** - Solo escritura:
```java
public class ProductWriteService 
    implements ICreatable<Product>, 
               IUpdatable<Product>, 
               IDeletable {
    // Solo métodos de modificación
    // NO necesita métodos de lectura
}
```

## ✅ Ventajas del ISP en este proyecto

| Ventaja | Beneficio |
|---------|-----------|
| **Menor Acoplamiento** | ProductReadService no depende de métodos de escritura |
| **Mayor Cohesión** | Cada interfaz tiene un propósito único y claro |
| **Mejor Testabilidad** | Fácil mockear solo las interfaces necesarias |
| **Mayor Flexibilidad** | Crear nuevos servicios con combinaciones específicas |
| **Mantenibilidad** | Cambios en una interfaz no afectan servicios que no la usan |

## 📊 Ejemplo de Segregación

### ❌ SIN ISP (Mal diseño)
```java
// Una interfaz gigante con TODO
public interface ICRUDRepository<T> {
    boolean create(T entity);      // Creación
    T getById(int id);             // Lectura
    boolean update(T entity);      // Actualización
    boolean delete(int id);        // Eliminación
}

// Un servicio que SOLO lee pero debe implementar TODAS
public class ReadOnlyService implements ICRUDRepository<Product> {
    @Override
    public boolean create(T entity) { 
        throw new UnsupportedOperationException(); // ❌ No la usa
    }
    
    @Override
    public T getById(int id) { 
        return repository.getById(id); // ✓ Usa esta
    }
    
    // ... más métodos no usados
}
```

### ✅ CON ISP (Buen diseño)
```java
// Interfaces segregadas por responsabilidad
public interface IReadable<T> {
    T getById(int id);
    List<T> getAll();
}

// El servicio SOLO implementa lo que necesita
public class ProductReadService implements IReadable<Product> {
    // Solo métodos de lectura ✓
    // No implementa métodos innecesarios ✓
}
```

## 🚀 Cómo Compilar y Ejecutar

### 1. Compilar todos los archivos
```bash
javac *.java
```

### 2. Ejecutar el programa
```bash
java Main
```

### Salida esperada:
```
╔════════════════════════════════════════════════════════════╗
║  SISTEMA CRUD CON PRINCIPIO DE SEGREGACIÓN DE INTERFACES  ║
╚════════════════════════════════════════════════════════════╝

█ CREANDO PRODUCTOS...
✓ Producto creado: Laptop (ID: 1)
✓ Producto creado: Mouse (ID: 2)
✓ Producto creado: Teclado (ID: 3)

█ CREANDO USUARIOS...
✓ Usuario creado: admin (ID: 1)
✓ Usuario creado: usuario1 (ID: 2)
✓ Usuario creado: usuario2 (ID: 3)

... (más operaciones)
```

## 📚 Casos de Uso

### Caso 1: Servicio de Lectura
```java
// Solo necesita IReadable, no depende de métodos de escritura
public class ReportService implements IReadable<Product> {
    public void generateProductReport(int productId) {
        Product product = getById(productId);
        // Generar reporte...
    }
}
```

### Caso 2: Servicio de Escritura
```java
// Solo necesita métodos de modificación
public class ProductImportService 
    implements ICreatable<Product>, IUpdatable<Product> {
    
    public void importFromCSV(String filename) {
        // Crear o actualizar productos
    }
}
```

### Caso 3: Servicio de Auditoría
```java
// Solo necesita IReadable y IDeletable
public class AuditService implements IReadable<Product>, IDeletable {
    public void logAccess(int productId) {
        if (exists(productId)) {
            // Registrar acceso
        }
    }
    
    public void purgeOldRecords() {
        // Eliminar registros antiguos
    }
}
```

## 🔄 Flujo de Operaciones

```
┌─────────────────────────────────────┐
│      CRUDController                 │
│  (Interfaz con el usuario)          │
└──────────┬──────────────────────────┘
           │
      ┌────┴─────────────┬────────────┐
      │                  │            │
      ▼                  ▼            ▼
 ProductRead         ProductWrite  UserServices
 Service             Service       (Similar)
      │                  │
      └────────┬─────────┘
               │
               ▼
        ProductRepository
     (Implementa todas las
      interfaces CRUD)
               │
               ▼
          Almacenamiento
          (Lista en memoria)
```

## 🎓 Conceptos Aprendidos

1. **Segregación de Interfaces**: Dividir interfaces grandes en interfaces pequeñas y específicas
2. **Separación de Responsabilidades**: Cada interfaz tiene una responsabilidad única
3. **Patrones de Diseño**: Repository, Service Layer
4. **SOLID**: Implementación práctica de uno de los cinco principios
5. **Inyección de Dependencias**: Pasar dependencias a través del constructor

## 🔗 Relación con otros Principios SOLID

- **SRP**: Cada clase tiene una única responsabilidad
- **OCP**: Abierto para extensión (nuevos servicios), cerrado para modificación
- **LSP**: Las subclases son intercambiables por sus padres
- **ISP**: ✓ Interfaces segregadas y específicas
- **DIP**: Depender de abstracciones (interfaces), no de concreciones

## 📝 Conclusión

El **ISP** en este proyecto garantiza que:
- ✓ Cada servicio es independiente y enfocado
- ✓ No hay métodos no utilizados en las clases
- ✓ El código es más mantenible y flexible
- ✓ Las pruebas unitarias son más simples
- ✓ La extensión de funcionalidad es más segura

Este es un ejemplo práctico de cómo los principios SOLID mejoran la calidad y mantenibilidad del código.
