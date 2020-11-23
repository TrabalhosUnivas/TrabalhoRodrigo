package br.edu.univas.main;

import java.util.Scanner;

import br.edu.univas.vo.Jogos;
import br.edu.univas.vo.Times;

public class StartApp {

	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {

		String option;
		Jogos[] jogos = new Jogos[50];
		Times[] times = new Times[50];
		
	do {
		System.out.println("1 - Cadastrar Time");
		System.out.println("2 - Editar Time");
		System.out.println("3 - Excluir Time");
		System.out.println("4 - Cadastrar Jogo");
		System.out.println("5 - Editar Jogo");
		System.out.println("6 - Excluir Jogo");
		System.out.println("7 - Listar Classificação do Campeonato");
		System.out.println("9 - Sair");
		System.out.println("\n Digite o número da opção desejada: ");
		
		option = sc.nextLine();
		if (option.equals("1")) {
			cadastrarTime(times);
		} else if (option.equals("2")) {
			editarTime(times, jogos);
		} else if (option.equals("3")) {
			excluirTime(times, jogos);
		} else if (option.equals("4")) {
			cadastrarJogo(times, jogos);
		} else if (option.equals("5")) {
			editarJogo(times, jogos);
		} else if (option.equals("6")) {
			excluirJogo(times, jogos);
		} else if (option.equals("7")) {
			rank(times);
		} else if (option.equals("9")) {
			System.out.println("\n \n \n FIM!");
			break;
		} else {

		}
	} while (true);
	sc.close();
}
	public static void cadastrarTime(Times[] times) {
		int i = 0;
		do {
			if (times[i] == null) {
				Times time = new Times();
				System.out.println("\n CADASTRAR TIMES  ");
				System.out.println("Escreva o nome do time: ");
				time.nome = sc.nextLine();
				System.out.println("Escreva a UF: ");
				time.estadoOrigem = sc.nextLine();
				times[i] = time;
				break;
			}
			i++;
		} while (true);
	}

	public static void editarTime(Times[] times, Jogos[] jogos) {
		listarTimes(times);
		System.out.println("Escreva o time que deseja EDITAR: ");
		String nome = sc.nextLine();
		for (int i = 0; i < 51; i++) {
			if (times[i] != null) {
				Times time = times[i];
				if (nome.equals(time.nome)) {
					System.out.println("Escreva o novo nome: ");
					String novo = sc.nextLine();
					for (int j = 0; j < 50; j++) {
						if (jogos[j] != null && jogos[j].mandante.equals(time.nome)) {
							jogos[j].mandante = novo;
						} else if (jogos[j] != null && jogos[j].visitante.equals(time.nome)) {
							jogos[j].visitante = novo;
						}
					}
					time.nome = novo;
					System.out.println("Escreva a sigla UF: ");
					String novoUF = sc.nextLine();
					time.estadoOrigem = novoUF;
					times[i] = time;
					break;
				}
			}
		}
	}
	public static void cadastrarJogo(Times[] times, Jogos[] jogos) {
		int i = 0;
		do {
			if (jogos[i] == null) {
				Jogos jogo = new Jogos();
				System.out.println("\n Cadastrar jogo");
				System.out.println("Escreva o nome do TIME MANDANTE: ");
				jogo.mandante = sc.nextLine();
				System.out.println("Escreva a quantidade de gols do time MANDANTE: ");
				jogo.gMandante = sc.nextInt();
				System.out.println("Escreva o nome do TIME VISITANTE: ");
				jogo.visitante = sc.nextLine();
				System.out.println("Escreva a quantidade de gols do time VISITANTE: ");
				jogo.gVisitante = sc.nextInt();
				sc.nextLine();
				jogos[i] = jogo;
				distribuirPontos(times, jogos, i);
				break;
			}
			i++;
		} while (true);
	}
	public static void editarJogo(Times[] times, Jogos[] jogos) {
		listarJogos(jogos);
		System.out.println("Escreva o número do jogo que deseja editar: ");
		int i = sc.nextInt() - 1;
		sc.nextLine();
		Jogos jogo = jogos[i];
		System.out.println("EDITANDO");
		System.out.println("Escreva o nome do TIME MANDANTE: ");
		jogo.mandante = sc.nextLine();
		System.out.println("Escreva o nome do TIME VISITANTE: ");
		jogo.visitante = sc.nextLine();
		System.out.println("Escreva os gols do time MANDANTE: ");
		jogo.gMandante = sc.nextInt();
		System.out.println("Escreva os gols do time VISITANTE: ");
		jogo.gVisitante = sc.nextInt();
		sc.nextLine();
		jogos[i] = jogo;
		distribuirPontos(times, jogos, i);
	}
	public static void distribuirPontos(Times[] times, Jogos[] jogos, int qJogos) {
		int m = 0, v = 0, i = 0, j = 0;
		Jogos jogo = jogos[qJogos];

		do {
			if (times[i] != null) {
				Times time = times[i];
				if (time.nome.equals(jogo.mandante)) {
					m = i;
					j++;
				} else if (time.nome.equals(jogo.visitante)) {
					v = i;
					j++;
				}
				if (j == 2) {
					break;
				}
			}

			i++;
		} while (true);
		if (jogo.gMandante > jogo.gVisitante) {
			times[m].pontos = times[m].pontos + 3;
			times[m].saldoGols = times[m].saldoGols + (jogo.gMandante - jogo.gVisitante);
			times[v].saldoGols = times[v].saldoGols + (jogo.gVisitante - jogo.gMandante);
		} else if (jogo.gVisitante > jogo.gMandante) {
			times[v].pontos = times[v].pontos + 3;
			times[m].saldoGols = times[m].saldoGols + (jogo.gMandante - jogo.gVisitante);
			times[v].saldoGols = times[v].saldoGols + (jogo.gVisitante - jogo.gMandante);
		} else if (jogo.gVisitante == jogo.gMandante) {
			times[m].pontos = times[m].pontos + 1;
			times[v].pontos = times[v].pontos + 1;
		}
	}
	public static void listarJogos(Jogos[] jogos) {
		int i = 0;
		System.out.println("Listagem de Jogos Cadastrados: ");
		do {
			if (i == 50) {
				break;
			}
			if (jogos[i] != null) {
				Jogos jogo = jogos[i];
				System.out.println((i + 1) + ".      " + jogo.mandante + " " + jogo.gMandante + " x " + jogo.gVisitante+ " "
						+ jogo.visitante);
			}

			i++;
		} while (true);
	}

	public static void listarTimes(Times[] times) {
		int i = 0;
		System.out.println("Listar Classificação do Campeonato: ");
		do {
			if (i == 50) {
				break;
			}
			if (times[i] != null) {
				Times time = times[i];
				System.out.println(time.nome);
			}

			i++;
		} while (true);
	}

	public static void excluirJogo(Times[] times, Jogos[] jogos) {
		listarJogos(jogos);
		System.out.println("Escreva o número do jogo que deseja excluir: ");
		int i = sc.nextInt() - 1;
		sc.nextLine();
		tirarPontos(i, jogos, times);
		jogos[i] = null;
	}

	public static void excluirTime(Times[] times, Jogos[] jogos) {
		listarTimes(times);
		int i = 0;
		System.out.println("Escreva o nome do time que deseja excluir: ");
		String nome = sc.nextLine();
		do {
			if (times[i] != null) {
				Times time = times[i];
				if (nome.equals(time.nome)) {
					break;
				}
			}
			i++;
		} while (true);
		Times time = times[i];
		for (int j = 0; j < 50; j++) {
			if (jogos[j] != null) {
				Jogos jogo = jogos[j];
				if (jogo.mandante.equals(time.nome)) {
					tirarPontos(j, jogos, times);
					jogos[j] = null;
				} else if (jogo.visitante.equals(time.nome)) {
					tirarPontos(j, jogos, times);
					jogos[j] = null;
				}
			}
		}
		times[i] = null;
	}

	public static void tirarPontos(int i, Jogos[] jogos, Times[] times) {
		Jogos jogo = jogos[i];
		int j = 0, m = 0, v = 0, k = 0;
		do {
			if (times[j] != null) {
				Times time = times[j];
				if (time.nome.equals(jogo.mandante)) {
					m = j;
					k++;
				} else if (time.nome.equals(jogo.visitante)) {
					v = j;
					k++;
				}
				if (k == 2) {
					break;
				}
			}
			j++;
		} while (true);
		if (jogo.gMandante > jogo.gVisitante) {
			times[m].pontos = times[m].pontos - 3;
			times[m].saldoGols = times[m].saldoGols - (jogo.gMandante - jogo.gVisitante);
			times[v].saldoGols = times[v].saldoGols - (jogo.gVisitante - jogo.gMandante);
		} else if (jogo.gVisitante > jogo.gMandante) {
			times[v].pontos = times[v].pontos - 3;
			times[m].saldoGols = times[m].saldoGols - (jogo.gMandante - jogo.gVisitante);
			times[v].saldoGols = times[v].saldoGols - (jogo.gVisitante - jogo.gMandante);
		} else if (jogo.gVisitante == jogo.gMandante) {
			times[m].pontos = times[m].pontos - 1;
			times[v].pontos = times[v].pontos - 1;
		}
	}

	public static void rank(Times[] times) {
		Times[] tabela = new Times[50];
		int x = passarValores(times, tabela);
		ordenar(tabela, x);
		imprimirTabela(tabela, x);
	}
	
	public static void imprimirTabela(Times[] tabela, int x) {
		System.out.println("CLASSIFICAÇÃO \n");
		int j = 1;
		for(int i = x; i >= 0; i--) {
			if(tabela[i] != null) {
				Times time = tabela[i];
				System.out.println(j+"º "+time.nome+" - "+time.pontos+" pontos - saldo: "+time.saldoGols);
				j++;
			}
		}
	}

	public static int passarValores(Times[] times, Times[] tabela) {
		int j = 0;
		for (int i = 0; i < 50; i++) {
			if (times[i] != null) {
				Times time = times[i];
				Times time2 = new Times();
				time2.nome = time.nome;
				time2.estadoOrigem = time.estadoOrigem;
				time2.pontos = time.pontos;
				time2.saldoGols = time.saldoGols;
				tabela[j] = time2;
				j++;
			}
		}
		return j;
	}

	public static void ordenar(Times[] tabela, int x) {
		for (int i = 0; i < x - 1; i++) {
			for (int j = 0; j < x - 1 - i; j++) {
				if (tabela[j] != null && tabela[j + 1] != null && tabela[j].pontos >= tabela[j + 1].pontos
						&& tabela[j].saldoGols > tabela[j + 1].saldoGols) {
					Times timeAux = tabela[j];
					tabela[j] = tabela[j+1];
					tabela[j+1] = timeAux;
				}
			}
		}
	}
	


}