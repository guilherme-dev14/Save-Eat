export function aplicarMascaraCpf(cpf: string) {
  return cpf
    .replace(/\D/g, "")
    .replace(/(\d{3})(\d)/, "$1.$2")
    .replace(/(\d{3})(\d)/, "$1.$2")
    .replace(/(\d{3})(\d{1,2})$/, "$1-$2");
}

export function isCpfValido(cpf: string): boolean {
  cpf = cpf.replace(/\D/g, "");
  if (cpf.length !== 11 || /^(\d)\1+$/.test(cpf)) return false;

  const digito = (cpf: string, pos: number) => {
    const soma = cpf
      .split("")
      .slice(0, pos)
      .reduce((acc, num, idx) => acc + +num * (pos + 1 - idx), 0);
    const resto = (soma * 10) % 11;
    return resto === 10 ? 0 : resto;
  };

  return digito(cpf, 9) === +cpf[9] && digito(cpf, 10) === +cpf[10];
}

export function isCnpjValido(cnpj: string): boolean {
  cnpj = cnpj.replace(/[^\d]+/g, "");

  if (cnpj.length !== 14 || /^(\d)\1+$/.test(cnpj)) return false;

  const calcularDigito = (base: string, pesos: number[]) => {
    const soma = base
      .split("")
      .reduce((acc, dig, i) => acc + +dig * pesos[i], 0);
    const resto = soma % 11;
    return resto < 2 ? 0 : 11 - resto;
  };

  const base = cnpj.slice(0, 12);
  const digito1 = calcularDigito(base, [5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2]);
  const digito2 = calcularDigito(
    base + digito1,
    [6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2]
  );

  return cnpj === base + digito1 + "" + digito2;
}

export function aplicarMascaraCnpj(cnpj: string) {
  return cnpj
    .replace(/\D/g, "")
    .replace(/(\d{2})(\d)/, "$1.$2")
    .replace(/(\d{3})(\d)/, "$1.$2")
    .replace(/(\d{3})(\d)/, "$1/$2")
    .replace(/(\d{4})(\d{1,2})$/, "$1-$2");
}

export function isCepValido(cep: string): boolean {
  return /^\d{5}-\d{3}$/.test(cep)
}

export function aplicarMascaraCep(cep: string) {
  return cep.replace(/\D/g, "").replace(/(\d{5})(\d{1,3})$/, "$1-$2");
}

export function isEmailValido(email: string): boolean {
  const regex = /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/;
  return regex.test(email);
}

export function isSenhaForte(senha: string): boolean {
  return (
    senha.length >= 8 &&
    /[A-Za-z]/.test(senha) &&
    /\d/.test(senha) &&
    /[^A-Za-z0-9]/.test(senha)
  );
}

export function isCampoPreenchido(valor: string): boolean {
  return valor.trim().length > 0;
}
