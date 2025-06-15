// Configuração da API
const API_BASE_URL = 'http://localhost:8080/api';

class ApiService {
  static async cadastrarPaciente(paciente) {
    try {
      const response = await fetch(`${API_BASE_URL}/pacientes`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(paciente)
      });
      
      if (!response.ok) {
        throw new Error('Erro ao cadastrar paciente');
      }
      
      return await response.json();
    } catch (error) {
      console.error('Erro na API:', error);
      throw error;
    }
  }

  static async listarPacientes() {
    try {
      const response = await fetch(`${API_BASE_URL}/pacientes`);
      
      if (!response.ok) {
        throw new Error('Erro ao carregar pacientes');
      }
      
      return await response.json();
    } catch (error) {
      console.error('Erro na API:', error);
      throw error;
    }
  }
}