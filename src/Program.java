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

                    if (produto_list.isEmpty()) {
                        System.out.println("A lista está vazia! não há nada para editar");
                    } else {
                        update_list.editProduto(produto_list);
                    }

                    break;

                case "dp":
                    data_manager.exibirLista(produto_list);
                    update_list.deleteProduto(produto_list);
                    break;

                case "ex":
                    data_manager.exibirLista(produto_list);
                    break;

                case "pp":

                    System.out.print("Procurar [Digite 0 para cancelar]: ");
                    String procura = input.next();

                    if (!procura.equalsIgnoreCase("0")) {

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
                    }
                    break;

                case "sv":
                    file_manager.saveOnFile(produto_list);
                    break;

                case "sr":
                    System.out.print("Deseja salvar antes de sair? [s/n]: ");
                    String resposta = input.next();
                    if (resposta.equalsIgnoreCase("s")) file_manager.saveOnFile(produto_list);
                    exited = true;
                    break;

                default:
                    System.out.println("ação inválida!");
                    break;
            }
        }
    }
}
