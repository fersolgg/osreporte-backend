// app.js - Lógica principal del dashboard

console.log('¡Dashboard OSReporte listo!');

// Gráfico de barras de ejemplo usando Chart.js y datos simulados
window.addEventListener('DOMContentLoaded', function() {
    const ctx = document.getElementById('chart-tickets-estado').getContext('2d');
    const labels = ['Abiertos', 'Cerrados', 'En Progreso'];
    const data = [12, 8, 5];
    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Tickets por Estado',
                data: data,
                backgroundColor: [
                    '#2e7d32', // Verde
                    '#c62828', // Rojo
                    '#ef6c00'  // Naranja
                ],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: { display: false },
                title: { display: false }
            },
            scales: {
                y: { beginAtZero: true }
            }
        }
    });

    // Gráfico de torta (pie chart) usando Chart.js y datos simulados
    const ctxPie = document.getElementById('chart-tickets-pie').getContext('2d');
    const labelsPie = ['Abierto', 'Cerrado', 'En Progreso'];
    const dataPie = [60, 30, 10]; // Porcentajes de ejemplo
    new Chart(ctxPie, {
        type: 'pie',
        data: {
            labels: labelsPie,
            datasets: [{
                data: dataPie,
                backgroundColor: [
                    '#2e7d32', // Verde
                    '#c62828', // Rojo
                    '#ef6c00'  // Naranja
                ]
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: { position: 'bottom' },
                title: { display: false }
            }
        }
    });

    // =============================
    // Llenar KPIs dinámicamente
    // =============================
    // Datos simulados para KPIs
    const kpiData = {
        totalTickets: 25,
        ticketsAbiertos: 12,
        ticketsCerrados: 8,
        ticketsEnProgreso: 5
    };
    // Asignar valores a los KPIs
    document.getElementById('kpi-total').textContent = kpiData.totalTickets;
    document.getElementById('kpi-abiertos').textContent = kpiData.ticketsAbiertos;
    document.getElementById('kpi-cerrados').textContent = kpiData.ticketsCerrados;
    document.getElementById('kpi-progreso').textContent = kpiData.ticketsEnProgreso;

    // ============================
   // Llenar tabla de agentes dinámicamente
    // =============================
    // Datos simulados para agentes
    const agentes = [
        { nombre: 'Juan Pérez', tickets: 7, estado: 'Activo' },
        { nombre: 'Ana López', tickets: 5, estado: 'Inactivo' },
        { nombre: 'Carlos Ruiz', tickets: 8, estado: 'Activo' },
        { nombre: 'María Torres', tickets: 5, estado: 'Activo' }
    ];
    const tbody = document.getElementById('tabla-agentes-body');
    tbody.innerHTML = '';
    agentes.forEach(agente => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${agente.nombre}</td>
            <td>${agente.tickets}</td>
            <td><span class="badge ${agente.estado === 'Activo' ? 'bg-success' : 'bg-secondary'}">${agente.estado}</span></td>
        `;
        tbody.appendChild(tr);
    });
});
