document.addEventListener('DOMContentLoaded', function() {
    // Form submission handler
    const complaintForm = document.getElementById('complaintForm');
    if (complaintForm) {
        complaintForm.addEventListener('submit', async function(e) {
            e.preventDefault();
            
            const formData = new FormData(complaintForm);
            const response = await fetch('/api/complaints', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(Object.fromEntries(formData))
            });

            if (response.ok) {
                alert('Pengaduan berhasil dikirim!');
                complaintForm.reset();
                loadComplaints();
            } else {
                alert('Terjadi kesalahan saat mengirim pengaduan');
            }
        });
    }

    // Load complaints on page load
    loadComplaints();
    loadAnalysis();
});

async function loadComplaints() {
    const response = await fetch('/api/complaints');
    const complaints = await response.json();
    
    const container = document.getElementById('complaintList');
    if (container) {
        container.innerHTML = complaints.map(complaint => `
            <div class="complaint-card">
                <h3 class="complaint-title">${complaint.title}</h3>
                <div class="complaint-meta">
                    <span>Kategori: ${complaint.category}</span> | 
                    <span>Status: ${complaint.status}</span> | 
                    <span>${new Date(complaint.createdAt).toLocaleString()}</span>
                </div>
                <p>${complaint.description}</p>
            </div>
        `).join('');
    }
}

async function loadAnalysis() {
    try {
        const [categoriesRes, trendsRes] = await Promise.all([
            fetch('/api/analysis/categories'),
            fetch('/api/analysis/trends')
        ]);
        
        const categories = await categoriesRes.json();
        const trends = await trendsRes.json();
        
        renderChart('categoriesChart', categories, 'Kategori Pengaduan');
        renderChart('trendsChart', trends, 'Tren Pengaduan');
    } catch (error) {
        console.error('Error loading analysis:', error);
    }
}

function renderChart(chartId, data, title) {
    const ctx = document.getElementById(chartId)?.getContext('2d');
    if (!ctx) return;
    
    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: Object.keys(data),
            datasets: [{
                label: title,
                data: Object.values(data),
                backgroundColor: '#2c3e50',
                borderColor: '#1a252f',
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}
