package br.com.totusttus.testes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DatasTeste {

	public static void main(String[] args) {

		/*
		 * Para representar uma data em java agora eu posso utilizar a classe LocalDate,
		 * presente no pacote java.time.
		 */
		LocalDate hoje = LocalDate.now();
		System.out.println(hoje);

		/*
		 * Criando uma data que representara a Copa de 2022
		 */
		LocalDate proximaCopa = LocalDate.of(2022, Month.JUNE, 5);

		/*
		 * Para saber a diferen�a entre duas datas podemos utilizar seu m�todo between.
		 * 
		 * Pesquise a respeito da classe Duration caso queira trabalhar com intervalo de horas.
		 */
		Period periodo = Period.between(hoje, proximaCopa);
		System.out.println("Falta " + periodo.getYears() + " anos, " + periodo.getMonths() + " meses e "
				+ periodo.getDays() + " dias.");

		/*
		 * Incrementando e decrementando datas
		 * 
		 * 
		 * Outra coisa bem comum em nosso dia a dia � quando queremos saber o dia
		 * anterior, ou posterior a uma data. Por exemplo como saber qual a data de
		 * amanh�? H� diversos m�todos pra nos ajudar com isso, vamos encontrar na API
		 * diversos m�todos minus ou plus para as diferentes unidades de tempo.
		 */
		System.out.println(hoje.minusYears(1));
		System.out.println(hoje.minusMonths(4));
		System.out.println(hoje.minusDays(2));

		System.out.println(hoje.plusYears(1));
		System.out.println(hoje.plusMonths(4));
		System.out.println(hoje.plusDays(2));

		/*
		 * Uma API imut�vel
		 * 
		 * Sabendo disso podemos escrever o seguinte c�digo para incrementar 4 anos na
		 * data atual, para saber quando ser� a pr�xima Olimp�ada, por exemplo.
		 */
		proximaCopa.plusYears(6);
		System.out.println(proximaCopa);

		/*
		 * Mas repare que a sa�da desse c�digo ainda ser� a data atual. Porque isso
		 * ocorreu? Da mesma forma que as novas API's, como o Stream, os m�todos da API
		 * de datas sempre v�o retornar uma nova instancia da sua data.
		 * 
		 * Ou seja, toda a API de datas � imut�vel. Ela nunca vai alterar a data
		 * original.
		 * 
		 * Portanto precisamos fazer algo como:
		 */

		LocalDate novaProximaCopa = proximaCopa.plusYears(6);
		System.out.println(novaProximaCopa);

		/*
		 * Formatando suas datas
		 * 
		 * Para formatar nossas datas podemos utilizar o DateTimeFormatter. Existem
		 * diversos j� prontos, mas h� ainda a alternativa de voc� criar o seu pr�prio
		 * formatador no padr�o j� conhecido.
		 * 
		 */

		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		String valorFormatado = proximaCopa.format(formatador);
		System.out.println(valorFormatado);

		/*
		 * Trabalhando com medida de tempo
		 * 
		 * Por enquanto s� estamos trabalhando com datas, fazendo formata��es e
		 * manipulando seu resultado. Mas � muito comum tamb�m precisar trabalhar com
		 * horas, minutos e segundos. Ou seja, trabalhar com uma medida de data com
		 * tempo.
		 * 
		 */
		LocalDateTime agora = LocalDateTime.now();

		/*
		 * Podemos criar um novo formatador para mostrar as horas, minutos e segundos
		 * para conseguirmos ver o resultado j� formatado:
		 */

		DateTimeFormatter formatadorComHoras = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
		System.out.println(LocalDateTime.now().format(formatadorComHoras));

		/*
		 * Lidando com modelos mais espec�ficos
		 * 
		 * � muito comum ignorarmos valores quando precisamos apenas de algumas medidas
		 * de tempo, como por exemplo ano e m�s. Nessa caso no lugar de criarmos um
		 * LocalDate ou algo assim e ignorar o seu valor de dia, podemos trabalhar com
		 * os modelos mais espec�ficos da nova API.
		 */

		YearMonth anoEMes = YearMonth.of(2020, Month.AUGUST);

		/*
		 * Outro exemplo, para trabalharmos apenas com tempo podemos utilizar o
		 * LocalTime.
		 */
		LocalTime intervalo = LocalTime.of(12, 30);
		System.out.println(intervalo);
	}
}
