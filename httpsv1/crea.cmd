@echo off
set PWD=Marconi
set DNSNAME=%COMPUTERNAME%

echo create ca key
keytool -genkeypair -alias ca -keystore server.jks -keyalg RSA -validity 3650 -ext bc:critical=ca:true -dname "CN=CA" -storepass:env PWD -keypass:env PWD
echo generate cert request for ca signing
keytool -certreq -keystore server.jks -storepass:env PWD -alias ca -file ca.csr -ext bc:critical=ca:true
echo generate signed cert
keytool -gencert -keystore server.jks -storepass:env PWD -alias ca -infile ca.csr -outfile ca.cer -validity 3650 -ext bc:critical=ca:true
echo CA created. Import ca.cer in windows and firefox' certificate store as "Trusted CA".
pause

echo create server cert key for %DNSNAME%
keytool -genkeypair -alias server -keystore server.jks -keyalg RSA -validity 3650 -ext bc=ca:false -ext san=dns:%DNSNAME%,dns:localhost,ip:127.0.0.1 -dname "CN=server" -storepass:env PWD -keypass:env PWD
echo generate cert request
keytool -certreq -keystore server.jks -storepass:env PWD -alias server -file server.csr -ext bc=ca:false -ext san=dns:%DNSNAME%,dns:localhost,ip:127.0.0.1
echo generate signed cert
keytool -gencert -keystore server.jks -storepass:env PWD -alias ca -infile server.csr -outfile server.cer -validity 3650 -ext bc=ca:false -ext san=dns:%DNSNAME%,dns:localhost,ip:127.0.0.1

rem see content
rem keytool -printcert -file server.cer -storepass:env PWD 

echo install in orig keystore
keytool -importcert -keystore server.jks -storepass:env PWD -file server.cer -alias server

echo content of server.jks:
keytool -list -v -storepass:env PWD -keystore server.jks
pause