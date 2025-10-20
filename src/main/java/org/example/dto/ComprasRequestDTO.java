// ComprasRequestDTO.java - SIN anotaciones JPA
package org.example.dto;

/*PORQUE CREE ESTA CLASE?¿

¿Qué es exactamente ComprasRequestDTO?

ComprasRequestDTO (siglas de Data Transfer Object o Objeto de Transferencia de Datos) es una clase simple de Java que se utiliza exclusivamente para transferir datos entre la capa de presentación (el controlador REST) y la capa de negocio (el servicio).

En tu caso, ComprasRequestDTO es un DTO de Entrada (Request DTO).

1. El Propósito en tu POST /api/compras

El objetivo principal de ComprasRequestDTO es definir el contrato exacto de lo que tu API espera recibir en el cuerpo de una solicitud POST para crear una compra.
Propiedad	Descripción
Desacoplamiento	Separa el formato de la API (JSON) de la estructura de la base de datos (entidad JPA).
Entrada Simple	Permite que el cliente (quien consume la API) envíe un JSON simple que solo contiene los IDs necesarios para la compra: usuarioId y juegoId.
Evitar Errores	Previene que el cliente intente enviar el objeto Usuario completo o el objeto Juego completo, lo cual causaba el error de serialización anterior.


2. Estructura de la Clase

La clase es un simple "portador de datos" con campos que coinciden con el JSON de entrada y sus respectivos getters y setters. No es una entidad JPA, por lo que no lleva las anotaciones @Entity ni @Table.
3. El Flujo de Trabajo con el DTO

Cuando implementas esta solución, el proceso es:

    Controlador (ComprasController): Recibe el ComprasRequestDTO y verifica que el JSON es válido.

    Servicio (ComprasService): Recibe el DTO, lee el usuarioId y juegoId.

    Servicio (ComprasService): Utiliza los IDs para llamar a los repositorios (UsuarioRepository y JuegoRepository) y buscar las entidades completas (Usuario y Juego).

    Servicio (ComprasService): Crea la entidad JPA Compras (new Compras(usuario, juego)) y la guarda en la base de datos.

Este flujo de trabajo es el estándar de la industria porque garantiza que la lógica de negocio (buscar y validar las entidades) se mantenga en la capa de Servicio, mientras que el controlador se mantiene limpio.

 */

public class ComprasRequestDTO {

    // Estos campos serán populados por Jackson (el deserializador de JSON)
    private Long usuarioId;
    private Long juegoId;

    // Getters y Setters (necesarios para que Jackson lea los IDs del JSON)
    public Long getUsuarioId() {
        return usuarioId;
    }
    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
    public Long getJuegoId() {
        return juegoId;
    }
    public void setJuegoId(Long juegoId) {
        this.juegoId = juegoId;
    }
}