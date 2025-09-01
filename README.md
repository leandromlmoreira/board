# 🎯 Board Management System

> **Sistema completo de gerenciamento de boards e cards** desenvolvido em Java com arquitetura robusta e interface moderna

[![Java](https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Gradle](https://img.shields.io/badge/Gradle-8.8-02303A?style=for-the-badge&logo=gradle&logoColor=white)](https://gradle.org/)
[![Liquibase](https://img.shields.io/badge/Liquibase-4.29-296DAD?style=for-the-badge&logo=liquibase&logoColor=white)](https://www.liquibase.org/)

## 🚀 **Sobre o Projeto**

Este é um sistema completo de gerenciamento de boards inspirado no **Kanban**, desenvolvido como parte do curso da **DIO (Digital Innovation One)**. O projeto demonstra conceitos avançados de **Java**, **arquitetura de software**, **persistência de dados** e **interface de usuário**.

### ✨ **Características Principais**

- 🎨 **Interface moderna** com cores, emojis e formatação
- 🗄️ **Persistência robusta** com MySQL e Liquibase
- 🔄 **Migrações automáticas** de banco de dados
- 📊 **Sistema de estatísticas** e relatórios
- 🛡️ **Validação robusta** de entrada do usuário
- 🎯 **Arquitetura limpa** com separação de responsabilidades

## 🏗️ **Arquitetura do Sistema**

```
src/
├── main/java/br/com/dio/
│   ├── dto/           # Data Transfer Objects
│   ├── entity/        # Entidades JPA
│   ├── persistence/   # Camada de persistência
│   │   ├── config/    # Configurações de conexão
│   │   ├── dao/       # Data Access Objects
│   │   ├── converter/ # Conversores de dados
│   │   └── migration/ # Estratégias de migração
│   ├── service/       # Lógica de negócio
│   ├── ui/           # Interface do usuário
│   │   └── util/     # Utilitários da UI
│   └── exception/    # Exceções customizadas
```

## 🛠️ **Tecnologias Utilizadas**

### **Backend**
- **Java 17+** - Linguagem principal
- **Gradle 8.8** - Build automation
- **MySQL 8.0** - Banco de dados
- **Liquibase 4.29** - Migrações de banco

### **Bibliotecas**
- **Lombok** - Redução de boilerplate
- **SLF4J + Logback** - Sistema de logging
- **Jackson** - Serialização YAML/JSON

### **Testes**
- **JUnit 5** - Framework de testes
- **Mockito** - Mocking framework
- **H2 Database** - Banco em memória para testes

## 🚀 **Como Executar**

### **Pré-requisitos**
- Java 17 ou superior
- MySQL 8.0 ou superior
- Gradle 8.8 ou superior

### **1. Configuração do Banco**
```sql
CREATE DATABASE board_db;
USE board_db;
```

### **2. Configuração das Propriedades**
Edite `src/main/resources/database.properties`:
```properties
db.url=jdbc:mysql://localhost:3306/board_db
db.username=root
db.password=sua_senha
```

### **3. Execução**
```bash
# Compilar
./gradlew build

# Executar
./gradlew run

# Ou criar JAR executável
./gradlew fatJar
java -jar build/libs/Board-1.0-SNAPSHOT-fat.jar
```

## 📋 **Funcionalidades**

### **🎯 Gestão de Boards**
- ✅ Criar novos boards
- ✅ Selecionar boards existentes
- ✅ Excluir boards
- ✅ Configuração automática de colunas

### **📁 Gestão de Colunas**
- 🚀 **Coluna Inicial** - Cards novos
- ⏳ **Colunas Pendentes** - Em progresso
- ✅ **Coluna Final** - Concluídos
- ❌ **Coluna de Cancelamento** - Cancelados

### **🃏 Gestão de Cards**
- ➕ Criar cards com título e descrição
- 🔄 Mover cards entre colunas
- 🚫 Bloquear/desbloquear cards
- ❌ Cancelar cards
- 📊 Visualizar detalhes

### **📈 Estatísticas e Relatórios**
- 📊 Estatísticas do board
- 📁 Estatísticas por coluna
- 🚀 Relatório de performance
- 📅 Data e hora dos relatórios

## 🎨 **Interface do Usuário**

O sistema possui uma interface moderna e intuitiva com:
- 🌈 **Cores personalizadas** para melhor experiência
- 🎭 **Emojis** para facilitar a navegação
- 📱 **Validação robusta** de entrada
- 🔄 **Fluxo intuitivo** entre menus
- 📋 **Headers e separadores** organizados

## 🧪 **Testes**

```bash
# Executar todos os testes
./gradlew test

# Executar testes específicos
./gradlew test --tests BoardApplicationTest
```

## 📁 **Estrutura do Banco**

### **Tabelas Principais**
- **`BOARDS`** - Informações dos boards
- **`BOARDS_COLUMNS`** - Colunas de cada board
- **`CARDS`** - Cards dos boards
- **`BLOCKS`** - Histórico de bloqueios

### **Relacionamentos**
- Board → BoardColumns (1:N)
- Board → Cards (1:N)
- BoardColumn → Cards (1:N)
- Card → Blocks (1:N)

## 🔧 **Configurações**

### **application.yml**
```yaml
database:
  url: jdbc:mysql://localhost:3306/board_db
  username: root
  password: ""

logging:
  level: INFO
  pattern: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"

ui:
  colors: true
  pagination: 10
  date-format: "dd/MM/yyyy HH:mm:ss"
```

## 🚀 **Melhorias Implementadas**

### **✨ Funcionalidades Adicionais**
- 🎨 Sistema de cores para console
- ✅ Validação robusta de entrada
- 📊 Serviço de estatísticas
- 🔄 Tratamento de exceções melhorado
- 📝 Logging estruturado

### **🏗️ Arquitetura**
- 📦 DTOs para transferência de dados
- 🎯 Separação clara de responsabilidades
- 🔧 Configuração externalizada
- 🧪 Estrutura de testes organizada

## 🤝 **Contribuição**

Este projeto foi desenvolvido como parte do curso da **DIO** e pode ser usado como:
- 📚 **Material de estudo** para Java e arquitetura
- 🎯 **Portfólio** para entrevistas técnicas
- 🚀 **Base** para projetos similares
- 🧪 **Exemplo** de boas práticas

## 📚 **Aprendizados**

### **Conceitos Aplicados**
- **Clean Architecture** - Separação de responsabilidades
- **DAO Pattern** - Acesso a dados
- **DTO Pattern** - Transferência de dados
- **Service Layer** - Lógica de negócio
- **Exception Handling** - Tratamento de erros
- **Database Migration** - Controle de versão do banco

### **Tecnologias Dominadas**
- **Java 17** - Recursos modernos da linguagem
- **MySQL** - Banco de dados relacional
- **Liquibase** - Migrações de banco
- **Gradle** - Build automation
- **Lombok** - Produtividade no desenvolvimento

## 🎉 **Sobre o Desenvolvedor**

**Leandro Macedo** - [@leandromlmoreira](https://github.com/leandromlmoreira)

👾 **Terror dos Bugs** - Programador há 4 anos, especializado em:
- 🎮 **RedM e FiveM** (Lua)
- 🌐 **Desenvolvimento Web** (TypeScript, React, Next.js)
- ☕ **Java** (este projeto!)
- 🎯 **Objetivo**: Lançar primeiro jogo indie fullstack

## 📄 **Licença**

Este projeto é parte do curso da **DIO (Digital Innovation One)** e está disponível para fins educacionais.

---

<div align="center">

**⭐ Se este projeto te ajudou, deixe uma estrela! ⭐**

**🚀 Desenvolvido com 💻 e ☕ por [@leandromlmoreira](https://github.com/leandromlmoreira)**

</div>
