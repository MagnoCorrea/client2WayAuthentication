# client2WayAuthentication
This is a simple 2-way authentication client in Java
that requires no Aditional libraries.

## Running
To test, you will need to perforn 2 main opperations. Configure Server and run Client.
If you have an configured 2-way authentication SERVER, up and ready, perform only te run client, with the proper keys.

### Settup the keys
the sample keys are on samples folder :D

The easiest way to test is start copying the keys to c:/tmp/chaves (Windows) or /tmp/chaves (xNIX);

### Configuring the server
#### Tomcat 8.0.X (Tested), 8.5.X (problaby run)
Edit the file server.xml including another Connector. For me works fine on 8444. Put below other Connectors input, i.e. 8443

    <Connector port="8444" protocol="org.apache.coyote.http11.Http11NioProtocol"
               maxThreads="150" SSLEnabled="true" scheme="https" secure="true"
               clientAuth="true" sslProtocol="TLSv1.2"
                keystoreFile="C:\tmp\chaves\MyServer.jks"
                keystorePass="password"
                truststoreFile="C:\tmp\chaves\MyServer.jks"
                />

The sample server is on samples folder :D
Deploy the ssl-server.war in your tomcat server and restart.

### Running the client
run the client.jar

The sample client is on samples folder :D

java -jar client.jar

### Editing your client

This project was developed on Eclipse Mars. Fell free to use another IDE. :)


### Generating your own keys

cd /tmp/chaves

keytool -genkey -alias MyServer -keyalg RSA -validity 1825 -keystore "MyServer.jks" -storetype JKS -dname "CN=myserver.com,OU=My Company Name,O=My Organization,L=My Location,ST=My State,C=My Country Short Code" -keypass password -storepass password

keytool -exportcert -alias MyServer -keystore MyServer.jks -file MyServer.cer

keytool -genkey -alias MyClient -keyalg RSA -validity 1825 -keystore MyClient.jks -storetype JKS -dname "CN=client.com,OU=Client Company,O=Client,L=CLient Location,ST=Client State,C=Client Country Short Code" -keypass password -storepass password
