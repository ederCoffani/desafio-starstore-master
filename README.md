# starstore-master

Um pequeno ecommerce para estender a sistematização de apps para vendas, este pequeno aplicativo mostra desde a parte de intalação 
até a parte de compra do produto, ele ja vem com uma simulação de cartão com condicionais e também tem a parte do carrinho, o projeto 
foi desenvolvido para um processo seletivo porém o designer dele não ajudou muito, mais ainda sim esta bacana vale a pena ver e até mesmo 
entender ele.

Foi utilizado o padrão MVP (Model, View e Presenter).

Simples Explicação:

View - em nosso caso é o formulário que exibirá os dados, não contém regra alguma do negócio a não ser disparar eventos que notificam mudança de estado dos dados que ele exibe e processamento próprio dele, como por exemplo código para fechar o formulário. Um objeto view implementa uma interface que expõe campos e eventos que o presenter necessita.

Model - São os objetos que serão manipulados. Um objeto Model implementa uma interface que expõe os campos que o presenter irá atualizar quando sofrerem alteração na view.

Presenter - É a ligação entre View e Model, possui papel de mediador entre eles. Ele é encarregado de atualizar o view quando o model é alterado e de sincronizar o model em relação ao view.



