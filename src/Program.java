import java.util.ArrayList;
import java.util.Scanner;
import java.util.Map;

public class Program {

    static DataManager data_manager = new DataManager();
    static UpdateProdutoList update_list = new UpdateProdutoList();
    static FileManager file_manager = new FileManager();
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        boolean exited = false;
        ArrayList<ProdutoInfo> produto_list = file_manager.convertToList();


        while (!exited) {
            System.out.print("\n[Cp - Criar produto] [Ep - Editar produto] [Dp - Deletar produto] [Ex - Exibir List] [Pp - Procurar produto] [Sv - Salvar lista] [Sr - Sair]\nDigite sua ação: ");
            String action = input.next();

            switch (action.toLowerCase()) {

                case "cp":
                    System.out.println();
                    String nome_produto, fornecedor, cod_barras;
                    float valor_custo, valor_venda;
                    int qtd_estoque;

                    System.out.print("Digite o Nome do produto: ");
                    nome_produto = input.next();
                    System.out.print("Digite o Fornecedor: ");
                    fornecedor = input.next();
                    System.out.print("Digite o Valor de custo: ");
                    valor_custo = input.nextFloat();
                    System.out.print("Digite o Valor de venda: ");
                    valor_venda = input.nextFloat();
                    System.out.print("Digite a Quantidade no estoque: ");
                    qtd_estoque = input.nextInt();
                    System.out.print("Digite o código de barras: ");
                    cod_barras = input.next();

                    produto_list.add(update_list.createProduto(nome_produto, fornecedor, valor_custo, valor_venda, qtd_estoque, cod_barras));
                    break;

                case "ep":
                    // precisa verificar se existe algo pra editar
                    System.out.println();
                    String field;
                    int cod_produto = -1;
                    byte col = 0;
                    boolean invalid_field = true, invalid_code = true;

                    while (invalid_code) {
                        System.out.print("Digite o código do produto: ");
                        cod_produto = input.nextInt();
                        if (cod_produto > produto_list.size() - 1) {
                            System.out.println("código inválido");
                        } else {
                            invalid_code = false;
                        }

                    }

                    System.out.printf("\n[Cod.] %d | [1 - nome do prod.] %s | [2 - fornecedor] %s | [3 - val custo] %f | [4 - val venda] %f | [5 - qtd. estoque] %d | [6 - cod. barras] %s \n",
                            cod_produto,
                            data_manager.GetSearchedList(produto_list, cod_produto).getNomeProduto(),
                            data_manager.GetSearchedList(produto_list, cod_produto).getFornecedor(),
                            data_manager.GetSearchedList(produto_list, cod_produto).getValorCusto(),
                            data_manager.GetSearchedList(produto_list, cod_produto).getValorVenda(),
                            data_manager.GetSearchedList(produto_list, cod_produto).getQtdEstoque(),
                            data_manager.GetSearchedList(produto_list, cod_produto).getCodBarra());


                    while (invalid_field) {

                        System.out.print("Digite o numero do campo: ");
                        col = input.nextByte();

                        invalid_field = switch (col) {
                            case 1 -> {
                                System.out.print("\nDigite o novo nome do produto: ");
                                yield false;
                            }
                            case 2 -> {
                                System.out.print("\nDigite o novo fornecedor: ");
                                yield false;
                            }
                            case 3 -> {
                                System.out.print("\nDigite o novo valor de custo: ");
                                yield false;
                            }
                            case 4 -> {
                                System.out.print("\nDigite o novo valor de venda: ");
                                yield false;
                            }
                            case 5 -> {
                                System.out.print("\nDigite a nova quantidade: ");
                                yield false;
                            }
                            case 6 -> {
                                System.out.print("\nDigite o novo código de barras: ");
                                yield false;
                            }
                            default -> {
                                System.out.println("\nnumero de campo inválido!");
                                yield true;
                            }
                        };
                    }

                    field = input.next();
                    update_list.editList(produto_list, cod_produto, col, field);

                    break;

                case "dp":

                    break;

                case "ex":
                    for (int i = 0; i < produto_list.size(); i++) {

                        System.out.printf(" %d | %s | %s | %f | %f | %d | %s \n",
                                i,
                                produto_list.get(i).getNomeProduto(),
                                produto_list.get(i).getFornecedor(),
                                produto_list.get(i).getValorCusto(),
                                produto_list.get(i).getValorVenda(),
                                produto_list.get(i).getQtdEstoque(),
                                produto_list.get(i).getCodBarra());
                    }
                    break;

                case "pp":
                    System.out.print("Procurar: ");
                    String procura = input.next();
                    Map<Integer, ProdutoInfo> searched_list = data_manager.GetSearchedList(produto_list, procura);
                    System.out.println();

                    if (searched_list != null) {
                        for (Integer key : searched_list.keySet()) {

                            System.out.printf(" %s | %s | %s | %f | %f | %d | %s \n",
                                    key,
                                    searched_list.get(key).getNomeProduto(),
                                    searched_list.get(key).getFornecedor(),
                                    searched_list.get(key).getValorCusto(),
                                    searched_list.get(key).getValorVenda(),
                                    searched_list.get(key).getQtdEstoque(),
                                    searched_list.get(key).getCodBarra());
                        }
                    }
                    break;

                case "sv":
                    file_manager.saveOnFile(produto_list);
                    break;

                case "sr":
                    exited = true;
                    break;

                default:
                    System.out.println("ação inválida!");
                    break;
            }
        }
    }
}
