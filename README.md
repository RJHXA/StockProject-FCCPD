# StockProject-FCCPD

## Resumo do Projeto
O servidor continua sendo um emissor de informações sobre ativos de investimento, entretanto, no checkpoint 2, decidimos utilizá-lo também como intermediário do chat para todos os clientes, portanto a partir dessa entrega o servidor adquiriu 2 funcionalidades dentro do sistema utilizando duas threads diferentes uma para gerenciamento do chat e outra para atualizações(envios) em tempo real dos preços dos ativos através do multicast.

O cliente também foi aprimorado desde o checkpoint 1, a partir de agora ele possui 2 funcionalidades sendo uma delas “ouvinte” na porta do multicast e printa os valores recebidos no monitor que já tinha sido proposto no passado checkpoint, e uma nova funcionalidade de modo chat que envia/responde mensagens para a porta do servidor. Tanto a recepção e emissão das mensagens foram feitas a partir de 2 threads separadas.

## Execução
1- Primeiro se deve executar o Servidor para que o serviço de receber as ações e seus preços esteja funcionando.

2- Se você quiser utilizar o serviço de comunicação entre clientes, você precisa rodar o Cliente duas vezes onde uma janela aparecerá para você informar a mensagem. Para encerrar a comunicação é so digitar "quit" que é encerrado!
