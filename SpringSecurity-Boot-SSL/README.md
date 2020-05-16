### Build and RUN

* Using IDE
1. Import as maven project
2. Right click -> build
3. Right click on Application.java and run as 'Spring boot'/'java application'

* Using cmd/shell
1. cd SpringSecurity-Boot-SSL
2. mvn clean install
3. mvn spring-boot:run

### KeyStore and Server Config
1. A keystore has already been created and places in resources.
2. The configuration related to the keystore and the server can be found in application.properties

`server.port=8443`
`server.ssl.enabled=true`
`server.ssl.protocol=TLS`
`server.ssl.key-store-password=mystorepass`
`server.ssl.key-store=classpath:myKeystore.p12`
`server.ssl.key-store-type=PKCS12`
`server.ssl.key-alias=tomcat`
`server.ssl.key-password=mystorepass`

3. To create your own certificate following command can be used, make sure to update server properties based on your own keystore.
![generate keystore](https://github.com/ankidaemon/SpringSecurityInEasySteps/blob/master/SpringSecurity-Boot-SSL/keystore.JPG?raw=true)

### Import KeyStore/certificates
![chrome->setting->manage certificates->import](https://github.com/ankidaemon/SpringSecurityInEasySteps/blob/master/SpringSecurity-Boot-SSL/import-certificate.JPG?raw=true)

### URL
![https://localhost:8443/login](https://github.com/ankidaemon/SpringSecurityInEasySteps/blob/master/SpringSecurity-Boot-SSL/URL.png?raw=true)

* The above certificate is not signed so you would need to trust the certificate.
