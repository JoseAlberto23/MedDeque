// Utilitários para validação e formatação
class Utils {
  static validarCPF(cpf) {
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

  static formatarCPF(cpf) {
    cpf = cpf.replace(/\D/g, '');
    return cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4');
  }

  static obterCorGravidade(gravidade) {
    switch (gravidade) {
      case 1: return { cor: '#ffebee', texto: 'Vermelha (Alta)', classe: 'gravidade-alta' };
      case 2: return { cor: '#fff8e1', texto: 'Amarela (Média)', classe: 'gravidade-media' };
      case 3: return { cor: '#e8f5e8', texto: 'Verde (Baixa)', classe: 'gravidade-baixa' };
      default: return { cor: '#fff', texto: 'Não definida', classe: '' };
    }
  }

  static mostrarNotificacao(mensagem, tipo = 'sucesso') {
    const notificacao = document.createElement('div');
    notificacao.className = `notificacao notificacao-${tipo}`;
    notificacao.textContent = mensagem;
    
    document.body.appendChild(notificacao);
    
    setTimeout(() => {
      notificacao.classList.add('mostrar');
    }, 100);
    
    setTimeout(() => {
      notificacao.classList.remove('mostrar');
      setTimeout(() => {
        document.body.removeChild(notificacao);
      }, 300);
    }, 3000);
  }
}