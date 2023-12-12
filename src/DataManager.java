import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class DataManager {

	
	public Map<Integer, ProdutoInfo> GetSearchedList(ArrayList<ProdutoInfo> produto_list, String field) {
		
		Map<Integer, ProdutoInfo> found_list = new HashMap<>();
			
		try {			
			for (int i = 0; i < produto_list.size(); i++) {
				
				if (matches(produto_list.get(i), field)) {
					found_list.put(i, produto_list.get(i));
				}
			}
			
			if (found_list.isEmpty()) return found_list;
			else return null;
			
		} catch (Exception e) {
			e.getStackTrace();
			System.out.print("Não foi possivel ler a list\n" + e);
			return null;
		}
	}


	public ProdutoInfo GetSearchedList(ArrayList<ProdutoInfo> produto_list, int cod_produto) {
			
		return produto_list.get(cod_produto);
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
	 
	
	public void editList(ArrayList<ProdutoInfo> produto_list, int index, byte field, String new_value) {
		
		if (index <= produto_list.size()) {
			
			switch (field) {
			
				case 1:
					produto_list.get(index).setNomeProduto(new_value);
					break;
				case 2:
					produto_list.get(index).setFornecedor(new_value);
					break;
				case 3:
					try {produto_list.get(index).setValorCusto(Float.parseFloat(new_value));} 
					catch(NumberFormatException e) {
						System.out.println("O valor digitado não é um float");
					}
					break;
				case 4:
					try {produto_list.get(index).setValorVenda(Float.parseFloat(new_value));} 
					catch(NumberFormatException e) {
						System.out.println("O valor digitado não é um float");
					}
					break;
				case 5:
					try {produto_list.get(index).setQtdEstoque(Integer.parseInt(new_value));} 
					catch(NumberFormatException e) {
						System.out.println("O valor digitado não é um número");
					}
					break;
				case 6:
					produto_list.get(index).setCodBarra(new_value);
					break;
			}
		}
	}
	

}

