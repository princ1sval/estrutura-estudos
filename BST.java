import java.util.ArrayList;
import java.util.List;

/*
 * =====================================================================
 *  BST DE ESTUDO - todos os metodos que podem cair na Bancada 2
 * =====================================================================
 *
 *  Mesma estrutura da lib do trabalho: No (valor, esq, dir) e raiz privada.
 *  Qualquer metodo daqui pode ser copiado para a sua BST. Se ele usar um
 *  auxiliar (ex.: buscarNo, altura(No)), copie o auxiliar junto.
 *
 *  Os comentarios estao sem acento de proposito (evita erro de codificacao
 *  do javac no Windows).
 *
 *  ---------------------------------------------------------------
 *  AS 5 RECEITAS (quase toda pergunta cai em uma delas)
 *  ---------------------------------------------------------------
 *
 *  R1 - VISITA TUDO (recursao)
 *       public int f() { return f(raiz); }
 *       private int f(No no) {
 *           if (no == null) return NEUTRO;      // 0 p/ contar e somar, -1 p/ altura
 *           ... resolve f(no.esq) e f(no.dir) e junta com no.valor ...
 *       }
 *       Custo: O(n), porque passa por todos os nos.
 *
 *  R2 - DESCE UM CAMINHO (laco)
 *       No atual = raiz;
 *       while (atual != null) {
 *           if (achou) return ...;
 *           if (valor < atual.valor) atual = atual.esq; else atual = atual.dir;
 *       }
 *       Custo: O(h). Balanceada: O(log n). Degenerada: O(n).
 *
 *  R3 - MONTA LISTA (recursao com a lista passada como parametro)
 *       public List<Integer> f() { List<Integer> l = new ArrayList<>(); f(raiz, l); return l; }
 *       private void f(No no, List<Integer> l) { if (no == null) return; ... l.add(no.valor) ... }
 *
 *  R4 - POR NIVEL (fila). Usa um ArrayList como fila: "inicio" anda pra frente.
 *       List<No> fila = new ArrayList<>(); fila.add(raiz); int inicio = 0;
 *       while (inicio < fila.size()) { No atual = fila.get(inicio); inicio++; ...add filhos... }
 *
 *  R5 - VISITA TUDO, MAS PODA usando a regra da BST
 *       Se no.valor < a, a esquerda inteira tambem e < a: nem desce nela.
 *       Usado em intervalos, "maiores que x", "menores que x".
 *
 *  INDICE DAS SECOES
 *   1. Basico (a API do trabalho)
 *   2. Versoes alternativas (recursiva <-> iterativa)
 *   3. Contagens
 *   4. Somas, media e mediana
 *   5. Altura, niveis e profundidade
 *   6. Buscas que usam a regra da BST
 *   7. Percursos e listas
 *   8. Verificacoes (metodos boolean)
 *   9. Modificacao (remover, podar, balancear, copiar...)
 *  10. Impressao
 * =====================================================================
 */
public class BST {
    private No raiz;
    private int quantidade; // so muda quando um no e criado ou removido

    // =================================================================
    // 1. BASICO (a API do trabalho)
    // =================================================================

    // inserir: recursivo. Devolve a subarvore ja com o valor dentro.
    // Repetido nao entra. O(h).
    public void inserir(int valor) {
        raiz = inserir(raiz, valor);
    }

    private No inserir(No no, int valor) {
        if (no == null) {
            quantidade++;
            return new No(valor);
        }
        if (valor < no.valor) {
            no.esq = inserir(no.esq, valor);
        } else if (valor > no.valor) {
            no.dir = inserir(no.dir, valor);
        }
        return no; // igual: nao faz nada
    }

    // contem: R2. O(h).
    public boolean contem(int valor) {
        No atual = raiz;
        while (atual != null) {
            if (valor == atual.valor) {
                return true;
            }
            if (valor < atual.valor) {
                atual = atual.esq;
            } else {
                atual = atual.dir;
            }
        }
        return false;
    }

    // tamanho: le o contador. O(1).
    public int tamanho() {
        return quantidade;
    }

    // altura em arestas: R1. Vazia = -1, um no = 0. O(n).
    public int altura() {
        return altura(raiz);
    }

    private int altura(No no) {
        if (no == null) {
            return -1;
        }
        return 1 + Math.max(altura(no.esq), altura(no.dir));
    }

    // contarFolhas: R1. Folha = sem filho nenhum. O(n).
    public int contarFolhas() {
        return contarFolhas(raiz);
    }

    private int contarFolhas(No no) {
        if (no == null) {
            return 0;
        }
        if (no.esq == null && no.dir == null) {
            return 1;
        }
        return contarFolhas(no.esq) + contarFolhas(no.dir);
    }

    // minimo: no mais a esquerda. R2. O(h).
    public int minimo() {
        if (raiz == null) {
            throw new IllegalStateException("Arvore vazia");
        }
        No atual = raiz;
        while (atual.esq != null) {
            atual = atual.esq;
        }
        return atual.valor;
    }

    // maximo: no mais a direita. R2. O(h).
    public int maximo() {
        if (raiz == null) {
            throw new IllegalStateException("Arvore vazia");
        }
        No atual = raiz;
        while (atual.dir != null) {
            atual = atual.dir;
        }
        return atual.valor;
    }

    // somaValores: R1. O(n).
    public int somaValores() {
        return somaValores(raiz);
    }

    private int somaValores(No no) {
        if (no == null) {
            return 0;
        }
        return no.valor + somaValores(no.esq) + somaValores(no.dir);
    }

    // profundidade: arestas da raiz ate o valor. Ausente = -1. R2. O(h).
    public int profundidade(int valor) {
        No atual = raiz;
        int arestas = 0;
        while (atual != null) {
            if (valor == atual.valor) {
                return arestas;
            }
            if (valor < atual.valor) {
                atual = atual.esq;
            } else {
                atual = atual.dir;
            }
            arestas++;
        }
        return -1;
    }

    // Percursos: R3. O(n). So muda a posicao do lista.add.
    public List<Integer> emOrdem() {           // esquerda, RAIZ, direita -> sai ordenado
        List<Integer> lista = new ArrayList<>();
        emOrdem(raiz, lista);
        return lista;
    }

    private void emOrdem(No no, List<Integer> lista) {
        if (no == null) {
            return;
        }
        emOrdem(no.esq, lista);
        lista.add(no.valor);
        emOrdem(no.dir, lista);
    }

    public List<Integer> preOrdem() {          // RAIZ, esquerda, direita
        List<Integer> lista = new ArrayList<>();
        preOrdem(raiz, lista);
        return lista;
    }

    private void preOrdem(No no, List<Integer> lista) {
        if (no == null) {
            return;
        }
        lista.add(no.valor);
        preOrdem(no.esq, lista);
        preOrdem(no.dir, lista);
    }

    public List<Integer> posOrdem() {          // esquerda, direita, RAIZ
        List<Integer> lista = new ArrayList<>();
        posOrdem(raiz, lista);
        return lista;
    }

    private void posOrdem(No no, List<Integer> lista) {
        if (no == null) {
            return;
        }
        posOrdem(no.esq, lista);
        posOrdem(no.dir, lista);
        lista.add(no.valor);
    }

    // estaVazia: O(1).
    public boolean estaVazia() {
        return raiz == null;
    }

    // inserirTodos: atalho para os testes. "int... valores" aceita
    // inserirTodos(1, 2, 3) ou inserirTodos(umArray). O(k * h).
    public void inserirTodos(int... valores) {
        for (int v : valores) {
            inserir(v);
        }
    }

    // contarArestas: toda arvore com n nos tem n - 1 arestas
    // (cada no, menos a raiz, tem exatamente uma aresta subindo pro pai). O(1).
    public int contarArestas() {
        if (raiz == null) {
            return 0;
        }
        return quantidade - 1;
    }

    // Auxiliar usado por varios metodos: devolve o NO que tem o valor (ou null). R2. O(h).
    private No buscarNo(int valor) {
        No atual = raiz;
        while (atual != null) {
            if (valor == atual.valor) {
                return atual;
            }
            if (valor < atual.valor) {
                atual = atual.esq;
            } else {
                atual = atual.dir;
            }
        }
        return null;
    }

    // =================================================================
    // 2. VERSOES ALTERNATIVAS (o professor pode pedir "faca com laco"
    //    ou "faca com recursao")
    // =================================================================

    // inserirIterativo: mesmo resultado do inserir, com laco. O(h).
    // Ideia: desce ate achar um filho null do lado certo e pendura ali.
    public void inserirIterativo(int valor) {
        if (raiz == null) {
            raiz = new No(valor);
            quantidade++;
            return;
        }
        No atual = raiz;
        while (true) {
            if (valor == atual.valor) {
                return; // repetido
            }
            if (valor < atual.valor) {
                if (atual.esq == null) {
                    atual.esq = new No(valor);
                    quantidade++;
                    return;
                }
                atual = atual.esq;
            } else {
                if (atual.dir == null) {
                    atual.dir = new No(valor);
                    quantidade++;
                    return;
                }
                atual = atual.dir;
            }
        }
    }

    // contemRecursivo: O(h).
    public boolean contemRecursivo(int valor) {
        return contemRecursivo(raiz, valor);
    }

    private boolean contemRecursivo(No no, int valor) {
        if (no == null) {
            return false;
        }
        if (valor == no.valor) {
            return true;
        }
        if (valor < no.valor) {
            return contemRecursivo(no.esq, valor);
        }
        return contemRecursivo(no.dir, valor);
    }

    // tamanhoRecursivo: conta os nos sem usar o campo quantidade. R1. O(n).
    // Se o professor proibir o contador, e esse que voce usa no tamanho().
    public int tamanhoRecursivo() {
        return tamanhoRecursivo(raiz);
    }

    private int tamanhoRecursivo(No no) {
        if (no == null) {
            return 0;
        }
        return 1 + tamanhoRecursivo(no.esq) + tamanhoRecursivo(no.dir);
    }

    // minimoRecursivo / maximoRecursivo: O(h).
    public int minimoRecursivo() {
        if (raiz == null) {
            throw new IllegalStateException("Arvore vazia");
        }
        return minimoRecursivo(raiz);
    }

    private int minimoRecursivo(No no) {
        if (no.esq == null) {
            return no.valor;
        }
        return minimoRecursivo(no.esq);
    }

    public int maximoRecursivo() {
        if (raiz == null) {
            throw new IllegalStateException("Arvore vazia");
        }
        return maximoRecursivo(raiz);
    }

    private int maximoRecursivo(No no) {
        if (no.dir == null) {
            return no.valor;
        }
        return maximoRecursivo(no.dir);
    }

    // profundidadeRecursiva: O(h).
    // Se o filho devolveu -1 (nao achou), repassa -1. Senao, soma 1 aresta.
    public int profundidadeRecursiva(int valor) {
        return profundidadeRecursiva(raiz, valor);
    }

    private int profundidadeRecursiva(No no, int valor) {
        if (no == null) {
            return -1;
        }
        if (valor == no.valor) {
            return 0;
        }
        int resultado;
        if (valor < no.valor) {
            resultado = profundidadeRecursiva(no.esq, valor);
        } else {
            resultado = profundidadeRecursiva(no.dir, valor);
        }
        if (resultado == -1) {
            return -1;
        }
        return resultado + 1;
    }

    // alturaIterativa: R4 (por nivel). Conta quantos niveis existem e tira 1. O(n).
    // "fimDoNivel" marca onde termina o nivel atual dentro da fila.
    public int alturaIterativa() {
        if (raiz == null) {
            return -1;
        }
        List<No> fila = new ArrayList<>();
        fila.add(raiz);
        int inicio = 0;
        int altura = -1;
        while (inicio < fila.size()) {
            int fimDoNivel = fila.size();
            altura++;
            while (inicio < fimDoNivel) {
                No atual = fila.get(inicio);
                inicio++;
                if (atual.esq != null) {
                    fila.add(atual.esq);
                }
                if (atual.dir != null) {
                    fila.add(atual.dir);
                }
            }
        }
        return altura;
    }

    // =================================================================
    // 3. CONTAGENS (R1 e R5) - todas O(n), exceto as com poda (R5)
    // =================================================================

    // contarNosInternos: nos com PELO MENOS um filho (= tamanho - folhas).
    public int contarNosInternos() {
        return contarNosInternos(raiz);
    }

    private int contarNosInternos(No no) {
        if (no == null) {
            return 0;
        }
        if (no.esq == null && no.dir == null) {
            return 0; // folha nao conta
        }
        return 1 + contarNosInternos(no.esq) + contarNosInternos(no.dir);
    }

    // contarNosComUmFilho: exatamente um filho.
    public int contarNosComUmFilho() {
        return contarNosComUmFilho(raiz);
    }

    private int contarNosComUmFilho(No no) {
        if (no == null) {
            return 0;
        }
        int conta = 0;
        if ((no.esq == null) != (no.dir == null)) { // um e null e o outro nao
            conta = 1;
        }
        return conta + contarNosComUmFilho(no.esq) + contarNosComUmFilho(no.dir);
    }

    // contarNosComDoisFilhos: os dois filhos existem.
    public int contarNosComDoisFilhos() {
        return contarNosComDoisFilhos(raiz);
    }

    private int contarNosComDoisFilhos(No no) {
        if (no == null) {
            return 0;
        }
        int conta = 0;
        if (no.esq != null && no.dir != null) {
            conta = 1;
        }
        return conta + contarNosComDoisFilhos(no.esq) + contarNosComDoisFilhos(no.dir);
    }

    // contarPares.
    public int contarPares() {
        return contarPares(raiz);
    }

    private int contarPares(No no) {
        if (no == null) {
            return 0;
        }
        int conta = 0;
        if (no.valor % 2 == 0) {
            conta = 1;
        }
        return conta + contarPares(no.esq) + contarPares(no.dir);
    }

    // contarImpares. CUIDADO: use "% 2 != 0", nao "% 2 == 1".
    // Em Java, -3 % 2 da -1, entao "== 1" erraria os impares negativos.
    public int contarImpares() {
        return contarImpares(raiz);
    }

    private int contarImpares(No no) {
        if (no == null) {
            return 0;
        }
        int conta = 0;
        if (no.valor % 2 != 0) {
            conta = 1;
        }
        return conta + contarImpares(no.esq) + contarImpares(no.dir);
    }

    // contarPositivos (> 0) e contarNegativos (< 0). O zero nao entra em nenhum.
    // Usam a poda da R5: sao so contarMaioresQue(0) e contarMenoresQue(0).
    public int contarPositivos() {
        return contarMaioresQue(0);
    }

    public int contarNegativos() {
        return contarMenoresQue(0);
    }

    // contarMultiplosDe(k): valor % k == 0. k nao pode ser 0.
    public int contarMultiplosDe(int k) {
        if (k == 0) {
            throw new IllegalArgumentException("k nao pode ser 0");
        }
        return contarMultiplosDe(raiz, k);
    }

    private int contarMultiplosDe(No no, int k) {
        if (no == null) {
            return 0;
        }
        int conta = 0;
        if (no.valor % k == 0) {
            conta = 1;
        }
        return conta + contarMultiplosDe(no.esq, k) + contarMultiplosDe(no.dir, k);
    }

    // contarMaioresQue(x): R5 (poda).
    // Se no.valor <= x, o no e toda a esquerda dele sao <= x: so olha a direita.
    // Pior caso O(n); com poda costuma visitar bem menos.
    public int contarMaioresQue(int x) {
        return contarMaioresQue(raiz, x);
    }

    private int contarMaioresQue(No no, int x) {
        if (no == null) {
            return 0;
        }
        if (no.valor <= x) {
            return contarMaioresQue(no.dir, x);
        }
        return 1 + contarMaioresQue(no.esq, x) + contarMaioresQue(no.dir, x);
    }

    // contarMenoresQue(x): espelho do anterior.
    public int contarMenoresQue(int x) {
        return contarMenoresQue(raiz, x);
    }

    private int contarMenoresQue(No no, int x) {
        if (no == null) {
            return 0;
        }
        if (no.valor >= x) {
            return contarMenoresQue(no.esq, x);
        }
        return 1 + contarMenoresQue(no.esq, x) + contarMenoresQue(no.dir, x);
    }

    // contarNoIntervalo(a, b): quantos valores estao em [a, b] (inclusive). R5.
    public int contarNoIntervalo(int a, int b) {
        return contarNoIntervalo(raiz, a, b);
    }

    private int contarNoIntervalo(No no, int a, int b) {
        if (no == null) {
            return 0;
        }
        if (no.valor < a) {
            return contarNoIntervalo(no.dir, a, b);   // esquerda toda e < a
        }
        if (no.valor > b) {
            return contarNoIntervalo(no.esq, a, b);   // direita toda e > b
        }
        return 1 + contarNoIntervalo(no.esq, a, b) + contarNoIntervalo(no.dir, a, b);
    }

    // contarNosNoNivel(k): quantos nos estao a k arestas da raiz.
    // Truque: desce diminuindo k; quando k chega a 0, voce esta no nivel pedido.
    public int contarNosNoNivel(int k) {
        return contarNosNoNivel(raiz, k);
    }

    private int contarNosNoNivel(No no, int k) {
        if (no == null || k < 0) {
            return 0;
        }
        if (k == 0) {
            return 1;
        }
        return contarNosNoNivel(no.esq, k - 1) + contarNosNoNivel(no.dir, k - 1);
    }

    // =================================================================
    // 4. SOMAS, MEDIA E MEDIANA
    // =================================================================

    // somaFolhas: soma so os nos sem filhos. R1. O(n).
    public int somaFolhas() {
        return somaFolhas(raiz);
    }

    private int somaFolhas(No no) {
        if (no == null) {
            return 0;
        }
        if (no.esq == null && no.dir == null) {
            return no.valor;
        }
        return somaFolhas(no.esq) + somaFolhas(no.dir);
    }

    // somaInternos: soma dos nos que tem pelo menos um filho. R1. O(n).
    public int somaInternos() {
        return somaInternos(raiz);
    }

    private int somaInternos(No no) {
        if (no == null) {
            return 0;
        }
        if (no.esq == null && no.dir == null) {
            return 0;
        }
        return no.valor + somaInternos(no.esq) + somaInternos(no.dir);
    }

    // somaPares / somaImpares. R1. O(n).
    public int somaPares() {
        return somaPares(raiz);
    }

    private int somaPares(No no) {
        if (no == null) {
            return 0;
        }
        int parcela = 0;
        if (no.valor % 2 == 0) {
            parcela = no.valor;
        }
        return parcela + somaPares(no.esq) + somaPares(no.dir);
    }

    public int somaImpares() {
        return somaImpares(raiz);
    }

    private int somaImpares(No no) {
        if (no == null) {
            return 0;
        }
        int parcela = 0;
        if (no.valor % 2 != 0) {
            parcela = no.valor;
        }
        return parcela + somaImpares(no.esq) + somaImpares(no.dir);
    }

    // somaNoNivel(k): mesmo truque do contarNosNoNivel. O(n).
    public int somaNoNivel(int k) {
        return somaNoNivel(raiz, k);
    }

    private int somaNoNivel(No no, int k) {
        if (no == null || k < 0) {
            return 0;
        }
        if (k == 0) {
            return no.valor;
        }
        return somaNoNivel(no.esq, k - 1) + somaNoNivel(no.dir, k - 1);
    }

    // somaNoIntervalo(a, b): soma dos valores em [a, b]. R5.
    public int somaNoIntervalo(int a, int b) {
        return somaNoIntervalo(raiz, a, b);
    }

    private int somaNoIntervalo(No no, int a, int b) {
        if (no == null) {
            return 0;
        }
        if (no.valor < a) {
            return somaNoIntervalo(no.dir, a, b);
        }
        if (no.valor > b) {
            return somaNoIntervalo(no.esq, a, b);
        }
        return no.valor + somaNoIntervalo(no.esq, a, b) + somaNoIntervalo(no.dir, a, b);
    }

    // somaMaioresQue(x): R5. Mesmo esquema do contarMaioresQue.
    public int somaMaioresQue(int x) {
        return somaMaioresQue(raiz, x);
    }

    private int somaMaioresQue(No no, int x) {
        if (no == null) {
            return 0;
        }
        if (no.valor <= x) {
            return somaMaioresQue(no.dir, x);
        }
        return no.valor + somaMaioresQue(no.esq, x) + somaMaioresQue(no.dir, x);
    }

    // media: soma / quantidade, em double. Vazia: excecao. O(n).
    // CUIDADO: dividir int por int corta a parte decimal. Por isso o (double).
    public double media() {
        if (raiz == null) {
            throw new IllegalStateException("Arvore vazia");
        }
        return (double) somaValores() / quantidade;
    }

    // mediana: valor do meio da lista em ordem. Quantidade par: media dos dois do meio. O(n).
    public double mediana() {
        if (raiz == null) {
            throw new IllegalStateException("Arvore vazia");
        }
        List<Integer> ordenada = emOrdem();
        int n = ordenada.size();
        if (n % 2 == 1) {
            return ordenada.get(n / 2);
        }
        return (ordenada.get(n / 2 - 1) + ordenada.get(n / 2)) / 2.0;
    }

    // somaCaminhoAte(valor): soma dos valores da raiz ate o valor (inclusive). R2. O(h).
    // Valor ausente: excecao (nao da pra usar -1, a soma pode ser -1 de verdade).
    public int somaCaminhoAte(int valor) {
        No atual = raiz;
        int soma = 0;
        while (atual != null) {
            soma += atual.valor;
            if (valor == atual.valor) {
                return soma;
            }
            if (valor < atual.valor) {
                atual = atual.esq;
            } else {
                atual = atual.dir;
            }
        }
        throw new IllegalArgumentException("Valor nao esta na arvore");
    }

    // maiorSomaRaizAteFolha: o caminho raiz -> folha de maior soma. R1. O(n).
    // Cuidado: so vale caminho que termina em FOLHA, por isso o filho null nao entra.
    public int maiorSomaRaizAteFolha() {
        if (raiz == null) {
            throw new IllegalStateException("Arvore vazia");
        }
        return maiorSomaRaizAteFolha(raiz);
    }

    private int maiorSomaRaizAteFolha(No no) {
        if (no.esq == null && no.dir == null) {
            return no.valor;
        }
        if (no.esq == null) {
            return no.valor + maiorSomaRaizAteFolha(no.dir);
        }
        if (no.dir == null) {
            return no.valor + maiorSomaRaizAteFolha(no.esq);
        }
        return no.valor + Math.max(maiorSomaRaizAteFolha(no.esq), maiorSomaRaizAteFolha(no.dir));
    }

    // =================================================================
    // 5. ALTURA, NIVEIS E PROFUNDIDADE
    // =================================================================

    // alturaDoNo(valor): altura da subarvore que comeca no valor. Ausente = -1. O(h + tamanho da subarvore).
    public int alturaDoNo(int valor) {
        No no = buscarNo(valor);
        if (no == null) {
            return -1;
        }
        return altura(no);
    }

    // quantidadeDeNiveis: altura + 1 (vazia = 0). O(n).
    public int quantidadeDeNiveis() {
        return altura() + 1;
    }

    // larguraMaxima: o maior numero de nos em um mesmo nivel. R4. O(n).
    public int larguraMaxima() {
        if (raiz == null) {
            return 0;
        }
        List<No> fila = new ArrayList<>();
        fila.add(raiz);
        int inicio = 0;
        int maior = 0;
        while (inicio < fila.size()) {
            int fimDoNivel = fila.size();
            int noNivel = fimDoNivel - inicio;
            if (noNivel > maior) {
                maior = noNivel;
            }
            while (inicio < fimDoNivel) {
                No atual = fila.get(inicio);
                inicio++;
                if (atual.esq != null) {
                    fila.add(atual.esq);
                }
                if (atual.dir != null) {
                    fila.add(atual.dir);
                }
            }
        }
        return maior;
    }

    // menorProfundidadeDeFolha: a folha mais perto da raiz. R1. Vazia = -1. O(n).
    // Cuidado: um no com UM filho nao e folha, entao nao pode usar o lado null.
    public int menorProfundidadeDeFolha() {
        if (raiz == null) {
            return -1;
        }
        return menorProfundidadeDeFolha(raiz);
    }

    private int menorProfundidadeDeFolha(No no) {
        if (no.esq == null && no.dir == null) {
            return 0;
        }
        if (no.esq == null) {
            return 1 + menorProfundidadeDeFolha(no.dir);
        }
        if (no.dir == null) {
            return 1 + menorProfundidadeDeFolha(no.esq);
        }
        return 1 + Math.min(menorProfundidadeDeFolha(no.esq), menorProfundidadeDeFolha(no.dir));
    }

    // valorMaisProfundo: o ultimo no visitado por nivel (o mais fundo e mais a direita). R4. O(n).
    public int valorMaisProfundo() {
        if (raiz == null) {
            throw new IllegalStateException("Arvore vazia");
        }
        List<No> fila = new ArrayList<>();
        fila.add(raiz);
        int inicio = 0;
        No ultimo = raiz;
        while (inicio < fila.size()) {
            ultimo = fila.get(inicio);
            inicio++;
            if (ultimo.esq != null) {
                fila.add(ultimo.esq);
            }
            if (ultimo.dir != null) {
                fila.add(ultimo.dir);
            }
        }
        return ultimo.valor;
    }

    // diametro: maior numero de arestas entre DOIS nos quaisquer. Vazia ou 1 no = 0.
    // O caminho mais longo que passa por um no = altura(esq) + altura(dir) + 2.
    // Versao simples O(n^2) (calcula altura em cada no). Facil de explicar.
    public int diametro() {
        return diametro(raiz);
    }

    private int diametro(No no) {
        if (no == null) {
            return 0;
        }
        int passandoPorAqui = altura(no.esq) + altura(no.dir) + 2;
        int melhorDosFilhos = Math.max(diametro(no.esq), diametro(no.dir));
        return Math.max(passandoPorAqui, melhorDosFilhos);
    }

    // =================================================================
    // 6. BUSCAS QUE USAM A REGRA DA BST (quase tudo R2, O(h))
    // =================================================================

    // ehFolha(valor): existe e nao tem filhos. O(h).
    public boolean ehFolha(int valor) {
        No no = buscarNo(valor);
        return no != null && no.esq == null && no.dir == null;
    }

    // grau(valor): numero de filhos (0, 1 ou 2). Ausente = -1. O(h).
    public int grau(int valor) {
        No no = buscarNo(valor);
        if (no == null) {
            return -1;
        }
        int filhos = 0;
        if (no.esq != null) {
            filhos++;
        }
        if (no.dir != null) {
            filhos++;
        }
        return filhos;
    }

    // pai(valor): valor do pai. Raiz ou ausente = null (por isso Integer, nao int). O(h).
    // Ideia: desce guardando quem era o no anterior.
    public Integer pai(int valor) {
        No anterior = null;
        No atual = raiz;
        while (atual != null) {
            if (valor == atual.valor) {
                if (anterior == null) {
                    return null; // e a raiz
                }
                return anterior.valor;
            }
            anterior = atual;
            if (valor < atual.valor) {
                atual = atual.esq;
            } else {
                atual = atual.dir;
            }
        }
        return null; // nao achou
    }

    // irmao(valor): o outro filho do mesmo pai. Sem irmao, raiz ou ausente = null. O(h).
    public Integer irmao(int valor) {
        No anterior = null;
        No atual = raiz;
        while (atual != null && atual.valor != valor) {
            anterior = atual;
            if (valor < atual.valor) {
                atual = atual.esq;
            } else {
                atual = atual.dir;
            }
        }
        if (atual == null || anterior == null) {
            return null;
        }
        No outro;
        if (anterior.esq == atual) {
            outro = anterior.dir;
        } else {
            outro = anterior.esq;
        }
        if (outro == null) {
            return null;
        }
        return outro.valor;
    }

    // sucessor(valor): menor valor ESTRITAMENTE maior que o valor. Nao precisa existir. Sem = null. O(h).
    // Tambem chamado de: "proximo maior", "menor valor maior que x".
    // Ideia: toda vez que vira para a esquerda, o no atual e um candidato.
    public Integer sucessor(int valor) {
        Integer resposta = null;
        No atual = raiz;
        while (atual != null) {
            if (atual.valor > valor) {
                resposta = atual.valor;   // candidato; tenta achar um menor ainda
                atual = atual.esq;
            } else {
                atual = atual.dir;
            }
        }
        return resposta;
    }

    // antecessor(valor): maior valor ESTRITAMENTE menor. Sem = null. O(h).
    // Tambem chamado de: "anterior", "maior valor menor que x".
    public Integer antecessor(int valor) {
        Integer resposta = null;
        No atual = raiz;
        while (atual != null) {
            if (atual.valor < valor) {
                resposta = atual.valor;
                atual = atual.dir;
            } else {
                atual = atual.esq;
            }
        }
        return resposta;
    }

    // piso(x): maior valor <= x (floor). Sem = null. O(h).
    public Integer piso(int x) {
        Integer resposta = null;
        No atual = raiz;
        while (atual != null) {
            if (atual.valor == x) {
                return x;
            }
            if (atual.valor < x) {
                resposta = atual.valor;
                atual = atual.dir;
            } else {
                atual = atual.esq;
            }
        }
        return resposta;
    }

    // teto(x): menor valor >= x (ceiling). Sem = null. O(h).
    public Integer teto(int x) {
        Integer resposta = null;
        No atual = raiz;
        while (atual != null) {
            if (atual.valor == x) {
                return x;
            }
            if (atual.valor > x) {
                resposta = atual.valor;
                atual = atual.esq;
            } else {
                atual = atual.dir;
            }
        }
        return resposta;
    }

    // maisProximo(x): valor da arvore mais perto de x. Empate: o menor. Vazia: excecao. O(h).
    // O mais proximo sempre esta no caminho da busca por x.
    public int maisProximo(int x) {
        if (raiz == null) {
            throw new IllegalStateException("Arvore vazia");
        }
        int melhor = raiz.valor;
        No atual = raiz;
        while (atual != null) {
            int distAtual = Math.abs(atual.valor - x);
            int distMelhor = Math.abs(melhor - x);
            if (distAtual < distMelhor || (distAtual == distMelhor && atual.valor < melhor)) {
                melhor = atual.valor;
            }
            if (x == atual.valor) {
                return x;
            }
            if (x < atual.valor) {
                atual = atual.esq;
            } else {
                atual = atual.dir;
            }
        }
        return melhor;
    }

    // kEsimoMenor(k): k comeca em 1 (k = 1 e o minimo). O(n) pela lista em ordem.
    public int kEsimoMenor(int k) {
        if (k < 1 || k > quantidade) {
            throw new IllegalArgumentException("k fora do intervalo");
        }
        return emOrdem().get(k - 1);
    }

    // kEsimoMaior(k): k = 1 e o maximo. O(n).
    public int kEsimoMaior(int k) {
        if (k < 1 || k > quantidade) {
            throw new IllegalArgumentException("k fora do intervalo");
        }
        return emOrdem().get(quantidade - k);
    }

    // segundoMenor / segundoMaior: casos especiais do k-esimo. Precisa de 2 nos.
    public int segundoMenor() {
        return kEsimoMenor(2);
    }

    public int segundoMaior() {
        return kEsimoMaior(2);
    }

    // menorDiferenca: menor diferenca entre dois valores quaisquer. O(n).
    // Na lista em ordem, os mais proximos sempre ficam lado a lado.
    public int menorDiferenca() {
        if (quantidade < 2) {
            throw new IllegalStateException("Precisa de pelo menos 2 nos");
        }
        List<Integer> v = emOrdem();
        int menor = v.get(1) - v.get(0);
        for (int i = 2; i < v.size(); i++) {
            int dif = v.get(i) - v.get(i - 1);
            if (dif < menor) {
                menor = dif;
            }
        }
        return menor;
    }

    // ancestralComum(a, b): o no mais fundo que tem a e b embaixo dele (LCA). O(h).
    // Enquanto os dois estao do mesmo lado, desce para esse lado.
    // Quando eles se separam (ou um deles e o no atual), achou.
    public int ancestralComum(int a, int b) {
        if (!contem(a) || !contem(b)) {
            throw new IllegalArgumentException("Os dois valores precisam estar na arvore");
        }
        No atual = raiz;
        while (true) {
            if (a < atual.valor && b < atual.valor) {
                atual = atual.esq;
            } else if (a > atual.valor && b > atual.valor) {
                atual = atual.dir;
            } else {
                return atual.valor;
            }
        }
    }

    // distancia(a, b): arestas entre a e b. Algum ausente = -1. O(h).
    // = prof(a) + prof(b) - 2 * prof(ancestral comum)
    public int distancia(int a, int b) {
        if (!contem(a) || !contem(b)) {
            return -1;
        }
        int lca = ancestralComum(a, b);
        return profundidade(a) + profundidade(b) - 2 * profundidade(lca);
    }

    // ehAncestral(a, b): a esta no caminho da raiz ate b (e a != b)? O(h).
    public boolean ehAncestral(int a, int b) {
        if (a == b || !contem(b)) {
            return false;
        }
        No atual = raiz;
        while (atual != null && atual.valor != b) {
            if (atual.valor == a) {
                return true;
            }
            if (b < atual.valor) {
                atual = atual.esq;
            } else {
                atual = atual.dir;
            }
        }
        return false;
    }

    // =================================================================
    // 7. PERCURSOS E LISTAS
    // =================================================================

    // emOrdemDecrescente: direita, RAIZ, esquerda -> sai do maior pro menor. R3. O(n).
    public List<Integer> emOrdemDecrescente() {
        List<Integer> lista = new ArrayList<>();
        emOrdemDecrescente(raiz, lista);
        return lista;
    }

    private void emOrdemDecrescente(No no, List<Integer> lista) {
        if (no == null) {
            return;
        }
        emOrdemDecrescente(no.dir, lista);
        lista.add(no.valor);
        emOrdemDecrescente(no.esq, lista);
    }

    // emOrdemIterativo: sem recursao, com uma PILHA (ArrayList: empilha no fim, desempilha do fim). O(n).
    // 1) desce tudo para a esquerda empilhando; 2) desempilha e visita; 3) vai para a direita.
    public List<Integer> emOrdemIterativo() {
        List<Integer> lista = new ArrayList<>();
        List<No> pilha = new ArrayList<>();
        No atual = raiz;
        while (atual != null || !pilha.isEmpty()) {
            while (atual != null) {
                pilha.add(atual);
                atual = atual.esq;
            }
            atual = pilha.remove(pilha.size() - 1);
            lista.add(atual.valor);
            atual = atual.dir;
        }
        return lista;
    }

    // preOrdemIterativo: pilha. Empilha a DIREITA antes, para a ESQUERDA sair primeiro. O(n).
    public List<Integer> preOrdemIterativo() {
        List<Integer> lista = new ArrayList<>();
        if (raiz == null) {
            return lista;
        }
        List<No> pilha = new ArrayList<>();
        pilha.add(raiz);
        while (!pilha.isEmpty()) {
            No atual = pilha.remove(pilha.size() - 1);
            lista.add(atual.valor);
            if (atual.dir != null) {
                pilha.add(atual.dir);
            }
            if (atual.esq != null) {
                pilha.add(atual.esq);
            }
        }
        return lista;
    }

    // posOrdemIterativo: faz "raiz, direita, esquerda" com pilha e depois inverte a lista. O(n).
    // (O inverso de raiz-dir-esq e exatamente esq-dir-raiz.)
    public List<Integer> posOrdemIterativo() {
        List<Integer> invertida = new ArrayList<>();
        if (raiz == null) {
            return invertida;
        }
        List<No> pilha = new ArrayList<>();
        pilha.add(raiz);
        while (!pilha.isEmpty()) {
            No atual = pilha.remove(pilha.size() - 1);
            invertida.add(atual.valor);
            if (atual.esq != null) {
                pilha.add(atual.esq);
            }
            if (atual.dir != null) {
                pilha.add(atual.dir);
            }
        }
        List<Integer> lista = new ArrayList<>();
        for (int i = invertida.size() - 1; i >= 0; i--) {
            lista.add(invertida.get(i));
        }
        return lista;
    }

    // porNivel (busca em largura / BFS): nivel 0, depois nivel 1, ... da esquerda para a direita. R4. O(n).
    public List<Integer> porNivel() {
        List<Integer> lista = new ArrayList<>();
        if (raiz == null) {
            return lista;
        }
        List<No> fila = new ArrayList<>();
        fila.add(raiz);
        int inicio = 0;
        while (inicio < fila.size()) {
            No atual = fila.get(inicio);
            inicio++;
            lista.add(atual.valor);
            if (atual.esq != null) {
                fila.add(atual.esq);
            }
            if (atual.dir != null) {
                fila.add(atual.dir);
            }
        }
        return lista;
    }

    // niveis: uma lista para cada nivel. Ex.: [[50], [30, 70], [20, 40, 60, 80], [85]]. R4. O(n).
    public List<List<Integer>> niveis() {
        List<List<Integer>> resultado = new ArrayList<>();
        if (raiz == null) {
            return resultado;
        }
        List<No> fila = new ArrayList<>();
        fila.add(raiz);
        int inicio = 0;
        while (inicio < fila.size()) {
            int fimDoNivel = fila.size();
            List<Integer> nivel = new ArrayList<>();
            while (inicio < fimDoNivel) {
                No atual = fila.get(inicio);
                inicio++;
                nivel.add(atual.valor);
                if (atual.esq != null) {
                    fila.add(atual.esq);
                }
                if (atual.dir != null) {
                    fila.add(atual.dir);
                }
            }
            resultado.add(nivel);
        }
        return resultado;
    }

    // valoresDoNivel(k): valores a k arestas da raiz, da esquerda para a direita. R3. O(n).
    public List<Integer> valoresDoNivel(int k) {
        List<Integer> lista = new ArrayList<>();
        valoresDoNivel(raiz, k, lista);
        return lista;
    }

    private void valoresDoNivel(No no, int k, List<Integer> lista) {
        if (no == null || k < 0) {
            return;
        }
        if (k == 0) {
            lista.add(no.valor);
            return;
        }
        valoresDoNivel(no.esq, k - 1, lista);
        valoresDoNivel(no.dir, k - 1, lista);
    }

    // folhas: lista das folhas, da esquerda para a direita. R3. O(n).
    public List<Integer> folhas() {
        List<Integer> lista = new ArrayList<>();
        folhas(raiz, lista);
        return lista;
    }

    private void folhas(No no, List<Integer> lista) {
        if (no == null) {
            return;
        }
        if (no.esq == null && no.dir == null) {
            lista.add(no.valor);
            return;
        }
        folhas(no.esq, lista);
        folhas(no.dir, lista);
    }

    // valoresNoIntervalo(a, b): valores em [a, b], ja ordenados. R3 + R5. O(n) no pior caso.
    public List<Integer> valoresNoIntervalo(int a, int b) {
        List<Integer> lista = new ArrayList<>();
        valoresNoIntervalo(raiz, a, b, lista);
        return lista;
    }

    private void valoresNoIntervalo(No no, int a, int b, List<Integer> lista) {
        if (no == null) {
            return;
        }
        if (no.valor > a) {
            valoresNoIntervalo(no.esq, a, b, lista);   // so vale descer se pode haver >= a na esquerda
        }
        if (no.valor >= a && no.valor <= b) {
            lista.add(no.valor);
        }
        if (no.valor < b) {
            valoresNoIntervalo(no.dir, a, b, lista);   // so vale descer se pode haver <= b na direita
        }
    }

    // caminhoAte(valor): valores da raiz ate o valor. Ausente = lista vazia. R2. O(h).
    public List<Integer> caminhoAte(int valor) {
        List<Integer> caminho = new ArrayList<>();
        No atual = raiz;
        while (atual != null) {
            caminho.add(atual.valor);
            if (valor == atual.valor) {
                return caminho;
            }
            if (valor < atual.valor) {
                atual = atual.esq;
            } else {
                atual = atual.dir;
            }
        }
        return new ArrayList<>(); // nao achou
    }

    // caminhosRaizAteFolha: todos os caminhos raiz -> folha. O(n * h).
    // "Backtracking": poe o no no caminho, desce, e TIRA na volta.
    // Ao achar uma folha, guarda uma COPIA do caminho (senao todas as listas seriam a mesma).
    public List<List<Integer>> caminhosRaizAteFolha() {
        List<List<Integer>> todos = new ArrayList<>();
        caminhosRaizAteFolha(raiz, new ArrayList<>(), todos);
        return todos;
    }

    private void caminhosRaizAteFolha(No no, List<Integer> atual, List<List<Integer>> todos) {
        if (no == null) {
            return;
        }
        atual.add(no.valor);
        if (no.esq == null && no.dir == null) {
            todos.add(new ArrayList<>(atual));
        } else {
            caminhosRaizAteFolha(no.esq, atual, todos);
            caminhosRaizAteFolha(no.dir, atual, todos);
        }
        atual.remove(atual.size() - 1); // remove pelo INDICE (int), nao pelo valor
    }

    // visaoEsquerda: o primeiro no de cada nivel (o que voce ve olhando da esquerda). O(n).
    public List<Integer> visaoEsquerda() {
        List<Integer> lista = new ArrayList<>();
        for (List<Integer> nivel : niveis()) {
            lista.add(nivel.get(0));
        }
        return lista;
    }

    // visaoDireita: o ultimo no de cada nivel. O(n).
    public List<Integer> visaoDireita() {
        List<Integer> lista = new ArrayList<>();
        for (List<Integer> nivel : niveis()) {
            lista.add(nivel.get(nivel.size() - 1));
        }
        return lista;
    }

    // paraArray: os valores em ordem, num int[]. O(n).
    public int[] paraArray() {
        List<Integer> lista = emOrdem();
        int[] v = new int[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            v[i] = lista.get(i);
        }
        return v;
    }

    // =================================================================
    // 8. VERIFICACOES (metodos boolean)
    // =================================================================

    // ehBSTValida: confere a regra da BST em TODOS os nos. O(n).
    // Nao basta comparar com os filhos: cada no tem um LIMITE minimo e maximo
    // herdado dos ancestrais. Usa long para os limites nao estourarem.
    public boolean ehBSTValida() {
        return ehBSTValida(raiz, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean ehBSTValida(No no, long minimo, long maximo) {
        if (no == null) {
            return true;
        }
        if (no.valor <= minimo || no.valor >= maximo) {
            return false;
        }
        return ehBSTValida(no.esq, minimo, no.valor) && ehBSTValida(no.dir, no.valor, maximo);
    }

    // ehCheia (full / estritamente binaria): todo no tem 0 ou 2 filhos. Vazia = true. O(n).
    public boolean ehCheia() {
        return ehCheia(raiz);
    }

    private boolean ehCheia(No no) {
        if (no == null) {
            return true;
        }
        if ((no.esq == null) != (no.dir == null)) {
            return false; // tem so um filho
        }
        return ehCheia(no.esq) && ehCheia(no.dir);
    }

    // ehDegenerada: todo no tem no maximo 1 filho (a arvore virou uma "lista"). O(n).
    public boolean ehDegenerada() {
        return ehDegenerada(raiz);
    }

    private boolean ehDegenerada(No no) {
        if (no == null) {
            return true;
        }
        if (no.esq != null && no.dir != null) {
            return false;
        }
        return ehDegenerada(no.esq) && ehDegenerada(no.dir);
    }

    // ehBalanceada (estilo AVL): em TODO no, |altura(esq) - altura(dir)| <= 1. O(n^2) na versao simples.
    public boolean ehBalanceada() {
        return ehBalanceada(raiz);
    }

    private boolean ehBalanceada(No no) {
        if (no == null) {
            return true;
        }
        int diferenca = Math.abs(altura(no.esq) - altura(no.dir));
        if (diferenca > 1) {
            return false;
        }
        return ehBalanceada(no.esq) && ehBalanceada(no.dir);
    }

    // fatorDeBalanceamento(valor): altura(esq) - altura(dir) do no. Ausente: excecao. O(n).
    public int fatorDeBalanceamento(int valor) {
        No no = buscarNo(valor);
        if (no == null) {
            throw new IllegalArgumentException("Valor nao esta na arvore");
        }
        return altura(no.esq) - altura(no.dir);
    }

    // ehPerfeita: todos os niveis cheios. Acontece quando n = 2^(h+1) - 1. O(n).
    public boolean ehPerfeita() {
        int h = altura();
        int esperado = 1;
        for (int i = 0; i <= h; i++) {
            esperado = esperado * 2;
        }
        esperado = esperado - 1;      // 2^(h+1) - 1
        return quantidade == esperado;
    }

    // ehCompleta: todos os niveis cheios, menos talvez o ultimo, que enche da esquerda pra direita. R4. O(n).
    // Por nivel: depois que aparece o primeiro "buraco" (filho null), nao pode vir mais nenhum no.
    public boolean ehCompleta() {
        if (raiz == null) {
            return true;
        }
        List<No> fila = new ArrayList<>();
        fila.add(raiz);
        int inicio = 0;
        boolean viuBuraco = false;
        while (inicio < fila.size()) {
            No atual = fila.get(inicio);
            inicio++;
            if (atual.esq != null) {
                if (viuBuraco) {
                    return false;
                }
                fila.add(atual.esq);
            } else {
                viuBuraco = true;
            }
            if (atual.dir != null) {
                if (viuBuraco) {
                    return false;
                }
                fila.add(atual.dir);
            } else {
                viuBuraco = true;
            }
        }
        return true;
    }

    // igual(outra): mesma FORMA e mesmos valores nas mesmas posicoes. O(n).
    // Da pra ler outra.raiz porque e a mesma classe.
    public boolean igual(BST outra) {
        return igual(this.raiz, outra.raiz);
    }

    private boolean igual(No a, No b) {
        if (a == null && b == null) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.valor == b.valor && igual(a.esq, b.esq) && igual(a.dir, b.dir);
    }

    // mesmosValores(outra): mesmos valores, mesmo que o formato seja diferente. O(n).
    public boolean mesmosValores(BST outra) {
        return this.emOrdem().equals(outra.emOrdem());
    }

    // existeCaminhoComSoma(alvo): algum caminho raiz -> folha soma exatamente alvo? O(n).
    // Vai subtraindo o valor de cada no; na folha, confere se sobrou 0.
    public boolean existeCaminhoComSoma(int alvo) {
        return existeCaminhoComSoma(raiz, alvo);
    }

    private boolean existeCaminhoComSoma(No no, int resta) {
        if (no == null) {
            return false;
        }
        resta = resta - no.valor;
        if (no.esq == null && no.dir == null) {
            return resta == 0;
        }
        return existeCaminhoComSoma(no.esq, resta) || existeCaminhoComSoma(no.dir, resta);
    }

    // =================================================================
    // 9. MODIFICACAO
    // =================================================================

    // remover(valor): devolve true se removeu. O(h).
    // Caso 1 - folha: some (o pai passa a apontar para null).
    // Caso 2 - um filho: o filho sobe para o lugar dele.
    // Caso 3 - dois filhos: copia o SUCESSOR (menor da direita) para o no
    //          e remove o sucessor la embaixo (ele tem no maximo 1 filho).
    public boolean remover(int valor) {
        int antes = quantidade;
        raiz = remover(raiz, valor);
        return quantidade < antes;
    }

    private No remover(No no, int valor) {
        if (no == null) {
            return null; // nao achou: nada muda
        }
        if (valor < no.valor) {
            no.esq = remover(no.esq, valor);
        } else if (valor > no.valor) {
            no.dir = remover(no.dir, valor);
        } else {
            // achou o no
            if (no.esq == null) {            // casos 1 e 2 (sem filho esquerdo)
                quantidade--;
                return no.dir;
            }
            if (no.dir == null) {            // caso 2 (so filho esquerdo)
                quantidade--;
                return no.esq;
            }
            No sucessor = no.dir;            // caso 3
            while (sucessor.esq != null) {
                sucessor = sucessor.esq;
            }
            no.valor = sucessor.valor;
            no.dir = remover(no.dir, sucessor.valor); // o quantidade-- acontece la
        }
        return no;
    }

    // removerMinimo / removerMaximo: remove e devolve o valor removido. Vazia: excecao. O(h).
    public int removerMinimo() {
        int menor = minimo();   // ja lanca excecao se vazia
        remover(menor);
        return menor;
    }

    public int removerMaximo() {
        int maior = maximo();
        remover(maior);
        return maior;
    }

    // removerFolhas: arranca todas as folhas de uma vez (so as que eram folha agora). O(n).
    public void removerFolhas() {
        raiz = removerFolhas(raiz);
    }

    private No removerFolhas(No no) {
        if (no == null) {
            return null;
        }
        if (no.esq == null && no.dir == null) {
            quantidade--;
            return null;
        }
        no.esq = removerFolhas(no.esq);
        no.dir = removerFolhas(no.dir);
        return no;
    }

    // podarForaDoIntervalo(a, b): deixa so os valores em [a, b]. O(n).
    // Se o no e < a, ele e a esquerda dele saem: sobra o que for podado da direita.
    // No fim recalcula a quantidade (mais simples do que ir descontando).
    public void podarForaDoIntervalo(int a, int b) {
        raiz = podar(raiz, a, b);
        quantidade = tamanhoRecursivo(raiz);
    }

    private No podar(No no, int a, int b) {
        if (no == null) {
            return null;
        }
        if (no.valor < a) {
            return podar(no.dir, a, b);
        }
        if (no.valor > b) {
            return podar(no.esq, a, b);
        }
        no.esq = podar(no.esq, a, b);
        no.dir = podar(no.dir, a, b);
        return no;
    }

    // somarEmTodos(k): soma k em todos os valores. A ordem nao muda, entao continua BST. O(n).
    // (Multiplicar por negativo, por exemplo, QUEBRARIA a BST.)
    public void somarEmTodos(int k) {
        somarEmTodos(raiz, k);
    }

    private void somarEmTodos(No no, int k) {
        if (no == null) {
            return;
        }
        no.valor = no.valor + k;
        somarEmTodos(no.esq, k);
        somarEmTodos(no.dir, k);
    }

    // limpar: esvazia a arvore. O(1) (o Java recolhe os nos sozinho).
    public void limpar() {
        raiz = null;
        quantidade = 0;
    }

    // copiar: arvore nova, com nos novos e o MESMO formato. O(n).
    public BST copiar() {
        BST copia = new BST();
        copia.raiz = copiarNos(this.raiz);
        copia.quantidade = this.quantidade;
        return copia;
    }

    private No copiarNos(No no) {
        if (no == null) {
            return null;
        }
        No novo = new No(no.valor);
        novo.esq = copiarNos(no.esq);
        novo.dir = copiarNos(no.dir);
        return novo;
    }

    // Curiosidade: inserir os valores na PRE-ORDEM em uma arvore vazia
    // reconstroi exatamente o mesmo formato. Por isso preOrdem "salva" a arvore.

    // construirBalanceada(ordenado): monta uma BST balanceada a partir de um array ORDENADO. O(n log n).
    // Insere o do meio primeiro (vira a raiz), depois o meio de cada metade, e assim por diante.
    public static BST construirBalanceada(int[] ordenado) {
        BST arvore = new BST();
        arvore.inserirMeio(ordenado, 0, ordenado.length - 1);
        return arvore;
    }

    private void inserirMeio(int[] v, int inicio, int fim) {
        if (inicio > fim) {
            return;
        }
        int meio = (inicio + fim) / 2;
        inserir(v[meio]);
        inserirMeio(v, inicio, meio - 1);
        inserirMeio(v, meio + 1, fim);
    }

    // balancear: reconstroi a propria arvore de forma balanceada. O(n log n).
    public void balancear() {
        int[] ordenado = paraArray();
        limpar();
        inserirMeio(ordenado, 0, ordenado.length - 1);
    }

    // =================================================================
    // 10. IMPRESSAO
    // =================================================================

    // imprimir: desenha a arvore DEITADA (raiz na esquerda, direita em cima).
    // Para ler: gire a cabeca para a esquerda. Otimo para conferir os testes.
    public void imprimir() {
        if (raiz == null) {
            System.out.println("(arvore vazia)");
            return;
        }
        imprimir(raiz, 0);
    }

    private void imprimir(No no, int nivel) {
        if (no == null) {
            return;
        }
        imprimir(no.dir, nivel + 1);
        for (int i = 0; i < nivel; i++) {
            System.out.print("      ");
        }
        System.out.println(no.valor);
        imprimir(no.esq, nivel + 1);
    }

    // toString: permite fazer System.out.println(arvore). Mostra em ordem.
    @Override
    public String toString() {
        return "BST" + emOrdem();
    }
}
