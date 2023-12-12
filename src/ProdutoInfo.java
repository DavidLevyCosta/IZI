public class ProdutoInfo {
    String nome_produto, fornecedor, cod_barra;
    float valor_custo, valor_venda;
    int qtd_estoque;
    
    public ProdutoInfo() {
    	nome_produto = "-";
    	fornecedor = "-";
    	valor_custo = 0.0f;
    	valor_venda = 0.0f;
    	qtd_estoque = 0;
    	cod_barra = "-";
    }
    
    public void setNomeProduto(String produto) {
        try {this.nome_produto = produto;} 
        catch (Exception e) {System.out.printf("%s nao e string! \n%s", produto, e);}
    }
    
    public String getNomeProduto() {
        return nome_produto;
    }
    
    public void setFornecedor(String fornecedor) {
        try {this.fornecedor = fornecedor;} 
        catch (Exception e) {System.out.printf("%s nao e string! \n%s", fornecedor, e);}
    }
    
    public String getFornecedor() {
        return fornecedor;
    }
    
    public void setValorCusto(float valor_custo) {
    	// precisa de formatações
        try {this.valor_custo = valor_custo;} 
        catch (Exception e) {System.out.printf("%s nao e float! \n%s", valor_custo, e);}
    }
    
    public float getValorCusto() {
        return valor_custo;
    }
    
    public void setValorVenda(float valor_venda) {
    	// precisa de formatações
        try {this.valor_venda = valor_venda;} 
        catch (Exception e) {System.out.printf("%s nao e float! \n%s", valor_venda, e);}
    }
    
    public float getValorVenda() {
        return valor_venda;
    }
    
    public void setQtdEstoque(int qtd_estoque) {
    	// precisa de formatações
        try {this.qtd_estoque = qtd_estoque;} 
        catch (Exception e) {System.out.printf("%d nao e int! \n%s", qtd_estoque, e);}
    }
    
    public int getQtdEstoque() {
        return qtd_estoque;
    }
    
    public void setCodBarra(String cod_barra) {
        try {this.cod_barra = cod_barra;} 
        catch (Exception e) {System.out.printf("%s nao e string! \n%s", cod_barra, e);}
    }
    
    public String getCodBarra() {
        return cod_barra;
    }
}
