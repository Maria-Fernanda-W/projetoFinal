export interface Veiculo {
  id: number;
  nome: string;
  placa: string;
  marca: string;
  modelo: string;
  ano: number;
  quilometragemAtual: number;
}

export interface VeiculoCadastro {
  nome: string;
  placa: string;
  marca: string;
  modelo: string;
  ano: number;
  quilometragemInicial: number;
}

export interface VeiculoAlteracao {
  nome: string;
  placa: string;
  marca: string;
  modelo: string;
  ano: number;
}
