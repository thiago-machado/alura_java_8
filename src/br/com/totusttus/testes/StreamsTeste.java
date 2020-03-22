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
		 * Streams: trabalhando com coleções no java 8
		 * 
		 * E se quisermos fazer outras tarefas com essa coleção de cursos?
		 * 
		 * Por exemplo, filtrar apenas os cursos com mais de 100 alunos. Poderíamos
		 * fazer um loop que, dado o critério desejado seja atendido, adicionamos este
		 * curso em uma nova lista, a lista filtrada.
		 * 
		 * No Java 8, podemos fazer de uma forma muito mais interessante. Há como
		 * invocar um filter. Para sua surpresa, esse método não se encontra em List,
		 * nem em Collection, nem em nenhuma das interfaces já conhecidas. Ela está
		 * dentro de uma nova interface, a Stream. Você pode pegar um Stream de uma
		 * coleção simplesmente invocando cursos.stream().
		 * 
		 * O que fazemos com ele? O Stream devolvido por esse método tem uma dezena de
		 * métodos bastante úteis. O primeiro é o filter, que recebe um predicado (um
		 * critério), que deve devolver verdadeiro ou falso, dependendo se você deseja
		 * filtrá-lo ou não.
		 * 
		 * Repare que o filtro devolve também um Stream! É um exemplo do que chamam de
		 * fluent interface.
		 * 
		 * IMPORTANTE: modificações em um stream não modificam a coleção/objeto que o
		 * gerou. Tudo que é feito nesse fluxo de objetos, nesse Stream, não impacta,
		 * não tem efeitos colaterais na coleção original. A coleção original continua
		 * com os mesmos cursos!
		 */
		Stream<Curso> streamDeCurso = cursos.stream().filter(c -> c.getAlunos() > 100);

		System.out.println("\n*******************");
		streamDeCurso.forEach(c -> System.out.println(c.getNome()));

		/*
		 * Executando a mesna função acima, mas de forma resumida
		 */

		System.out.println("\n*******************");
		cursos.stream().filter(c -> c.getAlunos() > 100).forEach(c -> System.out.println(c.getNome()));

		/*
		 * E se quisermos, dados esses cursos filtrados no nosso fluxo (Stream) de
		 * objetos, um novo fluxo apenas com a quantidade de alunos de cada um deles?
		 * Utilizamos o map.
		 * 
		 * Se você reparar, esse map não devolve um Stream<Curso>, e sim um
		 * Stream<Integer>!
		 * 
		 * A leitura ficaria assim: cursos.stream() .filter(sleecione somente o curso
		 * que tenha mais de 100 alunos) .map(crie um Stream<Integer> utilizando o
		 * numero de alunos) .forEach(imprima esse Stream);
		 */
		System.out.println("\n*******************");
		cursos.stream().filter(c -> c.getAlunos() > 100).map(c -> c.getAlunos()).forEach(i -> System.out.println(i));

		/*
		 * Gerando uma coleção a partir de um Stream
		 * 
		 * Invocar métodos no stream de uma coleção não altera o conteúdo da coleção
		 * original. Ele não gera efeitos colaterais. Como então obter uma coleção
		 * depois de alterar um Stream?
		 * 
		 * O método Collect recebe um Collector, uma interface não tão trivial de se
		 * implementar. Podemos usar a classe Collectors (repare o s no final), cheio de
		 * factory methods que ajudam na criação de coletores. Um dos coletores mais
		 * utilizados é o retornado por Collectors.toList()
		 */
		System.out.println("\n*******************");
		List<Long> listaInteiros = cursos.stream().map(c -> Long.valueOf(c.getAlunos())).collect(Collectors.toList());
		listaInteiros.forEach(System.out::println);

		/*
		 * Streams primitivos
		 * 
		 * 
		 * Trabalhar com Streams vai ser frequente no seu dia a dia. Há um cuidado a ser
		 * tomado: com os tipos primitivos. Quando fizemos o map(Curso::getAlunos),
		 * recebemos de volta um Stream<Integer>, que acaba fazendo o autoboxing dos
		 * ints. Isto é, utilizará mais recursos da JVM. Claro que, se sua coleção é
		 * pequena, o impacto será irrisório. Mas é possível trabalhar só com ints,
		 * invocando o método mapToInt
		 * 
		 * Ele devolve um IntStream, que não vai gerar autoboxing e possui novos métodos
		 * específicos para trabalhar com inteiros. Um exemplo? A soma!
		 * 
		 * A leitura fica assim: cursos.stream().filter(pegando os cursos que tem mais
		 * de 100 alunos) .mapToInt(pegando somente o numero de alunos do
		 * model).somaTodosOsValoresDessaNovaCollection();
		 */
		System.out.println("\n*******************");
		int soma = cursos.stream().filter(c -> c.getAlunos() > 100).mapToInt(c -> c.getAlunos()).sum();
		System.out.println(soma);

		/*
		 * Vamos conhecer outros métodos interessantes dos Streams. Um exemplo seria:
		 * quero um curso que tenha mais de 100 alunos! Pode ser qualquer um deles. Há o
		 * método findAny.
		 * 
		 * O que será que devolve o findAny? Um Curso? Não! Um Optional<Curso>.
		 * 
		 * Optional é uma importante nova classe do Java 8. É com ele que poderemos
		 * trabalhar de uma maneira mais organizada com possíveis valores null. Em vez
		 * de ficar comparando if(algumaCoisa == null), o Optional já fornece uma série
		 * de métodos para nos ajudar nessas situações. Por que o findAny utiliza esse
		 * recurso? Pois pode não haver nenhum curso com mais de 100 alunos! Nesse caso,
		 * o que seria retornado? null? uma exception?
		 */
		Optional<Curso> optional = cursos.stream().filter(c -> c.getAlunos() > 100).findAny();

		/*
		 * O orElse diz que ele deve devolver o curso que existe dentro desse optional,
		 * ou então o que foi passado como argumento.
		 * 
		 * Nesse caso ou ele devolve o curso encontrado, ou null, caso nenhum seja
		 * encontrado.
		 */
		Curso curso = optional.orElse(null);
		if (curso != null)
			System.out.println(curso.getNome());

		/*
		 * Mesmo assim, ainda não está tão interessante. Há como evitar tanto o null,
		 * quanto as exceptions, quanto os ifs. O método ifPresent executa um lambda (um
		 * Consumer) no caso de existir um curso dentro daquele optional.
		 */

		optional.ifPresent(c -> System.out.println(c.getNome()));

		
		/*
		 * Podemos gerar mapas! Queremos um mapa que, dado o nome do curso, o valor
		 * atrelado é a quantidade alunos. Um Map<String, Integer>. Utilizamos o
		 * Collectors.toMap. Ele recebe duas Functions. A primeira indica o que vai ser
		 * a chave, e a segunda o que será o valor:
		 */
		Map mapa = cursos.stream().filter(c -> c.getAlunos() > 100)
				.collect(Collectors.toMap(c -> c.getNome(), c -> c.getAlunos()));
		
		System.out.println(mapa);

	}
}
