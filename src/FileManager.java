import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.time.LocalDate;
import java.time.format.*;
import java.util.ArrayList;
import java.lang.StringBuilder;

public class FileManager extends DataManager{
	
	final byte columns = 6;
	FileWriter fw;
	String path;
	File directory;
	FilenameFilter product_filter;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	LocalDate today;
	
	public FileManager() {
		super();
		path = "db";
	}
	
	
	// salva no arquivo txt
	public void saveOnFile(ArrayList<ProdutoInfo> produto_list) {
		
		StringBuilder sb = new StringBuilder();
		today = LocalDate.now();		
		String today_string = today.format(formatter);
		
		
		try {
			
			//precisa fazer com que veifique se já existe um arquivo com o mesmo nome, e se existe, adicionar uma numeração do lado.
			fw = new FileWriter(path + "/productlist_" + today_string + ".txt");
			BufferedWriter bw = new BufferedWriter(fw);

            for (ProdutoInfo produtoInfo : produto_list) {

                for (int j = 0; j < columns; j++) {

                    switch (j) {
                        case 0:
                            sb.append(produtoInfo.getNomeProduto());
                            sb.append(';');
                            break;
                        case 1:
                            sb.append(produtoInfo.getFornecedor());
                            sb.append(';');
                            break;
                        case 2:
                            sb.append(produtoInfo.getValorCusto());
                            sb.append(';');
                            break;
                        case 3:
                            sb.append(produtoInfo.getValorVenda());
                            sb.append(';');
                            break;
                        case 4:
                            sb.append(produtoInfo.getQtdEstoque());
                            sb.append(';');
                            break;
                        case 5:
                            sb.append(produtoInfo.getCodBarra());
                            break;
                        default:
                            sb.append("invalid");
                            break;
                    }
                }

                bw.write(sb.toString());
                bw.newLine();
                sb.setLength(0);
            }
			
			bw.close();
			
		} catch (Exception e) {
			System.out.println("nao foi possivel criar arquivo");
			e.getStackTrace();
		}
	}
	
	// converter o aquivo txt em ArrayList<ProdutoInfo>
	public ArrayList<ProdutoInfo> convertToList() {
		
		ArrayList<ProdutoInfo> new_list = new ArrayList<>();

		try (FileReader fr = new FileReader(mostRecentFile())) {

			try (BufferedReader br = new BufferedReader(fr)) {

				String line = br.readLine();
				ArrayList<String[]> field = new ArrayList<>();

				while (line != null) {
					field.add(line.split(";", columns));
					line = br.readLine();
				}

				String nome_produto = "-", fornecedor = "-", cod_barra = "-";
				float valor_custo = 0.00f, valor_venda = 0.00f;
				int qtd_estoque = 0;

                for (String[] strings : field) {
                    ProdutoInfo produto_info = new ProdutoInfo();
                    try {
                        nome_produto = strings[0];
                        fornecedor = strings[1];
                        cod_barra = strings[5];
                    } catch (Exception e) {
                        System.out.println("erro ao tentar converter receber valores\n" + e);
                    }

                    try {
                        valor_custo = Float.parseFloat(strings[2]);
                        valor_venda = Float.parseFloat(strings[3]);
                        qtd_estoque = Integer.parseInt(strings[4]);
                    } catch (NumberFormatException e) {
                        System.out.println("erro ao tentar converter valores para numerico\n" + e);
                    }


                    produto_info.setNomeProduto(nome_produto);
                    produto_info.setFornecedor(fornecedor);
                    produto_info.setValorCusto(valor_custo);
                    produto_info.setValorVenda(valor_venda);
                    produto_info.setQtdEstoque(qtd_estoque);
                    produto_info.setCodBarra(cod_barra);

                    new_list.add(produto_info);
                }

			} catch (IOException e) {
				System.out.println("nao foi possivel transformar em ArrayList");
			}

		} catch (IOException e) {
			System.out.println("nao foi possivel acessar o arquivo mais recente");
		}

		return new_list;

	}


	// retorna o nome do arquivo mais recente
	private String mostRecentFile() {
		
		String most_recent_name = "";
		long lastModifiedTime = Long.MIN_VALUE;

		try {
			product_filter = (dir, name) -> name.toLowerCase().startsWith("productlist_") && name.toLowerCase().endsWith(".txt");

			directory = new File("db");
			File[] product_file = directory.listFiles(product_filter);

			if (product_file != null) {

				File most_recent = product_file[0];
				for (File file : product_file) {

					if (file.lastModified() > lastModifiedTime) {

						most_recent = file;
						lastModifiedTime = file.lastModified();
					}
				}
				most_recent_name = most_recent.getName();
			}
			return "db" + "/" + most_recent_name;

		} catch(Exception e) {
			return "";
		}



	}

}
