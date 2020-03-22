package br.com.totusttus.testes;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Pesquise os novos métodos default adicionados na interface List:
 * http://docs.oracle.com/javase/8/docs/api/java/util/List.html
 * 
 * @author thiag
 *
 */
public class DefaultMethodsTeste {

	List<String> lista = Arrays.asList("Biblioteca Católica", "Código de Direito Canônico",
			"Catecismo da Igreja Católica");

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
		 * São equivalentes nesse caso! 
		 * 
		 * Sim, é estranho ver String::length e dizer que é equivalente 
		 * a um lambda, pois não há nem a -> e nem os parênteses
		 *  de invocação ao método. 
		 *  
		 *  Por isso é chamado de method reference.
		 *  
		 *  Ambas geram a mesma função: dada um String, invoca o 
		 *  método length e devolve este Integer. As duas serão 
		 *  avaliadas/resolvidas (evaluated) para Functions equivalentes.
		 */
		
		/*
		 * Outro exemplo com method reference:
		 */
		lista.forEach(System.out::println);
		
		/*
		 * Novamente pode parecer estranho. 
		 * Não há os parênteses, não há a flechinha (->), nem os 
		 * argumentos que o Consumer recebe. Fica tudo implícito. 
		 * 
		 * Dessa vez, o argumento recebido (isso é, cada palavra dentro 
		 * da lista palavras), não será a variável onde o método será invocado. 
		 * 
		 * O Java 8 consegue perceber que tem um println que recebe objetos e 
		 * invocará esse método, passando a String da vez.
		 */
	}

	
	private void utilizandoFunction() {
		
		/*
		 * Há vários métodos auxiliares no Java 8. 
		 * Até em interfaces como o Comparator. 
		 * E você pode ter um método default que é estático. 
		 * Esse é o caso do Comparator.comparing, que é 
		 * uma fábrica, uma factory, de Comparator. 
		 * Passamos o lambda para dizer qual será o 
		 * critério de comparação desse Comparator
		 * 
		 * Veja a expressividade da linha, está escrito algo 
		 * como "palavras ordene comparando s.length".
		 * 
		 * Dizemos que Comparator.comparing recebe um lambda, mas 
		 * essa é uma expressão do dia a dia. 
		 * Na verdade, ela recebe uma instância de uma interface 
		 * funcional. No caso é a interface Function que tem apenas 
		 * um método, o apply.
		 */
		lista.sort(Comparator.comparing(s -> s.length()));
		
		
		/*
		 * A interface Function vai nos ajudar a passar um objeto para o 
		 * Comparator.comparing que diz qual será a informação que queremos 
		 * usar como critério de comparação. Ela recebe dois tipos genéricos. 
		 * 
		 * No nosso caso, recebe uma String, que é o tipo que queremos comparar e 
		 * um Integer, que é o que queremos extrair dessa string para usar como 
		 * critério.
		 * 
		 * Antes do Java 8, deveríamos escrever algo como:
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
		 * Após o Lambda
		 */
		
		new Thread(() -> System.out.println("Executando um Runnable via Lambda")).start();
		
	}

	private void utilizandoComparatorComLambda() {
		
		// Antes do Lamba, precisávamos escrever algo assim
		lista.sort(new Comparator<String>() {
		    public int compare(String s1, String s2) {
		    	return Integer.compare(s1.length(), s2.length());
		    }
		});
		
		/*
		 * Com o Lambda, escrevemos bem menos.
		 * 
		 * O return pode ser eliminado que o compilador vai inferir 
		 * que deve ser retornado o valor que o próprio compare 
		 * do Integer devolver.
		 * 
		 * A leitura ficaria assim:
		 * lista.sort((parametros) -> corpo_do_metodo_que_retornara_no_final_um_int);
		 */
		lista.sort((s1, s2) -> Integer.compare(s1.length(), s2.length()));
	}

	private void iterandoComLambda() {
		
		/*
		 * Em vez de escrever a classe anônima, deixamos de escrever 
		 * alguns itens que podem ser inferidos.
		 * 
		 * Como a interface Consumer só tem um método, não precisamos escrever o 
		 * nome do método. 
		 * Também não daremos new. 
		 * Apenas declararemos os argumentos e o bloco a ser executado, 
		 * separados por ->
		 * 
		 *  É uma forma bem mais sucinta de escrever! 
		 *  Essa sintaxe funciona para qualquer interface que tenha apenas 
		 *  um método abstrato, e é por esse motivo que nem precisamos falar 
		 *  que estamos implementando o método accept, já que não há 
		 *  outra possibilidade.
		 *  
		 *  Uma interface que possui apenas um método abstrato é agora conhecida 
		 *  como interface funcional e pode ser utilizada dessa forma.
		 *  
		 *  
		 *  REFORÇANDO:
		 *  Uma interface funcional deve ter 1 único método abstrato. 
		 *  Além desse método ela pode ter outros métodos, contanto que sejam default ou 'static'.
		 *  Essa estrutura é fundamental, pois assim o compilador sabe exatamente que o corpo da 
		 *  expressão lambda que escrevemos é a implementação de seu único método abstrato
		 *  
		 *  NOTA: com o Lambda, não precisamos inferir o tipo da variável que estamos
		 *  recebendo via método
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
		 * As linhas acima nos evitam de criarmos códigos como esse:
		 */
		lista.forEach(new Consumer<String>() {
		    public void accept(String s) {
		        System.out.println(s);
		    }
		});

	}

	private void iterandoListaComJava8() {

		/*
		 * Vamos a um outro método default adicionado as coleções do Java: o forEach na
		 * interface Iterable. Como Iterable é mãe de Collection, temos acesso a esse
		 * método na nossa lista.
		 * 
		 * Se você abrir o JavaDoc ou utilizar o auto complete do Eclipse, verá que
		 * List.forEach recebe um Consumer, que é uma das muitas interfaces do novo
		 * pacote java.util.functions. Então vamos criar um consumidor de String,
		 * chamado ConsumidorDeString.
		 */
		Consumer<String> consumidor = new ConsumidorDeString();
		lista.forEach(consumidor);
	}

	private void ordenandoListaComCOmparatorComJava8() {
		/*
		 * Como fazemos hoje com o Java 8 para implementar um Comparator
		 * 
		 * Esse método sort() não existia antes na interface List, nem em suas mães
		 * (Collection e Iterable).
		 * 
		 * O Java 8 optou por criar um novo recurso que possibilitasse adicionar métodos
		 * em interfaces e implementá-los ali mesmo! Se você abrir o código fonte da
		 * interface List, verá que esse método tem um corpo.
		 * 
		 * É um default method! Um método de interface que você não precisa implementar
		 * na sua classe se não quiser, pois você terá já essa implementação default.
		 * 
		 * Default methods foi uma forma que o Java encontrou para evoluir interfaces
		 * antigas, sem gerar incompatibilidades. Não é uma novidade da linguagem:
		 * Scala, C# e outras possuem recursos similares e até mais poderosos. E repare
		 * que é diferente de uma classe abstrata: em uma interface você não pode ter
		 * atributos de instância, apenas esses métodos que delegam chamadas ou
		 * trabalham com os próprios métodos da interface.
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
	 * Verifica qual string é maior que a outra
	 */
	public int compare(String s1, String s2) {
		if (s1.length() < s2.length())
			return -1;
		if (s1.length() > s2.length())
			return 1;
		return 0;
	}
}