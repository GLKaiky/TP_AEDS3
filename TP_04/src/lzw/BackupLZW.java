package lzw;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BackupLZW extends LZW {

    private static final int BUFFER_SIZE = 1024; // Tamanho do buffer em bytes

    public void compactarArquivos() throws Exception {
        // Diretórios de origem e destino
        String pastaDados = "./dados";
        String pastaBackup = "./backup";

        // Criação da pasta de backup com a data atual
        String dataBackup = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File pastaBackupDir = new File(pastaBackup + "backup_" + dataBackup);
        if (!pastaBackupDir.exists()) {
            pastaBackupDir.mkdirs();
        }

        // Caminho do arquivo de backup
        String arquivoBackup = pastaBackupDir + "/backup_file.lzw";
        long tamanhoOriginalTotal = 0;

        try (FileOutputStream arquivoSaida = new FileOutputStream(arquivoBackup)) {
            File pasta = new File(pastaDados);
            
            //Erro ao abrir a pasta
            if (!pasta.isDirectory()) {
                throw new IllegalArgumentException("O caminho especificado não é uma pasta.");
            }
            
            //Array de arquivos da pasta
            File[] arquivos = pasta.listFiles();

            if (arquivos == null || arquivos.length == 0) {
                System.out.println("Nenhum arquivo encontrado na pasta especificada.");
                return;
            }

            //Caso haja arquivos dentro da pasta, é feito a compactação para um array de bytes.
            for (File arquivo : arquivos) {
                if (arquivo.isFile()) {
                    System.out.println("Compactando arquivo: " + arquivo.getName());

                    tamanhoOriginalTotal += arquivo.length();

                    try (FileInputStream arquivoEntrada = new FileInputStream(arquivo)) {
                        // Compacta os dados enquanto lê
                        byte[] dadosCompactados = compactarFluxo(arquivoEntrada);

                        // Grava metadados e dados compactados no arquivo de backup
                        escreverArquivoNoBackup(arquivoSaida, arquivo.getName(), dadosCompactados);
                    }
                }
            }

            System.out.println("Backup realizado com sucesso!");


            //----| FAZENDO O CALUCLO DA TAXA DE COMPRESSÃO |----
            
            // Obtenha o tamanho do arquivo compactado
            File arquivoCompactado = new File(arquivoBackup);
            long tamanhoCompactado = arquivoCompactado.length();

            // Calcule a taxa de compressão
            double taxaCompressao = (double) tamanhoOriginalTotal / tamanhoCompactado;
            System.out.printf("Taxa de Compressão: %.2f%n", taxaCompressao);

            //----------------------------------------------------s

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Método de Compactação do Fluxo de bytes
    private byte[] compactarFluxo(InputStream inputStream) throws Exception {

        ByteArrayOutputStream dadosCompactados = new ByteArrayOutputStream();
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesLidos;
        
        //Looping enquanto haver bytes para ler
        while ((bytesLidos = inputStream.read(buffer)) != -1) {
            // Compactar o bloco lido
            byte[] dadosCompactadosBloco = super.codifica(sliceBuffer(buffer, bytesLidos));
            dadosCompactados.write(dadosCompactadosBloco);
        }
    
        return dadosCompactados.toByteArray();
    }

    private byte[] sliceBuffer(byte[] buffer, int length) {
        byte[] slicedBuffer = new byte[length];
        System.arraycopy(buffer, 0, slicedBuffer, 0, length);
        return slicedBuffer;
    }
    

    private void escreverArquivoNoBackup(FileOutputStream arquivoSaida, String nomeArquivo, byte[] dadosCompactados) throws IOException {
        // Escreve o nome do arquivo no backup (em UTF-8)
        byte[] nomeBytes = nomeArquivo.getBytes("UTF-8");
        arquivoSaida.write(intParaBytes(nomeBytes.length)); // Comprimento do nome
        arquivoSaida.write(nomeBytes); // Nome do arquivo

        // Escreve o tamanho dos dados compactados
        arquivoSaida.write(intParaBytes(dadosCompactados.length));

        // Escreve os dados compactados
        arquivoSaida.write(dadosCompactados);
    }


    //Método para recuperar arquivo
    public void recuperarArquivo(String caminhoBackup) throws Exception {
        File arquivoBackup = new File(caminhoBackup);

        try (FileInputStream fis = new FileInputStream(arquivoBackup)) {

            while (fis.available() > 0) {
                // Lê o tamanho do nome do arquivo
                byte[] nomeBytesLength = new byte[4];
                fis.read(nomeBytesLength);
                int nomeLength = bytesParaInt(nomeBytesLength);

                // Lê o nome do arquivo
                byte[] nomeBytes = new byte[nomeLength];
                fis.read(nomeBytes);
                String nomeArquivo = new String(nomeBytes, "UTF-8");

                // Lê o tamanho dos dados compactados
                byte[] dadosLengthBytes = new byte[4];
                fis.read(dadosLengthBytes);
                int dadosLength = bytesParaInt(dadosLengthBytes);

                // Lê os dados compactados
                byte[] dadosCompactados = new byte[dadosLength];
                fis.read(dadosCompactados);

                // Descompacta os dados usando LZW
                byte[] dadosDescompactados = descompactarFluxo(new ByteArrayInputStream(dadosCompactados));

                // Recupera o arquivo original
                Path path = Paths.get("./dados/" + nomeArquivo);
                Files.createDirectories(path.getParent()); // Cria diretórios se necessário
                Files.write(path, dadosDescompactados);

                System.out.println("Arquivo " + nomeArquivo + " recuperado com sucesso.");
            }
        }
    }

    //Método para descompactar o fluxo de bytes.
    private byte[] descompactarFluxo(InputStream inputStream) throws Exception{
        ByteArrayOutputStream dadosDescompactados = new ByteArrayOutputStream();
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesLidos;
    
        while ((bytesLidos = inputStream.read(buffer)) != -1) {
            // Descompactar o bloco lido
            byte[] dadosDescompactadosBloco = super.decodifica(sliceBuffer(buffer, bytesLidos));
            dadosDescompactados.write(dadosDescompactadosBloco);
        }
    
        return dadosDescompactados.toByteArray();
    }
    
    //Usa-se operadores de deslocamento de bits para converter um Int para array de byte
    private byte[] intParaBytes(int valor) {
        return new byte[]{
            (byte) (valor >> 24),
            (byte) (valor >> 16),
            (byte) (valor >> 8),
            (byte) valor
        };
    }


    //Usa-se operadores de deslocamento de bits para converter um array de bytes para Int
    private int bytesParaInt(byte[] bytes) {
        return ((bytes[0] & 0xFF) << 24) | ((bytes[1] & 0xFF) << 16) |
               ((bytes[2] & 0xFF) << 8) | (bytes[3] & 0xFF);
    }
}
