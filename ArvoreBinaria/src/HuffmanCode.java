import java.util.PriorityQueue;

public class HuffmanCode {
	
	// Compactação e Descompactação de um texto

	public static void main(String[] args) {
		
		//Entrada do texto
		String teste = "PARALELEPIPEDOPOLIGONOGRANDE";
		
		//Percorer o texto contando os símbolos e montando um vetor de frequência
		int[] charFrequencia = new int[256];
		for(char c : teste.toCharArray())
			charFrequencia[c]++;
		
		//Cria a árvore dos códigos para a Compactação, salva o nó raiz da árvore
		HuffmanNo arvore = construirArvore(charFrequencia);
		
		// Resultados das quantidade e o código da Compactação
        System.out.println("TABELA DE CÓDIGOS");
        System.out.println("SÍMBOLO\tQUANTIDADE\tHUFFMAN CÓDIGO");
        imprimirCodigos(arvore, new StringBuffer());
        
        // Compactando o texto
        String compactado = compactarTexto(arvore, teste);
        // Mostrar o texto Compactado
        System.out.println("\nTEXTO COMPACTADO");
        System.out.println(compactado);
        
        // Decodificar o texto
        System.out.println("\n\nTEXTO DECODIFICADO");
        System.out.println(decodificarTexto(arvore,compactado));
        
	}
	
	public static HuffmanNo construirArvore(int[] charFrequencia) {
		//Cria uma fila de prioridade
		//A fila será¡ criada pela ordem de frequência da letra no texto
		PriorityQueue<HuffmanNo> arvores = new PriorityQueue<HuffmanNo>();
		//Cria as Folhas da árvore para cada letra
		for(int i = 0; i < charFrequencia.length; i++) {
			if(charFrequencia[i] > 0)
				arvores.offer(new NoFolha(charFrequencia[i],(char)i)); // Insere os elementos do nó folha na fila de prioridade
		}
		// Percorre os elementos da fila, criando a arvore binária de baixo para cima
		while(arvores.size() > 1) {
			// Pega os dois nós com menor frequência
			HuffmanNo a = arvores.poll(); // poll - Retorna o próximo Nó da Fila ou NULL se não houver mais
			HuffmanNo b = arvores.poll(); // poll - Retorna o próximo Nó da Fila ou NULL se não houver mais
			
			// Cria os nÃ³s internos da Ã¡rvore binaria
			arvores.offer(new NoInterno(a, b));
		}
		// Retorna o nÃ³ raiz da Ã¡rvore
		return arvores.poll();
		
	}
	
	public static void imprimirCodigos(HuffmanNo arvore, StringBuffer prefixo) {
		assert arvore != null;
		
		if(arvore instanceof NoFolha) {
			NoFolha folha = (NoFolha)arvore;
			
			// Imprime na tela a Lista
			System.out.println(folha.valor + "\t" + folha.frequencia + "\t\t" + prefixo);
		}else if(arvore instanceof NoInterno) {
			NoInterno interno = (NoInterno) arvore;
			
			//Atravessando para a esquerda atravÃ©s de um mÃ©todo recursivo
			prefixo.append('0');
			imprimirCodigos(interno.esquerda, prefixo);
			prefixo.deleteCharAt(prefixo.length()-1);
			
			//Atravessando para a direita atravÃ©s de um mÃ©todo recursivo
			prefixo.append('1');
			imprimirCodigos(interno.direita, prefixo);
			prefixo.deleteCharAt(prefixo.length()-1);
		}
	}
	
	public static String compactarTexto(HuffmanNo arvore, String compactado) {
		assert arvore != null;
		
		String textoCompactado= "";
		for(char c : compactado.toCharArray()) {
			textoCompactado +=(getCodigos(arvore, new StringBuffer(),c));
		}
		return textoCompactado; // Retorna o texto compactado
	}
	
	public static String getCodigos(HuffmanNo arvore, StringBuffer prefixo, char w) {
		assert arvore != null;
		
		if(arvore instanceof NoFolha) {
			NoFolha folha = (NoFolha)arvore;
			
			// Retorna o texto compactado da letra
			if(folha.valor == w) {
				return prefixo.toString();
			}
		}else if (arvore instanceof NoInterno) {
			NoInterno interno = (NoInterno)arvore;
			
			// Percorre a esquerda
			prefixo.append('0');
			String esquerda = getCodigos(interno.esquerda, prefixo, w);
			prefixo.deleteCharAt(prefixo.length()-1);
			
			// Percorre a direita
			prefixo.append('1');
			String direita = getCodigos(interno.direita, prefixo, w);
			prefixo.deleteCharAt(prefixo.length()-1);
			
			if(esquerda==null) return direita;else return esquerda;
		}
		return null;
	}
	
	public static String decodificarTexto(HuffmanNo arvore, String compactado) {
		assert arvore != null;
		
		String textoDecodificado="";
		NoInterno interno = (NoInterno)arvore;
		for(char codigo : compactado.toCharArray()) {
			if(codigo=='0') { // Quando for igual a zero Ã© o lado esquerdo
				if(interno.esquerda instanceof NoFolha) {
					textoDecodificado += ((NoFolha)interno.esquerda).valor; //Retorna o valor do nÃ³ folha, pelo lado esquerdo
					interno = (NoInterno)arvore;
				}else {
					interno = (NoInterno) interno.esquerda; // Continua percorrendo a Ã¡rvore pelo lado esquerdo
				}
			}else if(codigo == '1') { // Se o codigo for igual a um percorre a Ã¡rvore pelo lado direito
				if(interno.direita instanceof NoFolha) {
					textoDecodificado += ((NoFolha)interno.direita).valor; // Retorna o valor do nÃ³ folha, pelo lado direito
					interno = (NoInterno)arvore; // Retorna para a raÃ­z da Ã¡rvore
				}else {
					interno = (NoInterno) interno.direita; // Continua percorrendo a Ã¡rvore pelo lado direito
				}
			}
		} // End for
		return textoDecodificado; // Retorna o texto decodificado
	}
}
