import javax.swing.JFrame;

public class TelaDoJogo extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public TelaDoJogo() {
		
//		GamePanel painel = new GamePanel(); //Poderíamos fazer a instancia nomeada
//		this.add(painel); //E depois adicionar esse objeto a nossa classe TelaDoJogo, ou podemos fazer isso junto como a seguir:
		
		//O this é usadopara adicionarmos a classe "GamePanel" na classe "TelaDoJogo"
		this.add(new PainelDoJogo()); // Isso é possivel pois só pretendemos instância a classe GamaPanel nesse ponto do cógido.
		this.setTitle("SnakeGame - Juvenal"); //Titulo na tela
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fechar a tela
		this.setResizable(false);//Redimensionável: Não
		this.pack(); //pack() deixa o gerenciador de layout do quadro responsável pelo tamanho do quadro
		this.setVisible(true); //Visível: Sim
		this.setLocationRelativeTo(null); //Tela do jogo abre no meio do display
	}
}
