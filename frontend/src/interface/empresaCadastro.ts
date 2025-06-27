import { Empresa } from "./empresa";

export interface EmpresaCadastro extends Empresa {
    senha: string;
}
