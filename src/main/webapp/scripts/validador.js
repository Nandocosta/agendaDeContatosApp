/**
 * 
 */
 function validPhone (phone) {
    var regex = new RegExp('^\\(?[0-9]{2}\\)? ?(([1-9]{1}[0-9]{3}-?[0-9]{4})|(9[1-9]{4}-?[0-9]{4}))$');
    return regex.test(phone);
}

function validEmail(email) {
  var regex = /\S+@\S+\.\S+/;
  return regex.test(email);
}

function validarForm(){
	let nome = frmContato.nome.value
	let fone = frmContato.fone.value
	let email = frmContato.email.value

	if(nome.trim() === ""){
		alert('Preencha o campo nome')
		frmContato.nome.focus
		return null
	}else if(!validPhone(fone)){
		alert('Preencha o campo com um telefone valido. ex:(99)99999-9999')
		frmContato.fone.focus
		return null
	}else if(!validEmail(email)){
		alert('Preencha o campo com um email valido')
		frmContato.email.focus
		return null
	}else{
		document.forms["frmContato"].submit()
	}
	
}
