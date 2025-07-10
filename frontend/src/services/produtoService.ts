import type { Produto } from '@/interface/produto'

const API = 'http://localhost:8080'

export async function listarDestaques(): Promise<Produto[]> {
  const res = await fetch(`${API}/produtos/destaques`)

  if (!res.ok) {
    throw new Error('Erro ao buscar produtos em destaque')
  }

  const data = await res.json()
  return data as Produto[]
}

export async function listarTodos(): Promise<Produto[]> {
  const res = await fetch(`${API}/produtos/todos`)

  if (!res.ok) {
    throw new Error('Erro ao buscar produtos em destaque')
  }
  const data = await res.json()
  return data as Produto[]
}

export async function buscarPorId(id: number): Promise<Produto> {
  const res = await fetch(`${API}/produtos/${id}`)

  if (!res.ok) {
    throw new Error('Erro ao buscar produto')
  }

  const data = await res.json()
  return data as Produto
}

export async function listarForaValidade(): Promise<Produto[]> {
  const res = await fetch(`${API}/produtos/ForaValidade`)

  if (!res.ok) {
    throw new Error('Erro ao buscar produtos fora de validade')
  }

  const data = await res.json()
  return data as Produto[]
}
export async function listarTodosDaEmpresa(id: number): Promise<Produto[]> {
  const res = await fetch(`${API}/produtos/Empresa/Todos?idEmpresa=${id}`)
  if (!res.ok) {
    throw new Error('Erro ao buscar produtos da empresa')
  }
  const data = await res.json()
  return data as Produto[];
}

export async function listarVencidosEmpresa(id: number): Promise<Produto[]> {
  const res = await fetch(`${API}/produtos/Empresa/Vencidos?idEmpresa=${id}`)
  if (!res.ok) {
    throw new Error('Erro ao buscar produtos vencidos da empresa')
  }
  const data = await res.json()
  return data as Produto[];
}


export async function listarProximosValidade(): Promise<Produto[]> {
  const res = await fetch(`${API}/produtos/ProximoValidade`)

  if (!res.ok) {
    throw new Error('Erro ao buscar produtos próximos da validade')
  }

  const data = await res.json()
  return data as Produto[]
}
export async function deletarProduto(id: number): Promise<void> {

  const res = await fetch(`${API}/produtos/${id}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
    },
  });


  if (!res.ok) {
    throw new Error('Erro ao deletar o Produto');
  }
}