// app.js

const API_ROOT = 'http://localhost:8081/api';
const statusMessage = document.getElementById('status-message');

// Función auxiliar para mostrar mensajes de estado
function showStatus(message, isError = false) {
    statusMessage.textContent = message;
    statusMessage.style.color = isError ? 'red' : 'green';
    setTimeout(() => statusMessage.textContent = '', 5000); // Limpiar después de 5s
}

// =================================================================
//                     FUNCIONES PARA COMPRAS
// =================================================================

const COMPRAS_URL = `${API_ROOT}/compras`;

// GET ALL
async function getAllCompras() {
    const resultsContainer = document.getElementById('compras-list-results');
    resultsContainer.textContent = 'Cargando...';
    try {
        const response = await fetch(COMPRAS_URL);
        const data = await response.json();
        resultsContainer.textContent = JSON.stringify(data, null, 2);
        showStatus('Compras listadas con éxito.');
    } catch (error) {
        resultsContainer.textContent = 'Error al listar compras: ' + error.message;
        showStatus(' Error al listar compras.', true);
    }
}

// GET BY ID
async function getCompraById() {
    const id = document.getElementById('compras-find-id').value;
    const resultsContainer = document.getElementById('compras-find-results');
    if (!id) { return showStatus(' Introduce un ID para buscar.', true); }

    resultsContainer.textContent = `Buscando compra ${id}...`;
    try {
        const response = await fetch(`${COMPRAS_URL}/${id}`);
        if (!response.ok) { throw new Error(`Error HTTP: ${response.status}`); }
        const data = await response.json();
        resultsContainer.textContent = JSON.stringify(data, null, 2);
        showStatus(` Compra ID ${id} encontrada.`);
    } catch (error) {
        resultsContainer.textContent = ' Error: Compra no encontrada o error de API.';
        showStatus(` Error al buscar ID ${id}.`, true);
    }
}


async function createCompra() {
    const usuarioId = document.getElementById('compras-post-usuario-id').value;
    const juegoId = document.getElementById('compras-post-juego-id').value;
    const resultsContainer = document.getElementById('compras-create-results');
    if (!usuarioId || !juegoId) { return showStatus(' Debes ingresar ID de Usuario y Juego.', true); }

    // El cuerpo de la petición debe coincidir con la entidad Compras
    const newCompra = {
        usuario: { id: usuarioId },
        juego: { id: juegoId }
    };

    resultsContainer.textContent = 'Creando compra...';
    try {
        const response = await fetch(COMPRAS_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json', },
            body: JSON.stringify(newCompra),
        });

        const data = await response.json();
        resultsContainer.textContent = JSON.stringify(data, null, 2);
        showStatus(' Compra creada con éxito.', false);
    } catch (error) {
        resultsContainer.textContent = ' Error: No se pudo crear la compra.';
        showStatus(' Error al crear compra.', true);
    }
}

// DELETE
async function deleteCompra() {
    const id = document.getElementById('compras-delete-id').value;
    const resultsContainer = document.getElementById('compras-delete-results');
    if (!id) { return showStatus(' Introduce un ID para borrar.', true); }

    resultsContainer.textContent = `Borrando compra ${id}...`;
    try {
        const response = await fetch(`${COMPRAS_URL}/${id}`, { method: 'DELETE', });

        if (response.status === 204 || response.ok) {
            resultsContainer.textContent = ` Compra ID ${id} borrada con éxito.`;
            showStatus(` Compra ID ${id} borrada con éxito.`, false);
        } else {
             resultsContainer.textContent = ` Error: ${response.status} - No se pudo borrar la compra.`;
             showStatus(` Error al borrar ID ${id}.`, true);
        }
    } catch (error) {
        resultsContainer.textContent = ' Error de conexión.';
        showStatus(' Error de conexión al borrar.', true);
    }
}


// =================================================================
//                     FUNCIONES PARA USUARIOS
// =================================================================

const USUARIOS_URL = `${API_ROOT}/usuarios`;

// GET ALL
async function getAllUsuarios() {
    const resultsContainer = document.getElementById('usuarios-list-results');
    resultsContainer.textContent = 'Cargando...';
    try {
        const response = await fetch(USUARIOS_URL);
        const data = await response.json();
        resultsContainer.textContent = JSON.stringify(data, null, 2);
        showStatus(' Usuarios listados con éxito.');
    } catch (error) {
        resultsContainer.textContent = 'Error al listar usuarios: ' + error.message;
        showStatus(' Error al listar usuarios.', true);
    }
}

// GET BY ID
async function getUsuarioById() {
    const id = document.getElementById('usuarios-find-id').value;
    const resultsContainer = document.getElementById('usuarios-find-results');
    if (!id) { return showStatus(' Introduce un ID para buscar.', true); }

    resultsContainer.textContent = `Buscando usuario ${id}...`;
    try {
        const response = await fetch(`${USUARIOS_URL}/${id}`);
        if (!response.ok) { throw new Error(`Error HTTP: ${response.status}`); }
        const data = await response.json();
        resultsContainer.textContent = JSON.stringify(data, null, 2);
        showStatus(` Usuario ID ${id} encontrado.`);
    } catch (error) {
        resultsContainer.textContent = ' Error: Usuario no encontrado o error de API.';
        showStatus(` Error al buscar ID ${id}.`, true);
    }
}

// POST
async function createUsuario() {
    const nome = document.getElementById('usuarios-post-nome').value;
    const email = document.getElementById('usuarios-post-email').value;
    const contraseña = document.getElementById('usuarios-post-senha').value;
    const idade = document.getElementById('usuarios-post-idade').value;
    const resultsContainer = document.getElementById('usuarios-create-results');

    if (!nome || !email || !contraseña || !idade) { return showStatus(' Faltan datos obligatorios del usuario.', true); }

    const newUsuario = {
        nome: nome,
        email: email,
        contraseña: contraseña,
        idade: idade
    };

    resultsContainer.textContent = 'Creando usuario...';
    try {
        const response = await fetch(USUARIOS_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json', },
            body: JSON.stringify(newUsuario),
        });

        const data = await response.json();
        resultsContainer.textContent = JSON.stringify(data, null, 2);
        showStatus(' Usuario creado con éxito.', false);
    } catch (error) {
        resultsContainer.textContent = ' Error: No se pudo crear el usuario.';
        showStatus(' Error al crear usuario.', true);
    }
}

// DELETE
async function deleteUsuario() {
    const id = document.getElementById('usuarios-delete-id').value;
    const resultsContainer = document.getElementById('usuarios-delete-results');
    if (!id) { return showStatus(' Introduce un ID para borrar.', true); }

    resultsContainer.textContent = `Borrando usuario ${id}...`;
    try {
        const response = await fetch(`${USUARIOS_URL}/${id}`, { method: 'DELETE', });

        if (response.status === 204 || response.ok) {
            resultsContainer.textContent = `Usuario ID ${id} borrado con éxito.`;
            showStatus(` Usuario ID ${id} borrado con éxito.`, false);
        } else {
             resultsContainer.textContent = ` Error: ${response.status} - No se pudo borrar el usuario.`;
             showStatus(` Error al borrar ID ${id}.`, true);
        }
    } catch (error) {
        resultsContainer.textContent = ' Error de conexión.';
        showStatus(' Error de conexión al borrar.', true);
    }
}

// =================================================================
//                      FUNCIONES PARA JUEGOS
// =================================================================

const JUEGOS_URL = `${API_ROOT}/juegos`;

// GET ALL
async function getAllJuegos() {
    const resultsContainer = document.getElementById('juegos-list-results');
    resultsContainer.textContent = 'Cargando...';
    try {
        const response = await fetch(JUEGOS_URL);
        const data = await response.json();
        resultsContainer.textContent = JSON.stringify(data, null, 2);
        showStatus(' Juegos listados con éxito.');
    } catch (error) {
        resultsContainer.textContent = ' Error al listar juegos: ' + error.message;
        showStatus(' Error al listar juegos.', true);
    }
}

// GET BY ID
async function getJuegoById() {
    const id = document.getElementById('juegos-find-id').value;
    const resultsContainer = document.getElementById('juegos-find-results');
    if (!id) { return showStatus(' Introduce un ID para buscar.', true); }

    resultsContainer.textContent = `Buscando juego ${id}...`;
    try {
        const response = await fetch(`${JUEGOS_URL}/${id}`);
        if (!response.ok) { throw new Error(`Error HTTP: ${response.status}`); }
        const data = await response.json();
        resultsContainer.textContent = JSON.stringify(data, null, 2);
        showStatus(` Juego ID ${id} encontrado.`);
    } catch (error) {
        resultsContainer.textContent = ' Error: Juego no encontrado o error de API.';
        showStatus(` Error al buscar ID ${id}.`, true);
    }
}

// POST
async function createJuego() {
    const nombre = document.getElementById('juegos-post-nombre').value;
    const categoria = document.getElementById('juegos-post-categoria').value;
    const precio = document.getElementById('juegos-post-precio').value;
    const resultsContainer = document.getElementById('juegos-create-results');
    const descripcion = document.getElementById('juegos-post-descripcion').value;
    const imagen = document.getElementById('juegos-post-imagen').value;

    if (!nombre || !categoria || !precio || !descripcion || !imagen) { return showStatus(' Faltan datos obligatorios del juego.', true); }

    // El cuerpo de la petición debe coincidir con la entidad Juego
    const newJuego = {
        nombre: nombre,
        categoria: categoria,
        precio: parseFloat(precio), // Asegura que el precio sea un número
        descripcion: descripcion,
        imagenUrl: imagen
    };

    resultsContainer.textContent = 'Creando juego...';
    try {
        const response = await fetch(JUEGOS_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json', },
            body: JSON.stringify(newJuego),
        });

        const data = await response.json();
        resultsContainer.textContent = JSON.stringify(data, null, 2);
        showStatus('✅ Juego creado con éxito.', false);
    } catch (error) {
        resultsContainer.textContent = ' Error: No se pudo crear el juego.';
        showStatus(' Error al crear juego.', true);
    }
}

// DELETE
async function deleteJuego() {
    const id = document.getElementById('juegos-delete-id').value;
    const resultsContainer = document.getElementById('juegos-delete-results');
    if (!id) { return showStatus(' Introduce un ID para borrar.', true); }

    resultsContainer.textContent = `Borrando juego ${id}...`;
    try {
        const response = await fetch(`${JUEGOS_URL}/${id}`, { method: 'DELETE', });

        if (response.status === 204 || response.ok) {
            resultsContainer.textContent = ` Juego ID ${id} borrado con éxito.`;
            showStatus(` Juego ID ${id} borrado con éxito.`, false);
        } else {
             resultsContainer.textContent = ` Error: ${response.status} - No se pudo borrar el juego.`;
             showStatus(` Error al borrar ID ${id}.`, true);
        }
    } catch (error) {
        resultsContainer.textContent = 'Error de conexión.';
        showStatus('Error de conexión al borrar.', true);
    }
}