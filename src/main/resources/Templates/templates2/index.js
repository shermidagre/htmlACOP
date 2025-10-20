document.getElementById('formLibro').addEventListener('submit', function(e) {
    e.preventDefault(); // Evita que el formulario se envíe de forma tradicional

    const titulo = document.getElementById('titulo').value;
    const autor = document.getElementById('autor').value;

    const libro = {
        titulo: titulo,
        autor: autor
    };

    fetch('/api/libros/crear', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(libro)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        console.log('Libro creado:', data);
        document.getElementById('mensaje').className = 'success';
        document.getElementById('mensaje').innerText = 'Libro creado exitosamente';
        document.getElementById('formLibro').reset(); // Limpia el formulario
    })
    .catch(error => {
        console.error('Error:', error);
        document.getElementById('mensaje').className = 'error';
        document.getElementById('mensaje').innerText = 'Error al crear el libro';
    });
});