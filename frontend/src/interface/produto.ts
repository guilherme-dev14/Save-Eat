export interface Produto {
  nome: string
  descricao: string
  precoOriginal: number
  precoDesconto: number
  dataValidade: string
  quantidade: number
  status: string
  categoria: string
  idEmpresa: number
}

export interface ProdutoEdit extends Produto {
  id: number
}
