keytool -genkeypair -keyalg RSA -keysize 3072 -alias localhost -dname "CN=localhost,OU=Development,O=My Organization,C=DE" -ext BC:c=ca:false -ext EKU:c=serverAuth -ext "SAN:c=DNS:localhost,IP:127.0.0.1" -validity 3650 -keystore server.jks -storepass Marconi5a -keypass Marconi5a
keytool -certreq -keystore server.jks -storepass Marconi5a -alias localhost -keypass Marconi5a -file server.csr

keytool -gencert -keystore ../root-ca/ca.jks -storepass Marconi5a -infile server.csr -alias root-ca -keypass Marconi5a -ext BC:c=ca:false -ext EKU:c=serverAuth -ext "SAN:c=DNS:localhost,IP:127.0.0.1" -validity 3650 -rfc -outfile server.pem

keytool -importcert -noprompt -keystore server.jks -storepass Marconi5a -alias root-ca -keypass Marconi5a -file ../root-ca/ca.pem
keytool -importcert -noprompt -keystore server.jks -storepass Marconi5a -alias localhost -keypass Marconi5a -file server.pem

pause