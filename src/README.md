# Sistema de Gestión de Biblioteca

## Descripción

Sistema de gestión bibliotecaria desarrollado en Java que permite administrar libros, socios y préstamos. Implementa una arquitectura MVC (Modelo-Vista-Controlador) con capas adicionales para separar responsabilidades, garantizando un código mantenible y escalable para futuras migraciones a interfaz gráfica y base de datos.

## Arquitectura del Proyecto

El proyecto sigue una arquitectura en capas con patrón MVC + Capas:

- **src/**
  - **controllers/**
    - BookController.java
    - LoanController.java
    - PartnerController.java
  - **models/**
    - Book.java
    - Loan.java
    - Partner.java
    - StatusLoan.java
  - **views/**
    - BookMenu.java
    - LoanMenu.java
    - PartnerMenu.java
  - **services/**
    - BookService.java
    - LoanService.java
    - PartnerService.java
  - **repositories/**
    - Identifiable.java
    - Repositories.java
    - RepositoryMemory.java
  - **exceptions/**
    - BookNotFoundException.java
    - DuplicateBookException.java
    - DuplicatePartnerException.java
    - LoanAlreadyReturnedException.java
    - LoanLimitExceededException.java
    - LoanNotFoundException.java
    - NoAvailableCopiesException.java
    - PartnerNotFoundException.java
  - AppContext.java
  - Main.java


## 📊 Modelos de Datos (Entidades)

### 📖 Libro (Book)
| Atributo | Tipo | Descripción |
|----------|------|-------------|
| id | String | Identificador único del libro |
| author | String | Autor del libro |
| title | String | Título del libro |
| numberOfCopies | int | Número total de copias disponibles |

### 👤 Socio (Partner)
| Atributo | Tipo | Descripción |
|----------|------|-------------|
| id | String | Identificador único del socio |
| name | String | Nombre completo del socio |
| email | String | Correo electrónico del socio |
| listActiveLoan | List\<Book\> | Lista de libros que el socio tiene en préstamo activo |

### 📋 Préstamo (Loan)
| Atributo | Tipo | Descripción |
|----------|------|-------------|
| id | String | Identificador único del préstamo |
| bookId | String | ID del libro prestado |
| partnerId | String | ID del socio que realiza el préstamo |
| loanDate | LocalDate | Fecha en que se realizó el préstamo |
| dueDate | LocalDate | Fecha límite de devolución |
| statusLoan | StatusLoan | Estado actual del préstamo |

### 🔄 StatusLoan (Enum)
- `ACTIVE` - Préstamo activo
- `RETURNED` - Libro devuelto
- `OVERDUE` - Préstamo atrasado (fecha actual > fecha límite)

## 🎯 Funcionalidades Principales

### Gestión de Libros (CRUD)
- ✅ **Agregar** nuevos libros con título, autor y cantidad de copias
- ✅ **Listar** todos los libros disponibles
- ✅ **Buscar** libros por título o autor
- ✅ **Eliminar** libros del sistema
- ✅ Validación de copias disponibles para préstamos

### Gestión de Socios (CRUD)
- ✅ **Registrar** nuevos socios con nombre y email
- ✅ **Listar** todos los socios registrados
- ✅ **Buscar** socios por nombre o email
- ✅ **Eliminar** socios del sistema
- ✅ Cada socio mantiene su lista de libros en préstamo activo

### Gestión de Préstamos
- ✅ **Registrar** préstamo de un libro a un socio
  - Valida que haya copias disponibles
  - Valida que el socio no exceda el límite de préstamos
  - Asigna fecha de préstamo y fecha límite de devolución
- ✅ **Registrar** devolución de libro
  - Actualiza disponibilidad del libro
  - Cambia estado del préstamo a "RETURNED"
- ✅ **Listar** préstamos activos de un socio específico
- ✅ **Listar** préstamos atrasados (fecha actual > fecha límite)

## ⚙️ Reglas de Negocio

### 🚫 Límite de Préstamos por Socio
Un socio no puede tener más de **N** préstamos activos simultáneamente (valor configurable).

- Si un socio intenta tomar un préstamo y ya alcanzó su límite, se lanza la excepción `LoanLimitExceededException`
- Esta regla se valida en la capa de servicios antes de registrar un nuevo préstamo

### 📚 Disponibilidad de Libros
- Cada libro tiene un número total de copias (`numberOfCopies`)
- Al registrar un préstamo, se decrementa automáticamente `numberOfCopies`
- Al registrar una devolución, se incrementa `numberOfCopies`
- Si no hay copias disponibles (`numberOfCopies == 0`), se lanza `NoAvailableCopiesException`

### 🔄 Estados de Préstamos
- **ACTIVE**: Préstamo vigente dentro de la fecha límite
- **OVERDUE**: Préstamo que excedió la fecha límite (calculado automáticamente)
- **RETURNED**: Préstamo ya devuelto (no puede volver a prestarse)

### 🔗 Relaciones entre Entidades
- **Loan** almacena `bookId` y `partnerId` (no objetos completos)
- **Partner** mantiene `listActiveLoan` con los libros prestados actualmente
- Esto permite un acceso rápido a los préstamos activos de cada socio

## ⚙️ Manejo de Excepciones

El sistema cuenta con un robusto sistema de manejo de excepciones para garantizar la integridad de los datos:

| Excepción | Descripción |
|-----------|-------------|
| `BookNotFoundException` | Libro no encontrado en el sistema |
| `DuplicateBookException` | Intento de agregar libro duplicado |
| `DuplicatePartnerException` | Socio ya registrado (mismo email) |
| `LoanAlreadyReturnedException` | Intento de devolver un préstamo ya finalizado |
| `LoanLimitExceededException` | Socio superó el límite máximo de préstamos |
| `LoanNotFoundException` | Préstamo no encontrado en el sistema |
| `NoAvailableCopiesException` | No hay copias disponibles del libro |
| `PartnerNotFoundException` | Socio no encontrado en el sistema |

## 🗄️ Capa de Repositorios

### RepositoryMemory<T extends Identifiable>
Implementación genérica en memoria que maneja cualquier entidad que implemente `Identifiable`:

```java
public class RepositoryMemory<T extends Identifiable> implements Repositories<T> {
    private final List<T> entityList;

    @Override
    public void create(T entity) { 
        entityList.add(entity); 
    }
    
    @Override
    public List<T> findAll() { 
        return entityList; 
    }
    
    @Override
    public T findById(String id) {
        for (T data : this.entityList) {
            if (data.getId().equals(id)) {
                return data;
            }
        }
        return null;
    }
    
    @Override
    public void update(T entity) {
        for (int i = 0; i < this.entityList.size(); i++) {
            if (this.entityList.get(i).getId().equals(entity.getId())) {
                this.entityList.set(i, entity);
                return;
            }
        }
    }
    
    @Override
    public void deleteById(String id) {
        this.entityList.removeIf(data -> data.getId().equals(id));
    }
}

