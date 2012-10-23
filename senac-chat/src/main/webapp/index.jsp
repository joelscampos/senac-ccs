<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chat Senac</title>
        <script type="text/javascript" src="resources/js/jquery-1.7.2.min.js"></script>
        <script type="text/javascript" src="resources/js/knockout-2.0.0.js"></script>
        <script type="text/javascript" src="resources/js/knockout.mapping-latest.js"></script>
    </head>
    <body>
        <h1>Chat</h1>
        <div id="participant">
            <h2>Insira seu nome e clique no botão Entrar para acessar:</h2>
            <input type="text" name="participant" data-bind="value: participantName" />
            <input type="button" value="Entrar" data-bind="click: entrar" />
        </div>
        <br/>
        <div id="chatMessagesDiv">
            <ul data-bind="foreach: chatMessages">
                <li style="list-style: none;">
                    <span data-bind="text: $data">Msgs</span>
                </li>
            </ul>
        </div>
        <br/>
        <div id="chatInputDiv">
            <textarea data-bind="value: message"></textarea>
            <br/>
            <input type="button" data-bind="click: sendChatMessage" value="Enviar"/>
        </div>
        <br/>
        <script>
            var Chat = function() {
                var self = this;
                self.participantName = ko.observable();
                self.chatMessages = ko.observableArray([]);
                self.message = ko.observable();

                self.entrar = function(data) {
                    $.get("/chat/entrar", {participantName: self.participantName()}, function(data){
                        self.bind();
                    });
                }

                self.bind = function(data) {
                    $.get("/chat/bind", {}, function(data){
                        self.chatMessages.push(data);
                    }).complete(function(data){
                        self.bind();
                    });
                }

                self.sendChatMessage = function(data) {
                    $.get("/chat/sendChatMessage", {message: self.message()}, function(data){
                        self.chatMessages.push(data);
                    });
                }
            }
            
            ko.applyBindings(new Chat());
            
        </script>
    </body>
    
</html>
