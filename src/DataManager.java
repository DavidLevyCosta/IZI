import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class DataManager {


	public Map<Integer, ProdutoInfo> GetSearchedList(ArrayList<ProdutoInfo> produto_list, String field) {
		
		Map<Integer, ProdutoInfo> found_list = new HashMap<>();
			
		try {			
			for (int i = 0; i < produto_list.size(); i++) {
				
				if (matches(produto_list.get(i), field)) {
					found_list.put(i, produto_list.get(i));
				}
			}
			
			if (!found_list.isEmpty()) return found_list;
			else {
				System.out.print("Não foram encontrados resultados para a pesquisa!");
				return null;
			}
			
		} catch (Exception e) {
			System.out.print("Não foi possivel ler a lista");
			return null;
		}
	}

	public ProdutoInfo GetSearchedList(ArrayList<ProdutoInfo> produto_list, int cod_produto) {
		return produto_list.get(cod_produto);
	}

	public void exibirLista(ArrayList<ProdutoInfo> produto_list) {

		if (produto_list.isEmpty()) {
			System.out.println("A lista está vazia!");
		} else {
			for (int i = 0; i < produto_list.size(); i++) {

				System.out.printf(" %d | %s | %s | %.2f | %.2f | %d | %s \n",
						i + 1,
						produto_list.get(i).getNomeProduto(),
						produto_list.get(i).getFornecedor(),
						produto_list.get(i).getValorCusto(),
						produto_list.get(i).getValorVenda(),
						produto_list.get(i).getQtdEstoque(),
						produto_list.get(i).getCodBarra());
			}
		}
	}

	private boolean matches(ProdutoInfo p, String field) {
		
	    if (field.trim().equalsIgnoreCase(p.getNomeProduto().trim())) {
	        return true;
	    }
	    if (field.trim().equalsIgnoreCase(p.getFornecedor().trim())) {
	        return true;
	    }
	    if (field.trim().equalsIgnoreCase(p.getCodBarra().trim())) {
	        return true;
	    }
	    try {
	        float fieldValueFloat= Float.parseFloat(field.trim());
	        if (fieldValueFloat == p.getValorCusto() || fieldValueFloat == p.getValorVenda()) {
	            return true;
	        }
	        int fieldValueInt = Integer.parseInt(field.trim());
	        if (fieldValueInt == p.getQtdEstoque()) {
	            return true;
	        }
	    } catch (NumberFormatException e) {
	        // ignora e continua
	    }
	    return false;
	}
	
}

class UpdateProdutoList extends DataManager {
	Scanner input = new Scanner(System.in);
	public UpdateProdutoList() {
		super();
	}
	

	 public ProdutoInfo createProduto(String produto_nome, String fornecedor, float valor_custo, float valor_venda, int qnt_estoque, String cod_barras) {


		 ProdutoInfo new_produto = new ProdutoInfo();
		   
		 new_produto.setNomeProduto(produto_nome);
		 new_produto.setFornecedor(fornecedor);
		 new_produto.setValorCusto(valor_custo);
		 new_produto.setValorVenda(valor_venda);
		 new_produto.setQtdEstoque(qnt_estoque); 
		 new_produto.setCodBarra(cod_barras);
		 
		 return new_produto;	 
	 }

	 public void deleteProduto(ArrayList<ProdutoInfo> produto_list) {

		 System.out.println("Digite o código do produto que deseja deletar [Digite 0 para cancelar]");
		 int cod = input.nextInt();
		 if (cod != 0) {
			 produto_list.remove(cod - 1);
		 }
	 }
	
	public void editProduto(ArrayList<ProdutoInfo> produto_list) {

		exibirLista(produto_list);
		System.out.println();

		String field = "";
		int cod_produto = -1;
		byte col = 0;
		boolean invalid_field = true, invalid_code = true;

		while (invalid_code) {
			System.out.print("Digite o código do produto [Digite 0 para cancelar]: ");
			try {
				cod_produto = input.nextInt();
				if (cod_produto > produto_list.size() || cod_produto < 0) {
					System.out.println("código inválido");
				} else {
					invalid_code = false;
				}
			} catch (Exception e) {
				System.out.println("o valor digitado é inválido!");
				input.nextLine(); // descarta a entrada inválida
			}
		}

		if (cod_produto != 0) {

			System.out.printf("\n[Cod.] %d | [1 - nome do prod.] %s | [2 - fornecedor] %s | [3 - val custo] %.2f | [4 - val venda] %.2f | [5 - qtd. estoque] %d | [6 - cod. barras] %s \n",
					cod_produto,
					GetSearchedList(produto_list, cod_produto - 1).getNomeProduto(),
					GetSearchedList(produto_list, cod_produto - 1).getFornecedor(),
					GetSearchedList(produto_list, cod_produto - 1).getValorCusto(),
					GetSearchedList(produto_list, cod_produto - 1).getValorVenda(),
					GetSearchedList(produto_list, cod_produto - 1).getQtdEstoque(),
					GetSearchedList(produto_list, cod_produto - 1).getCodBarra());


			while (invalid_field) {

				System.out.print("Digite o numero do campo [Digite 0 para cancelar]: ");
				col = input.nextByte();

				invalid_field = switch (col) {
					case 0 -> false;
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

				if (col != 0) field = input.next();
			}


			if (cod_produto <= produto_list.size()) {

				switch (col) {

					case 1:
						produto_list.get(cod_produto -1).setNomeProduto(field);
						break;
					case 2:
						produto_list.get(cod_produto -1).setFornecedor(field);
						break;
					case 3:
						try {
							produto_list.get(cod_produto -1).setValorCusto(Float.parseFloat(field));
						} catch (NumberFormatException e) {
							System.out.println("O valor digitado não é um float");
						}
						break;
					case 4:
						try {
							produto_list.get(cod_produto -1).setValorVenda(Float.parseFloat(field));
						} catch (NumberFormatException e) {
							System.out.println("O valor digitado não é um float");
						}
						break;
					case 5:
						try {
							produto_list.get(cod_produto -1).setQtdEstoque(Integer.parseInt(field));
						} catch (NumberFormatException e) {
							System.out.println("O valor digitado não é um número");
						}
						break;
					case 6:
						produto_list.get(cod_produto -1).setCodBarra(field);
						break;
				}
			}
		}
	}
}

