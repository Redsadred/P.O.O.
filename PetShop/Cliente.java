
public class Cliente {
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		if (nome != null) {
			this.nome = nome;
		}
		else {
			System.out.println("Sem nome?");
		}
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	private String nome;
	private String telefone;
	Cliente(String nome,String telefone){
		this.nome = nome;
		this.telefone = telefone;
		
	}
	
	public void exibirInformações() {
		System.out.println(getNome());
		System.out.println(getTelefone());
	}
}
