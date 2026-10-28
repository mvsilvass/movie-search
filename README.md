# 🎬 AI-Powered Movie Search Engine | Desafio Versus Codecon

> **Status do Projeto:** **Em andamento**. Um projeto pessoal desenvolvido a partir de um desafio da Codecon, focado na construção de um sistema inteligente de busca semântica e processamento de dados em lote.

---

## 🚀 Sobre o Projeto & Arquitetura de IA

Inspirado no [desafio da Codecon](https://github.com/codecon-dev/versus-google-filmes), este projeto foi construído para ir além da busca tradicional por palavras-chave. Ele implementa uma arquitetura moderna de **Inteligência Artificial e Recuperação Semântica**, operando diretamente "por dentro da máquina":

* **Pipelines de Ingestão e Processamento:** Utilização de **Spring Batch** para realizar o upload, leitura massiva e ingestão eficiente de todo o dataset com milhares de filmes do CSV para o backend em Spring Boot.
* **Modelos de Linguagem e Embeddings:** Integração com **Ollama** rodando localmente, utilizando o modelo de *text embedding* (`nomic-embed-text`) para transformar títulos, sinopses e metadados em vetores de alta dimensionalidade.
* **Banco de Dados Vetorial:** Armazenamento e indexação dos vetores em PostgreSQL utilizando a extensão **PGVector**, permitindo realizar buscas baseadas em similaridade semântica real (ex: encontrar filmes por temática ou contexto, e não apenas correspondência exata).
*  **Integração com APIs Externas:** Consumo da API do TMDB em tempo de execução para buscar e renderizar dinamicamente os pôsteres e imagens oficiais dos filmes, utilizando @FeignClient para uma comunicação limpa, declarativa e integrada entre serviços.
*  **Interface Moderna:** Front-end desenvolvido em **Angular** para uma interação fluida com a busca inteligente.

---

## 🛠️ Stack Tecnológica

* **Backend:** Java, Spring Boot, Spring Batch e Spring AI
* **Inteligência Artificial & Dados:** Ollama (`nomic-embed-text`), PostgreSQL + PGVector
* **Infraestrutura:** Docker Compose
* **Frontend:** Angular

---

## ⚙️ Como Executar o Projeto Localmente

Siga o passo a passo abaixo para configurar o ambiente e rodar a aplicação na sua máquina:

### ⚠️ Pré-requisitos
* **Docker** instalados e em execução na sua máquina.
* Java JDK e Node.js/Angular CLI instalados.

Siga o passo a passo abaixo para configurar o ambiente e rodar a aplicação na sua máquina:

### 1. Clonando o Repositório
```bash
git clone https://github.com/mvsilvass/movie-search.git
cd movie-search
```

### 2. Configurando o Dataset (CSV)
1. Baixe o dataset oficial de filmes diretamente do repositório do desafio: [GitHub - Codecon Versus Google Filmes](https://github.com/codecon-dev/versus-google-filmes).
2. **Recomendação:** Salve o arquivo CSV na pasta de recursos do seu projeto backend (`src/main/resources/dataset/tmdb_5000_movies.csv`) ou ajuste os diretórios conforme o local onde salvou o seu CSV.

### 3. Configurando as Variáveis de Ambiente (.env)
Na raiz do projeto, crie o seu arquivo .env a partir do arquivo de exemplo .env.exemple, preenchendo a sua chave válida da API do TMDB (TMDB_API_KEY) para garantir o correto funcionamento das requisições das imagens.

### 4. Subindo a Infraestrutura com Docker Compose
Execute o comando abaixo para subir os containers (banco de dados com suporte a PGVector e o Ollama):
```bash
docker-compose up -d
```

### 5. Baixando o Modelo de IA no Container do Ollama
Como o Ollama precisa do modelo de embeddings para realizar a vetorização, execute o comando abaixo no seu terminal para baixar o modelo diretamente no container em execução:
```bash
docker exec -it movie-search-ollama-1 ollama pull nomic-embed-text
```
