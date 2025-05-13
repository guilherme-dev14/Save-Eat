const API_URL = 'http://localhost:8080/auth';

export async function login(email: string, senha: string) {
    const response = await fetch(`${API_URL}/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ email, senha })
    });
    if (!response.ok) {
        throw new Error('Credenciais inválidas');
    }

    return response.json(); 
}