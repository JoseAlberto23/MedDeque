# MedDeque - Sistema de Triagem Médica

Sistema de triagem médica desenvolvido com Spring Boot (backend) e HTML/CSS/JavaScript (frontend).

## 🚀 Funcionalidades

### Versão Atual
- ✅ Cadastro de pacientes com validação
- ✅ Listagem ordenada por gravidade
- ✅ Interface responsiva e intuitiva
- ✅ Armazenamento local (localStorage)

### Melhorias Implementadas
- ✅ **Validação de CPF** - Validação completa do CPF brasileiro
- ✅ **Formatação automática** - CPF formatado automaticamente durante digitação
- ✅ **Integração com API** - Comunicação com backend Spring Boot
- ✅ **Notificações** - Sistema de notificações para feedback ao usuário
- ✅ **Loading states** - Indicadores visuais durante operações
- ✅ **Filtros e busca** - Filtrar por gravidade e buscar por nome
- ✅ **Estatísticas** - Dashboard com estatísticas dos pacientes
- ✅ **Tratamento de erros** - Melhor tratamento de erros e validações
- ✅ **CORS configurado** - Comunicação segura entre frontend e backend

## 🛠️ Tecnologias

### Backend
- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- H2 Database (em memória)
- Maven

### Frontend
- HTML5
- CSS3 (com Grid e Flexbox)
- JavaScript ES6+
- Fetch API para comunicação com backend

## 📦 Como executar

### Backend
```bash
cd backend
mvn spring-boot:run
```
O backend estará disponível em: `http://localhost:8080`

### Frontend
Abra o arquivo `frontend/index.html` em um servidor local ou navegador.

Para usar as funcionalidades melhoradas, acesse:
- `frontend/triagem/triagem-melhorada.html` - Triagem com validações
- `frontend/triagem/listagem-melhorada.html` - Listagem com filtros

## 🎯 Próximas melhorias sugeridas

1. **Autenticação e autorização**
   - Login para médicos/enfermeiros
   - Diferentes níveis de acesso

2. **Banco de dados persistente**
   - PostgreSQL ou MySQL
   - Backup automático

3. **Relatórios**
   - Relatórios por período
   - Estatísticas avançadas
   - Exportação para PDF/Excel

4. **Notificações em tempo real**
   - WebSockets para atualizações em tempo real
   - Notificações push

5. **Mobile responsivo**
   - PWA (Progressive Web App)
   - App mobile nativo

6. **Integração com sistemas hospitalares**
   - HL7 FHIR
   - APIs de sistemas existentes

## 🔧 Estrutura do projeto

```
meddeque/
├── backend/
│   ├── src/main/java/com/meddeque/backend/
│   │   ├── MedDequeApplication.java
│   │   ├── Paciente.java
│   │   ├── PacienteController.java
│   │   ├── PacienteRepository.java
│   │   └── config/CorsConfig.java
│   └── pom.xml
├── frontend/
│   ├── js/
│   │   ├── api.js
│   │   └── utils.js
│   ├── style/
│   │   ├── index.css
│   │   └── components.css
│   ├── triagem/
│   │   ├── triagem-melhorada.html
│   │   └── listagem-melhorada.html
│   └── index.html
└── README.md
```

## 📋 API Endpoints

- `GET /api/pacientes` - Lista todos os pacientes ordenados por gravidade
- `POST /api/pacientes` - Cadastra novo paciente
- `GET /api/pacientes/{id}` - Busca paciente por ID
- `GET /api/pacientes/gravidade/{gravidade}` - Lista pacientes por gravidade

## 🎨 Melhorias de UX/UI

- Interface mais moderna e profissional
- Cores que seguem padrões médicos (vermelho, amarelo, verde)
- Feedback visual imediato
- Responsividade aprimorada
- Acessibilidade melhorada