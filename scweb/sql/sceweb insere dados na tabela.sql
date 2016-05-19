select * from empresa; 
insert into empresa (cnpj, nomeDaEmpresa, nomeFantasia, endereco, telefone) values("81965361000174","casas bahia", "casas bahia", "rua taquari","222-2222");
insert into convenio (empresa_cnpj, dataInicio, dataFim) values("81965361000174","03/05/2016", "15/05/2016");
delete from convenio where empresa_cnpj = "81965361000174";
delete from empresa where cnpj = "81965361000174";
insert into empresa (cnpj, nomeDaEmpresa, nomeFantasia, endereco, telefone) values("64938854000104","casas bahia", "casas bahia", "rua taquari","222-2222");
select * from convenio;
