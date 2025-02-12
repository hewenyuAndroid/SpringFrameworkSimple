package com.example.handler;

import com.example.parser.CustomElementBeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class CustomNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("custom-element", new CustomElementBeanDefinitionParser());
    }
}
