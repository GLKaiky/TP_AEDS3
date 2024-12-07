cmake_minimum_required(VERSION 3.10)

# Nome do projeto
project(MyJavaProject)

# Diretórios de fontes e destino
set(SRC_DIR "${CMAKE_SOURCE_DIR}/src")
set(LIB_DIR "${CMAKE_SOURCE_DIR}/lib")

# Cria o diretório de saída se não existir
file(MAKE_DIRECTORY ${LIB_DIR})

# Adiciona um alvo personalizado para compilar o código Java
add_custom_target(compile ALL
    COMMAND ${CMAKE_COMMAND} -E echo "Compilando código Java..."
    COMMAND javac -d ${LIB_DIR} ${SRC_DIR}/*/*.java
    WORKING_DIRECTORY ${CMAKE_SOURCE_DIR}
    COMMENT "Compilando fontes Java para ${LIB_DIR}"
)

# Alvo personalizado para rodar o aplicativo Java
add_custom_target(run
    COMMAND java -cp ${LIB_DIR} App/App
    WORKING_DIRECTORY ${CMAKE_SOURCE_DIR}
    COMMENT "Executando a aplicação Java"
    DEPENDS compile
)
