# AcademiaDev - Desafio Clean Architecture

Este projeto é a implementação de um protótipo de plataforma de cursos online, desenvolvido estritamente sob os princípios da **Clean Architecture** (Arquitetura Limpa) e **SOLID**, sem o uso de frameworks "mágicos" como Spring Boot, priorizando a lógica de domínio e a inversão de dependência.

## 🏗️ Decisões de Arquitetura

O projeto foi estruturado em 4 camadas concêntricas, respeitando rigorosamente a **Regra da Dependência** (o código fonte só aponta para dentro).

### 1. Camada Domain (Núcleo)
* **Responsabilidade:** Contém as regras de negócio corporativas e entidades (`Student`, `Course`, `Enrollment`).
* **Isolamento:** Não possui dependências de nenhuma outra camada. Não sabe que existe banco de dados ou interface gráfica.
* **Exemplo de Lógica:** O método `Student.canEnroll()` encapsula a regra de que alunos BASIC só podem ter 3 matrículas ativas.

### 2. Camada Application (Casos de Uso)
* **Responsabilidade:** Orquestra o fluxo de dados para atingir os objetivos do usuário.
* **Inversão de Dependência:** Define interfaces (`Repository`) para o acesso a dados, mas não as implementa. Os UseCases (`MatricularAlunoUseCase`) recebem essas interfaces via construtor.

### 3. Camada Infrastructure (Adaptadores)
* **Responsabilidade:** Implementa os detalhes técnicos.
* **Persistência:** Utiliza `Maps` e `Lists` em memória para simular um banco de dados, implementando as interfaces definidas na camada Application.
* **Utils:** A classe `GenericCsvExporter` utiliza **Java Reflection** para exportar dados dinamicamente, isolando essa complexidade técnica da lógica de negócio.

### 4. Camada Main (Raiz de Composição)
* **Responsabilidade:** É o único ponto do sistema acoplado a todas as camadas.
* **Injeção de Dependência:** Realiza a instanciação manual das classes e injeta as dependências de infraestrutura dentro dos Casos de Uso.

## 🛠️ Tecnologias Utilizadas
* **Java 17** (Puro, sem frameworks)
* **Maven** (Gerenciamento de dependências e build)
* **PlantUML** (Modelagem da arquitetura)

## 🚀 Como Rodar
1. Clone o repositório.
2. Execute a classe principal:
   ```bash
   mvn clean compile exec:java -Dexec.mainClass="br.com.academiadev.main.Main"