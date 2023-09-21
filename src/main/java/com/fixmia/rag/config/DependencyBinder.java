package com.fixmia.rag.config;

import com.fixmia.rag.util.JwtUtil;
import jakarta.inject.Singleton;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.hibernate.boot.jaxb.Origin;
import org.hibernate.boot.jaxb.spi.Binding;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.StartElement;

public class DependencyBinder extends AbstractBinder {

    @Override
    protected void configure() {
       bind(JwtUtil.class).to(JwtUtil.class).in(Singleton.class);
    }
}
