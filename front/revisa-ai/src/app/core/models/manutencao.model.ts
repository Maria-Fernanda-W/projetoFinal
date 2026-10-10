export interface Manutencao {
  id: number;
  veiculoId: number;
  veiculoNome: string;
  tipoManutencaoNome: string;
  nome: string;
  dataRealizacao: string;
  valor: number;
  quilometragem: number;
  localRealizado: string | null;
  observacoes: string | null;
}

export interface ManutencaoCadastro {
  veiculoId: number;
  tipoManutencaoId: number;
  nome: string;
  dataRealizacao: string;
  valor: number;
  quilometragem?: number;
  localRealizado?: string;
  observacoes?: string;
}

export interface ManutencaoAlteracao {
  tipoManutencaoId: number;
  nome: string;
  dataRealizacao: string;
  valor: number;
  localRealizado?: string;
  observacoes?: string;
}
