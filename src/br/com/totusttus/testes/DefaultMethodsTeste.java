package br.com.totusttus.testes;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Pesquise os novos m�todos default adicionados na interface List:
 * http://docs.oracle.com/javase/8/docs/api/java/util/List.html
 * 
 * @author thiag
 *
 */
public class DefaultMethodsTeste {

	List<String> lista = Arrays.asList("Biblioteca Cat�lica", "C�digo de Direito Can�nico",
			"Catecismo da Igreja Cat�lica");

	public DefaultMethodsTeste() {

		/*ordenandoListaAntesJava8();
		ordenandoListaComComparatorAntesJava8();
		ordenandoListaComCOmparatorComJava8();
		iterandoListaComJava8();*/
		iterandoComLambda();
		utilizandoComparatorComLambda();
		utilizandoThreadComLambda();
		utilizandoFunction();
		utilizandoMethodReference();

	}

	private void utilizandoMethodReference() {

		// Podemos substituir isso:
		lista.sort(Comparator.comparing(s -> s.length()));
		
		// Por isso:
		lista.sort(Comparator.comparing(String::length));
		
		/*
		 * S�o equivalentes nesse caso! 
		 * 
		 * Sim, � estranho ver String::length e dizer que � equivalente 
		 * a um lambda, pois n�o h� nem a -> e nem os par�nteses
		 *  de invoca��o ao m�todo. 
		 *  
		 *  Por isso � chamado de method reference.
		 *  
		 *  Ambas geram a mesma fun��o: dada um String, invoca o 
		 *  m�todo length e devolve este Integer. As duas ser�o 
		 *  avaliadas/resolvidas (evaluated) para Functions equivalentes.
		 */
		
		/*
		 * Outro exemplo com method reference:
		 */
		lista.forEach(System.out::println);
		
		/*
		 * Novamente pode parecer estranho. 
		 * N�o h� os par�nteses, n�o h� a flechinha (->), nem os 
		 * argumentos que o Consumer recebe. Fica tudo impl�cito. 
		 * 
		 * Dessa vez, o argumento recebido (isso �, cada palavra dentro 
		 * da lista palavras), n�o ser� a vari�vel onde o m�todo ser� invocado. 
		 * 
		 * O Java 8 consegue perceber que tem um println que recebe objetos e 
		 * invocar� esse m�todo, passando a String da vez.
		 */
	}

	
	private void utilizandoFunction() {
		
		/*
		 * H� v�rios m�todos auxiliares no Java 8. 
		 * At� em interfaces como o Comparator. 
		 * E voc� pode ter um m�todo default que � est�tico. 
		 * Esse � o caso do Comparator.comparing, que � 
		 * uma f�brica, uma factory, de Comparator. 
		 * Passamos o lambda para dizer qual ser� o 
		 * crit�rio de compara��o desse Comparator
		 * 
		 * Veja a expressividade da linha, est� escrito algo 
		 * como "palavras ordene comparando s.length".
		 * 
		 * Dizemos que Comparator.comparing recebe um lambda, mas 
		 * essa � uma express�o do dia a dia. 
		 * Na verdade, ela recebe uma inst�ncia de uma interface 
		 * funcional. No caso � a interface Function que tem apenas 
		 * um m�todo, o apply.
		 */
		lista.sort(Comparator.comparing(s -> s.length()));
		
		
		/*
		 * A interface Function vai nos ajudar a passar um objeto para o 
		 * Comparator.comparing que diz qual ser� a informa��o que queremos 
		 * usar como crit�rio de compara��o. Ela recebe dois tipos gen�ricos. 
		 * 
		 * No nosso caso, recebe uma String, que � o tipo que queremos comparar e 
		 * um Integer, que � o que queremos extrair dessa string para usar como 
		 * crit�rio.
		 * 
		 * Antes do Java 8, dever�amos escrever algo como:
		 */
		Function<String, Integer> funcao = new Function<String, Integer>() {
			@Override
			public Integer apply(String s) {
				return s.length();
			}
		};
		
		
		Comparator<String> comparador = Comparator.comparing(funcao);
		lista.sort(comparador);
	}

	private void utilizandoThreadComLambda() {
		
		/*
		 * Antes do Lambda
		 */
		
		new Thread(new Runnable() {

		    @Override
		    public void run() {
		        System.out.println("Executando um Runnable");
		    }

		}).start();
		
		/*
		 * Ap�s o Lambda
		 */
		
		new Thread(() -> System.out.println("Executando um Runnable via Lambda")).start();
		
	}

	private void utilizandoComparatorComLambda() {
		
		// Antes do Lamba, precis�vamos escrever algo assim
		lista.sort(new Comparator<String>() {
		    public int compare(String s1, String s2) {
		    	return Integer.compare(s1.length(), s2.length());
		    }
		});
		
		/*
		 * Com o Lambda, escrevemos bem menos.
		 * 
		 * O return pode ser eliminado que o compilador vai inferir 
		 * que deve ser retornado o valor que o pr�prio compare 
		 * do Integer devolver.
		 * 
		 * A leitura ficaria assim:
		 * lista.sort((parametros) -> corpo_do_metodo_que_retornara_no_final_um_int);
		 */
		lista.sort((s1, s2) -> Integer.compare(s1.length(), s2.length()));
	}

	private void iterandoComLambda() {
		
		/*
		 * Em vez de escrever a classe an�nima, deixamos de escrever 
		 * alguns itens que podem ser inferidos.
		 * 
		 * Como a interface Consumer s� tem um m�todo, n�o precisamos escrever o 
		 * nome do m�todo. 
		 * Tamb�m n�o daremos new. 
		 * Apenas declararemos os argumentos e o bloco a ser executado, 
		 * separados por ->
		 * 
		 *  � uma forma bem mais sucinta de escrever! 
		 *  Essa sintaxe funciona para qualquer interface que tenha apenas 
		 *  um m�todo abstrato, e � por esse motivo que nem precisamos falar 
		 *  que estamos implementando o m�todo accept, j� que n�o h� 
		 *  outra possibilidade.
		 *  
		 *  Uma interface que possui apenas um m�todo abstrato � agora conhecida 
		 *  como interface funcional e pode ser utilizada dessa forma.
		 *  
		 *  
		 *  REFOR�ANDO:
		 *  Uma interface funcional deve ter 1 �nico m�todo abstrato. 
		 *  Al�m desse m�todo ela pode ter outros m�todos, contanto que sejam default ou 'static'.
		 *  Essa estrutura � fundamental, pois assim o compilador sabe exatamente que o corpo da 
		 *  express�o lambda que escrevemos � a implementa��o de seu �nico m�todo abstrato
		 *  
		 *  NOTA: com o Lambda, n�o precisamos inferir o tipo da vari�vel que estamos
		 *  recebendo via m�todo
		 */
		// Podemos usar o Lambda dessa maneira
		lista.forEach((String s) -> {
		    System.out.println(s);
		});
		
		/*
		 *  Ou dessa maneira.
		 *  
		 *  A leitura seria algo assim:
		 *  lista.forEach(parametro_enviado_via_metodo -> implementacao_do_metodo_que_sera_executado);
		 */
		lista.forEach(s -> System.out.println(s));
		
		/*
		 * As linhas acima nos evitam de criarmos c�digos como esse:
		 */
		lista.forEach(new Consumer<String>() {
		    public void accept(String s) {
		        System.out.println(s);
		    }
		});

	}

	private void iterandoListaComJava8() {

		/*
		 * Vamos a um outro m�todo default adicionado as cole��es do Java: o forEach na
		 * interface Iterable. Como Iterable � m�e de Collection, temos acesso a esse
		 * m�todo na nossa lista.
		 * 
		 * Se voc� abrir o JavaDoc ou utilizar o auto complete do Eclipse, ver� que
		 * List.forEach recebe um Consumer, que � uma das muitas interfaces do novo
		 * pacote java.util.functions. Ent�o vamos criar um consumidor de String,
		 * chamado ConsumidorDeString.
		 */
		Consumer<String> consumidor = new ConsumidorDeString();
		lista.forEach(consumidor);
	}

	private void ordenandoListaComCOmparatorComJava8() {
		/*
		 * Como fazemos hoje com o Java 8 para implementar um Comparator
		 * 
		 * Esse m�todo sort() n�o existia antes na interface List, nem em suas m�es
		 * (Collection e Iterable).
		 * 
		 * O Java 8 optou por criar um novo recurso que possibilitasse adicionar m�todos
		 * em interfaces e implement�-los ali mesmo! Se voc� abrir o c�digo fonte da
		 * interface List, ver� que esse m�todo tem um corpo.
		 * 
		 * � um default method! Um m�todo de interface que voc� n�o precisa implementar
		 * na sua classe se n�o quiser, pois voc� ter� j� essa implementa��o default.
		 * 
		 * Default methods foi uma forma que o Java encontrou para evoluir interfaces
		 * antigas, sem gerar incompatibilidades. N�o � uma novidade da linguagem:
		 * Scala, C# e outras possuem recursos similares e at� mais poderosos. E repare
		 * que � diferente de uma classe abstrata: em uma interface voc� n�o pode ter
		 * atributos de inst�ncia, apenas esses m�todos que delegam chamadas ou
		 * trabalham com os pr�prios m�todos da interface.
		 */
		ComparadorDeStringPorTamanho comparador = new ComparadorDeStringPorTamanho();
		lista.sort(comparador);
		System.out.println(lista);
	}

	private void ordenandoListaComComparatorAntesJava8() {
		// Como faziamos antes do Java 8 para implementar um Comparator
		Comparator<String> comparador = new ComparadorDeStringPorTamanho();
		Collections.sort(lista, comparador);
		System.out.println(lista);
	}

	public static void main(String[] args) {

		new DefaultMethodsTeste();
	}

	private void ordenandoListaAntesJava8() {
		// Ordenando lista com sort
		Collections.sort(lista);
		System.out.println(lista);
	}

}


/**
 * 
 * @author thiag
 *
 */
class ConsumidorDeString implements Consumer<String> {
	public void accept(String s) {
		System.out.println(s);
	}
}

/**
 * 
 * @author thiag
 *
 */
class ComparadorDeStringPorTamanho implements Comparator<String> {

	/*
	 * Verifica qual string � maior que a outra
	 */
	public int compare(String s1, String s2) {
		if (s1.length() < s2.length())
			return -1;
		if (s1.length() > s2.length())
			return 1;
		return 0;
	}
}