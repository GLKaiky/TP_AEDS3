# Documentação - TP_04  

## 🔴 Motivação e Contexto  
A classe `BackupLZW` foi desenvolvida para compactação e recuperação de arquivos em um formato eficiente usando o algoritmo de compressão LZW (Lempel-Ziv-Welch). O principal objetivo é facilitar o backup de dados, reduzindo o espaço ocupado e preservando a integridade dos arquivos.

---

## 🔵 Estrutura e Métodos  

### 🔵 **Classe: `BackupLZW`**  
A classe herda funcionalidades da classe `LZW` e implementa métodos adicionais para manipulação de arquivos, possibilitando a compactação de múltiplos arquivos em um único backup e a recuperação posterior.

### **Métodos principais**  

#### 🔹 `compactarArquivos()`  
Compacta todos os arquivos dentro do diretório `./dados` e os armazena em um único arquivo de backup no diretório `./backup`, com a data e hora no nome do arquivo.

- **Passos do processo**:
  1. Lê os arquivos do diretório de origem.
  2. Compacta os dados usando o algoritmo LZW.
  3. Armazena os dados compactados e metadados (nome e tamanho) em um único arquivo.

- **Saída**:
  - Arquivo de backup com o formato `backup_file.lzw` no diretório `./backup`.
  - Calcula e exibe a taxa de compressão.

#### 🔹 `recuperarArquivo(String caminhoBackup)`  
Recupera os arquivos compactados de um arquivo de backup, restaurando-os para o diretório `./dados`.

- **Passos do processo**:
  1. Lê os metadados (nome e tamanho) de cada arquivo.
  2. Descompacta os dados usando o algoritmo LZW.
  3. Restaura os arquivos no diretório original.

#### 🔹 `compactarFluxo(InputStream inputStream)`  
Compacta os dados de um fluxo de entrada em pequenos blocos usando o algoritmo LZW.

- **Entrada**: Um `InputStream` contendo os dados do arquivo.  
- **Saída**: Um array de bytes compactados.

#### 🔹 `descompactarFluxo(InputStream inputStream)`  
Descompacta os dados de um fluxo de entrada usando o algoritmo LZW.

- **Entrada**: Um `InputStream` contendo os dados compactados.  
- **Saída**: Um array de bytes descompactados.

#### 🔹 `escreverArquivoNoBackup(FileOutputStream arquivoSaida, String nomeArquivo, byte[] dadosCompactados)`  
Escreve os metadados e os dados compactados de um arquivo no arquivo de backup.

- **Parâmetros**:
  - `arquivoSaida`: O fluxo de saída para o arquivo de backup.
  - `nomeArquivo`: Nome do arquivo original.
  - `dadosCompactados`: Dados compactados a serem armazenados.

#### 🔹 Métodos Auxiliares  
- `intParaBytes(int valor)`: Converte um inteiro para um array de 4 bytes.  
- `bytesParaInt(byte[] bytes)`: Converte um array de 4 bytes para um inteiro.  
- `sliceBuffer(byte[] buffer, int length)`: Redimensiona um array de bytes para o tamanho necessário.

---

## 🟢 Experiência  
O desenvolvimento desta classe foi desafiador, especialmente na integração dos métodos de compactação e recuperação. Foi necessário garantir a consistência dos dados e implementar o cálculo da taxa de compressão, o que proporcionou maior aprendizado sobre manipulação de fluxos de entrada/saída e algoritmos de compressão.

---

## 🟠 Checklist de Funcionalidades  
### Há uma rotina de compactação usando o algoritmo LZW para fazer backup dos arquivos?
<pre>Sim, foi feito a implementação da compactação LZW para fazer os backups dos arquivos.</pre>

### Há uma rotina de descompactação usando o algoritmo LZW para recuperação dos arquivos?
<pre>Sim, além da compactação para fazer o backup dos arquivos, há também a implementação da descompactação e recuperação dos arquivos usando o algoritmo LZW.</pre>

### O usuário pode escolher a versão a recuperar?
<pre>Sim, ao fazer a recuperação dos arquivos, é disponivel ao usuário escolher qual versão ele gostaria de recuperar.</pre>

### Qual foi a taxa de compressão alcançada por esse backup?
<pre>A taxa de compressão alcançada foi de 1,02.</pre>

### O trabalho está funcionando corretamente?
<pre>Sim, o trabalho está totalmente funcional</pre>

### O trabalho está completo?
<pre>Sim, o trabalho foi completo com sucesso</pre>

### O trabalho é original e não a cópia de um trabalho de outro grupo?
<pre>Sim, foi um trabalho feito em equipe pelo grupo</pre>
---

## O Samuel

<img width="400px" height="300px" src="../Pictures/samuel.jpg">
