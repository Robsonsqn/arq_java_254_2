# RobsonGallina API

API REST simples para gerenciamento de jogos (videogames, jogos de tabuleiro e card games)

## O que este projeto faz

Este repositório contém uma API REST construída com Spring Boot que expõe operações CRUD para entidades de jogos. Ele carrega dados iniciais a partir de arquivos de texto em `src/main/resources/data/` e usa um banco H2 em memória por padrão.

Principais recursos:
- Endpoints REST para listar, buscar, criar, atualizar, excluir e alternar o status "jogado" de jogos.
- Estrutura de domínio que separa `Game` em subtipos (VideoGame, BoardGame, CardGame).
- Carregadores de dados iniciais que populam o repositório a partir de arquivos `*.txt` em `resources/data`.
- Banco H2 em memória configurado para facilitar testes locais.

## Requisitos

- Java 17
- Maven 3.6+ (ou usar o wrapper `mvnw`/`mvnw.cmd` já incluído)

## Como começar (desenvolvimento)

1. Clone o repositório:

```powershell
git clone <repo-url>
cd robsongallinaapi
```

2. Build e rodar usando o wrapper Maven:

```powershell
./mvnw spring-boot:run
# no Windows PowerShell use:
.\mvnw.cmd spring-boot:run
```

3. A API estará disponível por padrão em `http://localhost:8080`.

Endpoint principal:
- `GET /games` — lista todos os jogos
- `GET /games/{id}` — busca por id
- `GET /games/title/{title}` — busca por título
- `POST /games` — cria um novo jogo (JSON)
- `PUT /games/{id}` — atualiza jogo
- `DELETE /games/{id}` — deleta jogo
- `PATCH /games/{id}/toggle-played` — alterna campo `played`

Exemplo de payload para criação (JSON):

```json
{
  "title": "Nome do Jogo",
  "publisher": { "name": "Editora X" },
  "year": 2023,
  "played": false
}
```

## Configuração de banco (H2)

O projeto vem configurado para usar o H2 em memória (`spring.datasource.url=jdbc:h2:mem:gamedb`) e habilita o console H2 em `/h2-console`.

Para acessar o console H2 localmente (após subir a aplicação):

1. Abra `http://localhost:8080/h2-console`
2. Use a URL JDBC: `jdbc:h2:mem:gamedb`, usuário `sa` e senha em branco

## Estrutura do projeto

- `src/main/java/.../controller` — controladores REST (ex.: `GamesController`)
- `src/main/java/.../service` — lógica de negócio (`GameService`)
- `src/main/java/.../repository` — interfaces Spring Data JPA
- `src/main/java/.../loaders` — classes que carregam dados iniciais a partir de `resources/data`
- `src/main/resources/data` — arquivos `videogames.txt`, `boardgames.txt`, `cardgames.txt` com dados de exemplo

## Próximos passos 

- Adicionar autenticação/autorização para endpoints sensíveis
- Externalizar configurações para profiles (`application-dev.properties`, `application-prod.properties`)
