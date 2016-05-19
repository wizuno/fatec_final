<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
            label {
                font: normal 12px courier !important;
            }
</style>
<title>Cadastrar Convenio</title>
<link rel="stylesheet" type="text/css" href="/sceweb/CSS/Formato.css">
<script type="text/javascript" language="javascript">
	
	function incluir() {
		document.formConvenioIncluir.acao.value = "IncluirConvenio";
		document.formConvenioIncluir.submit();
	}
	
	function cancelar() {
		history.go(-1)
	}
</script>
</head>
<body>
	<jsp:include page="Cabecalho.jsp" />
	<div id="principal">
		<div id="titulo">
			<h3>Cadastrar Convenio</h3>
		</div>
		<hr>
		<div id="formulario2">

			<form name="formConvenioIncluir" action="/sceweb/ServletControle" method="post">
				<table id="tabcampos">
					
					<tr>
						<td><label> CNPJ:</label></td>
						<td><input id="campo" size="18" type="text" name="txtCNPJ"
							value=""></td>
					</tr>
					<tr>
						<td><label> Data inicio:</label></td>
						<td><input id="campo" size="18" type="text" name="txtDtInicio"
							value=""></td>
					</tr>
					<tr>
						<td><label> Data termino:</label></td>
						<td><input id="campo" size="18" type="text" name="txtDtTermino"
							value=""></td>
					</tr>
					
					
					<tr>
						<td colspan=2 id="botoes" align="center">
						<input id="botao" type=button name=btnIncluir onclick=incluir() value=Confirmar>
						<input id="botao" type=reset name=btnCancelar onclick=cancelar() value=Cancelar>
					    
					    </td>
					</tr>
				</table>
				<input type="hidden" name="acao" value="">
				<input type="hidden" name="menu" value="principal">
				<%	String msg = (String) request.getAttribute("msg"); 
					if(msg == null)
						msg="";
				%>
				<label><%=msg%></label>
			</form>
		</div>
	</div>
	<jsp:include page="Rodape.jsp" />
</body>
</html>