# Documentação TP_01

## 💠Motivações e Esforço
À primeira parte do trabalho em questão, se trata de um CRUD (Create, Read, Update, Delete) para um sistema de registro de Tarefas no geral. Tais tarefas são descritas por uma entidade com: Nome, Data de Inicio, Data de Conclusão, Prioridade e Status da tarefa.

## 💠Funcionamento do CRUD, suas Classes e métodos

### 💠Main app
Iniciamos com um arquivo principal "APP".java onde serão feitas as principais requisições para o CRUD e acesso do usuário, sendo isso a Main do programa. Descrevendo mais adentro dos registros, criamos também a classe "Registro".java que tem por sua principal funcionalidade ser uma interface (uma interface é um espécime de "contrato" criado para implementar em classes que deverão ter tais métodos descritos na interface) para as demais classes. Logo em seguida a interface "Registro" é implementada na classe "Tarefa".java onde já descrita anteriormente, irá ter os métodos do tal registro como funcionalidades extras necessárias para o funcionamento do programa como os métodos de "toByteArray" e "fromByteArray" métodos que transformem um objeto em uma sequência de bytes e que transforme uma sequência de bytes em um objeto "Tarefa".

### 💠Classes Tarefa, Registro e Arquivo
Seguindo o envio do objeto "Tarefa" para seu registro, chegamos na classe "Arquivo".java sendo uma classe genérica (que pode implementar qualquer tipo de objeto sem excessões) tal o objeto genérico "T" terá uma herança de "Registro" onde a classe "Arquivo" apenas poderá implementar e registrar objetos que tenham implementado a interface "Registro". Como construtor a classe "Arquivo".java terá como parâmetro o objeto do tipo Constructor<> para receber construtores de quaisquer objetos, e um nome para o arquivo, nomeado no trabalho como "fN", e como função ele irá criar uma pasta de dados para salvar o arquivo de registro .DB e os arquivos com as referências de hashExtensível dos registros contidos no arquivo, além disso criará o cabeçalho do arquivo que irá conter um numero inteiro dizendo a quantidade de registros presentes no arquivo até então.

### 💠Classes RegistroHashExtensível, ParIDEndereco
Como padrão de implementação, assim como feito anteriormente, criamos o arquivo de Registro genérico que será seguido como interface padrão para a hash extensível, agora implementando métodos como o de HashCode e size. A classe ParIDEndereco implementa o método RegistroHashExtensivel que será responsável principalmente por implementar o id e endereço dos objetos registrados dentro do arquivo principal dando assim uma maneira de acesso mais rápida e menos sequencial aos registros feitos.

### 💠HashExtensível, Cestos e Diretórios
A classe de HashExtensível funciona a base de outras 2 classes, chamadas respectivamente de Cestos e Diretório, sabendo disso, seu funcionamento funciona a base de funções como CRUDS também onde inserimos as referências do endereço dos registros do arquivo principal dentro dos Cestos.
Os cestos armazenam os registros efetivos (como tarefas). Cada cesto é responsável por manter um conjunto de registros que possuem o mesmo valor de hash base, limitado pela capacidade do cesto.Quando um cesto atinge sua capacidade máxima, ele é dividido, e o diretório é atualizado para refletir essa mudança.

O diretório mantém ponteiros (endereços) para os cestos, organizando-os de acordo com a profundidade global do diretório. A profundidade global define quantos bits do valor de hash são usados para determinar o cesto apropriado.
O diretório é um array de endereços, onde cada posição aponta para um cesto específico. A profundidade global define o tamanho do array.

