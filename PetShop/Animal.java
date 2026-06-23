
public abstract class Animal {
	protected String getNome() {
		return nome;
	}
	protected void setNome(String nome) {
		this.nome = nome;
	}
	protected int getIdade() {
		return idade;
	}
	protected void setIdade(int idade) {
		if (idade >= 0) {
			this.idade = idade;
		}
		else {
			System.out.println("idade negativa?");
		}
	}
	protected boolean isFaminto() {
		return faminto;
	}
	protected void setFaminto(boolean faminto) {
		this.faminto = faminto;
	}
	protected Cliente getDono() {
		return dono;
	}
	protected void setDono(Cliente dono) {
		this.dono = dono;
	}
		protected String getRaca() {
		return raca;
	}
	protected void setRaca(String raca) {
		this.raca = raca;
	}
	public static int getQuantidade() {
		return quantidade;
	}
	protected String nome;
	protected int idade;
	protected boolean faminto = true;
	protected Cliente dono;
	protected String raca;
	Animal(String nome, int idade, String raca) {
		this.nome = nome;
		this.idade = idade;
		this.raca = raca;
	}
	protected static int quantidade = 0;
	public abstract void emitirSom();
	public void comer() {
		if (faminto) {
			System.out.println("O " + nome + " comeu!");
			faminto = false;
		}
		else {
			System.out.println("O " + nome + " já está satisfeito");
		}
	}
		public String exibirDados() {
		return "Nome: " + nome + "\n" +
			   "Raça: " + raca + "\n" +
			   "Idade: " + idade + "\n" +
			   "Dono: " + (dono != null ? dono.getNome() : "Sem dono") + "\n" +
			   "Telefone do dono: " + (dono != null ? dono.getTelefone() : "Sem telefone");
	}
	
}

