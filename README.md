# BST de estudo — todos os métodos para a Bancada 2

Biblioteca de **estudo** com 105 métodos públicos de árvore binária de busca (BST) de inteiros, usando a **mesma estrutura** da lib do trabalho (`No` com `valor`, `esq`, `dir`; `raiz` privada). Qualquer método daqui pode ser copiado para a sua `BST` do trabalho.

> ⚠️ **Esta pasta NÃO é o repositório do trabalho.** Não copie estes arquivos para dentro de `ed-2026-2-trabalho-etapa-2-...` e não dê push deles lá: o Crivo corrige aquele repositório. Se quiser versionar o estudo, crie um repositório novo no GitHub só para ele.

Todos os métodos foram conferidos automaticamente contra respostas de força bruta em 4.000 árvores aleatórias (vazias, degeneradas, com negativos e repetidos), num total de 1,6 milhão de comparações, sem nenhuma falha.

## Arquivos

| Arquivo | O que tem |
|---|---|
| `No.java` | Igual ao do trabalho |
| `BST.java` | Os 105 métodos, divididos em 10 seções, cada um com comentário explicando a ideia e o Big-O |
| `Main.java` | Chama **todos** os métodos; o valor esperado de cada linha está no comentário ao lado |

Para rodar: `javac *.java` e depois `java Main` (ou abra o `Main.java` no jGRASP → Compile → Run).

## Como estudar (sem IA, do jeito da Bancada)

1. **Leia as 5 receitas** abaixo. Quase toda pergunta cai em uma delas.
2. **Prever, Comparar, Confirmar:** no `Main.java`, cubra o comentário, preveja o valor olhando o desenho da árvore, rode e compare.
3. **Treino cego:** copie a `BST.java` do **trabalho** para uma pasta de rascunho, escolha um exercício da lista abaixo, escreva o método **sem olhar** e confira com o valor esperado. Só depois compare com a versão daqui.
4. Repita os que errar no dia seguinte.

## As 5 receitas

### R1 — Visita tudo (recursão) · O(n)
```java
public int f() { return f(raiz); }

private int f(No no) {
    if (no == null) return NEUTRO;   // 0 para contar/somar, -1 para altura
    // resolve f(no.esq) e f(no.dir) e junta com no.valor
}
```
Exemplos: `contarFolhas`, `somaValores`, `contarPares`, `somaFolhas`, `contarNosInternos`, `ehCheia`.

### R2 — Desce um caminho (laço) · O(h)
```java
No atual = raiz;
while (atual != null) {
    if (achou) return ...;
    if (valor < atual.valor) atual = atual.esq; else atual = atual.dir;
}
```
Exemplos: `contem`, `minimo`, `profundidade`, `pai`, `sucessor`, `piso`, `teto`, `caminhoAte`.

### R3 — Monta lista (recursão + lista por parâmetro) · O(n)
```java
public List<Integer> f() {
    List<Integer> lista = new ArrayList<>();
    f(raiz, lista);
    return lista;
}
private void f(No no, List<Integer> lista) {
    if (no == null) return;
    // ... lista.add(no.valor) na posição certa ...
}
```
Exemplos: percursos, `folhas`, `valoresDoNivel`, `emOrdemDecrescente`.

### R4 — Por nível (fila) · O(n)
```java
List<No> fila = new ArrayList<>();
fila.add(raiz);
int inicio = 0;
while (inicio < fila.size()) {
    No atual = fila.get(inicio);
    inicio++;
    // visita atual; adiciona atual.esq e atual.dir na fila se não forem null
}
```
Para saber onde termina cada nível: guarde `int fimDoNivel = fila.size();` antes de processar o nível.
Exemplos: `porNivel`, `niveis`, `larguraMaxima`, `alturaIterativa`, `ehCompleta`.

### R5 — Visita tudo, mas poda usando a regra da BST
Se `no.valor < a`, a esquerda inteira também é `< a`: nem desce nela.
Exemplos: `contarNoIntervalo`, `somaNoIntervalo`, `contarMaioresQue`, `valoresNoIntervalo`, `podarForaDoIntervalo`.

## Índice dos métodos

### 1. Básico (a API do trabalho)
`inserir`, `contem`, `tamanho`, `altura`, `contarFolhas`, `minimo`, `maximo`, `somaValores`, `profundidade`, `emOrdem`, `preOrdem`, `posOrdem`, `estaVazia`, `inserirTodos`, `contarArestas`, e o auxiliar privado `buscarNo` (usado por vários outros).

### 2. Versões alternativas ("faça com laço" / "faça com recursão")
| Método | Ideia | Custo |
|---|---|---|
| `inserirIterativo` | desce até um filho `null` do lado certo e pendura ali | O(h) |
| `contemRecursivo` | compara e chama para um lado só | O(h) |
| `tamanhoRecursivo` | `1 + esq + dir` (sem o contador) | O(n) |
| `minimoRecursivo` / `maximoRecursivo` | desce até não ter filho daquele lado | O(h) |
| `profundidadeRecursiva` | se o filho devolveu -1, repassa -1; senão soma 1 | O(h) |
| `alturaIterativa` | conta os níveis com fila | O(n) |

### 3. Contagens
| Método | O que conta | Receita |
|---|---|---|
| `contarNosInternos` | nós com pelo menos 1 filho | R1 |
| `contarNosComUmFilho` | exatamente 1 filho | R1 |
| `contarNosComDoisFilhos` | 2 filhos | R1 |
| `contarPares` / `contarImpares` | por paridade (`% 2 != 0` para ímpar!) | R1 |
| `contarPositivos` / `contarNegativos` | `> 0` / `< 0` | R5 |
| `contarMultiplosDe(k)` | `valor % k == 0` | R1 |
| `contarMaioresQue(x)` / `contarMenoresQue(x)` | com poda | R5 |
| `contarNoIntervalo(a, b)` | valores em `[a, b]` | R5 |
| `contarNosNoNivel(k)` | desce diminuindo k; conta quando k chega a 0 | R1 |

### 4. Somas, média e mediana
`somaFolhas`, `somaInternos`, `somaPares`, `somaImpares`, `somaNoNivel(k)`, `somaNoIntervalo(a, b)`, `somaMaioresQue(x)`, `media` (cuidado com divisão inteira: use `(double)`), `mediana`, `somaCaminhoAte(valor)`, `maiorSomaRaizAteFolha`.

### 5. Altura, níveis e profundidade
`alturaDoNo(valor)`, `quantidadeDeNiveis` (= altura + 1), `larguraMaxima`, `menorProfundidadeDeFolha`, `valorMaisProfundo`, `diametro`.

### 6. Buscas que usam a regra da BST
| Método | Também chamado de | Sem resposta |
|---|---|---|
| `ehFolha(v)`, `grau(v)` | número de filhos | `false` / -1 |
| `pai(v)`, `irmao(v)` | — | `null` |
| `sucessor(v)` | próximo maior, menor valor maior que v | `null` |
| `antecessor(v)` | anterior, maior valor menor que v | `null` |
| `piso(x)` | floor, maior valor ≤ x | `null` |
| `teto(x)` | ceiling, menor valor ≥ x | `null` |
| `maisProximo(x)` | valor mais perto (empate: o menor) | exceção se vazia |
| `kEsimoMenor(k)`, `kEsimoMaior(k)`, `segundoMenor`, `segundoMaior` | k começa em 1 | exceção |
| `menorDiferenca` | vizinhos na lista em ordem | exceção (< 2 nós) |
| `ancestralComum(a, b)` | LCA | exceção |
| `distancia(a, b)` | prof(a) + prof(b) − 2·prof(LCA) | -1 |
| `ehAncestral(a, b)` | a está no caminho até b | `false` |

Os que devolvem `Integer` (com I maiúsculo) usam `null` para "não existe", porque qualquer `int` poderia ser um valor real da árvore.

### 7. Percursos e listas
`emOrdemDecrescente`, `emOrdemIterativo` (pilha), `preOrdemIterativo` (pilha), `posOrdemIterativo` (pilha + inverter), `porNivel` (BFS), `niveis`, `valoresDoNivel(k)`, `folhas`, `valoresNoIntervalo(a, b)`, `caminhoAte(v)`, `caminhosRaizAteFolha` (backtracking), `visaoEsquerda`, `visaoDireita`, `paraArray`.

### 8. Verificações (boolean)
| Método | Verdadeiro quando |
|---|---|
| `ehBSTValida` | todo nó respeita os limites herdados dos ancestrais |
| `ehCheia` | todo nó tem 0 ou 2 filhos |
| `ehDegenerada` | todo nó tem no máximo 1 filho |
| `ehBalanceada` | em todo nó, \|altura(esq) − altura(dir)\| ≤ 1 |
| `ehPerfeita` | n = 2^(h+1) − 1 (todos os níveis cheios) |
| `ehCompleta` | cheia até o penúltimo nível e o último enche da esquerda |
| `igual(outra)` | mesmo formato e mesmos valores |
| `mesmosValores(outra)` | mesmos valores (formato pode mudar) |
| `existeCaminhoComSoma(alvo)` | algum caminho raiz → folha soma `alvo` |

Mais `fatorDeBalanceamento(v)` = altura(esq) − altura(dir).

### 9. Modificação
`remover(v)` (3 casos: folha, um filho, dois filhos → sucessor), `removerMinimo`, `removerMaximo`, `removerFolhas`, `podarForaDoIntervalo(a, b)`, `somarEmTodos(k)`, `limpar`, `copiar`, `construirBalanceada(int[])` (estático), `balancear`.

### 10. Impressão
`imprimir` (desenha a árvore deitada) e `toString`.

**Fora de propósito:** `espelhar`/`inverter` a árvore não está aqui porque **quebra a regra da BST** (os menores iriam para a direita). Se cair, é só trocar `no.esq` e `no.dir` em cada nó, recursivamente.

## Pegadinhas que mais derrubam

1. **Ímpar negativo:** `-3 % 2` dá `-1` em Java. Use `valor % 2 != 0`, nunca `== 1`.
2. **Divisão inteira:** `soma / quantidade` corta os decimais. Use `(double) soma / quantidade`.
3. **Altura da vazia é -1**, por isso uma folha tem altura 0. Contagens e somas começam em 0.
4. **Esquecer o `raiz =`** no `inserir`/`remover` recursivo: a árvore vazia nunca recebe o primeiro nó.
5. **`quantidade++` só onde o nó é criado** (no caso base `no == null`). Se puser antes, o repetido conta.
6. **Acessar `raiz.valor` com a árvore vazia** dá `NullPointerException`. Teste `raiz == null` antes.
7. **`lista.remove(...)` em `List<Integer>`:** `remove(int)` remove pelo **índice**; `remove(Integer)` remove pelo **valor**.
8. **Guardar a lista do caminho sem copiar:** use `new ArrayList<>(atual)`, senão todos os caminhos viram a mesma lista.
9. **Validar BST comparando só com os filhos** não basta: um neto pode violar a regra. Passe limites mínimo e máximo.
10. **Nó com um filho não é folha:** em `menorProfundidadeDeFolha` e `maiorSomaRaizAteFolha`, não trate o lado `null` como se fosse uma folha.

## Treino para a Bancada (árvore do professor)

Insira `50, 30, 70, 20, 40, 60, 80, 85` e escreva **sem olhar**:

| # | Escreva | Resultado esperado |
|---|---|---|
| 1 | `contarNosInternos()` | `4` |
| 2 | `somaFolhas()` | `205` |
| 3 | `contarPares()` | `7` |
| 4 | `contarNosComUmFilho()` | `1` |
| 5 | `contarMaioresQue(50)` | `4` |
| 6 | `contarNosNoNivel(2)` | `4` |
| 7 | `somaNoNivel(1)` | `100` |
| 8 | `ehFolha(40)` / `ehFolha(80)` | `true` / `false` |
| 9 | `pai(60)` | `70` |
| 10 | `sucessor(50)` | `60` |
| 11 | `alturaDoNo(70)` | `2` |
| 12 | `folhas()` | `[20, 40, 60, 85]` |
| 13 | `valoresDoNivel(2)` | `[20, 40, 60, 80]` |
| 14 | `caminhoAte(60)` | `[50, 70, 60]` |
| 15 | `emOrdemDecrescente()` | `[85, 80, 70, 60, 50, 40, 30, 20]` |
| 16 | `porNivel()` | `[50, 30, 70, 20, 40, 60, 80, 85]` |
| 17 | `ehCheia()` | `false` |
| 18 | `contarNoIntervalo(30, 70)` | `5` |
| 19 | `media()` | `54.375` |
| 20 | `remover(50)` e depois `preOrdem()` | `[60, 30, 20, 40, 70, 80, 85]` |

## Perguntas de teoria que podem cair

- **Por que o percurso em ordem sai ordenado?** Porque visita esquerda (menores), raiz e direita (maiores), e isso vale em toda subárvore.
- **Quando a BST fica no pior caso?** Quando as chaves são inseridas já ordenadas: ela vira uma lista, h = n − 1 e as buscas ficam O(n).
- **Altura × profundidade:** altura é medida de baixo (do nó até a folha mais funda); profundidade é medida de cima (da raiz até o nó).
- **Por que `tamanho()` é O(1)?** Porque o contador é atualizado no `inserir`; não precisa percorrer a árvore.
- **Por que o mínimo é o nó mais à esquerda?** Porque todo valor menor que um nó fica à esquerda dele; quem não tem filho esquerdo não tem ninguém menor abaixo.
- **Recursão ou laço?** Laço quando o caminho é um só (busca, mínimo, profundidade); recursão quando precisa visitar os dois lados (altura, soma, contagens, percursos).
- **Remoção com dois filhos:** troca pelo sucessor (menor da subárvore direita) ou pelo antecessor (maior da esquerda); qualquer um deles tem no máximo um filho, então sai fácil.
- **Inserir na pré-ordem reconstrói a árvore?** Sim: a raiz entra primeiro e cada nó entra antes dos seus descendentes, então o formato se repete.
