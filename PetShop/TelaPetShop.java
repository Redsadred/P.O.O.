import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaPetShop extends JFrame {

    private final JComboBox<String> comboTipo = new JComboBox<>(new String[] { "Cachorro", "Gato" });
    private final PetShopRepositorio repositorio = new PetShopRepositorio();

    // ── Campos do formulário ───────────────────────────────
    private final JTextField campNome = new JTextField(10);
    private final JTextField campRaca = new JTextField(10);
    private final JTextField campIdade = new JTextField(10);
    private final JTextField campDono = new JTextField(10);
    private final JTextField campTelefone = new JTextField(10);

    // ── Área de resultado ──────────────────────────────────
    private final JTextArea areaResultado = new JTextArea(12, 50);

    // ── Botões ─────────────────────────────────────────────
    private final JButton btnCadastrar = new JButton("Cadastrar");
    private final JButton btnListar = new JButton("Listar Todos");
    private final JButton btnBuscar = new JButton("Buscar");
    private final JButton btnatualizar = new JButton("Atualizar");
    private final JButton btnRemover = new JButton("Remover");

    // ── Construtor ─────────────────────────────────────────
    public TelaPetShop() {
        super("Pet Shop — Gerenciador de Animais");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // O JFrame usa BorderLayout por padrão
        setLayout(new BorderLayout(8, 8));

        add(criarPainelFormulario(), BorderLayout.NORTH);
        add(criarAreaResultado(), BorderLayout.CENTER);
        add(criarPainelBotoes(), BorderLayout.SOUTH);

        configurarListeners();

        setSize(900, 600);
        pack();
        setLocationRelativeTo(null); // centraliza na tela

        setVisible(true);
    }

    // ── Painel Norte: formulário ───────────────────────────
    private JPanel criarPainelFormulario() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        painel.setBorder(BorderFactory.createTitledBorder("Dados do Pet e Tutor"));

        painel.add(new JLabel("Tipo:"));
        painel.add(comboTipo);
        painel.add(new JLabel("Nome:"));
        painel.add(campNome);
        painel.add(new JLabel("Raça:"));
        painel.add(campRaca);
        painel.add(new JLabel("Idade:"));
        painel.add(campIdade);
        painel.add(new JLabel("Tutor:"));
        painel.add(campDono);
        painel.add(new JLabel("Telefone:"));
        painel.add(campTelefone);

        return painel;
    }

    // ── Centro: área de texto com scroll ──────────────────
    private JScrollPane criarAreaResultado() {
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaResultado.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
        exibirTexto("Bem-vindo ao sistema do Pet Shop!\n"
                + "Preencha os campos acima e use os botões para gerenciar os pets.\n");
        return new JScrollPane(areaResultado);
    }

    // ── Painel Sul: botões ─────────────────────────────────
    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        painel.add(btnCadastrar);
        painel.add(btnBuscar);
        painel.add(btnatualizar);
        painel.add(btnRemover);
        painel.add(btnListar);
        return painel;
    }

    // ── ActionListeners ────────────────────────────────────
    private void configurarListeners() {

        // ---- CADASTRAR ----
        btnCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = campNome.getText().trim();
                String raca = campRaca.getText().trim();
                String idadeStr = campIdade.getText().trim();
                String dono = campDono.getText().trim();
                String telefone = campTelefone.getText().trim();

                if (nome.isEmpty()) {
                    exibirTexto("ERRO: O campo Nome é obrigatório.");
                    return;
                }
                if (!nome.matches("[a-zA-ZÀ-ÿ ]+")) {
                    exibirTexto("ERRO: O nome deve conter apenas letras.");
                    return;
                }
                if (raca.isEmpty()) {
                    raca = "Indefinida";
                } else if (!raca.matches("[a-zA-ZÀ-ÿ ]+")) {
                    exibirTexto("ERRO: A raça deve conter apenas letras.");
                    return;
                }
                if (dono.isEmpty()) {
                    exibirTexto("ERRO: O campo Tutor é obrigatório.");
                    return;
                }
                if (!dono.matches("[a-zA-ZÀ-ÿ ]+")) {
                    exibirTexto("ERRO: O nome do tutor deve conter apenas letras.");
                    return;
                }
                if (telefone.isEmpty()) {
                    telefone = "Sem telefone";
                }
                int idade = 4;
                if (!idadeStr.isEmpty()) {
                    try {
                        idade = Integer.parseInt(idadeStr);
                    } catch (NumberFormatException ex) {
                        exibirTexto("ERRO: O campo Idade deve ser um número válido.");
                        return;
                    }
                }

                String tipo = (String) comboTipo.getSelectedItem();

                Animal novo;

                if (tipo.equals("Cachorro")) {
                    novo = new Cachorro(nome, raca, idade);
                } else {
                    novo = new Gato(nome, idade, raca);
                }

                Cliente novoDono = new Cliente(dono, telefone);
                novo.setDono(novoDono);

                repositorio.adicionar(novo);
                exibirTexto(tipo + " cadastrado com sucesso!\n\n" + novo.exibirDados());
                limparCampos();
            }
        });

        // ---- BUSCAR ----
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nomeBusca = campNome.getText().trim();
                String tipo = (String) comboTipo.getSelectedItem();
                if (nomeBusca.isEmpty()) {
                    exibirTexto("ERRO: Digite o nome do pet para buscar.");
                    return;
                }
                Animal encontrado = repositorio.buscarPorNome(nomeBusca);
                if (encontrado == null) {
                    exibirTexto("Nenhum pet encontrado com o nome: " + nomeBusca);
                    return;
                }
                if (tipo.equals("Cachorro") && !(encontrado instanceof Cachorro)) {
                    exibirTexto("Não existe cachorro com esse nome.");
                    return;
                }
                if (tipo.equals("Gato") && !(encontrado instanceof Gato)) {
                    exibirTexto("Não existe gato com esse nome.");
                    return;
                }
                exibirTexto("Pet encontrado:\n\n" + encontrado.exibirDados());
            }
        });

        // ---- ATUALIZAR ----
        btnatualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nomeBusca = campNome.getText().trim();
                if (nomeBusca.isEmpty()) {
                    exibirTexto("ERRO: Digite o nome do pet para atualizar.");
                    return;
                }
                Animal encontrado = repositorio.buscarPorNome(nomeBusca);
                if (encontrado == null) {
                    exibirTexto("Nenhum pet encontrado com o nome: " + nomeBusca);
                    return;
                }
                String tipo = (String) comboTipo.getSelectedItem();
                if (tipo.equals("Cachorro") && !(encontrado instanceof Cachorro)) {
                    exibirTexto("Não existe cachorro com esse nome.");
                    return;
                }
                if (tipo.equals("Gato") && !(encontrado instanceof Gato)) {
                    exibirTexto("Não existe gato com esse nome.");
                    return;
                }
                String raca = campRaca.getText().trim();
                String idadeStr = campIdade.getText().trim();
                String dono = campDono.getText().trim();
                String telefone = campTelefone.getText().trim();
                if (raca.isEmpty() &&
                        idadeStr.isEmpty() &&
                        dono.isEmpty() &&
                        telefone.isEmpty()) {

                    exibirTexto("ERRO: Preencha pelo menos um campo para atualizar.");
                    return;
                }
                if (!raca.isEmpty() && !raca.matches("[a-zA-ZÀ-ÿ ]+")) {
                    exibirTexto("ERRO: A raça deve conter apenas letras.");
                    return;
                }
                if (!dono.isEmpty() && !dono.matches("[a-zA-ZÀ-ÿ ]+")) {
                    exibirTexto("ERRO: O nome do tutor deve conter apenas letras.");
                    return;
                }
                if (!idadeStr.isEmpty()) {
                    try {
                        int idade = Integer.parseInt(idadeStr);

                        if (idade < 0) {
                            exibirTexto("ERRO: A idade não pode ser negativa.");
                            return;
                        }

                    } catch (NumberFormatException ex) {
                        exibirTexto("ERRO: O campo Idade deve ser um número válido.");
                        return;
                    }
                }
                if (!raca.isEmpty()) {
                    encontrado.setRaca(raca);
                }
                if (!idadeStr.isEmpty()) {
                    encontrado.setIdade(Integer.parseInt(idadeStr));
                }
                if (!dono.isEmpty()) {
                    if (telefone.isEmpty()) {
                        telefone = "Sem telefone";
                    }
                    encontrado.setDono(new Cliente(dono, telefone));
                }
                if (!telefone.isEmpty() && encontrado.getDono() != null) {
                    encontrado.getDono().setTelefone(telefone);
                }
                exibirTexto("Pet atualizado com sucesso!\n\n" +
                        encontrado.exibirDados());
                limparCampos();
            }
        });

        // ---- REMOVER ----
        btnRemover.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nomeBusca = campNome.getText().trim();
                if (nomeBusca.isEmpty()) {
                    exibirTexto("ERRO: Digite o nome do pet para remover.");
                    return;
                }
                Animal encontrado = repositorio.buscarPorNome(nomeBusca);
                if (encontrado == null) {
                    exibirTexto("Nenhum pet encontrado com o nome: " + nomeBusca);
                    return;
                }
                String tipo = (String) comboTipo.getSelectedItem();
                if (tipo.equals("Cachorro") && !(encontrado instanceof Cachorro)) {
                    exibirTexto("Não existe cachorro com esse nome.");
                    return;
                }
                if (tipo.equals("Gato") && !(encontrado instanceof Gato)) {
                    exibirTexto("Não existe gato com esse nome.");
                    return;
                }
                repositorio.remover(nomeBusca);
                exibirTexto(tipo + " removido com sucesso!");
                limparCampos();
            }
        });

        // ---- LISTAR TODOS ----
        btnListar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (repositorio.quantidade() == 0) {
                    exibirTexto("Nenhum pet cadastrado.");
                    return;
                }
                String resultado = "=== CACHORROS ===\n\n";
                for (Animal animal : repositorio.listarTodos()) {
                    if (animal instanceof Cachorro) {
                        resultado += animal.exibirDados() + "\n";
                        resultado += "--------------------------\n";
                    }
                }
                resultado += "\n=== GATOS ===\n\n";
                for (Animal animal : repositorio.listarTodos()) {
                    if (animal instanceof Gato) {
                        resultado += animal.exibirDados() + "\n";
                        resultado += "--------------------------\n";
                    }
                }
                exibirTexto(resultado);
            }
        });
    }

    // ── Métodos auxiliares ─────────────────────────────────

    /** Exibe texto na área de resultado, substituindo o conteúdo anterior. */
    private void exibirTexto(String texto) {
        areaResultado.setText(texto);
    }

    /** Limpa todos os campos do formulário. */
    private void limparCampos() {
        campNome.setText("");
        campRaca.setText("");
        campIdade.setText("");
        campDono.setText("");
        campTelefone.setText("");
        campNome.requestFocus();
    }

}