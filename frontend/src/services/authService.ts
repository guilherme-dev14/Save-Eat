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

export async function cadastrarConsumidor(payload: {
    nome: string;
    cpf: string;
    email: string;
    senha: string;
    }) {
    const response = await fetch(`${API_URL}/cadastrar-consumidor`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    });

    if (!response.ok) {
        throw new Error('Erro ao cadastrar consumidor');
    }

    return response.text();
}

export async function cadastrarEmpresa(payload: {
    nome: string;
    cnpj: string;
    email: string;
    senha: string;
    logradouro: string;
    numero: number;
    bairro: string;
    cidade: string;
    cep: string;
    estado: string;
    }) {
    const response = await fetch(`${API_URL}/cadastrar-empresa`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    });

    if (!response.ok) {
        throw new Error('Erro ao cadastrar empresa');
    }

    return response.text();
}