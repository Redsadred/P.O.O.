
public class Cachorro extends Animal implements AtendivelNoEstetica {
	;
	private static int quantidadeDeCachorro = 0;

	public static int getQuantidadeDeCachorro() {
		return quantidadeDeCachorro;
	}

	Cachorro(String nome, String raca, int idade) {
		super(nome, idade, raca);
		this.nome = nome;
		this.raca = raca;
		this.idade = idade;
		Cachorro.quantidadeDeCachorro++;
	}

	@Override
	public void emitirSom() {
		System.out.println("Au Au");
	}

	@Override
	public void darBanho() {
		System.out.println("O cachorro " + this.nome + " recebeu um banho");
	}

	@Override
	public void cortarUnhas() {
		System.out.println("O cachorro " + this.nome + " teve suas unhas cortadas");
	}
}
