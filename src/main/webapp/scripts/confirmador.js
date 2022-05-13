/**
 * 
 */
 function confirmar(idcon) {
	let resposta = confirm("Deseja excluir esse contato?");
	if(resposta === true){
		document.location.href = "delete?idcon=" + idcon;
	}
}
