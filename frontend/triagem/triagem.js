function cadastrarPaciente(event) {
  event.preventDefault(); 

  const nome = document.getElementById("nome").value;
  const cpf = document.getElementById("cpf").value;
  const sintomas = document.getElementById("sintomas").value;
  const gravidade = parseInt(document.getElementById("gravidade").value);

  const paciente = { nome, cpf, sintomas, gravidade };

  const pacientes = JSON.parse(localStorage.getItem("pacientes")) || [];
  pacientes.push(paciente);

  localStorage.setItem("pacientes", JSON.stringify(pacientes));

  alert("Paciente cadastrado com sucesso!");
  document.getElementById("form-triagem").reset();
}


function carregarPacientes() {
  const tabela = document.querySelector("#tabela-pacientes tbody");
  let pacientes = JSON.parse(localStorage.getItem("pacientes")) || [];

  pacientes.sort((a, b) => a.gravidade - b.gravidade);

  pacientes.forEach(paciente => {
    const tr = document.createElement("tr");

    let cor;
    switch (paciente.gravidade) {
      case 1: cor = "#ffcccc"; break;
      case 2: cor = "#fff8b3"; break;
      case 3: cor = "#ccffcc"; break;
      default: cor = "#fff";
    }

    tr.innerHTML = `
      <td>${paciente.nome}</td>
      <td>${paciente.cpf}</td>
      <td>${paciente.sintomas}</td>
      <td style="background-color: ${cor}; text-align: center;">${paciente.gravidade}</td>
    `;

    tabela.appendChild(tr);
  });
}

function voltar() {
  window.location.href = "../index.html";
}

carregarPacientes();
