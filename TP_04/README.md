# Documentação TP_03

## 🔴Motivações e Esforço
À primeira parte do trabalho em questão, se trata de um CRUD (Create, Read, Update, Delete) para um sistema de registro de Tarefas no geral. Tais tarefas são descritas por uma entidade com: Nome, Data de Inicio, Data de Conclusão, Prioridade e Status da tarefa.

## 🔵Desenvolvimento da Busca por Lista Invertida e Etiqueta

### 🔵Main app
No arquivo "App.java", o CRUD de Tarefas foi modificado, agora é possível fazer o Update, Delete e Read pesquisando pelo título da tarefa, pela categoria que as tarefas estão atreladas ou pelas etiquetas que a tarefa está correlacionada. 

<pre>A pesquisa pelo título da tarefa é efetuada utilizando a classe ArquivoTarefas.</pre>
<pre>A pesquisa por etiquetas é feita pela classe ArquivoEtiqueta.</pre>
<pre>A pesquisa por Categoria é feita pela classe ArquivoCategoria.</pre>

### 🔵ArquivoTarefas.java
Este arquivo permite a manipulação do arquivo que contém as tarefas em conjunto com a manipulação da Lista Invertida (efetuada pelo StopWords.java). 

### Criação de Tarefas.
<pre>Inserção de Tarefas em duas Árvores B. Uma faz inserção na Árvore B de Categorias, e a outra para Árvore B de etiquetas. Além disso é utilizada a classe StopWords.java para identificar quais as palavras válidas do titulo da tarefa para em seguida inseri-las na Lista Invertida.</pre>

### Leitura de Tarefas.
<pre>Identificação por meio do ID da Etiqueta para encontrar as tarefas atreladas à aquele ID. Além de também identificação por meio do ID de Categoria. Como também há o método listar por meio do titulo da tarefa, assim pegando apenas as palavras válidas e verificando na lista invertida se os IDs de tarefa possuem aquela palavra no titulo.</pre>

### Atualização de Tarefas.
<pre>A atualização pega o ID da tarefa no qual se deseja mudar. São verificadas as palavras válidas e então deletadas param serem substituídas e formar um novo título.</pre>

### Deletação de Tarefas.
<pre>Ao pegar o ID da Categoria da Tarefa desejada, remove-se o ID da Tarefa da Árvore B+ de Categoria, repete-se esse mesmo processo para cada Etiqueta correlacionada à Tarefa. Por fim é executada uma verificação de quais são as palavras válidas existentes no título da tarefa e então é feito a remoção do ID da tarefa para cada um dessas palavras.</pre>


### 🔵ArquivoEtiqueta.java

<pre>A classe ArquivoEtiqueta gerencia as operações de CRUD para etiquetas associadas às tarefas, incluindo criação, leitura, atualização e exclusão de etiquetas. Esta classe utiliza uma árvore B+ para armazenamento eficiente das etiquetas e permite vincular ou desvincular etiquetas às tarefas, além de buscar tarefas baseadas nas etiquetas associadas.</pre>

### 🔵StopWords.java
Possui a Lista Invertida, um ArrayList contendo uma lista de palavras inválidas (No caso, a lista de StopWords). Ele possui o método "stopWordsCheck",  para verificar as palavras que são StopWords, assim substituindo por espaços em branco. Possui o método "wordsCounter" , no qual verifica as palavras válidas e faz o cálculo da frequência. O construtor abre o arquivo de texto "stopWordsList.txt" e lê o arquivo para verificar quais são as stopwords. E por fim, um método de inserção na Lista Invertida 

## 🟢Experiência
Foi uma experiência desafiadora, mas divertida, uma vez que, necessitou-se de muita atenção e estudo, além de horas de teste para verificar o funcionamento completo do código. Em especial, gostaria de destacar a parte que inclui a criação do CRUD de etiquetas e do desafio de integrar esse CRUD de maneira que fosse possível inserir e remover etiquetas de uma determinada tarefa a qualquer momento.

## 🟠Checklist

### O índice invertido com os termos das tarefas foi criado usando a classe ListaInvertida?
<pre>Sim, a Lista Invertida foi corretamente implementada de acordo com os códigos e orientações do professor.</pre>

### O CRUD de rótulos foi implementado?
<pre>Sim, a implementação do CRUD faz a inserção, remoção, atualização e leitura de etiquetas corretamente na Árvore B+ correspondente.</pre>

### No arquivo de tarefas, os rótulos são incluídos, alterados e excluídos em uma árvore B+? 
<pre>Sim, como mencionada anteriormente, todas as operações foram implementadas seguindo os códigos previamente desenvolvidos, tanto pelo professor, quanto pelos alunos.</pre>

### É possível buscar tarefas por palavras usando o índice invertido?
<pre>Sim, é possível buscar tarefas por palavras válidas presentes no título das tarefas. O índice invertido permite buscar rapidamente por essas palavras e encontrar os IDs das tarefas associadas.</pre>

### É possível buscar tarefas por rótulos usando uma árvore B+? 
<pre>Sim, a busca por rótulos é realizada usando a Árvore B+, que armazena e indexa as etiquetas, permitindo uma busca rápida e eficiente por tarefas relacionadas a um rótulo específico.</pre>

### O trabalho está funcionando corretamente?
<pre>Sim, o trabalho está totalmente funcional</pre>

### O trabalho está completo?
<pre>Sim, o trabalho foi completo com sucesso</pre>

### O trabalho é original e não a cópia de um trabalho de outro grupo?
<pre>Sim, foi um trabalho feito em equipe pelo grupo</pre>


## 🔴O Grupo
<img src="../Pictures/IMG_20241125_095944174.jpg" style="width: 600px; height: 270px;">
