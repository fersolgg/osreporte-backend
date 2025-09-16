// Componente para obtener mensajes internacionalizados desde el archivo de mensajes
package com.tuxpan.soportesw.osreporte_backend.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageUtil {
    // Fuente de mensajes configurada en Spring
    private final MessageSource messageSource;

    // Constructor que inyecta la fuente de mensajes
    public MessageUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    // Obtiene un mensaje por clave usando el locale actual
    public String get(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }

    // Obtiene un mensaje por clave y argumentos usando el locale actual
    public String get(String key, Object[] args) {
        return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
    }
}
