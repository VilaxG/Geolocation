let table;
let contador = 0;
let map;
let marker;

window.addEventListener("load", () => {
    table = $('#ipTable').DataTable();

    getAllIp();

    let input = document.querySelector("#inpIp");
    input.addEventListener("keydown", (event) => {
        if (event.key === "Enter") { // Detecta la tecla Enter
            event.preventDefault();  // Evita el comportamiento por defecto si es necesario
            getGeolocation(input.value);
            input.value = '';
        }
    });
    document.querySelector("#btnIp").addEventListener("click", () => {
        getGeolocation(input.value);
        input.value = '';
    });

    initMap();
});

function initMap() {
    map = L.map('mapa', {
        center: [19.3909832, -99.3084239],
        zoom: 13
    });

    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);

}

async function getAllIp() {
    const allResponse = await fetch('/getAllIp', {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' }
    });

    if (!allResponse.ok) throw new Error(`Error al obtener todas las IPs: ${allResponse.status}`);
    const allData = await allResponse.json();
    table.clear().draw();
    allData.forEach(item => {
        table.row.add([
            item.id,
            item.ip,
            item.latitude,
            item.longitude,
            item.countryCode,
            item.countryName,
            item.phoneCode,
            item.continentCode,
            `<button class="bg-red-800 text-white py-1 px-2 w-full rounded-full" onclick = "print(${item.latitude},${item.longitude})">
                    <i class="fa-solid fa-pencil"></i>
                 </button>`,
            `<button class="bg-red-800 text-white py-1 px-2 w-full rounded-full" onclick = "removeIp(${item.id})">
                    <i class="fa-regular fa-trash-can"></i>
                 </button>`
        ]).draw();
    });
}

function print(lat, lng) {

    // Mover la vista del mapa
    map.setView([lat, lng], 13);

    // Si ya existe un marcador, lo movemos
    if (marker) {
        marker.setLatLng([lat, lng]);
    } else {
        // Crear marcador
        marker = L.marker([lat, lng]).addTo(map);
    }
}

async function getGeolocation(ip) {
    console.log(`Obteniendo coordenadas de ${ip}`);

    if (await findIp(ip)) {
        Swal.fire({
            title: "Atencion",
            text: `Esta direccion IP (${ip}) ya ha sido registrada`,
            icon: "warning"
        });
        return;
    }

    try {
        const geoResponse = await fetch('https://ip-location5.p.rapidapi.com/get_geo_info', {
            method: 'POST',
            headers: {
                'x-rapidapi-key': 'b2f72b162emshd0fb3d1a5f93e39p10fe84jsnb27826faaa37',
                'x-rapidapi-host': 'ip-location5.p.rapidapi.com',
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams({ ip })
        });

        if (!geoResponse.ok)
            throw new Error(`Error al obtener geolocalización: ${geoResponse.status}`);

        const geoData = await geoResponse.json();
        console.log("GeoData:", geoData);

        await saveIp(geoData);
        getAllIp();

    } catch (error) {
        console.error('Error en getGeolocation:', error);
    }
}

async function findIp(ip) {
    try {
        const response = await fetch(`/getIp?ip=${ip}`);

        if (!response.ok) return false;

        const data = await response.json();
        console.log("Respuesta:", data);

        return data ? true : false;

    } catch (error) {
        return false; // si la IP NO existe, el backend puede lanzar error
    }
}


async function saveIp(geoData, ip) {
    const dataIp = {
        ip: geoData.ip,
        latitude: geoData.latitude,
        longitude: geoData.longitude,
        countryCode: geoData.country.code,
        countryName: geoData.country.name,
        phoneCode: geoData.country["phone-code"],
        continentCode: geoData.continent.code
    };

    console.log('Datos geolocalización a guardar:', dataIp);

    const saveResponse = await fetch('/saveIp', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(dataIp)
    });

    if (!saveResponse.ok)
        throw new Error(`Error al guardar en backend: ${saveResponse.status}`);

    const savedId = await saveResponse.json();
    console.log('ID guardado en servidor:', savedId);
}


async function removeIp(id) {
    try {
        const url = `/removeIp?id=${id}`;

        const response = await fetch(url, {
            method: 'DELETE',
            headers: { 'Content-Type': 'application/json' }
        });

        if (!response.ok) throw new Error(`Error al eliminar la IP: ${response.status}`);

        const result = await response.json(); // tu endpoint devuelve un Integer
        console.log('IP eliminada, filas afectadas:', result);

        getAllIp();
    } catch (error) {
        console.error('Error al eliminar la IP:', error);
    }
}

