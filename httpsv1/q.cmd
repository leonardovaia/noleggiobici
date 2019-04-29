keytool -export -alias localhost -file mydomain.der -keystore server.jks 
openssl x509 -inform der -in mydomain.der -out certificate.pem

keytool -importkeystore -srckeystore server.jks -destkeystore keystore.p12 -deststoretype PKCS12


openssl pkcs12 -in keystore.p12  -nodes -nocerts -out server.key

keytool -export -keystore server -alias localhost -file Example.cer


openssl x509 -outform der -in ca.pem -out ca.crt