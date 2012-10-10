/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.ccs.chat;

/**
 *
 * @author yara
 */
public class ChatUser {
    
    private String name = null;
    private Emissor emissor;
    private Receptor receptor;
    private Sala sala;
    private boolean connected = true;
    private ArrayList messages = new ArrayList(); // message
    private String hora(){return new SimpleDateFormat("hh:mm:ss").format(new Date()) + " ";}


	
	public ChatUser(String name, Sala sala) {
		this.name = name;
		this.sala = sala;
		if (sala.getByName(getName()) !=null) {
			throw new IllegalArgumentException("Invalid username");
		} else {
			this.setResponse(emissor);
			this.setRequest(receptor);
			sala.addUser(this);
		}		
	}
	// getName
	public String getName() {
		return name;
	}
	// getResponse
	public Emissor getResponse() {
		return emissor;
	}
	// setResponse
	public void setResponse(Emissor res) {
		this.emissor = res;
	}
	// getRequest
	public Receptor getRequest() {
		return receptor;
	}
	// setRequest
	public void setRequest(Receptor request) {
		this.receptor = request;
	}
	// getSala
	public Sala getSala() {
		return sala;
	}	
	// isConnected
	public boolean isConnected() {
		return connected;
	}
	// disconnect
	public void disconnect() {
		connected = false;
	}
	
	// checkSession
	public void checkSession() {
		try {
			/*if (getRequest().getSession() ==null
			    || getRequest().getSession().getAttribute("user") ==null) {
				this.disconnect();
			}*/			
		} catch (IllegalStateException isex) {
			//this.disconnect();  //tem algum problema aqui, pq ta chamando esse metodo...
		}
	}

	// addMessage
	public void addMessage(GenericMessage msg) {
		messages.add(msg);
		synchronized (messages) {
			// sinaliza para quem está esperando (o objeto Mensagens fica monitorando)
			messages.notifyAll();
		}
	}
	
	// getNewMessage
	public synchronized GenericMessage getNewMessage() {
		if (messages.isEmpty()) {
			//iremos inserir o codigo para esperar aqui
			try {
				synchronized (messages) {
					messages.wait(3 * 1000);
				}
			} catch (InterruptedException e) {
				// o que fazer se der um exception?
				// desconecta
				System.out.println(hora()+"ChatUser - vai executar this.disconnect()-isconnect():"+isConnected());
				this.disconnect();
				// ele desconectou
				return (null);
			}
			// se deu timeout
			if (messages.isEmpty()) {
				return (null);
			}
		}
		return (GenericMessage) (messages.remove(0));
	}

	// showMessage
	public void showMessage(GenericMessage msg) throws IOException {
		if ((msg.getMsg() !=null)&&(msg!=null)) {
			this.emissor.envia(msg); // envia o objeto mensagem
		}			
		return;
	}

    
}
