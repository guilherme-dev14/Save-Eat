export interface Empresa {
    nome: string;
    cnpj: string;
    email: string;
    localizacao: {
        logradouro: string;
        numero: number;
        bairro: string;
        cidade: string;
        cep: string;
        estado: string;
    };
}
