# Campeonato

API Spring Boot 4.1.1 / Java 26 para campeonatos, times, jogadores, partidas e cartões.

Referência: https://github.com/ezambomsantana/arquitetura-objetos-2026-2/tree/main/aula-14/turismo

A estrutura segue a referência de turismo, mantendo o fluxo JWT iniciado no campeonato.

## Executar

No IntelliJ, configure o Project SDK e o Maven Runner para JDK 26, recarregue o Maven e execute br.edu.insper.campeonato.CampeonatoApplication.

No PowerShell, dentro da pasta do projeto:

```powershell
$env:JAVA_HOME = "$env:USERPROFILE\.jdks\openjdk-26.0.2"
.\mvnw.cmd clean verify
.\mvnw.cmd spring-boot:run
```

O perfil padrão é campeonato, na porta 8080. O pacote executável também inicia CampeonatoApplication.

Swagger: http://localhost:8080/swagger-ui/index.html

H2: http://localhost:8080/h2-console — URL JDBC jdbc:h2:file:./data/campeonato, usuário sa, senha vazia.

O banco local persiste em data/. Os testes usam bancos H2 em memória separados.

## Autenticação

1. POST /usuarios com {"email":"aluno@exemplo.com","senha":"senha123"}.
2. POST /login com os mesmos dados.
3. Copie o campo token da resposta.
4. No Swagger, clique em Authorize e cole o token. Em outros clientes, envie o cabeçalho Authorization: Bearer <token>.

O cadastro nunca retorna a senha. As senhas são armazenadas com BCrypt. Emails são normalizados para minúsculas, e duplicidade retorna HTTP 409. Credenciais incorretas e tokens inválidos ou expirados retornam HTTP 401.

A chave JWT padrão serve somente para desenvolvimento local. Para implantação, defina JWT_SECRET com pelo menos 32 bytes de segredo aleatório. JWT_EXPIRATION controla a validade em milissegundos (padrão: 3600000).

## Rotas

| Método | Caminho | Função |
| --- | --- | --- |
| POST | /usuarios | Cadastro público |
| POST | /login | Obter token |
| POST / GET | /campeonatos | Cadastrar / listar |
| GET | /campeonatos/{id} | Consultar campeonato |
| GET | /campeonatos/{id}/classificacao | Consultar pontos |
| POST / GET | /times | Cadastrar / listar |
| GET | /times/{id} | Consultar time e jogadores |
| POST | /times/{idTime}/jogadores/{idJogador} | Vincular jogador |
| POST / GET | /jogadores | Cadastrar / listar |
| GET | /jogadores/{id} | Consultar jogador |
| POST / GET | /partidas | Cadastrar / listar |
| GET | /partidas/{id} | Consultar partida e cartões |
| PATCH | /partidas/{id}/placar | Registrar ou corrigir resultado |
| POST | /partidas/{id}/cartoes | Registrar cartão |

As rotas do campeonato exigem autenticação.

Exemplos de corpos, respectivamente campeonato, time, jogador, partida, placar e cartão:

```json
{"nome":"Copa Insper","ano":2026}
```
```json
{"nome":"Time A","cidade":"São Paulo"}
```
```json
{"nome":"João","numero":10,"posicao":"Atacante","idade":22}
```
```json
{"data":"2026-10-20","idTimeMandante":1,"idTimeVisitante":2,"idCampeonato":1}
```
```json
{"golMandante":2,"golVisitante":1}
```
```json
{"tipo":"AMARELO","idJogador":1,"minuto":30}
```

Uma partida inclui os dois times no campeonato. Partidas sem placar definido não dão pontos; vitória vale 3 pontos e empate vale 1. A classificação ordena por pontos e, em empate de pontos, por nome do time. Corrigir um placar recalcula a classificação sem acumular os pontos antigos.

## Testes

CampeonatoApiTests verifica cadastro, senha protegida, login, bloqueio de tokens inválidos, duplicidade, validação HTTP, documentação pública, vínculo de jogadores, partidas, placares, cartões e classificação. CampeonatoApplicationTests verifica a inicialização do contexto.

Execute .\mvnw.cmd clean verify no terminal local para compilar, testar e empacotar.
Durante a edição no ambiente isolado do Codex, a execução foi bloqueada por java.nio.file.AccessDeniedException em Path.toRealPath, ao resolver arquivos do projeto e dependências. Portanto, os testes ainda precisam ser confirmados no ambiente local.

O código da loja virtual continua em seu pacote separado. A classe br.edu.insper.lojavirtual.LojaApplication ativa o perfil lojavirtual, com banco e porta próprios.
