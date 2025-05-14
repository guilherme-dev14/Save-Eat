export function saveAuthData(token: string, tipoUsuario: string, email: string) {
    localStorage.setItem('token', token);
    localStorage.setItem('tipoUsuario', tipoUsuario);
    localStorage.setItem('email', email);
}

export function getAuthToken(): string | null {
    return localStorage.getItem('token');
}

export function getTipoUsuario(): string | null {
    return localStorage.getItem('tipoUsuario');
}

export function isAuthenticated(): boolean {
    return !!localStorage.getItem('token');
}

export function logout() {
    localStorage.clear();
}
