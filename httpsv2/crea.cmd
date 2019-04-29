keytool -genkeypair -keyalg RSA -keysize 3072 -alias root-ca -dname "CN=ITT Root CA,OU=Development,O=ITT Marconi,C=DE" -ext BC:c=ca:true -ext KU=keyCertSign -validity 3650 -keystore ca.jks -storepass Marconi -keypass Marconi
keytool -exportcert -keystore ca.jks -storepass Marconi -alias root-ca -rfc -file ca.pem
keytool -genkeypair -keyalg RSA -keysize 3072 -alias localhost -dname "CN=localhost,OU=Development,O=ITT Marconi,C=DE" -ext BC:c=ca:false -ext EKU:c=serverAuth -ext "SAN:c=DNS:localhost,IP:127.0.0.1" -validity 3650 -keystore server.jks -storepass Marconi -keypass Marconi
keytool -certreq -keystore server.jks -storepass Marconi -alias localhost -keypass Marconi -file server.csr

keytool -gencert -keystore ca.jks -storepass Marconi -infile server.csr -alias root-ca -keypass Marconi -ext BC:c=ca:false -ext EKU:c=serverAuth -ext "SAN:c=DNS:localhost,IP:127.0.0.1" -validity 3650 -rfc -outfile server.pem

keytool -importcert -noprompt -keystore server.jks -storepass Marconi -alias root-ca -keypass Marconi -file ca.pem
keytool -importcert -noprompt -keystore server.jks -storepass Marconi -alias localhost -keypass Marconi -file server.pem
