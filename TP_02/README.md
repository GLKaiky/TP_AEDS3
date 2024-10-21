# Instruções de Utilização

Para rodar o projeto, siga as seguintes instruções:

<pre>Navegue para o diretório Build</pre>

<pre>Execute "cmake .." para configurar o projeto</pre>

<pre>Para compilar o projeto, use "cmake --build ."</pre>

<pre>Para rodar o projeto utilize "cmake --build . --target run"</pre>

# Documentação TP_02

  

## 🔴Sobre

Uma simples base de dados para armazenar e recuperar dados. Os dados são armazenados em um arquivo indexado, e várias estruturas de dados podem ser usadas para encontrar, alterar e modificar dados através de outros atributos do registro. Neste trabalho apresentamos um exemplo de como fazer uma relação 1 com N usando uma estrutura de Árvore B+ para permitir extrair todos os N dados de um só bloco

## 🔵Funcionamento

### Arquivo Indexado Genérico

É usado de um arquivo indexado, estruturado em dois arquivos. No primeiro arquivo, o Arquivo Principal, são armazenados os dados da seguinte forma:

- Cabeçalho: contém próximo UID incrementável
- Conjunto de Registro: o resto do arquivo contém os demais registros, organizado da seguinte forma:
	- Lápide: marca se o arquivo foi logicamente removido ou não
	- Tamanho: tamanho em bytes do Registro de tamanho variável que será armazenado logo em frente
	- Registro: contém o id do registro e os seus demais atributos

  

Cada posição inicial de um registro é armazenada em um Arquivo de Hash Extensível, que armazena um par de ID do registro e a endereço do registro no arquivo (em que 0 representa o primeiro byte). A busca na hash é feita com ID, o que disponibiliza acesso direto ao registro atrelado.

Este gerenciamento é feito de forma genérica, e qualquer alteração no arquivo consequentemente afeta a estrutura de acesso atrelada. Isto acontece para todos os métodos de CRUD do arquivo (criação, leitura, edição e deleção), onde métodos do Arquivo de Hash Extensível são disparados para notificar a mudança do Arquivo Principal.

Podemos expandir a capacidade das operações da base de dado atrelando métodos de outras estruturas de dados no final de cada método presente CRUD na classe arquivo (create, delete, update, read), permitindo atualizar outras estruturas de dados com comportamento semelhante ao Hash Extensível



## Estrutura de Dados Implementadas

### Hash Extensível Genérico

Implementação usada para atrelar dois valores quando não é relevante guardar os dados em ordem. É usada também para a implementação interna não editável dentro do Arquivo Indexado Genérico.

Ela espera como tipo genérico uma classe que capaz de de ser convertido e bytes e recriado através desses bytes, nomeada de *RegistroHashExtensivel*, mas para o funcionamento é esperado que a classe contenha apenas dois valores e um deles serve como um UID responsável pela criação do hash. O método hashCode() também deverá ser atualizado para que apenas este UID seja utilizado. O tipo não deverá conter tamanho variável, o que permite estimar precisamente o tamanho em bytes de cada instância.

Esta estrutura não aceita dados sem UIDS, e elementos de mesma hash não são aceitas pela estrutura. O motivo disso é permitir que cada busca retorne apenas um valor associado à ele.

O Hash Extensível é dividido em dois arquivos, ambos com capacidade de crescer indefinidamente:

- Arquivo de Diretório: Contém profundidade global e uma lista de posições para acessar os cestos
- Arquivo de Cestos: Contém todos os cestos de tamanho fixo, capaz de ser lido diretamente pela memória principal. Cada cesto contém sua profundidade local e em sequência os dados de hash local correspondente  

A expansão do diretório é feito de acordo com os incrementos da sua profundidade global, e é uma lista de tamanho $2^{profundidadeGlobal}$. Assim como no conceito de Hash Extensível, mais de um índice da lista podem referenciar ao mesmo cesto, caso possua uma profundidade local menor que a profundidade global.

A quantidade máxima de cada cesto é recebida em seu construtor, junto com o nome de cada um dos dois arquivos. O cesto tem uma capacidade máxima de 32767 elementos, e uma profundidade global máxima de 127. Não há tamanho máximo para cada instância, então é necessário cautela na hora de definir o par de chave-valor.

  
  

### Árvore B+ Genérica

Implementação usada para atrelar dois valores quando é relevante guardar os dados em ordem.

Similarmente ao Hash Extensível Genérico, é esperado que a classe seja convertida e recriada por uma lista de bytes, e é esperado que contenha um par de valores de um UID e outro valor atrelado. Mas diferentemente da estrutura de Hash, não é necessário atualizar o hashCode(), pois não será atualizado, mas será necessário fazer comparação compareTo() com outro do mesmo tipo. A classe que adiciona esta funcionalidade é chamada de RegistroArvoreBMais

Diferentemente do HashExtensivel, esta estrutura será usada para busca por meio de um valor não único, para retornar lista de valores únicos.

Para compareTo(), a definição esperada é uma ordenação que priorize este primeiro o valor não único, mas caso ele seja repetido o elemento será ordenado pelo outro valor do par.

A estrutura é organizada por meio de nós de n elementos, e geram uma árvore uniforme. Quando a quantidade de elementos excede o tamanho do nó, os nós não folhas possuem apenas cópias não válidas da chave, responsáveis para indicar por meio do método compareTo() onde será o destino dos métodos CRUD.

A ordem da árvore B+ é informada no construtor, mas não há limite de profundidade.

  

## 🔵Exemplo Gerado

Como exemplo, foi criado um crud para inserir de forma extensiva uma lista de tarefas de classe Tarefa que são classificadas por outra classe de classificação. Os dados são armazenados na implementação de Arquivo Genérico para a classe Tarefa, mas duas árvores B+ auxiliares são usadas para atrelar um UID de classificação com múltiplas UIDS de Tarefa, e um UID de classificação com um nome.

O CRUD no terminal permite a adição e edição de Tarefas diretamente através da especificação de seus atributos: nome, índice de classificação (as possibilidades são informadas), data de início e data de fim.

Fora isto, é possível adicionar e remover categorias caso estejam vazias.

Para visualização, é usado da classificação para mostrar apenas as tarefas pertencente a aquele grupo. Usamos esta mesma estrutura para a remoção e edição, onde a classificação é fornecida primeiro para facilitar na escolha de qual tarefa editar / remover.

  
  

## 🟢Experiência

Como experiência, foi possível aprofundar no conhecimento e visualização de como organizar, armazenar e acessar dados sem necessitar de um id específico, permitindo ter uma noção sobre como os bancos de dados fazem para armazenar valores de forma extremamente escalável e eficiente.

  
  
## 🟠Checklist

  

### O CRUD (com índice direto) de categorias foi implementado?

<pre>Sim, a tabela Hash Extensível foi corretamente implementada de acordo com os códigos mostrados em sala de aula</pre>

  

### Há um índice indireto de nomes para as categorias?

<pre>Sim, existe uma árvore B+ que fornece o id de uma categoria através de seu nome</pre>

  

### O atributo de ID de categoria, como chave estrangeira, foi criado na classe Tarefa?

<pre>Sim, Tarefa possui a chave estrangeira, e ela é usada para definição das Árvore B+ entre id categoria e id tarefa</pre>

  

### Há uma árvore B+ que registre o relacionamento 1:N entre tarefas e categorias?

<pre>Sim, uma das árvores trata exatamente este tipo de relacionamento</pre>

  

### É possível listar as tarefas de uma categoria?

<pre>Sim, o método de busca é responsável por listar todas as tarefas presentes em uma categoria</pre>

  

### A remoção de categorias checa se há alguma tarefa vinculada a ela?

<pre>Sim, a categoria não pode ser removido caso haja alguma tarefa</pre>

  

### A inclusão da categoria em uma tarefa se limita às categorias existentes?

<pre>Sim, uma lista das possíveis categorias é fornecida na operação</pre>

  

### O trabalho está funcionando corretamente?

<pre>Sim, é possível executar todas operações disponíveis sem que haja erros no dados armazenados</pre>

  

### O trabalho está funcionando corretamente?

<pre>Sim, todos os requisitos foram atendidos</pre>

  

### O trabalho é original e não a cópia de um trabalho de outro grupo?

<pre>Sim, usamos como auxílio apenas o código fornecido pelo professor Marcos Kutova</pre> 
