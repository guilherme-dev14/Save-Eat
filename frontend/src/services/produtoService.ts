import type { Produto, ProdutoEdit } from '@/interface/produto'

const API = 'http://localhost:8080'

export async function listarDestaques(): Promise<ProdutoEdit[]> {
  const res = await fetch(`${API}/produtos/destaques`)

  if (!res.ok) {
    throw new Error('Erro ao buscar produtos em destaque')
  }

  const data = await res.json()
  return data as ProdutoEdit[]
}

export async function listarTodos(): Promise<ProdutoEdit[]> {
  const res = await fetch(`${API}/produtos/todos`)

  if (!res.ok) {
    throw new Error('Erro ao buscar produtos em destaque')
  }
  const data = await res.json()
  return data as ProdutoEdit[]
}

export async function buscarPorId(id: number): Promise<ProdutoEdit> {
  const res = await fetch(`${API}/produtos/${id}`)

  if (!res.ok) {
    throw new Error('Erro ao buscar produto')
  }

  const data = await res.json()
  return data as ProdutoEdit
}

export async function listarForaValidade(): Promise<ProdutoEdit[]> {
  const res = await fetch(`${API}/produtos/ForaValidade`)

  if (!res.ok) {
    throw new Error('Erro ao buscar produtos fora de validade')
  }

  const data = await res.json()
  return data as ProdutoEdit[]
}
export async function listarTodosDaEmpresa(id: number): Promise<ProdutoEdit[]> {
  const res = await fetch(`${API}/produtos/Empresa/Todos?idEmpresa=${id}`)
  if (!res.ok) {
    throw new Error('Erro ao buscar produtos da empresa')
  }
  const data = await res.json()
  return data as ProdutoEdit[];
}

export async function listarVencidosEmpresa(id: number): Promise<ProdutoEdit[]> {
  const res = await fetch(`${API}/produtos/Empresa/Vencidos?idEmpresa=${id}`)
  if (!res.ok) {
    throw new Error('Erro ao buscar produtos vencidos da empresa')
  }
  const data = await res.json()
  return data as ProdutoEdit[];
}


export async function listarProximosValidade(): Promise<ProdutoEdit[]> {
  const res = await fetch(`${API}/produtos/ProximoValidade`)

  if (!res.ok) {
    throw new Error('Erro ao buscar produtos próximos da validade')
  }

  const data = await res.json()
  return data as ProdutoEdit[]
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

export async function adicionar(produto: Produto): Promise<ProdutoEdit> {
  const res = await fetch(`${API}/produtos`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(produto),
  });

  if (!res.ok) {
    throw new Error('Erro ao adicionar o produto');
  }

  const data = await res.json();
  return data as ProdutoEdit;
}