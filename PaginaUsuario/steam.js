const API_ROOT = 'https://hibernateswagger-api-latest.onrender.com/api';
// =============================================
// ======== LOGIN MODAL + AUTENTICACIÓN ========
// =============================================

const userBtn = document.getElementById("user-account");
const loginModal = document.getElementById("login-modal");
const closeLogin = document.getElementById("close-login");
const loginSubmit = document.getElementById("login-submit");
const loginError = document.getElementById("login-error");
//aqui guardaremos el usuario para poder saber su recomendacion de juego
let usuarioActual = null;

// Abrir pestaña de Login
userBtn.addEventListener("click", () => {
  loginModal.classList.remove("hidden");
});

// Cerrar pestaña de Login
closeLogin.addEventListener("click", () => {
  loginModal.classList.add("hidden");
  loginError.textContent = "";
});

// Login
loginSubmit.addEventListener("click", async () => {
  const username = document.getElementById("login-username").value.trim();
  const password = document.getElementById("login-password").value.trim();

  if (!username || !password) {
    loginError.textContent = "Introduce usuario y contraseña.";
    return;
  }

  try {
    //recoge todos los usuarios
    const response = await fetch(`${API_ROOT}/usuarios`);
    const usuarios = await response.json();

    // Buscar usuario válido
    // Se puede hacer así pero es ineficiente ya que siempre que quieras hacer log tienes que cargar a todos los usuarios
    // esto se puede mejorar creando dentro de usuarios un log
    const usuario = usuarios.find(
      u => (u.nome === username || u.email === username) && u.contraseña === password
    );

    if (usuario) {
      usuarioActual = usuario;
      loginModal.classList.add("hidden");
      userBtn.textContent = `Hola, ${usuario.nome}`;
      mostrarRecomendaciones(usuario);
    } else {
      loginError.textContent = "Usuario o contraseña incorrectos.";
    }
  } catch (err) {
    console.error(err);
    loginError.textContent = "Error al conectar con la API.";
  }
});

// =============================================
// ============ SISTEMA DE JUEGOS ==============
// =============================================

async function getJuegos() {
  const res = await fetch(`${API_ROOT}/juegos`);
  return await res.json();
}

async function getComprasByUsuario(idUsuario) {
  const res = await fetch(`${API_ROOT}/compras`);
  const compras = await res.json();
  return compras.filter(c => c.usuario.id === idUsuario);//filtra las compras seleccionando segun usuario
}

// =============================================
// ====== RECOMENDACIONES PERSONALIZADAS =======
// =============================================

async function mostrarRecomendaciones(usuario) {
    //recogemos juegos y compras
  const juegos = await getJuegos();
  const compras = await getComprasByUsuario(usuario.id);

    //filtramos la informacion para que solo aparezca la id de los juegos u su categoria
  const juegosCompradosIds = compras.map(c => c.juego.id);
  const generosFav = compras.map(c => c.juego.categoria);

    //filtramos los juegos para que de los de la categoria gustada no aparezcan juegos que ya tenemos
  const recomendados = juegos.filter(
    j => generosFav.includes(j.categoria) && !juegosCompradosIds.includes(j.id)
  );

    //si no hemos hecho ninguna compra, no se nos muestran recomendados
  if (recomendados.length === 0) return;

    //esto es por si se queria crear un contenedor desde el html
    //como no hay, lo creo desde aqui
  let contenedor = document.querySelector(".recomendados");
  if (!contenedor) {
    contenedor = document.createElement("div");
    contenedor.classList.add("recomendados");
    const novedades = document.querySelector(".JuegosNovedad");
    novedades.insertAdjacentElement("afterend", contenedor);
  }

  contenedor.innerHTML = `
    <h2 style="text-align:center; color:#66c0f4; margin-top:3rem;">Recomendados para ti</h2>
    <div style="position:relative; display:flex; justify-content:center; align-items:center;">
      <button id="prevRecomendado" style="position:absolute; left:0; background:#66c0f4; color:#1b2838; border:none; border-radius:50%; width:50px; height:50px; cursor:pointer;">&#10094;</button>
      <div class="grid" style="display:flex; justify-content:center; gap:20px; padding:2rem;"></div>
      <button id="nextRecomendado" style="position:absolute; right:0; background:#66c0f4; color:#1b2838; border:none; border-radius:50%; width:50px; height:50px; cursor:pointer;">&#10095;</button>
    </div>
  `;

  const grid = contenedor.querySelector(".grid");
  const btnPrev = document.getElementById("prevRecomendado");
  const btnNext = document.getElementById("nextRecomendado");

  let index = 0;
  //que solo haya 3 contenedore en pantalla
  const mostrar = 3;

  function mostrarSlice() {
    grid.innerHTML = '';
    for (let i = 0; i < mostrar; i++) {
      const juego = recomendados[(index + i) % recomendados.length];
      grid.innerHTML += `
        <div style="background:#2a475e; border-radius:10px; padding:10px; text-align:center; width:200px;">
          <img src="${juego.imagenUrl}" alt="${juego.nombre}" style="width:100%; height:250px; object-fit:cover; border-radius:8px;">
          <p style="margin-top:10px; color:#c7d5e0;">${juego.nombre}</p>
        </div>
      `;
    }
  }

  mostrarSlice();

    /**
    aqui son las funciones para swapear a los siguientes juegos que se nos recomienda,
    si clickamos a las flechas o automaticamente cada 10s
    */
  btnNext.addEventListener("click", () => {
    index = (index + 1) % recomendados.length; // Rotación circular de 1 en 1
    mostrarSlice();
  });

  btnPrev.addEventListener("click", () => {
    index = (index - 1 + recomendados.length) % recomendados.length; // Rotación circular hacia atrás
    mostrarSlice();
  });

  setInterval(() => {
    index = (index + 1) % recomendados.length;
    mostrarSlice();
  }, 10000);
}

// =============================================
// ================== Peluches  ================
// =============================================

function iniciarCarruselPeluches() {
  const peluchesContainer = document.getElementById("Peluches");
  const peluches = peluchesContainer.querySelectorAll(".peluche");
  const btnPrev = document.getElementById("btnPrev");
  const btnNext = document.getElementById("btnNext");

  if (!peluches.length) return;

  let index = 0;

  // Añadimos clase para controlarlas por CSS
  peluches.forEach(p => p.classList.add("peluche"));
  peluches[index].classList.add("active");

  function mostrarPeluche(i) {
    peluches.forEach((p, idx) => {
      p.classList.remove("active");
      if (idx === i) p.classList.add("active");
    });
  }

  btnNext.addEventListener("click", () => {
    index = (index + 1) % peluches.length;
    mostrarPeluche(index);
  });

  btnPrev.addEventListener("click", () => {
    index = (index - 1 + peluches.length) % peluches.length;
    mostrarPeluche(index);
  });

  // Cambio automático cada 6 segundos
  setInterval(() => {
    index = (index + 1) % peluches.length;
    mostrarPeluche(index);
  }, 6000);
}

document.addEventListener("DOMContentLoaded", iniciarCarruselPeluches);


// =============================================
// ============ NOVEDADES AUTOMÁTICAS ==========
// =============================================
function iniciarNovedades() {
  const novedades = [
    {
      img: "ImagenesUsuario/hsr.jpg",
      alt: "Hsr",
      texto: "Adéntrate a una nueva aventura y disfruta de la nueva versión 5.6 ya disponible."
    },
    {
      img: "ImagenesUsuario/hades.jpg",
      alt: "Hades",
      texto: "Nuevo contenido y mucho más."
    },
    {
      img: "ImagenesUsuario/minecraft.jpg",
      alt: "Minecraft",
      texto: "¿Alguien ha dicho capibaras??"
    },
    {
      img: "ImagenesUsuario/limbus.jpg",
      alt: "Limbus Company",
      texto: "Answer me Jia Bao."
    },
    {
      img: "ImagenesUsuario/doki.jpg",
      alt: "Doki Doki",
      texto: "Tip del día: nunca te olvides de guardar tu partida."
    }
  ];

  const contenedor = document.querySelector(".JuegosNovedad");
  if (!contenedor) return;

  let index = 0;

  function mostrarNovedad() {
    const novedad = novedades[index];
    contenedor.innerHTML = `
      <div class="Tarjeta slide-in">
        <img src="${novedad.img}" alt="${novedad.alt}">
        <p>${novedad.texto}</p>
      </div>
    `;
  }

  mostrarNovedad();

  // Cambia automáticamente cada 10 segundos
  setInterval(() => {
    index = (index + 1) % novedades.length;
    mostrarNovedad();
  }, 5000);
}

document.addEventListener("DOMContentLoaded", () => {
  iniciarCarruselPeluches();
  iniciarNovedades();
});

// =============================================
// =============== CHATBOT INTELIGENTE ===============
// =============================================

// Base URL ya definida: const API_ROOT = 'https://hibernateswagger-api-latest.onrender.com/api';
const API_BASE_URL = 'http://localhost:8081'; // ⚠️ ASUME QUE EL BACKEND CORRE EN LOCALHOST:8081
const API_CHAT_URL = API_BASE_URL + '/api/chat';
const API_JUEGOS_URL = API_BASE_URL + '/juegos'; // Endpoint de inserción

// Elementos del Chatbot (Asegúrate de que sus IDs coincidan con tu HTML/CSS)
const chatWindow = document.getElementById('chatbot-window');
const toggleButton = document.getElementById('chatbot-toggle-button');
const messagesArea = document.getElementById('chatbot-messages');
const inputField = document.getElementById('chatbot-input');
const sendButton = document.getElementById('chatbot-send-button');

let conversationState = 'initial';
let newGameData = {}; // Objeto para almacenar datos del juego a insertar

const AUTO_ACTIVATE_DELAY = 15000; // 15 segundos
let autoActivateTimeout;

// Función auxiliar para alternar la visibilidad
function toggleChatbot() {
    const isHidden = chatWindow.style.display === 'none' || chatWindow.style.display === '';
    chatWindow.style.display = isHidden ? 'flex' : 'none';
    toggleButton.textContent = isHidden ? '✖️' : '💬';

    // Si se abre, cancela la activación automática
    clearTimeout(autoActivateTimeout);
}

// Función auxiliar para añadir mensajes al chat
function appendMessage(text, type) {
    const messageDiv = document.createElement('div');
    messageDiv.classList.add('message', type);
    messageDiv.textContent = text;
    messagesArea.appendChild(messageDiv);
    messagesArea.scrollTop = messagesArea.scrollHeight;
}

// ------------------------------------
// LÓGICA DE COMUNICACIÓN CON EL BACKEND
// ------------------------------------

/**
 * Llama al endpoint inteligente /api/chat
 */
async function getAiResponse(prompt) {
    try {
        const response = await fetch(API_CHAT_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ prompt: prompt })
        });

        if (!response.ok) {
            throw new Error(`Error ${response.status}: El servicio experto no respondió.`);
        }

        const data = await response.json();
        appendMessage(data.text, 'bot-message');
    } catch (error) {
        console.error('Error al obtener respuesta de IA:', error);
        appendMessage("Disculpe. Hemos experimentado un fallo de conexión con la IA. Por favor, inténtelo de nuevo más tarde.", 'bot-message');
    }
}

/**
 * Llama al endpoint transaccional /juegos
 */
async function insertGameToDatabase(gameData) {
    try {
        const response = await fetch(API_JUEGOS_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(gameData)
        });

        if (!response.ok) {
            throw new Error(`Error ${response.status}: La inserción falló en el microservicio.`);
        }
        const data = await response.json();
        appendMessage(`✅ Notificación: El juego **${data.nombre || gameData.nombre}** ha sido registrado con éxito. Gracias por su contribución.`, 'bot-message');
    } catch (error) {
        console.error('Error al insertar el juego:', error);
        appendMessage(`❌ Ha ocurrido un error crítico al registrar el título. Por favor, revise el servidor.`, 'bot-message');
    }
}

// ------------------------------------
// LÓGICA CENTRAL DEL CHATBOT
// ------------------------------------

async function processConversation(inputLower, originalInput) {
    let botResponse = '';

    // 1. MODO TRANSACCIONAL (INSERCIÓN DE JUEGOS)
    if (conversationState !== 'initial') {

        if (conversationState === 'awaiting_name') {
            newGameData.nombre = originalInput.toUpperCase();
            botResponse = `Estimado usuario, el juego es: "${newGameData.nombre}". ¿Podría indicar la **plataforma** principal?`;
            conversationState = 'awaiting_platform';

        } else if (conversationState === 'awaiting_platform') {
            newGameData.plataforma = originalInput.toUpperCase();
            botResponse = `Agradezco la información sobre ${newGameData.plataforma}. Finalmente, requerimos una **breve descripción** o el género del título.`;
            conversationState = 'awaiting_description';

        } else if (conversationState === 'awaiting_description') {
            newGameData.descripcion = originalInput;
            appendMessage("Procederemos con la inserción del juego. Un momento, por favor...", 'bot-message');

            await insertGameToDatabase(newGameData);
            conversationState = 'initial'; // Reinicia el estado

        }

    // 2. MODO INTELIGENTE (IA) O INICIO DE TRANSACCIÓN
    } else {

        // Comprueba palabras clave para iniciar la inserción
        if (inputLower.includes("insertar") || inputLower.includes("añadir juego") || inputLower.includes("recomendar")) {
            botResponse = "Entendido. Si desea recomendar un título para nuestra base de datos, por favor, comience indicando el **nombre** completo del juego.";
            conversationState = 'awaiting_name';

        } else {
            // Cualquier otra pregunta va al microservicio de IA
            appendMessage("Consultando a nuestro sistema experto. Aguarde, por favor...", 'bot-message');
            await getAiResponse(originalInput);
            return;
        }
    }

    if (botResponse) {
        appendMessage(botResponse, 'bot-message');
    }
}

function handleUserInput() {
    const userText = inputField.value.trim();
    if (userText === "") return;

    appendMessage(userText, 'user-message');
    inputField.value = '';

    setTimeout(() => {
        processConversation(userText.toLowerCase(), userText);
    }, 500);
}

// ------------------------------------
// INICIALIZACIÓN Y EVENTOS
// ------------------------------------

// Manejadores para enviar mensajes
sendButton.addEventListener('click', handleUserInput);
inputField.addEventListener('keypress', (e) => {
    if (e.key === 'Enter') {
        handleUserInput();
    }
});

// Manejador para el botón de abrir/cerrar
toggleButton.addEventListener('click', toggleChatbot);

// Configuración de Activación Automática (15s)
document.addEventListener("DOMContentLoaded", () => {
    // Esto se ejecutará una vez que la página esté completamente cargada
    chatWindow.style.display = 'none'; // Asegura que esté oculto al inicio
    toggleButton.textContent = '💬';

    // Inicia el temporizador de 15 segundos
    autoActivateTimeout = setTimeout(() => {
        if (chatWindow.style.display === 'none' || chatWindow.style.display === '') {
            toggleChatbot();
            appendMessage("Estimado usuario, parece que tiene alguna consulta. Estamos a su disposición.", 'bot-message');
        }
    }, AUTO_ACTIVATE_DELAY);

    // Llamadas a tus funciones existentes de DOMContentLoaded
    iniciarCarruselPeluches();
    iniciarNovedades();
});

// Nota: Hemos movido las llamadas de tus funciones existentes de DOMContentLoaded
// dentro del nuevo listener de inicialización para mantener el orden.