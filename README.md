# clientejacrm-api

Este projeto utiliza o Quarkus, o framework Java Supersônico Subatômico.

Se quiser saber mais sobre o Quarkus, visite <https://quarkus.io/>.

## Executando a aplicação em modo dev

Você pode executar a aplicação em modo dev, o que permite live coding, com:

```shell script
./mvnw quarkus:dev
```

> **_NOTA:_** O Quarkus agora possui uma Dev UI, disponível apenas em modo dev em <http://localhost:8080/q/dev/>.

## Empacotando e executando a aplicação

A aplicação pode ser empacotada usando:

```shell script
./mvnw package
```

Isso gera o arquivo `quarkus-run.jar` no diretório `target/quarkus-app/`. Note que não é um _über-jar_, pois as dependências são copiadas para `target/quarkus-app/lib/`.

A aplicação pode ser executada com `java -jar target/quarkus-app/quarkus-run.jar`.

Se você quiser construir um _über-jar_, execute o seguinte comando:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

A aplicação empacotada como _über-jar_ pode ser executada com `java -jar target/*-runner.jar`.

## Criando um executável nativo

Você pode criar um executável nativo usando:

```shell script
./mvnw package -Dnative
```

Ou, se não tiver o GraalVM instalado, é possível executar a compilação nativa em um contêiner:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

Em seguida, execute o binário gerado com: `./target/clientejacrm-api-1.0.0-SNAPSHOT-runner`

Se desejar saber mais sobre a criação de executáveis nativos, consulte <https://quarkus.io/guides/maven-tooling>.

## Código fornecido

### REST

Inicie facilmente seus Web Services REST

[Seção relacionada do guia...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)

## Contêiner de desenvolvimento

Este repositório contém uma pasta `.devcontainer` usada pelo Codex para iniciar o ambiente de desenvolvimento. Ao iniciar o contêiner, um serviço PostgreSQL é iniciado automaticamente utilizando as credenciais de `src/main/resources/application.properties`:

```
database:     clientejacrm
username:     postgres
password:     senha123
```

Para iniciar o ambiente manualmente, execute:

```bash
docker compose -f .devcontainer/docker-compose.yml up -d
```

A API estará disponível na porta `8080` e o banco de dados na `5432`.

## Integração com Gemini AI

Para habilitar as chamadas à inteligência artificial do Gemini, defina a
variável de ambiente `GOOGLE_API_KEY` com sua chave de API antes de
executar a aplicação:

```bash
export GOOGLE_API_KEY=SUAS_CHAVE_AQUI
```

Com a aplicação em execução, é possível gerar texto acessando o endpoint
`/gemini?prompt=SEU_PROMPT`.

Se preferir, também é possível omitir o nome do parâmetro e enviar somente o
texto, por exemplo: `/gemini?Olá%20tudo%20bem%3F`.

## Integração com OpenRouter

Para habilitar as chamadas à API do OpenRouter, defina a
variável de ambiente `OPENROUTER_API_KEY` com sua chave antes de
executar a aplicação:

```bash
export OPENROUTER_API_KEY=SUAS_CHAVE_AQUI
```

Com a aplicação em execução, é possível gerar texto acessando o endpoint
`/openrouter?prompt=SEU_PROMPT`.

Assim como no Gemini, também é possível omitir o nome do parâmetro e enviar
somente o texto, por exemplo: `/openrouter?Olá%20tudo%20bem%3F`.
