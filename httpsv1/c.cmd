@echo off
set PWD=Marconi
set DNSNAME=%COMPUTERNAME%

echo create ca key
keytool -genkeypair -alias ca -keystore test.jks -keyalg RSA -validity 3650 -ext bc:critical=ca:true -dname "CN=CA" -storepass:env PWD -keypass:env PWD
echo generate cert request for ca signing
keytool -certreq -keystore test.jks -storepass:env PWD -alias ca -file ca.csr -ext bc:critical=ca:true
echo generate signed cert
keytool -gencert -keystore test.jks -storepass:env PWD -alias ca -infile ca.csr -outfile ca.cer -validity 3650 -ext bc:critical=ca:true
echo CA created. Import ca.cer in windows and firefox' certificate store as "Trusted CA".
pause

echo create server cert key for %DNSNAME%
keytool -genkeypair -alias leaf -keystore test.jks -keyalg RSA -validity 3650 -ext bc=ca:false -ext san=dns:%DNSNAME%,dns:localhost,ip:127.0.0.1 -dname "CN=Leaf" -storepass:env PWD -keypass:env PWD
echo generate cert request
keytool -certreq -keystore test.jks -storepass:env PWD -alias leaf -file leaf.csr -ext bc=ca:false -ext san=dns:%DNSNAME%,dns:localhost,ip:127.0.0.1
echo generate signed cert
keytool -gencert -keystore test.jks -storepass:env PWD -alias ca -infile leaf.csr -outfile leaf.cer -validity 3650 -ext bc=ca:false -ext san=dns:%DNSNAME%,dns:localhost,ip:127.0.0.1

rem see content
rem keytool -printcert -file leaf.cer -storepass:env PWD 

echo install in orig keystore
keytool -importcert -keystore test.jks -storepass:env PWD -file leaf.cer -alias leaf

echo content of test.jks:
keytool -list -v -storepass:env PWD -keystore test.jks
pause