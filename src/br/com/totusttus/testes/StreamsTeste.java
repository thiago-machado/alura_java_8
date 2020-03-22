package br.com.totusttus.testes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.totusttus.testes.model.Curso;

public class StreamsTeste {

	public static void main(String[] args) {

		List<Curso> cursos = new ArrayList<Curso>();
		cursos.add(new Curso("Python", 45));
		cursos.add(new Curso("JavaScript", 150));
		cursos.add(new Curso("Java 8", 113));
		cursos.add(new Curso("C", 55));

		// Ordenando a lista pela quantidade de alunos
		cursos.sort(Comparator.comparingInt(Curso::getAlunos));
		cursos.forEach(System.out::println);

		/*
		 * Streams: trabalhando com cole��es no java 8
		 * 
		 * E se quisermos fazer outras tarefas com essa cole��o de cursos?
		 * 
		 * Por exemplo, filtrar apenas os cursos com mais de 100 alunos. Poder�amos
		 * fazer um loop que, dado o crit�rio desejado seja atendido, adicionamos este
		 * curso em uma nova lista, a lista filtrada.
		 * 
		 * No Java 8, podemos fazer de uma forma muito mais interessante. H� como
		 * invocar um filter. Para sua surpresa, esse m�todo n�o se encontra em List,
		 * nem em Collection, nem em nenhuma das interfaces j� conhecidas. Ela est�
		 * dentro de uma nova interface, a Stream. Voc� pode pegar um Stream de uma
		 * cole��o simplesmente invocando cursos.stream().
		 * 
		 * O que fazemos com ele? O Stream devolvido por esse m�todo tem uma dezena de
		 * m�todos bastante �teis. O primeiro � o filter, que recebe um predicado (um
		 * crit�rio), que deve devolver verdadeiro ou falso, dependendo se voc� deseja
		 * filtr�-lo ou n�o.
		 * 
		 * Repare que o filtro devolve tamb�m um Stream! � um exemplo do que chamam de
		 * fluent interface.
		 * 
		 * IMPORTANTE: modifica��es em um stream n�o modificam a cole��o/objeto que o
		 * gerou. Tudo que � feito nesse fluxo de objetos, nesse Stream, n�o impacta,
		 * n�o tem efeitos colaterais na cole��o original. A cole��o original continua
		 * com os mesmos cursos!
		 */
		Stream<Curso> streamDeCurso = cursos.stream().filter(c -> c.getAlunos() > 100);

		System.out.println("\n*******************");
		streamDeCurso.forEach(c -> System.out.println(c.getNome()));

		/*
		 * Executando a mesna fun��o acima, mas de forma resumida
		 */

		System.out.println("\n*******************");
		cursos.stream().filter(c -> c.getAlunos() > 100).forEach(c -> System.out.println(c.getNome()));

		/*
		 * E se quisermos, dados esses cursos filtrados no nosso fluxo (Stream) de
		 * objetos, um novo fluxo apenas com a quantidade de alunos de cada um deles?
		 * Utilizamos o map.
		 * 
		 * Se voc� reparar, esse map n�o devolve um Stream<Curso>, e sim um
		 * Stream<Integer>!
		 * 
		 * A leitura ficaria assim: cursos.stream() .filter(sleecione somente o curso
		 * que tenha mais de 100 alunos) .map(crie um Stream<Integer> utilizando o
		 * numero de alunos) .forEach(imprima esse Stream);
		 */
		System.out.println("\n*******************");
		cursos.stream().filter(c -> c.getAlunos() > 100).map(c -> c.getAlunos()).forEach(i -> System.out.println(i));

		/*
		 * Gerando uma cole��o a partir de um Stream
		 * 
		 * Invocar m�todos no stream de uma cole��o n�o altera o conte�do da cole��o
		 * original. Ele n�o gera efeitos colaterais. Como ent�o obter uma cole��o
		 * depois de alterar um Stream?
		 * 
		 * O m�todo Collect recebe um Collector, uma interface n�o t�o trivial de se
		 * implementar. Podemos usar a classe Collectors (repare o s no final), cheio de
		 * factory methods que ajudam na cria��o de coletores. Um dos coletores mais
		 * utilizados � o retornado por Collectors.toList()
		 */
		System.out.println("\n*******************");
		List<Long> listaInteiros = cursos.stream().map(c -> Long.valueOf(c.getAlunos())).collect(Collectors.toList());
		listaInteiros.forEach(System.out::println);

		/*
		 * Streams primitivos
		 * 
		 * 
		 * Trabalhar com Streams vai ser frequente no seu dia a dia. H� um cuidado a ser
		 * tomado: com os tipos primitivos. Quando fizemos o map(Curso::getAlunos),
		 * recebemos de volta um Stream<Integer>, que acaba fazendo o autoboxing dos
		 * ints. Isto �, utilizar� mais recursos da JVM. Claro que, se sua cole��o �
		 * pequena, o impacto ser� irris�rio. Mas � poss�vel trabalhar s� com ints,
		 * invocando o m�todo mapToInt
		 * 
		 * Ele devolve um IntStream, que n�o vai gerar autoboxing e possui novos m�todos
		 * espec�ficos para trabalhar com inteiros. Um exemplo? A soma!
		 * 
		 * A leitura fica assim: cursos.stream().filter(pegando os cursos que tem mais
		 * de 100 alunos) .mapToInt(pegando somente o numero de alunos do
		 * model).somaTodosOsValoresDessaNovaCollection();
		 */
		System.out.println("\n*******************");
		int soma = cursos.stream().filter(c -> c.getAlunos() > 100).mapToInt(c -> c.getAlunos()).sum();
		System.out.println(soma);

		/*
		 * Vamos conhecer outros m�todos interessantes dos Streams. Um exemplo seria:
		 * quero um curso que tenha mais de 100 alunos! Pode ser qualquer um deles. H� o
		 * m�todo findAny.
		 * 
		 * O que ser� que devolve o findAny? Um Curso? N�o! Um Optional<Curso>.
		 * 
		 * Optional � uma importante nova classe do Java 8. � com ele que poderemos
		 * trabalhar de uma maneira mais organizada com poss�veis valores null. Em vez
		 * de ficar comparando if(algumaCoisa == null), o Optional j� fornece uma s�rie
		 * de m�todos para nos ajudar nessas situa��es. Por que o findAny utiliza esse
		 * recurso? Pois pode n�o haver nenhum curso com mais de 100 alunos! Nesse caso,
		 * o que seria retornado? null? uma exception?
		 */
		Optional<Curso> optional = cursos.stream().filter(c -> c.getAlunos() > 100).findAny();

		/*
		 * O orElse diz que ele deve devolver o curso que existe dentro desse optional,
		 * ou ent�o o que foi passado como argumento.
		 * 
		 * Nesse caso ou ele devolve o curso encontrado, ou null, caso nenhum seja
		 * encontrado.
		 */
		Curso curso = optional.orElse(null);
		if (curso != null)
			System.out.println(curso.getNome());

		/*
		 * Mesmo assim, ainda n�o est� t�o interessante. H� como evitar tanto o null,
		 * quanto as exceptions, quanto os ifs. O m�todo ifPresent executa um lambda (um
		 * Consumer) no caso de existir um curso dentro daquele optional.
		 */

		optional.ifPresent(c -> System.out.println(c.getNome()));

		
		/*
		 * Podemos gerar mapas! Queremos um mapa que, dado o nome do curso, o valor
		 * atrelado � a quantidade alunos. Um Map<String, Integer>. Utilizamos o
		 * Collectors.toMap. Ele recebe duas Functions. A primeira indica o que vai ser
		 * a chave, e a segunda o que ser� o valor:
		 */
		Map mapa = cursos.stream().filter(c -> c.getAlunos() > 100)
				.collect(Collectors.toMap(c -> c.getNome(), c -> c.getAlunos()));
		
		System.out.println(mapa);

	}
}
