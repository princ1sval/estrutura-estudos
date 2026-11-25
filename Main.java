/*
 * Main de estudo: chama TODOS os metodos da BST de estudo.
 * O valor esperado de cada linha esta no comentario ao lado.
 *
 * Como estudar (Prever, Comparar, Confirmar):
 *   1. Cubra o comentario e tente prever o valor olhando o desenho da arvore.
 *   2. Rode (javac *.java  e  java Main) e compare.
 *   3. Se errou, releia o metodo na BST.java e descubra por que.
 *
 * Arvore principal (a mesma do teste do professor):
 *
 *            50
 *          /    \
 *        30      70
 *       /  \    /  \
 *     20   40  60   80
 *                     \
 *                      85
 */
public class Main {
    public static void main(String[] args) {
        BST a = new BST();
        a.inserirTodos(50, 30, 70, 20, 40, 60, 80, 85, 50);

        System.out.println("===== Desenho (deitado: gire a cabeca para a esquerda) =====");
        a.imprimir();

        // ------------------------------------------------------------
        System.out.println("\n===== 1. BASICO =====");
        System.out.println("tamanho = " + a.tamanho());  // 8
        System.out.println("altura = " + a.altura());  // 3
        System.out.println("contarFolhas = " + a.contarFolhas());  // 4
        System.out.println("minimo = " + a.minimo());  // 20
        System.out.println("maximo = " + a.maximo());  // 85
        System.out.println("somaValores = " + a.somaValores());  // 435
        System.out.println("contem(60) = " + a.contem(60));  // true
        System.out.println("contem(55) = " + a.contem(55));  // false
        System.out.println("profundidade(60) = " + a.profundidade(60));  // 2
        System.out.println("profundidade(55) = " + a.profundidade(55));  // -1
        System.out.println("emOrdem = " + a.emOrdem());  // [20, 30, 40, 50, 60, 70, 80, 85]
        System.out.println("preOrdem = " + a.preOrdem());  // [50, 30, 20, 40, 70, 60, 80, 85]
        System.out.println("posOrdem = " + a.posOrdem());  // [20, 40, 30, 60, 85, 80, 70, 50]
        System.out.println("estaVazia = " + a.estaVazia());  // false
        System.out.println("contarArestas = " + a.contarArestas());  // 7
        System.out.println("toString = " + a);  // BST[20, 30, 40, 50, 60, 70, 80, 85]

        // ------------------------------------------------------------
        System.out.println("\n===== 2. VERSOES ALTERNATIVAS =====");
        BST it = new BST();
        for (int v : new int[] {50, 30, 70, 20, 40, 60, 80, 85}) {
            it.inserirIterativo(v);
        }
        System.out.println("inserirIterativo da a mesma arvore = " + it.igual(a));  // true
        System.out.println("contemRecursivo(40) = " + a.contemRecursivo(40));  // true
        System.out.println("contemRecursivo(45) = " + a.contemRecursivo(45));  // false
        System.out.println("tamanhoRecursivo = " + a.tamanhoRecursivo());  // 8
        System.out.println("minimoRecursivo = " + a.minimoRecursivo());  // 20
        System.out.println("maximoRecursivo = " + a.maximoRecursivo());  // 85
        System.out.println("profundidadeRecursiva(85) = " + a.profundidadeRecursiva(85));  // 3
        System.out.println("profundidadeRecursiva(99) = " + a.profundidadeRecursiva(99));  // -1
        System.out.println("alturaIterativa = " + a.alturaIterativa());  // 3

        // ------------------------------------------------------------
        System.out.println("\n===== 3. CONTAGENS =====");
        System.out.println("contarNosInternos = " + a.contarNosInternos());  // 4
        System.out.println("contarNosComUmFilho = " + a.contarNosComUmFilho());  // 1
        System.out.println("contarNosComDoisFilhos = " + a.contarNosComDoisFilhos());  // 3
        System.out.println("contarPares = " + a.contarPares());  // 7
        System.out.println("contarImpares = " + a.contarImpares());  // 1
        System.out.println("contarPositivos = " + a.contarPositivos());  // 8
        System.out.println("contarNegativos = " + a.contarNegativos());  // 0
        System.out.println("contarMultiplosDe(20) = " + a.contarMultiplosDe(20));  // 4
        System.out.println("contarMaioresQue(50) = " + a.contarMaioresQue(50));  // 4
        System.out.println("contarMenoresQue(50) = " + a.contarMenoresQue(50));  // 3
        System.out.println("contarNoIntervalo(30, 70) = " + a.contarNoIntervalo(30, 70));  // 5
        System.out.println("contarNosNoNivel(0) = " + a.contarNosNoNivel(0));  // 1
        System.out.println("contarNosNoNivel(2) = " + a.contarNosNoNivel(2));  // 4
        System.out.println("contarNosNoNivel(3) = " + a.contarNosNoNivel(3));  // 1
        System.out.println("contarNosNoNivel(9) = " + a.contarNosNoNivel(9));  // 0

        // ------------------------------------------------------------
        System.out.println("\n===== 4. SOMAS, MEDIA E MEDIANA =====");
        System.out.println("somaFolhas = " + a.somaFolhas());  // 205
        System.out.println("somaInternos = " + a.somaInternos());  // 230
        System.out.println("somaPares = " + a.somaPares());  // 350
        System.out.println("somaImpares = " + a.somaImpares());  // 85
        System.out.println("somaNoNivel(1) = " + a.somaNoNivel(1));  // 100
        System.out.println("somaNoNivel(2) = " + a.somaNoNivel(2));  // 200
        System.out.println("somaNoIntervalo(40, 70) = " + a.somaNoIntervalo(40, 70));  // 220
        System.out.println("somaMaioresQue(60) = " + a.somaMaioresQue(60));  // 235
        System.out.println("media = " + a.media());  // 54.375
        System.out.println("mediana = " + a.mediana());  // 55.0
        System.out.println("somaCaminhoAte(60) = " + a.somaCaminhoAte(60));  // 180
        System.out.println("maiorSomaRaizAteFolha = " + a.maiorSomaRaizAteFolha());  // 285

        // ------------------------------------------------------------
        System.out.println("\n===== 5. ALTURA, NIVEIS E PROFUNDIDADE =====");
        System.out.println("alturaDoNo(70) = " + a.alturaDoNo(70));  // 2
        System.out.println("alturaDoNo(30) = " + a.alturaDoNo(30));  // 1
        System.out.println("alturaDoNo(85) = " + a.alturaDoNo(85));  // 0
        System.out.println("alturaDoNo(99) = " + a.alturaDoNo(99));  // -1
        System.out.println("quantidadeDeNiveis = " + a.quantidadeDeNiveis());  // 4
        System.out.println("larguraMaxima = " + a.larguraMaxima());  // 4
        System.out.println("menorProfundidadeDeFolha = " + a.menorProfundidadeDeFolha());  // 2
        System.out.println("valorMaisProfundo = " + a.valorMaisProfundo());  // 85
        System.out.println("diametro = " + a.diametro());  // 5

        // ------------------------------------------------------------
        System.out.println("\n===== 6. BUSCAS DA BST =====");
        System.out.println("ehFolha(40) = " + a.ehFolha(40));  // true
        System.out.println("ehFolha(80) = " + a.ehFolha(80));  // false
        System.out.println("grau(50) = " + a.grau(50));  // 2
        System.out.println("grau(80) = " + a.grau(80));  // 1
        System.out.println("grau(85) = " + a.grau(85));  // 0
        System.out.println("grau(99) = " + a.grau(99));  // -1
        System.out.println("pai(60) = " + a.pai(60));  // 70
        System.out.println("pai(50) = " + a.pai(50));  // null
        System.out.println("irmao(20) = " + a.irmao(20));  // 40
        System.out.println("irmao(85) = " + a.irmao(85));  // null
        System.out.println("sucessor(50) = " + a.sucessor(50));  // 60
        System.out.println("sucessor(40) = " + a.sucessor(40));  // 50
        System.out.println("sucessor(85) = " + a.sucessor(85));  // null
        System.out.println("antecessor(60) = " + a.antecessor(60));  // 50
        System.out.println("antecessor(20) = " + a.antecessor(20));  // null
        System.out.println("piso(65) = " + a.piso(65));  // 60
        System.out.println("piso(60) = " + a.piso(60));  // 60
        System.out.println("piso(10) = " + a.piso(10));  // null
        System.out.println("teto(65) = " + a.teto(65));  // 70
        System.out.println("teto(90) = " + a.teto(90));  // null
        System.out.println("maisProximo(64) = " + a.maisProximo(64));  // 60
        System.out.println("maisProximo(65) = " + a.maisProximo(65));  // 60
        System.out.println("kEsimoMenor(3) = " + a.kEsimoMenor(3));  // 40
        System.out.println("kEsimoMaior(2) = " + a.kEsimoMaior(2));  // 80
        System.out.println("segundoMenor = " + a.segundoMenor());  // 30
        System.out.println("segundoMaior = " + a.segundoMaior());  // 80
        System.out.println("menorDiferenca = " + a.menorDiferenca());  // 5
        System.out.println("ancestralComum(20, 40) = " + a.ancestralComum(20, 40));  // 30
        System.out.println("ancestralComum(20, 85) = " + a.ancestralComum(20, 85));  // 50
        System.out.println("ancestralComum(70, 85) = " + a.ancestralComum(70, 85));  // 70
        System.out.println("distancia(20, 85) = " + a.distancia(20, 85));  // 5
        System.out.println("distancia(60, 80) = " + a.distancia(60, 80));  // 2
        System.out.println("ehAncestral(70, 85) = " + a.ehAncestral(70, 85));  // true
        System.out.println("ehAncestral(30, 85) = " + a.ehAncestral(30, 85));  // false

        // ------------------------------------------------------------
        System.out.println("\n===== 7. PERCURSOS E LISTAS =====");
        System.out.println("emOrdemDecrescente = " + a.emOrdemDecrescente());  // [85, 80, 70, 60, 50, 40, 30, 20]
        System.out.println("emOrdemIterativo = " + a.emOrdemIterativo());  // [20, 30, 40, 50, 60, 70, 80, 85]
        System.out.println("preOrdemIterativo = " + a.preOrdemIterativo());  // [50, 30, 20, 40, 70, 60, 80, 85]
        System.out.println("posOrdemIterativo = " + a.posOrdemIterativo());  // [20, 40, 30, 60, 85, 80, 70, 50]
        System.out.println("porNivel = " + a.porNivel());  // [50, 30, 70, 20, 40, 60, 80, 85]
        System.out.println("niveis = " + a.niveis());  // [[50], [30, 70], [20, 40, 60, 80], [85]]
        System.out.println("valoresDoNivel(2) = " + a.valoresDoNivel(2));  // [20, 40, 60, 80]
        System.out.println("folhas = " + a.folhas());  // [20, 40, 60, 85]
        System.out.println("valoresNoIntervalo(35, 75) = " + a.valoresNoIntervalo(35, 75));  // [40, 50, 60, 70]
        System.out.println("caminhoAte(60) = " + a.caminhoAte(60));  // [50, 70, 60]
        System.out.println("caminhoAte(55) = " + a.caminhoAte(55));  // []
        System.out.println("caminhosRaizAteFolha = " + a.caminhosRaizAteFolha());  // [[50, 30, 20], [50, 30, 40], [50, 70, 60], [50, 70, 80, 85]]
        System.out.println("visaoEsquerda = " + a.visaoEsquerda());  // [50, 30, 20, 85]
        System.out.println("visaoDireita = " + a.visaoDireita());  // [50, 70, 80, 85]
        int[] vetor = a.paraArray();
        System.out.println("paraArray (tamanho do vetor) = " + vetor.length);  // 8

        // ------------------------------------------------------------
        System.out.println("\n===== 8. VERIFICACOES =====");
        System.out.println("ehBSTValida = " + a.ehBSTValida());  // true
        System.out.println("ehCheia = " + a.ehCheia());  // false
        System.out.println("ehDegenerada = " + a.ehDegenerada());  // false
        System.out.println("ehBalanceada = " + a.ehBalanceada());  // true
        System.out.println("fatorDeBalanceamento(50) = " + a.fatorDeBalanceamento(50));  // -1
        System.out.println("fatorDeBalanceamento(70) = " + a.fatorDeBalanceamento(70));  // -1
        System.out.println("ehPerfeita = " + a.ehPerfeita());  // false
        System.out.println("ehCompleta = " + a.ehCompleta());  // false
        System.out.println("existeCaminhoComSoma(285) = " + a.existeCaminhoComSoma(285));  // true
        System.out.println("existeCaminhoComSoma(100) = " + a.existeCaminhoComSoma(100));  // true

        // Arvore perfeita: 50, 30, 70, 20, 40, 60, 80 (sem o 85)
        BST perfeita = new BST();
        perfeita.inserirTodos(50, 30, 70, 20, 40, 60, 80);
        System.out.println("perfeita.ehPerfeita = " + perfeita.ehPerfeita());  // true
        System.out.println("perfeita.ehCompleta = " + perfeita.ehCompleta());  // true
        System.out.println("perfeita.ehCheia = " + perfeita.ehCheia());  // true

        // Arvore completa mas nao perfeita: ultimo nivel enchendo da esquerda
        BST completa = new BST();
        completa.inserirTodos(50, 30, 70, 20);
        System.out.println("completa.ehCompleta = " + completa.ehCompleta());  // true
        System.out.println("completa.ehPerfeita = " + completa.ehPerfeita());  // false

        // Degenerada: 1, 2, 3, 4, 5
        BST degenerada = new BST();
        degenerada.inserirTodos(1, 2, 3, 4, 5);
        System.out.println("degenerada.ehDegenerada = " + degenerada.ehDegenerada());  // true
        System.out.println("degenerada.ehBalanceada = " + degenerada.ehBalanceada());  // false

        // Mesmos valores, ordem de insercao diferente -> formato diferente
        BST outraOrdem = new BST();
        outraOrdem.inserirTodos(20, 30, 40, 50, 60, 70, 80, 85);
        System.out.println("igual(outraOrdem) = " + a.igual(outraOrdem));  // false
        System.out.println("mesmosValores(outraOrdem) = " + a.mesmosValores(outraOrdem));  // true

        // ------------------------------------------------------------
        System.out.println("\n===== 9. MODIFICACAO (sempre em copias) =====");
        BST r1 = a.copiar();
        System.out.println("remover(85) [folha] = " + r1.remover(85));  // true
        System.out.println("depois de remover 85: preOrdem = " + r1.preOrdem());  // [50, 30, 20, 40, 70, 60, 80]
        BST r2 = a.copiar();
        System.out.println("remover(80) [um filho] = " + r2.remover(80));  // true
        System.out.println("depois de remover 80: preOrdem = " + r2.preOrdem());  // [50, 30, 20, 40, 70, 60, 85]
        BST r3 = a.copiar();
        System.out.println("remover(50) [dois filhos] = " + r3.remover(50));  // true
        System.out.println("depois de remover 50: preOrdem = " + r3.preOrdem());  // [60, 30, 20, 40, 70, 80, 85]
        System.out.println("remover(99) [nao existe] = " + r3.remover(99));  // false
        System.out.println("tamanho depois das remocoes = " + r3.tamanho());  // 7

        BST r4 = a.copiar();
        System.out.println("removerMinimo = " + r4.removerMinimo());  // 20
        System.out.println("removerMaximo = " + r4.removerMaximo());  // 85
        System.out.println("depois de remover min e max: emOrdem = " + r4.emOrdem());  // [30, 40, 50, 60, 70, 80]

        BST r5 = a.copiar();
        r5.removerFolhas();
        System.out.println("removerFolhas: preOrdem = " + r5.preOrdem());  // [50, 30, 70, 80]

        BST r6 = a.copiar();
        r6.podarForaDoIntervalo(30, 70);
        System.out.println("podarForaDoIntervalo(30, 70): preOrdem = " + r6.preOrdem());  // [50, 30, 40, 70, 60]

        BST r7 = a.copiar();
        r7.somarEmTodos(5);
        System.out.println("somarEmTodos(5): emOrdem = " + r7.emOrdem());  // [25, 35, 45, 55, 65, 75, 85, 90]

        BST r8 = a.copiar();
        r8.limpar();
        System.out.println("limpar: tamanho = " + r8.tamanho());  // 0

        BST copia = a.copiar();
        copia.inserir(99);
        System.out.println("copia e independente (original nao tem 99) = " + !a.contem(99));  // true

        BST bal = BST.construirBalanceada(new int[] {1, 2, 3, 4, 5, 6, 7});
        System.out.println("construirBalanceada(1..7): preOrdem = " + bal.preOrdem());  // [4, 2, 1, 3, 6, 5, 7]
        System.out.println("construirBalanceada(1..7): altura = " + bal.altura());  // 2

        degenerada.balancear();
        System.out.println("degenerada.balancear: preOrdem = " + degenerada.preOrdem());  // [3, 1, 2, 4, 5]
        System.out.println("degenerada.balancear: altura = " + degenerada.altura());  // 2
        System.out.println("===== Desenho da degenerada depois de balancear =====");
        degenerada.imprimir();

        // ------------------------------------------------------------
        System.out.println("\n===== 10. ARVORE VAZIA E EXCECOES =====");
        BST vazia = new BST();
        System.out.println("vazia.altura = " + vazia.altura());  // -1
        System.out.println("vazia.larguraMaxima = " + vazia.larguraMaxima());  // 0
        System.out.println("vazia.diametro = " + vazia.diametro());  // 0
        System.out.println("vazia.ehCompleta = " + vazia.ehCompleta());  // true
        System.out.println("vazia.sucessor(10) = " + vazia.sucessor(10));  // null
        try {
            vazia.media();
            System.out.println("vazia.media = sem excecao");  // (nao deve aparecer)
        } catch (IllegalStateException e) {
            System.out.println("vazia.media = excecao ok");  // <- e esta linha que deve aparecer
        }
        try {
            a.kEsimoMenor(20);
            System.out.println("kEsimoMenor(20) = sem excecao");  // (nao deve aparecer)
        } catch (IllegalArgumentException e) {
            System.out.println("kEsimoMenor(20) = excecao ok");  // <- e esta linha que deve aparecer
        }
        try {
            a.ancestralComum(20, 99);
            System.out.println("ancestralComum(20, 99) = sem excecao");  // (nao deve aparecer)
        } catch (IllegalArgumentException e) {
            System.out.println("ancestralComum(20, 99) = excecao ok");  // <- e esta linha que deve aparecer
        }

        // Negativos: cuidado com o % nos impares
        BST neg = new BST();
        neg.inserirTodos(0, -10, 10, -3, -15, 7);
        System.out.println("neg.contarImpares = " + neg.contarImpares());  // 3
        System.out.println("neg.somaImpares = " + neg.somaImpares());  // -11
        System.out.println("neg.contarNegativos = " + neg.contarNegativos());  // 3
        System.out.println("neg.contarPositivos = " + neg.contarPositivos());  // 2
    }
}
