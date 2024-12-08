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

---

## 🟣 Exemplo de Uso  

### Compactação  
```java
BackupLZW backup = new BackupLZW();
backup.compactarArquivos();

## O Samuel

<img src="../Pictures/samuel.jpg">
