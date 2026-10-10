export interface TipoManutencao {
  id: number;
  nome: string;
  periodicidadeKm: number | null;
  periodicidadeMeses: number | null;
  padrao: boolean;
}
