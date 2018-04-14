package by.vorobyov.skillmatrix.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("su.help.tabs.util")
@ComponentScan("su.help.tabs.data")
@ComponentScan("su.help.tabs.controller")
@ComponentScan("su.help.tabs.service")
@ComponentScan("su.help.tabs.repository")
public class AppConfig {

//    public DataParserUtil getDataParserUtil(){return new DataParserUtil(indexProvider )}
}
