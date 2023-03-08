import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

public class PainelDoJogo extends JPanel implements ActionListener {

//---------------------------------------------------------------------VARI�VEIS E CONSTANTES---------------------------------------------------------------------------------------	
	private static final long serialVersionUID = 1L;
	static final int LARGURA_TELA = 600; //Largura
	static final int ALTURA_TELA = 600; //Altura
	static final int TAMANHO_BLOCO = 25; //Tamanho dos blocos
	static final int QUANTIDADE_BLOCOS = LARGURA_TELA*ALTURA_TELA/TAMANHO_BLOCO; //Quantidade de blocos
	static final int DELAY = 75; //Tempo de delay
	final int[] x = new int[QUANTIDADE_BLOCOS]; // Tamanho do array que comp�e a cobra igual a quantidade de blocos pois a combra n�o poder� ser maior que o tamnho da matriz
	final int[] y = new int[QUANTIDADE_BLOCOS];
	int blocosDoCorpo = 6; // Quantidade de partes que a cobra iniciara
	int frutasComidas = 0;
	int X_Fruta; //Posi��o x da fruta
	int Y_Fruta; //Posi��o y da fruta
	String direcao = "DIREITA"; //A cobra come�ar� andando para a direita
	boolean executando = false;
	Timer timer;
	Random random;

//---------------------------------------------------------------------CONSTRUTOR DA CLASSE----------------------------------------------------------------------------------------		
	//Criando construtor do Painel
	public PainelDoJogo() {
		random = new Random();
		this.setPreferredSize(new Dimension(LARGURA_TELA, ALTURA_TELA)); //Tamanho da tela preferencialmente no tamanho indicado nas constantes
		this.setBackground(Color.BLACK); //Cor de fundo
		this.setFocusable(true); //Mant�m o Painel em foco.
		this.addKeyListener(new identificandoTeclas()); // Fica escutando os eventos para captura do bot�o pressionado.
		iniciarJogo(); //Despois de criar tudo, inicia o jogo.
	}
	
//-----------------------------------------------------------------------------M�TODOS---------------------------------------------------------------------------------------------
	
//---------------------------------------------------------------------------iniciarJogo()-----------------------------------------------------------------------------------------		
	public void iniciarJogo() {
		criarFruta();
		executando = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		desenhar(g);
	}

//---------------------------------------------------------------------------desenhar()---------------------------------------------------------------------------------------------		
	public void desenhar (Graphics g) {
		if(executando) {	// Esse if terar a fun��o de verificar se o jogo est� rodando, casosim, monte o jogo, caso n�o exiba "fimDeJogo"
				//desenhando a matriz (linhas x colunas) para facilitar a visualiza��o
				/*for(int i = 0; i<ALTURA_TELA/TAMANHO_BLOCO; i++) { // Faremos isso iterando i pelo tamanho dividido por 25 que � o tamanho de cada quadrado da matriz
					//Inicialmente x0 = 0, y=0, x2 = 0      y2 = 600 - Primeira linha criada rente com a parede da tela
					//Depois    x=25	   y=0	x=25		y = 600 - E assim por diante	
					g.drawLine(i*TAMANHO_BLOCO, 0, i*TAMANHO_BLOCO, ALTURA_TELA); // desenhando as linhas no eixo y
					g.drawLine(0, i*TAMANHO_BLOCO, LARGURA_TELA, i*TAMANHO_BLOCO);			
				}*/
				
				//Desenhando a fruta
				g.setColor(Color.RED);
				g.fillOval(X_Fruta, Y_Fruta, TAMANHO_BLOCO, TAMANHO_BLOCO);
				//		  x(aleat), y(aleat),  tamX=25,        tamY=25
				
				//Desenhando a cobra
				for(int i = 0; i < blocosDoCorpo; i++) {
						g.setColor(Color.GREEN);
						g.fillRect(x[i], y[i], TAMANHO_BLOCO, TAMANHO_BLOCO);
				}
				
				//Desenhando a pontua��o durante a partida
				g.setColor(Color.RED);
				g.setFont(new Font("Ink Free", Font.BOLD, 25)); //Instanciando a class Font para cria��o de textos
				g.drawString("Pontua��o: " + frutasComidas, 450, 30); //Texto e posi��o
				
			} else {
				fimDeJogo(g);
		}
	}

//---------------------------------------------------------------------------criarFrutas()------------------------------------------------------------------------------------------		
	public void criarFruta() { //Cria as frutas 
		X_Fruta = random.nextInt((int)(LARGURA_TELA/TAMANHO_BLOCO))*TAMANHO_BLOCO; /*O x da fruta ser� definido pelo random, fizemos o casting (int) para garantir que n�o ter� valor quebrado
		e multiplicado pelo TAMANHO_BLOCO para garantir que o n�mero n�o seja maior que 25.*/
		Y_Fruta = random.nextInt((int)(ALTURA_TELA/TAMANHO_BLOCO))*TAMANHO_BLOCO;
	}

//---------------------------------------------------------------------------mover()------------------------------------------------------------------------------------------------		
	public void mover() {
		//Criando o movimento do corpo da cobra
		// i = corpo da cobra para que possamos ter movimento em todos os objetos do corpo da cobra
		for(int i = blocosDoCorpo; i > 0; i--) {
			x[i] = x[i-1]; // Aqui a cobra
			y[i] = y[i-1];  
		}
		
		switch(direcao) {
		case "CIMA": y[0] = y[0] - TAMANHO_BLOCO; break; //Cima
		case "BAIXO": y[0] = y[0] + TAMANHO_BLOCO; break; //Baixo
		case "ESQUERDA": x[0] = x[0] - TAMANHO_BLOCO; break; //Esquerda
		case "DIREITA": x[0] = x[0] + TAMANHO_BLOCO; break; //Direita
		}
	}

//---------------------------------------------------------------------------verificarFrutas()---------------------------------------------------------------------------------------		
	public void verificarFruta() {
		
		//Verificando a colisa�o entre a cobra e a fruta
		if((x[0] == X_Fruta) && (y[0] == Y_Fruta)) { //Se as coordenadas forem iguais, ent�o...
			blocosDoCorpo++; //Aumenta o tamanha da cobra
			frutasComidas++; //Aumenta a pontua��o
			criarFruta(); //Chamamos ent�o o m�todo de cria��o de frutas para criar um novo m�todo para n�s
		}
	}

//---------------------------------------------------------------------------verificarColisao()---------------------------------------------------------------------------------------		
	public void verificarColisao() {
		
		//Verificando se a cabe�a da cobra colide com o pr�prio corpo, para isso vamos iterar por todo o corpo da cobra e verificar se as posi��es s�o iguais
		for(int i = blocosDoCorpo; i < 0; i--) {
			
			//Esse primeiro if verifica se a cabe�a colidiu com o corpo
			if((x[0] == x[i]) && (y[0] == y[i])) { //Se i for igual a x[0] e y[0] ent�o para a execu��o do jogo, pois as posi��es da cabe�a e corpo s�o iguais 
				executando = false;
			}
		}
		
		//Esse segundo if verifica se a cobra colidiu com as bordas
		if((x[0]+TAMANHO_BLOCO/2 < 0) || (x[0]-TAMANHO_BLOCO/2 > LARGURA_TELA) || (y[0]+TAMANHO_BLOCO/2<0)||(y[0]-TAMANHO_BLOCO/2>ALTURA_TELA)) {
			executando = false;
		}
		
		if(!executando) {
			timer.stop();
		}
	}

//---------------------------------------------------------------------------fimdeJogo()---------------------------------------------------------------------------------------	
	//Esse m�todo criar o texto "fimDeJogo" que ser� exibido ao fim do jogo
	public void fimDeJogo(Graphics g) {
		g.setColor(Color.GREEN);
		g.setFont(new Font("Ink Free", Font.BOLD, 75)); //Instanciando a class Font para cria��o de textos
		g.drawString("Fim de Jogo", 100, 200); //Texto e posi��o
		
		//Desenhando a pontua��o ao final da partida
		g.setColor(Color.RED);
		g.setFont(new Font("Ink Free", Font.BOLD, 40)); //Instanciando a class Font para cria��o de textos
		g.drawString("Pontua��o: " + frutasComidas, 175, 450); //Texto e posi��o	
	}

	@Override
	public void actionPerformed(ActionEvent e) { //m�todo action performed s� � disparado quando uma a��o � executada
		if(executando) { //verificando se o jogo est� em execu��o
			mover();
			verificarFruta();
			verificarColisao();
		} 
		repaint(); // chama o m�todo update() internamente que chama o m�todo paint() para redesenhar o componente
	}
	
//---------------------------------------------------------------------------controleDosMovimentos()---------------------------------------------------------------------------------------	
	//Criando bloco que faz o controle do movimento
	public class identificandoTeclas extends KeyAdapter { //Criamos a classe identificandoTeclas  extendida da classe KeyAdapter que sobrecrever� a classe keyPressed
		@Override
		public void keyPressed(KeyEvent e) { //Aqui ser� capturado o c�digo do bot�o que foi pressionado, e ser� enviado a um switch para que seja selecionado o comando
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT: //C�digo de chave virtual
				if(direcao != "DIREITA") { //Esse bloco evita que o usuario vire 180� bantendo em si mesmo.
					direcao = "ESQUERDA"; //Ou seja, voc� s� pode virar 90�, um lado de cada vez, n�o d� pra virar pra tr�s.
				} break;
				
			case KeyEvent.VK_RIGHT:
				if(direcao != "ESQUERDA") {
					direcao = "DIREITA";
				} break;
				
			case KeyEvent.VK_UP:
				if(direcao != "BAIXO") {
					direcao = "CIMA";
				} break;
				
			case KeyEvent.VK_DOWN:
				if(direcao != "CIMA") {
					direcao = "BAIXO";
				} break;
			}
		}
	}
}