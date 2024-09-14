# Documentação TP_01

## Motivações e Esforço
À primeira parte do trabalho em questão, se trata de um CRUD (Create, Read, Update, Delete) para um sistema de registro de Tarefas no geral. Tais tarefas são descritas por uma entidade com: Nome, Data de Inicio, Data de Conclusão, Prioridade e Status da tarefa.

## Funcionamento do CRUD

### Main app
Iniciamos com um arquivo principal "APP".java onde serão feitas as principais requisições para o CRUD e acesso do usuário, sendo isso a Main do programa. Descrevendo mais adentro dos registros, criamos também a classe "Registro".java que tem por sua principal funcionalidade ser uma interface (uma interface é um espécime de "contrato" criado para implementar em classes que deverão ter tais métodos descritos na interface) para as demais classes. Logo em seguida a interface "Registro" é implementada na classe "Tarefa".java onde já descrita anteriormente, irá ter os métodos do tal registro como funcionalidades extras necessárias para o funcionamento do programa como os métodos de "toByteArray" e "fromByteArray" métodos que transformem um objeto em uma sequência de bytes e que transforme uma sequência de bytes em um objeto "Tarefa".

### classes Tarefa, Registro e Arquivo
Seguindo o envio do objeto "Tarefa" para seu registro, chegamos na classe "Arquivo".java sendo uma classe genérica (que pode implementar qualquer tipo de objeto sem excessões) tal o objeto genérico "T" terá uma herança de "Registro" onde a classe "Arquivo" apenas poderá implementar e registrar objetos que tenham implementado a interface "Registro". Como construtor a classe "Arquivo".java terá como parâmetro o objeto do tipo Constructor<> para receber construtores de quaisquer objetos, e um nome para o arquivo, nomeado no trabalho como "fN", e como função ele irá criar uma pasta de dados para salvar o arquivo de registro .DB e os arquivos com as referências de hashExtensível dos registros contidos no arquivo, além disso criará o cabeçalho do arquivo que irá conter um numero inteiro dizendo a quantidade de registros presentes no arquivo até então.

### classes RegistroHashExtensível, ParIDEndereco
Como padrão de implementação, assim como feito anteriormente, criamos o arquivo de Registro genérico que será seguido como interface padrão para a hash extensível, agora implementando métodos como o de HashCode e size. A classe ParIDEndereco implementa o método RegistroHashExtensivel que será responsável principalmente por implementar o id e endereço dos objetos registrados dentro do arquivo principal dando assim uma maneira de acesso mais rápida e menos sequencial aos registros feitos.