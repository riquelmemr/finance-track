# Finance Track API

API backend do **Finance Track**, uma aplicação para controle financeiro, construída em **Java 17** com **Spring Boot**, seguindo boas práticas de organização em camadas e preparada para ambientes de desenvolvimento, homologação e produção.

O projeto foi pensado para ser simples de rodar localmente, fácil de configurar via variáveis de ambiente e pronto para uso com Docker e CI/CD.

---

## 🧩 Tecnologias

- **Java 17**
- **Spring Boot** (Web, Data JPA, Validation)
- **PostgreSQL**
- **Maven**
- **Autenticação com JWT**
- **Docker / Docker Compose**
- **GitHub Actions (CI/CD)**

---

## 📁 Estrutura geral

```
financetrack/
 ├── src/
 │   ├── main/
 │   │   ├── java/
 │   │   │   └── com.financetrack
 │   │   └── resources/
 │   │       ├── application.yml
 │   │       ├── application-dev.yml
 │   │       └── application-prd.yml
 │   └── test/
 ├── Dockerfile
 ├── docker-compose.yml
 ├── pom.xml
 └── README.md
```

---

## ⚙️ Configuração por ambiente

A aplicação utiliza o padrão do **Spring Profiles**, permitindo múltiplos ambientes:

- `dev` → desenvolvimento local
- `prd` → produção

O profile ativo é definido pela variável de ambiente:

```
SPRING_PROFILES_ACTIVE=dev
```

---

## 🌱 Variáveis de ambiente

As principais variáveis esperadas pela aplicação são:

| Variável                | Descrição                             |
|-------------------------|---------------------------------------|
| `SPRING_PROFILES_ACTIVE` | Define o profile ativo (`dev`, `prd`) |
| `POSTGRES_HOST`         | Host do banco de dados                |
| `POSTGRES_PORT`         | Porta do banco de dados               |
| `POSTGRES_DB`           | Nome do banco                         |
| `POSTGRES_USER`         | Usuário do banco                      |
| `POSTGRES_PASSWORD`     | Senha do usuário do banco             |
| `DATASOURCE_ADMIN_USERNAME` | Usuário administrador da aplicação    |
| `DATASOURCE_ADMIN_PASSWORD` | Senha do administrador da aplicação   |

Essas variáveis são usadas diretamente nos arquivos `application-*.yml`.

---

## 🧠 Como funciona o `application.yml`

O arquivo `application.yml` contém **configurações comuns a todos os ambientes**, como:

- Nome da aplicação
- Configurações gerais do Spring
- Configurações que não variam entre `dev` e `prd`

Exemplo conceitual:

- Configurações globais → `application.yml`
- Configurações locais → `application-dev.yml`
- Configurações de produção → `application-prd.yml`


---

## 🗄️ Banco de dados

O projeto utiliza **PostgreSQL**.

Em desenvolvimento local, é recomendado utilizar **Docker Compose** para subir o banco rapidamente.

---

## 🐳 Rodando Docker Compose

### Subir aplicação + banco

```
docker-compose up -d
```

---

## ▶️ Rodando localmente

1. Configure as variáveis de ambiente
2. Certifique-se de ter Java 17 instalado
3. Execute:

```
./mvnw spring-boot:run
```
A aplicação ficará disponível em:

```
http://localhost:8080/api/v1
```


---

## 🧪 Testes

Para rodar os testes:

```
./mvnw test
```

Os testes também são executados automaticamente no pipeline de CI.

---

## 🔁 CI/CD

O projeto possui pipeline configurado no **GitHub Actions** com:

- Build do projeto
- Execução de testes
- (Opcional) Build e publish de imagem Docker

As pipelines são executadas nas branches principais (`develop` e `main`).

---

## 📌 Observações

- Todas as configurações sensíveis devem ser feitas via **variáveis de ambiente**
- Nenhuma senha ou segredo deve ser versionado
- O projeto está preparado para evolução futura (front-end separado, autenticação, etc.)

---

## 👤 Autor

**Riquelme Maia Rodrigues**  
Projeto pessoal para estudo, evolução técnica e uso real em produção.

