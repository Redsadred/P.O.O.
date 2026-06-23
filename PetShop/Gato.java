
public class Gato extends Animal implements AtendivelNoEstetica {
	Gato(String nome, int idade, String raca) {
		super(nome, idade, raca);
		quantidadeDeGato++;
	}
	public static int getQuantidadeDeGato() {
		return quantidadeDeGato;
		
	}
	private static int quantidadeDeGato = 0;
	@Override
	public void emitirSom() {
		System.out.println("Miau");
}

	@Override
	public void darBanho() {
		System.out.println("O gato " + this.nome + " recebeu um banho");
	}

	@Override
	public void cortarUnhas() {
		System.out.println("O gato " + this.nome + " teve suas unhas cortadas");
	}
}