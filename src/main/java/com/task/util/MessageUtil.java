package com.task.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

//class responsible for sending messages to the application context
public class MessageUtil {
	
    public static void errorMessage(String mensagem) {
        addMessages(new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, null));
    }

    public static void successMensage(String mensagem) {
        addMessages(new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, null));
    }

    public static void warningMensage(String mensagem) {
        addMessages(new FacesMessage(FacesMessage.SEVERITY_WARN, mensagem, null));
    }
    
    private static void addMessages(FacesMessage fm) {
    	FacesContext.getCurrentInstance().addMessage(null, fm);
    }
}