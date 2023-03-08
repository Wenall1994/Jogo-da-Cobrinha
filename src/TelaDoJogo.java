import javax.swing.JFrame;

public class TelaDoJogo extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public TelaDoJogo() {
		
//		GamePanel painel = new GamePanel(); //Poder�amos fazer a instancia nomeada
//		this.add(painel); //E depois adicionar esse objeto a nossa classe TelaDoJogo, ou podemos fazer isso junto como a seguir:
		
		//O this � usadopara adicionarmos a classe "GamePanel" na classe "TelaDoJogo"
		this.add(new PainelDoJogo()); // Isso � possivel pois s� pretendemos inst�ncia a classe GamaPanel nesse ponto do c�gido.
		this.setTitle("SnakeGame - Juvenal"); //Titulo na tela
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fechar a tela
		this.setResizable(false);//Redimension�vel: N�o
		this.pack(); //pack() deixa o gerenciador de layout do quadro respons�vel pelo tamanho do quadro
		this.setVisible(true); //Vis�vel: Sim
		this.setLocationRelativeTo(null); //Tela do jogo abre no meio do display
	}
}
