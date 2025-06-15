// Configuração da API
const API_BASE_URL = 'http://localhost:8080/api/triagem';

// Classe principal para gerenciar o sistema
class MedDequeSystem {
    constructor() {
        this.initializeSystem();
    }

    async initializeSystem() {
        console.log('🏥 MedDeque System iniciado');
        this.setupEventListeners();
    }

    setupEventListeners() {
        // Event listeners globais podem ser adicionados aqui
        document.addEventListener('DOMContentLoaded', () => {
            console.log('DOM carregado');
        });
    }

    // Métodos para comunicação com a API
    async cadastrarPaciente(paciente) {
        try {
            const response = await fetch(`${API_BASE_URL}/cadastrar`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(paciente)
            });

            if (!response.ok) {
                const error = await response.text();
                throw new Error(error);
            }

            return await response.json();
        } catch (error) {
            console.error('Erro ao cadastrar paciente:', error);
            throw error;
        }
    }

    async realizarTriagem(gravidade) {
        try {
            const response = await fetch(`${API_BASE_URL}/triar/${gravidade}`, {
                method: 'POST'
            });

            if (!response.ok) {
                throw new Error('Erro ao realizar triagem');
            }

            return await response.json();
        } catch (error) {
            console.error('Erro ao realizar triagem:', error);
            throw error;
        }
    }

    async chamarProximoPaciente() {
        try {
            const response = await fetch(`${API_BASE_URL}/chamar-proximo`, {
                method: 'POST'
            });

            if (!response.ok) {
                throw new Error('Nenhum paciente na fila');
            }

            return await response.json();
        } catch (error) {
            console.error('Erro ao chamar próximo paciente:', error);
            throw error;
        }
    }

    async finalizarAtendimento(cpf) {
        try {
            const response = await fetch(`${API_BASE_URL}/finalizar/${cpf}`, {
                method: 'POST'
            });

            if (!response.ok) {
                throw new Error('Erro ao finalizar atendimento');
            }

            return true;
        } catch (error) {
            console.error('Erro ao finalizar atendimento:', error);
            throw error;
        }
    }

    async buscarPaciente(cpf) {
        try {
            const response = await fetch(`${API_BASE_URL}/paciente/${cpf}`);

            if (!response.ok) {
                throw new Error('Paciente não encontrado');
            }

            return await response.json();
        } catch (error) {
            console.error('Erro ao buscar paciente:', error);
            throw error;
        }
    }

    async getFilaEspera() {
        try {
            const response = await fetch(`${API_BASE_URL}/fila-espera`);
            return await response.json();
        } catch (error) {
            console.error('Erro ao carregar fila de espera:', error);
            return [];
        }
    }

    async getFilaPrioridade() {
        try {
            const response = await fetch(`${API_BASE_URL}/fila-prioridade`);
            return await response.json();
        } catch (error) {
            console.error('Erro ao carregar fila de prioridade:', error);
            return [];
        }
    }

    async getPacientesAtendimento() {
        try {
            const response = await fetch(`${API_BASE_URL}/em-atendimento`);
            return await response.json();
        } catch (error) {
            console.error('Erro ao carregar pacientes em atendimento:', error);
            return [];
        }
    }

    async getEstatisticas() {
        try {
            const response = await fetch(`${API_BASE_URL}/estatisticas`);
            return await response.json();
        } catch (error) {
            console.error('Erro ao carregar estatísticas:', error);
            return {};
        }
    }

    async getTodosPacientes() {
        try {
            const response = await fetch(`${API_BASE_URL}/todos`);
            return await response.json();
        } catch (error) {
            console.error('Erro ao carregar todos os pacientes:', error);
            return [];
        }
    }

    // Utilitários
    validarCPF(cpf) {
        cpf = cpf.replace(/[^\d]+/g, '');
        
        if (cpf.length !== 11 || /^(\d)\1{10}$/.test(cpf)) {
            return false;
        }
        
        let soma = 0;
        for (let i = 0; i < 9; i++) {
            soma += parseInt(cpf.charAt(i)) * (10 - i);
        }
        let resto = 11 - (soma % 11);
        if (resto === 10 || resto === 11) resto = 0;
        if (resto !== parseInt(cpf.charAt(9))) return false;
        
        soma = 0;
        for (let i = 0; i < 10; i++) {
            soma += parseInt(cpf.charAt(i)) * (11 - i);
        }
        resto = 11 - (soma % 11);
        if (resto === 10 || resto === 11) resto = 0;
        if (resto !== parseInt(cpf.charAt(10))) return false;
        
        return true;
    }

    formatarCPF(cpf) {
        cpf = cpf.replace(/\D/g, '');
        return cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4');
    }

    formatarDataHora(dataHora) {
        if (!dataHora) return '-';
        return new Date(dataHora).toLocaleString('pt-BR');
    }

    obterCorGravidade(gravidade) {
        switch (gravidade) {
            case 1: return 'gravidade-1';
            case 2: return 'gravidade-2';
            case 3: return 'gravidade-3';
            default: return '';
        }
    }

    obterTextoGravidade(gravidade) {
        switch (gravidade) {
            case 1: return 'Vermelho (Crítico)';
            case 2: return 'Amarelo (Urgente)';
            case 3: return 'Verde (Pouco Urgente)';
            default: return 'Não Classificado';
        }
    }

    obterClasseStatus(status) {
        switch (status) {
            case 'AGUARDANDO_TRIAGEM': return 'status-aguardando';
            case 'TRIADO': return 'status-triado';
            case 'ATENDIDO': return 'status-atendido';
            case 'FINALIZADO': return 'status-finalizado';
            default: return '';
        }
    }

    obterTextoStatus(status) {
        switch (status) {
            case 'AGUARDANDO_TRIAGEM': return 'Aguardando Triagem';
            case 'TRIADO': return 'Triado';
            case 'ATENDIDO': return 'Em Atendimento';
            case 'FINALIZADO': return 'Finalizado';
            default: return status;
        }
    }

    mostrarNotificacao(mensagem, tipo = 'success') {
        const notification = document.createElement('div');
        notification.className = `notification ${tipo}`;
        notification.textContent = mensagem;
        
        document.body.appendChild(notification);
        
        setTimeout(() => {
            notification.classList.add('show');
        }, 100);
        
        setTimeout(() => {
            notification.classList.remove('show');
            setTimeout(() => {
                document.body.removeChild(notification);
            }, 300);
        }, 3000);
    }

    mostrarLoading(elemento) {
        elemento.innerHTML = '<div class="loading"></div>';
    }

    esconderLoading(elemento, conteudoOriginal) {
        elemento.innerHTML = conteudoOriginal;
    }
}

// Função global para navegação
function navegarPara(pagina) {
    window.location.href = pagina;
}

// Função global para voltar
function voltar() {
    window.history.back();
}

// Instância global do sistema
const medDeque = new MedDequeSystem();

// Exportar para uso em outras páginas
window.MedDeque = medDeque;